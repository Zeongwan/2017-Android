package com.example.myapplication3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 丁丁 on 2017/10/21 0021.
 */

public class MyViewholder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    public MyViewholder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }
    public static MyViewholder get(Context context, ViewGroup parent, int layoutId) {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            MyViewholder holder = new MyViewholder(context, itemView, parent);
            return holder;
        }
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T)view;
    }
}
