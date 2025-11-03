package com.training.core.models;

import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.List;

/*
 * Documentation for SLing Models
//https://sling.apache.org/documentation/bundles/models.html
*/

@Model(adaptables= SlingHttpServletRequest.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,resourceType = "sunnycloud/components/multifieldwithslingmodel")
@Exporter(name="jackson",extensions ="json")
@Getter
public class MultiFieldWithSlingModel {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Self
    SlingHttpServletRequest slingHttpServletRequest;

    @ValueMapValue
    private String[] states;

    @ChildResource
    List<CountrywithCurrencies> multicountryandcurrency;

    @RequestAttribute
    private String value1;

    @RequestAttribute
    private String value2;

    private String newData;

    @PostConstruct
    public void init(){
        logger.info("I am executing after the ValueMapValue and Request Attributes");
        String path = slingHttpServletRequest.getResource().getPath();
        logger.info("Current Resource Path: {}", path);
        if(value1!=null && value2!=null){
            newData= value1.concat(value2);
        }else{
            logger.warn("Request attributes value1 or value2 are null. Setting newData to empty");
            newData="";
        }
        logger.info("NewData from PostConstruct init Method::::::::::::::::"+newData);
        logger.info("Completed the init Method");

    }


}