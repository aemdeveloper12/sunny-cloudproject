package com.training.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

/*
 * Documentation for SLing Models
//https://sling.apache.org/documentation/bundles/models.html
*/

@Model(adaptables= Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MultiFieldWithSlingModel {
    @ValueMapValue
    private String[] states;

    @ChildResource
    List<CountrywithCurrencies> multicountryandcurrency;

    public String[] getStates() {
        return states;
    }

    public List<CountrywithCurrencies> getMulticountryandcurrency() {
        return multicountryandcurrency;
    }
}