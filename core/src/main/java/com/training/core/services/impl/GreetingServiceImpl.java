package com.training.core.services.impl;

import com.training.core.services.GreetingService;
import org.osgi.service.component.annotations.Component;

@Component(service = GreetingService.class)
public class GreetingServiceImpl implements GreetingService{
    @Override
    public String getGreeting(String name) {
        return "Hello "+ name + " Welcome to AEM osgi Services";
    }
}
