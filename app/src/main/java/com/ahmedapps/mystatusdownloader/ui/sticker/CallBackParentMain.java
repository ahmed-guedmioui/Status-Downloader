package com.ahmedapps.mystatusdownloader.ui.sticker;

import java.util.List;


public interface CallBackParentMain<C> {

    List<C> getChildList();
    boolean isInitiallyExpanded();
}