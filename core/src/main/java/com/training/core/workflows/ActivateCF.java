package com.training.core.workflows;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.Replicator;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Custom Workflow Process implementation used to activate/publish content from Author to Publish.
 * This class is registered as an OSGi service so it appears in the workflow step dropdown.
 */
@Component(property = {
        Constants.BUNDLE_NAME + "=Custom Workflow Process",
        Constants.SERVICE_DESCRIPTION + "=Custom workflow component to Activate Page",
        Constants.SERVICE_VENDOR + "=Sunny Cloud"
})
public class ActivateCF extends WorkflowHelper implements WorkflowProcess {

    // Injecting Replicator service to perform activation/publish
    @Reference
    Replicator replicator;

    /**
     * This method is executed when workflow reaches this step.
     */
    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
            throws WorkflowException {
        try {
            // Calls helper method to process payload and publish
            payloadPublishProcess(workItem, workflowSession, metaDataMap,
                    ReplicationActionType.ACTIVATE, replicator);

        } catch (Exception e) {
            throw new WorkflowException(e.getMessage(), e);
        }
    }
}
