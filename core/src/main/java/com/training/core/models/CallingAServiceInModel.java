package com.training.core.models;

import com.training.core.services.GetPathService;
import com.training.core.services.PaymentConfigs;
import com.training.core.services.ReadDummyJson;
import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import javax.annotation.PostConstruct;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Getter
public class CallingAServiceInModel {
    private String pathOfAsset;

    private String datafromUrl;

    private String paymentUrl;

    @OSGiService(filter = "(component.name=com.training.core.services.impl.GetPathServiceImpl1)")
    GetPathService getPathService;

    @OSGiService
    ReadDummyJson readDummyJson;

    @OSGiService
    PaymentConfigs pay;

    @PostConstruct
    public void init() throws  Exception{
        pathOfAsset = getPathService.getPathOfTheAsset();
        datafromUrl = readDummyJson.getDatafromDummyJsonApi();
        paymentUrl=pay.getPaymentUrl();
    }








}
