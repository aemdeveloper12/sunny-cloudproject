package com.training.core.models;

import com.adobe.cq.wcm.core.components.models.Title;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.via.ResourceSuperType;

@Model(
        adaptables = SlingHttpServletRequest.class,
        adapters = { CustomTitle.class },
        resourceType = "sunnycloud/components/customtitle",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = "jackson", extensions = "json")
public class CustomTitleImpl implements CustomTitle {

    @Self
    @Via(type = ResourceSuperType.class)
    private Title coreTitle; // Inject core Title model


    @ValueMapValue
    private String path; // Our custom dialog field

    @Override
    public String getText() {
        return coreTitle.getText();
    }

    @Override
    public String getLinkURL() {
        return coreTitle.getLinkURL();
    }

    @Override
    public String getPath() {
        return path;
    }
}
