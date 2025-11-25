package com.training.core.servlets;

import com.training.core.services.ReadDummyJson;
import com.training.core.utils.CommonUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Objects;

@Component(service = Servlet.class,
        property = { "sling.servlet.paths=/bin/practice",
        "sling.servlet.resourceTypes=/apps/sunnycloud/components/callaservletviaajax" ,
        "sling.servlet.methods=GET",
        "sling.servlet.extensions=txt" })
public class SaveDatainAEM extends SlingSafeMethodsServlet {

    @Reference
    ReadDummyJson readDummyJson;

    @Reference
    ResourceResolverFactory resolverFactory;

    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        //ResourceResolver resourceresolver = request.getResourceResolver();

        final Logger log = LoggerFactory.getLogger(getClass());
        try {

            ResourceResolver resourceresolver = CommonUtils.getWriteResolver(resolverFactory);
            log.info("User ID:::"+ resourceresolver.getUserID());

            // Getting the Data from 3rd Party APi via a Service
            String jsonfromService = readDummyJson.getDatafromDummyJsonApi();
            log.info("JSON::: " + jsonfromService);

            // Converting the String into Resource by using ResourceResolver
            Resource resource = resourceresolver.getResource("/content/sunnycloud/data");
            log.info("Resource Found? " + (resource != null));

            if (Objects.nonNull(resource)) {
                // This MVM is used to Update/remove any of the Properties in JCR in AEM
                ModifiableValueMap map = resource.adaptTo(ModifiableValueMap.class);
                log.info("MVM is null? ::: " + (map == null));

                // to add the Data to AEM, ADD the values in the for Key value Pair
                map.put("json", jsonfromService);
                resourceresolver.commit();
                resourceresolver.close();
                log.info("JSON Saved Successfully");
            }
        } catch (Exception e) {
            response.getWriter().write("Json Creation is Unsuccesful" + e.getMessage());
            e.printStackTrace();
        }
        response.setContentType("text/plain");
        response.getWriter().write("Json Creation is Successful");

    }

}
