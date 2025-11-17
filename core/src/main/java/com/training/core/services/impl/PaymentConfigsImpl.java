package com.training.core.services.impl;

import com.training.core.configurations.SampleOsgiConfigurations;
import com.training.core.services.PaymentConfigs;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = PaymentConfigs.class, immediate = true)
@Designate(ocd = SampleOsgiConfigurations.class)
public class PaymentConfigsImpl implements PaymentConfigs {

    private String url;
    private String datareadUrl;

    @Activate
    public void readConfig(SampleOsgiConfigurations sampleOsgiConfigurations)
    {
        url=sampleOsgiConfigurations.paymentUrl();
        datareadUrl=sampleOsgiConfigurations.jsonUrl();

    }

    public String getUrl() {
        return url;
    }

    public String getDatareadUrl() {
        return datareadUrl;
    }

    @Override
    public String getPaymentUrl() {
        return url;
    }

    @Override
    public String getJsonURL() {
        return datareadUrl;
    }
}
