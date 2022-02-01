package com.moworks.eqinfo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ScrollChildSwipeRefreshLayout  extends SwipeRefreshLayout {

    public View scrollUpChild = null;

    public ScrollChildSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean canChildScrollUp() {
        return scrollUpChild != null ? scrollUpChild.canScrollVertically(-1) : super.canChildScrollUp();
    }
}
