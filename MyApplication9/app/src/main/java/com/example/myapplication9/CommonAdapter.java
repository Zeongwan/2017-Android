package com.example.myapplication9;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 丁丁 on 2017/12/21 0021.
 */

public abstract class CommonAdapter<M> extends RecyclerView.Adapter<MyViewholder> {
    Context mContext;
    int mLayoutId;
    List<M> mdatas;
    private OnItemClickListener onItemClickListener;
    public CommonAdapter(Context context, int layoutId, List datas) {
        mContext = context;
        mLayoutId = layoutId;
        mdatas = datas;
    }
    @Override
    public MyViewholder onCreateViewHolder(final ViewGroup parent, int viewtype) {
        MyViewholder viewholder = MyViewholder.get(mContext, parent, mLayoutId);
        return viewholder;
    }
    public interface OnItemClickListener {
        void onClick(int position);
        void onLongClick(int position);
    }
    public void addData(M data) {
        mdatas.add(data);
        notifyItemInserted(mdatas.size() - 1);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public abstract void convert(MyViewholder myViewholder, M s);
    @Override
    public void onBindViewHolder(final MyViewholder myViewholder, int position) {
        convert(myViewholder, mdatas.get(position));
        if (onItemClickListener != null) {
            myViewholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(myViewholder.getAdapterPosition());
                }
            });
            myViewholder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onLongClick(myViewholder.getAdapterPosition());
                    return false;
                }
            });
        }
    }
    @Override
    public int getItemCount() {return mdatas.size();}
    public void removeItem(int position) {
        mdatas.remove(position);
        notifyItemRemoved(position);
    }
    // 移走所有元素
    public void removeAll() {
        mdatas.removeAll(mdatas);
        notifyDataSetChanged();
    }
    public M getItem(int position) {
        return mdatas.get(position);
    }
}
