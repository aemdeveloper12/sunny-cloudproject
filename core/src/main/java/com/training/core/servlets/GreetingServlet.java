package com.training.core.servlets;

import com.training.core.services.GreetingService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import java.io.IOException;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.paths=" + "/bin/greeting"
        }
)
public class GreetingServlet extends SlingSafeMethodsServlet {

    @Reference
    private GreetingService greetingService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException {

        String name = request.getParameter("name");
        if (name == null) {
            name = "Student";
        }

        String message = greetingService.getGreeting(name);

        JSONObject json = new JSONObject();
        json.put("greeting", message);

        response.setContentType("application/json");
        response.getWriter().write(json.toString());
    }
}
