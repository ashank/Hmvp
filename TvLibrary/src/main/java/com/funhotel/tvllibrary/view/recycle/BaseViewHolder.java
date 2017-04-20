package com.funhotel.tvllibrary.view.recycle;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhiyahan on 16/2/2.
 * ViewHodler的基类
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener,View.OnFocusChangeListener{

    private OnItemClickListner onItemClickListner;
    private OnItemLongClickListner onItemLongClickListner;
    private OnItemSelectListner onItemSelectListner;


    public BaseViewHolder(View itemView, OnItemClickListner onItemClickListner, OnItemLongClickListner onItemLongClickListner, OnItemSelectListner onItemSelectListner) {
        super(itemView);
        this.onItemClickListner=onItemClickListner;
        this.onItemLongClickListner=onItemLongClickListner;
        this.onItemSelectListner=onItemSelectListner;

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        itemView.setOnFocusChangeListener(this);
    }


    public BaseViewHolder(View itemView, OnItemClickListner onItemClickListner) {
        super(itemView);
        this.onItemClickListner=onItemClickListner;
        itemView.setOnClickListener(this);
    }


    public BaseViewHolder(View itemView, OnItemLongClickListner onItemLongClickListner) {
        super(itemView);
        this.onItemLongClickListner=onItemLongClickListner;
        itemView.setOnLongClickListener(this);
    }


    public BaseViewHolder(View itemView, OnItemSelectListner onItemSelectListner) {
        super(itemView);
        this.onItemSelectListner=onItemSelectListner;
        itemView.setFocusable(true);
        itemView.setOnFocusChangeListener(this);
    }


    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void onClick(View v) {

        if (null!=onItemClickListner){
            onItemClickListner.onItemClick(v,getPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (null!=onItemLongClickListner){
            onItemLongClickListner.onItemLongClick(v,getPosition());
        }
        return true;
    }

    
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            if (null!=onItemSelectListner){
                onItemSelectListner.onItemSelect(v,getPosition());
            }
        }
    }
}
