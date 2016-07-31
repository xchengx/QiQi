package com.uqi.qiqi.widget.alert;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uqi.qiqi.R;

import java.util.List;

/**
 * Created by Shuxin on 2016/7/31.
 */
public class ActionAdapter extends BaseAdapter {
    private Context xContext;
    private List<AlertItem> actions;
    public ActionAdapter(Context pContext,List<AlertItem> pActions){
        this.xContext = pContext;
        this.actions = pActions;
    }
    @Override
    public int getCount() {
        return actions.size();
    }

    @Override
    public Object getItem(int pI) {
        return actions.get(pI);
    }

    @Override
    public long getItemId(int pI) {
        return pI;
    }

    @Override
    public View getView(int pI, View pView, ViewGroup pViewGroup) {
        ViewHolder lViewHolder;
        if (pView == null){
            pView = LayoutInflater.from(xContext).inflate(R.layout.item_action_sheet_lay,null,false);
            lViewHolder = new ViewHolder();
            lViewHolder.actionName = (TextView) pView.findViewById(R.id.actionName);
            pView.setTag(lViewHolder);
        }else {
            lViewHolder = (ViewHolder) pView.getTag();
        }
        AlertItem item = actions.get(pI);
        lViewHolder.actionName.setText(item.getContent());
        lViewHolder.actionName.setTextColor(item.getColor());
        if(item.isBold())
            lViewHolder.actionName.setTypeface(Typeface.DEFAULT_BOLD);
        return pView;
    }
    static class ViewHolder {
        public TextView actionName;
    }
}
