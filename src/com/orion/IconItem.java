package com.orion;

import com.sun.istack.internal.NotNull;

public class IconItem {

    private final Integer itemId;

    private final String icon;

    public IconItem(Integer itemId, String icon) {
        this.itemId = itemId;
        this.icon = icon;
    }

    public Integer getItemId() {
        return itemId;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "DELETE FROM `data_itemdisplayinfo` WHERE `id` = " + itemId + ";\n" +
               "INSERT INTO `data_itemdisplayinfo` (`id`, `iconname`) VALUES (" + itemId + ", \"" + icon + "\");\n\n";
    }
}
