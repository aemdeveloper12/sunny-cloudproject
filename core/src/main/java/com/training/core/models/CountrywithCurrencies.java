package com.training.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CountrywithCurrencies {

    @ValueMapValue
    private String country;

    @ValueMapValue
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public String getCountry() {
        return country;
    }
}
