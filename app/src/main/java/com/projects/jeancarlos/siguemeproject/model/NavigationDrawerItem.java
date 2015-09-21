package com.projects.jeancarlos.siguemeproject.model;

/**
 * Created by JEANCARLOS on 20/09/2015.
 */
public class NavigationDrawerItem {
    private String titleIcon;

    public NavigationDrawerItem() {
    }

    public NavigationDrawerItem(String titleIcon) {
        this.titleIcon = titleIcon;
    }

    public String getTitleIcon() {
        return titleIcon;
    }

    public void setTitleIcon(String titleIcon) {
        this.titleIcon = titleIcon;
    }
}
