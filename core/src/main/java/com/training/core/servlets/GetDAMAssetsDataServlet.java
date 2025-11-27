package com.training.core.servlets;

import com.day.cq.dam.api.Asset;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

/**
 * Servlet to fetch DAM Assets under /content/dam/sunnycloud and return details as JSON.
 */
@Component(
        service = Servlet.class,
        property = {
                // The URL you will call → http://localhost:4502/bin/getassets.json
                "sling.servlet.paths=/bin/getassets",

                // This servlet accepts only GET requests
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,

                // Output format
                "sling.servlet.extensions=json"
        }
)
public class GetDAMAssetsDataServlet extends SlingSafeMethodsServlet {

        // Inject the QueryBuilder service to run AEM searches
        @Reference
        QueryBuilder queryBuilder;

        @Override
        protected void doGet(SlingHttpServletRequest request,
                             SlingHttpServletResponse response)
                throws ServletException, IOException {

                // Set response type to JSON
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                // Get resourceResolver from the request (logged-in user context)
                ResourceResolver resourceResolver = request.getResourceResolver();

                // Get a JCR session for the query builder
                Session session = resourceResolver.adaptTo(Session.class);

                // Final list that will hold each asset’s details
                List<Map<String, Object>> assetsList = new ArrayList<>();

                try {
                        /**
                         * STEP 1: Build Query Parameters for QueryBuilder
                         * -----------------------------------------------
                         * path       → Search inside this folder
                         * type       → Search only dam:Asset nodes
                         * p.limit    → -1 means fetch ALL results
                         */
                        Map<String, String> queryMap = new HashMap<>();
                        queryMap.put("path", "/content/dam/sunnycloud");
                        queryMap.put("type", "dam:Asset");
                        queryMap.put("p.limit", "-1");

                        // Convert queryMap into QueryBuilder predicates
                        Query query = queryBuilder.createQuery(PredicateGroup.create(queryMap), session);

                        // Execute the query and get results
                        SearchResult result = query.getResult();

                        /**
                         * STEP 2: Loop through each asset returned by QueryBuilder
                         */
                        for (Hit hit : result.getHits()) {

                                // Get absolute JCR path of the asset
                                String assetPath = hit.getPath();

                                // Resolve the resource (convert JCR path → Sling Resource)
                                Resource assetRes = resourceResolver.getResource(assetPath);

                                if (assetRes != null) {

                                        // Convert resource → Asset API object
                                        Asset asset = assetRes.adaptTo(Asset.class);

                                        if (asset != null) {

                                                /**
                                                 * STEP 3: Extract useful metadata from the asset
                                                 * ------------------------------------------------
                                                 * name          → asset file name
                                                 * title         → dc:title metadata (if exists)
                                                 * description   → dc:description metadata (if exists)
                                                 */
                                                Map<String, Object> assetInfo = new HashMap<>();
                                                assetInfo.put("path", assetPath);
                                                assetInfo.put("name", asset.getName());
                                                assetInfo.put("title",
                                                        StringUtils.defaultString(asset.getMetadataValueFromJcr("dc:title")));
                                                assetInfo.put("description",
                                                        StringUtils.defaultString(asset.getMetadataValueFromJcr("dc:description")));

                                                // Add one asset’s info to final list
                                                assetsList.add(assetInfo);
                                        }
                                }
                        }

                        /**
                         * STEP 4: Convert List to JSON and send as response
                         */
                        String json = new ObjectMapper().writeValueAsString(assetsList);
                        response.getWriter().write(json);

                } catch (Exception e) {

                        // Convert any exception into a servlet exception
                        throw new ServletException(e);

                } finally {

                        /**
                         * IMPORTANT:
                         * Always close the JCR session to prevent memory leaks.
                         */
                        if (session != null) {
                                session.logout();
                        }
                }
        }
}
