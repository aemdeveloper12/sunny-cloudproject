package com.training.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.Servlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.methods=" + HttpConstants.METHOD_POST,
                "sling.servlet.paths=/bin/storeUser"
        }
)
public class StoreUserServlet extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(StoreUserServlet.class);

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String address = request.getParameter("address");
        String age = request.getParameter("age");

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ResourceResolverFactory.SUBSERVICE, "writeService");

        try (ResourceResolver resolver =
                     resolverFactory.getServiceResourceResolver(paramMap)) {

            Session session = resolver.adaptTo(Session.class);

            if (session == null) {
                response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(
                        "{ \"status\": \"error\", \"message\": \"Unable to obtain JCR session\" }"
                );
                return;
            }

            // Only check existence — do NOT create
            if (!session.nodeExists("/content/userdata")) {
                response.setStatus(SlingHttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(
                        "{ \"status\": \"error\", \"message\": \"/content/userdata node does not exist\" }"
                );
                return;
            }

            Node rootNode = session.getNode("/content/userdata");

            Node userNode = rootNode.addNode(
                    firstName + "-" + System.currentTimeMillis(),
                    "nt:unstructured"
            );

            userNode.setProperty("firstName", firstName);
            userNode.setProperty("lastName", lastName);
            userNode.setProperty("address", address);
            userNode.setProperty("age", age);

            session.save();

            response.setStatus(SlingHttpServletResponse.SC_OK);
            response.getWriter().write(
                    "{ \"status\": \"success\", \"message\": \"User data stored successfully\" }"
            );

        } catch (Exception e) {
            LOG.error("Error while storing user data", e);
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                    "{ \"status\": \"error\", \"message\": \"Internal server error\" }"
            );
        }
    }
}
