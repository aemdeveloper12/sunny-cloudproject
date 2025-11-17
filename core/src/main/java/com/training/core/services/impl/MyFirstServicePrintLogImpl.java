package com.training.core.services.impl;

import com.training.core.services.GetPathService;
import com.training.core.services.MyFirstServicePrintLog;
import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = MyFirstServicePrintLog.class, immediate = true)
public class MyFirstServicePrintLogImpl implements MyFirstServicePrintLog{

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Reference(target = "(component.name=com.training.core.services.impl.GetPathServiceImpl1)")
    GetPathService getPathService;


    @Override
    public String PrintLog() {
        logger.info("I am from MyFirstPrintLog Service");
        return getPathService.getPathOfTheAsset();
    }

    @Activate
    public void Activated(){
        logger.info("Service is Activated");
    }

    @Deactivate
    public void DeActivated(){
        logger.info("Service is De-Activated");
    }

    @Modified // OSGI Configs
    public void ModifiedMethod(){
        logger.info("Service is Modifield");
    }
}
