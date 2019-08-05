package com.example.demo.springsecurity.wdtest.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class TMenu implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer menuId;

    private Integer parentId;

    private String path;

    private String icon;

    private String name;

    private String component;

    private String redirect;

    private String hidechildreninmenu;

    private String hideinmenu;

    private Integer order;

    private static final long serialVersionUID = 1L;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component == null ? null : component.trim();
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect == null ? null : redirect.trim();
    }

    public String getHidechildreninmenu() {
        return hidechildreninmenu;
    }

    public void setHidechildreninmenu(String hidechildreninmenu) {
        this.hidechildreninmenu = hidechildreninmenu == null ? null : hidechildreninmenu.trim();
    }

    public String getHideinmenu() {
        return hideinmenu;
    }

    public void setHideinmenu(String hideinmenu) {
        this.hideinmenu = hideinmenu == null ? null : hideinmenu.trim();
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(menuId);
        sb.append(", parentId=").append(parentId);
        sb.append(", path=").append(path);
        sb.append(", icon=").append(icon);
        sb.append(", name=").append(name);
        sb.append(", component=").append(component);
        sb.append(", redirect=").append(redirect);
        sb.append(", hidechildreninmenu=").append(hidechildreninmenu);
        sb.append(", hideinmenu=").append(hideinmenu);
        sb.append(", order=").append(order);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}