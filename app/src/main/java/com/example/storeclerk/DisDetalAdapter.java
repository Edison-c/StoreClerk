package com.example.storeclerk;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import java.util.List;


public class DisDetalAdapter extends SimpleAdapter {
    Context context;
    private int index ;
    private List<DisbursementDetail> data;


    public DisDetalAdapter(Context context, List<DisbursementDetail> data,
                           int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.data = data;
        // TODO Auto-generated constructor stub
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;
        final int p = position;

        View v = super.getView(position, convertView, parent);
        if(convertView==null) {
            viewHolder = new ViewHolder();
            viewHolder.text1 = (EditText) v.findViewById(R.id.detail_actual);

            viewHolder.text2 = (EditText) v.findViewById(R.id.detail_remark);

            v.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)v.getTag();
        }


        final DisbursementDetail itemObj = data.get(position);
        if (viewHolder.text1.getTag() instanceof TextWatcher) {
            viewHolder.text1.removeTextChangedListener((TextWatcher) viewHolder.text1.getTag());
        }

        if (viewHolder.text2.getTag() instanceof TextWatcher) {
            viewHolder.text2.removeTextChangedListener((TextWatcher) viewHolder.text2.getTag());
        }
        viewHolder.text1.setText(itemObj.get("actual").toString());
        viewHolder.text2.setText(itemObj.get("remark").toString());

        TextWatcher watcher1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    itemObj.replace("actual",itemObj.get("actual"),"");

                } else {
                    itemObj.replace("actual",itemObj.get("actual").toString(),s.toString());
                }
            }
        };


        TextWatcher watcher2 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    itemObj.replace("remark",itemObj.get("remark"),"");

                } else {
                    itemObj.replace("remark",itemObj.get("remark").toString(),s.toString());
                }
            }
        };

        viewHolder.text1.addTextChangedListener(watcher1);
        viewHolder.text1.setTag(watcher1);

        viewHolder.text2.addTextChangedListener(watcher2);
        viewHolder.text2.setTag(watcher2);
        return v;
    }

    public List<DisbursementDetail>dis_data(){
        return data;
    }

    private static final class ViewHolder {
        EditText text1;
        EditText text2;
    }
}
