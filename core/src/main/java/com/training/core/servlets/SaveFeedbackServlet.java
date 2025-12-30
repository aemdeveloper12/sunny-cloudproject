package com.training.core.servlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.training.core.utils.CommonUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.resourceTypes=sunnycloud/components/feedback",
                "sling.servlet.methods=POST",
                "sling.servlet.extensions=json"
        }
)
public class SaveFeedbackServlet extends SlingAllMethodsServlet {

    private static final Logger log =
            LoggerFactory.getLogger(SaveFeedbackServlet.class);

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Override
    protected void doPost(SlingHttpServletRequest request,
                          SlingHttpServletResponse response)
            throws IOException {

        ResourceResolver resolver = null;

        try {
            // Get service resolver (write access)
            resolver = CommonUtils.getWriteResolver(resolverFactory);

            // Parse JSON body
            JsonObject json =
                    JsonParser.parseReader(request.getReader()).getAsJsonObject();

            String comment = json.get("comment").getAsString();
            String userAgent = request.getHeader("User-Agent");

            // Parent path
            Resource parent =
                    resolver.getResource("/content/sunnycloud/feedback");

            if (parent == null) {
                response.setStatus(500);
                response.getWriter().write("{\"status\":\"Parent path not found\"}");
                return;
            }

            Map<String, Object> props = new HashMap<>();
            props.put("comment", comment);
            props.put("timestamp", Instant.now().toString());
            props.put("userAgent", userAgent);

            Resource feedback =
                    resolver.create(
                            parent,
                            "feedback-" + System.currentTimeMillis(),
                            props
                    );

            resolver.commit();

            log.info("Feedback saved at {}", feedback.getPath());

            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"success\"}");

        } catch (Exception e) {
            log.error("Error saving feedback", e);
            response.setStatus(500);
            response.getWriter().write("{\"status\":\"failed\"}");
        } finally {
            if (resolver != null && resolver.isLive()) {
                resolver.close();
            }
        }
    }
}
