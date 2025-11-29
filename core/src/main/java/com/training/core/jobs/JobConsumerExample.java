package com.training.core.jobs;

import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = JobConsumer.class,
        immediate = true,
        property = {
                Constants.SERVICE_DESCRIPTION+ "=JOB CONSUMER EXAMPLE",
                JobConsumer.PROPERTY_TOPICS + "=practice/job"
        }
)
public class JobConsumerExample implements JobConsumer {
    private  final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public JobResult process(Job job) {
        try{
            logger.info("JobConsumer Example JOB Called *******");
            String test = (String)job.getProperty("data");
            String path = (String)job.getProperty("path");
            return JobConsumer.JobResult.OK;
        } catch (Exception e){
            logger.error("Exception", e);
            return JobResult.FAILED;
        }
    }
}
