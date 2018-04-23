package com.hilfritz.mvp.ui.psi.helper;

/**
 * @author Hilfritz Camallere on 22/4/18
 */
public class ReadingsContainer {
    String regionName;
    String snippet = "";


    public ReadingsContainer(String regionName, String snippet) {
        this.regionName = regionName;
        this.snippet = snippet;
    }
}
