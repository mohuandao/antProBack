package com.example.demo.springsecurity.wdtest.model;

import java.util.List;

public class MenuPo {
    private String path;
    private String component;
    private List<Object> routes;



    public List<Object> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Object> routes) {
        this.routes = routes;
    }



    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }


}
