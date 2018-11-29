package com.example.kit.Event;

import com.example.kit.Model.FilterItemModel;

/**
 * Created by davidm on 11/20/17.
 */

public interface FilterCallback {
    void onFilterSelect(FilterItemModel filter);
    void onFilterDeselect(FilterItemModel filter);
}
