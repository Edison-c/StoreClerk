package com.example.storeclerk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Groupname> expandableListTitle;
    private HashMap<Groupname, List<Groupname>> expandableListDetail;

    public CustomExpandableListAdapter(Context context, List<Groupname> expandableListTitle,
                                       HashMap<Groupname, List<Groupname>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(final int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHold viewHolder = null;
        final Groupname expandedListText = (Groupname) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            viewHolder = new ViewHold();
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            viewHolder.ret_actual = convertView.findViewById(R.id.expandedListItem4);
            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHold)convertView.getTag();
        }

        if (viewHolder.ret_actual.getTag() instanceof TextWatcher) {
            viewHolder.ret_actual.removeTextChangedListener((TextWatcher) (viewHolder.ret_actual.getTag()));
        }
        viewHolder.ret_actual.setText(expandedListText.getActual());

        TextWatcher paramsWatcher = new SimpeTextWath(viewHolder.ret_actual) {

            @Override
            public void  afterTextChanged(Editable s) {

                if (TextUtils.isEmpty(s)) {
                    expandedListText.setActual("");
                } else {
                    expandedListText.setActual(s.toString());
                }
            }
        };
        viewHolder.ret_actual.addTextChangedListener(paramsWatcher);
        viewHolder.ret_actual.setTag(paramsWatcher);

        TextView expandedListTextView_1 = convertView
                .findViewById(R.id.expandedListItem_1);
        expandedListTextView_1.setText(expandedListText.getDesc());


        TextView expandedListTextView_2 = convertView
                .findViewById(R.id.expandedListItem_2);
        expandedListTextView_2.setText(expandedListText.getDept());

        TextView expandedListTextView_3 = convertView
                .findViewById(R.id.expandedListItem_3);
        expandedListTextView_3.setText(expandedListText.getNeeded());

        TextView expandedListTextView_4 = convertView
                .findViewById(R.id.expandedListItem_4);
        expandedListTextView_4.setText(expandedListText.getNeeded());

        TextView expandedListTextView = convertView
                .findViewById(R.id.expandedListItem1);
        expandedListTextView.setText(expandedListText.getDesc());


        TextView expandedListTextView2 = convertView
                .findViewById(R.id.expandedListItem2);
        expandedListTextView2.setText(expandedListText.getDept());

        TextView expandedListTextView3 = convertView
                .findViewById(R.id.expandedListItem3);
        expandedListTextView3.setText(expandedListText.getNeeded());

//        EditText expandedListTextView4= convertView.findViewById(R.id.expandedListItem4);
//        expandedListTextView4.setText(expandedListText.getActual());
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        Groupname listTitle = (Groupname) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listdesc);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle.getDesc());

        TextView listTitleTextView1 = (TextView) convertView
                .findViewById(R.id.listdept);
        listTitleTextView1.setTypeface(null, Typeface.BOLD);
        listTitleTextView1.setText(listTitle.getDept());

        TextView listTitleTextView2 = (TextView) convertView
                .findViewById(R.id.listneeded);
        listTitleTextView2.setTypeface(null, Typeface.BOLD);
        listTitleTextView2.setText(listTitle.getNeeded());

        TextView listTitleTextView3 = (TextView) convertView
                .findViewById(R.id.listactul);
        listTitleTextView3.setTypeface(null, Typeface.BOLD);
        listTitleTextView3.setText(listTitle.getActual());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }


    public abstract class SimpeTextWath implements TextWatcher {
        public EditText myEditText;

        public SimpeTextWath(EditText myEditText) {
            super();
            this.myEditText = myEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }

    public List<Groupname> ret_data(){
        List<Groupname> post_ret =new ArrayList<>();
        for (List<Groupname> value : expandableListDetail.values()) {
           post_ret.addAll(value);
        }
        return post_ret;
    }

    class ViewHold {
        EditText ret_actual;
    }

}

