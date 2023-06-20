package com.example.myapptrenlop.models;

import com.example.myapptrenlop.utils.Constant;

public class Filter {
    private String filterName;

    private String activeColorString;

    private String inActiveColorString;

    private boolean isSelected;

    public Filter(String filterName) {
        this.filterName = filterName;
        this.activeColorString = Constant.ACTIVE_FILTER_COLOR_STRING;
        this.inActiveColorString = Constant.INACTIVE_FILTER_COLOR_STRING;
        this.isSelected = false;
    }

    public Filter(String filterName, boolean isSelected) {
        this.filterName = filterName;
        this.isSelected = isSelected;
        this.activeColorString = Constant.ACTIVE_FILTER_COLOR_STRING;
        this.inActiveColorString = Constant.INACTIVE_FILTER_COLOR_STRING;
    }

    public Filter(String filterName, String activeColorString, String inActiveColorString, boolean isSelected) {
        this.filterName = filterName;
        this.activeColorString = activeColorString;
        this.inActiveColorString = inActiveColorString;
        this.isSelected = isSelected;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getActiveColorString() {
        return activeColorString;
    }

    public void setActiveColorString(String activeColorString) {
        this.activeColorString = activeColorString;
    }

    public String getInActiveColorString() {
        return inActiveColorString;
    }

    public void setInActiveColorString(String inActiveColorString) {
        this.inActiveColorString = inActiveColorString;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
