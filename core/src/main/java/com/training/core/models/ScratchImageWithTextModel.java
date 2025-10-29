package com.training.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Required;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ScratchImageWithTextModel {

    @ValueMapValue(name = "title")
    @Required
    private String name;

    @ValueMapValue
    private String richtext;

    @ValueMapValue
    private String path;

    @ValueMapValue
    private String toplefttext;

    @ValueMapValue
    private String bottomlefttext;

    @ValueMapValue
    private String centeredtext;

    @ValueMapValue
    private String toprighttext;

    @ValueMapValue
    private String bottomrighttext;

    public String getName() { return name; }
    public String getRichtext() { return richtext; }
    public String getPath() { return path; }
    public String getToplefttext() { return toplefttext; }
    public String getBottomlefttext() { return bottomlefttext; }
    public String getToprighttext() { return toprighttext; }
    public String getCenteredtext() { return centeredtext; }
    public String getBottomrighttext() { return bottomrighttext; }
}
