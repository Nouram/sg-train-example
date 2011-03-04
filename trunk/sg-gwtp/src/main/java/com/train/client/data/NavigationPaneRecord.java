package com.train.client.data;

import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.train.client.ContextAreaFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Hu Jing Ling
 * Date: 3/1/11
 * Time: 7:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class NavigationPaneRecord extends ListGridRecord {
    public NavigationPaneRecord() {
    }

    public NavigationPaneRecord(String icon, String name, ContextAreaFactory factory,
                                CellDoubleClickHandler clickHandler) {
        setIcon(icon);
        setName(name);
        setFactory(factory);
        setDoubleClickHandler(clickHandler);
    }

    public void setIcon(String appIcon) {
        setAttribute("icon", appIcon);
    }

    public void setName(String appName) {
        setAttribute("name", appName);
    }

    public void setFactory(ContextAreaFactory factory) {
        setAttribute("factory", factory);
    }

    public void setDoubleClickHandler(CellDoubleClickHandler clickHandler) {
        setAttribute("clickHandler", clickHandler);
    }

    public String getIcon() {
        return getAttributeAsString("icon");
    }

    public String getName() {
        return getAttributeAsString("name");
    }

    public ContextAreaFactory getFactory() {
        return (ContextAreaFactory) getAttributeAsObject("factory");
    }

    public CellDoubleClickHandler getDoubleClickHandler() {
        return (CellDoubleClickHandler) getAttributeAsObject("clickHandler");
    }

}
