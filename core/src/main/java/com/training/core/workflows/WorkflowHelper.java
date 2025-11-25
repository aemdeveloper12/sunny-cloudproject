package com.training.core.workflows;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.ArrayList;
import java.util.List;

public class WorkflowHelper {

    private static final Logger log = LoggerFactory.getLogger(WorkflowHelper.class);

    public void payloadPublishProcess(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap,
                                      ReplicationActionType actionType, Replicator replicator) throws WorkflowException {
        try {

            log.info("Custom Workflow Helper PayloadProcess - Start");

            WorkflowData workflowData = workItem.getWorkflowData();

            //Getting payload from Workflow
            String payloadType = workflowData.getPayloadType();

            Session jcrSession = workflowSession.adaptTo(Session.class);
            // Check type of payload; there are two - JCR_PATH and JCR_UUID
            if (StringUtils.equals(payloadType, "JCR_PATH")) {
                log.info("Payload type: {}", payloadType);


                // Get the JCR path from the payload
                String path = workItem.getWorkflowData().getPayload().toString();
                log.info("Payload path: {}", path);
            }

            List<String> paths = new ArrayList<String>();
            // getPayloads(workItem,workflowSession,rcManager);
            paths.add("/content/sunnycloud/us/en/demo");
            for (String payloadPath : paths) {
                log.debug("Custom Workflow Helper PayloadProcess : Payload and Action {}  {}", payloadPath,
                        actionType);
                replicate(jcrSession, payloadPath, actionType, replicator);
            }
            log.info("Custom Workflow Helper PayloadProcess - End");
        } catch (Exception e) {
            throw new WorkflowException(e.getMessage(), e);
        }

    }

    public void replicate(Session jcrSession, String payLoadPath, ReplicationActionType actionType,
                          Replicator replicator) {
        log.info("Custom Workflow Helper replicate - Start");
        try {
            replicator.replicate(jcrSession, actionType, payLoadPath);
            log.info("Custom Workflow Helper replicate - END");
        } catch (ReplicationException e) {
            log.error("Custom Workflow Helper replication error {} \n Agents used {}", e.getMessage());
        }
        log.debug("Custom Workflow Helper replicate - End");
    }
}
