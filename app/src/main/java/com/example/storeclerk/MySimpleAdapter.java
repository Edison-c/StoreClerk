package com.example.storeclerk;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MySimpleAdapter extends SimpleAdapter{

    Context context;

    private List<AdjustmentItem> data;

    public MySimpleAdapter(Context context, List<AdjustmentItem> data,
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
            viewHolder.btn = (Button) v.findViewById(R.id.adj_delete);
            viewHolder.reason = v.findViewById((R.id.adj_reason));
            viewHolder.item = v.findViewById((R.id.adj_item));
            viewHolder.quantity = v.findViewById((R.id.adj_quantity));

            v.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)v.getTag();
        }
        final AdjustmentItem itemObj = data.get(position);


        if (viewHolder.reason.getTag() instanceof TextWatcher) {
            viewHolder.reason.removeTextChangedListener((TextWatcher) (viewHolder.reason.getTag()));
        }
        viewHolder.reason.setText(itemObj.get("Reason").toString());

        if (viewHolder.quantity.getTag() instanceof TextWatcher) {
            viewHolder.quantity.removeTextChangedListener((TextWatcher) (viewHolder.quantity.getTag()));
        }
        viewHolder.quantity.setText(itemObj.get("QuantityAdjusted").toString());

        if (viewHolder.item.getTag() instanceof TextWatcher) {
            viewHolder.item.removeTextChangedListener((TextWatcher) (viewHolder.item.getTag()));
        }
        viewHolder.item.setText(itemObj.get("ItemNumber").toString());

        TextWatcher paramsWatcher2 = new SimpeTextWather(viewHolder.reason) {

            @Override
            public void  afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    itemObj.remove("Reason");
                    itemObj.put("Reason","");
                } else {
                    itemObj.remove("Reason");
                    itemObj.put("Reason",s.toString());
                }

            }
        };

        viewHolder.reason.addTextChangedListener(paramsWatcher2);

        TextWatcher paramsWatcher = new SimpeTextWather(viewHolder.quantity) {

            @Override
            public void  afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    itemObj.remove("QuantityAdjusted");
                    itemObj.put("QuantityAdjusted","");
                } else {
                    itemObj.remove("QuantityAdjusted");
                    itemObj.put("QuantityAdjusted",s.toString());
                }

            }
        };

        viewHolder.quantity.addTextChangedListener(paramsWatcher);


        TextWatcher paramsWatcher1 = new SimpeTextWather(viewHolder.item) {

            @Override
            public void  afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    itemObj.remove("ItemNumber");
                    itemObj.put("ItemNumber","");
                } else {
                    itemObj.remove("ItemNumber");
                    itemObj.put("ItemNumber",s.toString());
                }

            }
        };

        viewHolder.item.addTextChangedListener(paramsWatcher1);

        viewHolder.btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    mOnItemDeleteListener.onDeleteClick(p);
                }
            });

        return v;
    }

    public interface onItemDeleteListener {
        void onDeleteClick(int i);
    }

    private onItemDeleteListener mOnItemDeleteListener;

    public void setOnItemDeleteClickListener(onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }

    class ViewHolder {
        Button btn;
        EditText reason;
        EditText item;
        EditText quantity;
    }

    public List<AdjustmentItem> adj_data(){
        return data;
    }


    public abstract class SimpeTextWather implements TextWatcher {
        public EditText mEditText;

        public SimpeTextWather(EditText mEditText) {
            super();
            this.mEditText = mEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }
}

