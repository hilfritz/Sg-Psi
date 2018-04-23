
package com.hilfritz.mvp.api.psi.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hilfritz.mvp.ui.psi.helper.ReadingsContainer;

public class RegionMetadatum {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("label_location")
    @Expose
    private LabelLocation labelLocation;

    String mapSnippet = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LabelLocation getLabelLocation() {
        return labelLocation;
    }

    public void setLabelLocation(LabelLocation labelLocation) {
        this.labelLocation = labelLocation;
    }

    public String getMapSnippet() {
        return mapSnippet;
    }

    public void setMapSnippet(String mapSnippet) {
        this.mapSnippet = mapSnippet;
    }
}
