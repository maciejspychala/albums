package com.mcksp.albums.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.mcksp.albums.helpers.WidgetHelper;

public class AutoSpanRecyclerView extends RecyclerView {
    GridLayoutManager manager;

    public AutoSpanRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public AutoSpanRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutoSpanRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        int columnWidth = (int) WidgetHelper.dpToPixel(160, getContext());
        int spanCount = Math.max(1, getMeasuredWidth() / columnWidth);
        manager.setSpanCount(spanCount);
    }

    private void init(Context context) {
        manager = new GridLayoutManager(context, 1);
        setLayoutManager(manager);
    }
}
