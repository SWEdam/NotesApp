package com.snope.notes_app.assets.data_managers;

import com.snope.notes_app.assets.data_managers.enums.SortOptions;
import com.snope.notes_app.assets.data_managers.enums.SortOrders;

public class Settings {

    private SortOptions sortOption = SortOptions.DATE_CREATED;
    private SortOrders sortOrder = SortOrders.DESCENDING;

    public Settings() {}

    public Settings(SortOptions sortOption, SortOrders sortOrder) {
        this.sortOption = sortOption;
        this.sortOrder = sortOrder;
    }

    public SortOptions getSort_option() { return sortOption; }
    public void setSort_option(SortOptions sortOption) { this.sortOption = sortOption; }

    public SortOrders getSort_order() { return sortOrder; }
    public void setSort_order(SortOrders sortOrder) { this.sortOrder = sortOrder; }

}
