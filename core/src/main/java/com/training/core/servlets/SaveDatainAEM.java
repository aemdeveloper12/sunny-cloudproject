package com.training.core.servlets;

import com.training.core.services.ReadDummyJson;
import com.training.core.utils.CommonUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Objects;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.resourceTypes=sunnycloud/components/callaservletviaajax",
                "sling.servlet.methods=POST",
                "sling.servlet.extensions=json"
        }
)
public class SaveDatainAEM extends SlingAllMethodsServlet {

    private static final Logger log = LoggerFactory.getLogger(SaveDatainAEM.class);

    @Reference
    ReadDummyJson readDummyJson;

    @Reference
    ResourceResolverFactory resolverFactory;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        ResourceResolver resolver = null;

        try {
            // Get Service User Resolver
            resolver = CommonUtils.getWriteResolver(resolverFactory);
            log.info("Service User ID ::: {}", resolver.getUserID());

            // Get data from 3rd-party API
            String jsonFromService = readDummyJson.getDatafromDummyJsonApi();
            log.info("JSON from API ::: {}", jsonFromService);

            // Get target resource
            Resource resource = resolver.getResource("/content/sunnycloud/data");

            if (Objects.nonNull(resource)) {
                ModifiableValueMap mvm = resource.adaptTo(ModifiableValueMap.class);

                if (Objects.nonNull(mvm)) {
                    mvm.put("json", jsonFromService);
                    resolver.commit();
                    log.info("JSON saved successfully in JCR");
                }
            }

            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"success\"}");

        } catch (Exception e) {
            log.error("Error while saving JSON", e);
            response.setStatus(500);
            response.getWriter().write("{\"status\":\"failed\"}");
        } finally {
            if (resolver != null && resolver.isLive()) {
                resolver.close();
            }
        }
    }
}