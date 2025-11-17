package com.training.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.training.core.services.MyFirstServicePrintLog;
import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = SampleInterface.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Getter
public class SampleModelWithAdapters implements SampleInterface {

    @ValueMapValue
    private String sampleText;

    @ValueMapValue
    private String sampleTextArea;

    @ValueMapValue
    private String path;

    @Self
    SlingHttpServletRequest slingHttpServletRequest;

    @SlingObject
    private Resource currentResource;

    @SlingObject
    private ResourceResolver resourceResolver;

    @OSGiService // @Inject
    MyFirstServicePrintLog myFirstServicePrintLog;

    private String pageTitleViaSling;
    private  String pageTitleViaAEM;

    private String stringFromService;

    @PostConstruct
    public void init(){
        PageManager pageManager= resourceResolver.adaptTo(PageManager.class);
        if(pageManager!=null){
            Page currentPage = pageManager.getContainingPage(currentResource);

            if(currentPage!=null){
                //By Using Sling ValueMap(reads directly from jcr:content)
                Resource jcrContent = currentPage.getContentResource();
                if(jcrContent!=null){
                    ValueMap vm = jcrContent.adaptTo(ValueMap.class);
                    pageTitleViaSling = vm.get("jcr:title", String.class);
                }

                // By Using Page API
                pageTitleViaAEM= currentPage.getTitle();

                // Assigning via Sling Model
                stringFromService = myFirstServicePrintLog.PrintLog();

            }
        }


    }



}
