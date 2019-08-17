package com.example.storeclerk;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.ListFragment;
import java.util.ArrayList;
import java.util.List;

public class AdjustmentFragment extends ListFragment implements View.OnClickListener {
    AdjustmentFragment.resultInterface1 callback;

    public AdjustmentFragment() {
    }
    private MySimpleAdapter listItemAdapter;
    private Button add_new;
    private Button adj_done;
    private List<AdjustmentItem> adjustmentitems = null;
    private View v;
    private AdjustmentItem blank;
    private List<AdjustmentItem> postdata;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.frag_adjustment, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            adjustmentitems = (ArrayList<AdjustmentItem>)bundle.getSerializable("ajustments");
        }


        listItemAdapter= new MySimpleAdapter(getActivity(), adjustmentitems,
                R.layout.adjustment_item, new String[]{ "ItemNumber", "QuantityAdjusted","Reason","delete" },
                new int[]{R.id.adj_item, R.id.adj_quantity,R.id.adj_reason,R.id.adj_delete});
        setListAdapter(listItemAdapter);

        listItemAdapter.setOnItemDeleteClickListener(new MySimpleAdapter.onItemDeleteListener() {
            @Override
            public void onDeleteClick(int i){
                adjustmentitems.remove(i);
                Toast.makeText(getActivity(), "delete item:" + i, Toast.LENGTH_SHORT).show();
                listItemAdapter.notifyDataSetChanged();
            }
        });

        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
        add_new = v.findViewById(R.id.add_new);
        adj_done = v.findViewById(R.id.adj_done);
//        lv = getListView();
        add_new.setOnClickListener(this);
        adj_done.setOnClickListener(this);

    }

    @Override
    public void onListItemClick(ListView av, View v, int pos, long id)
    {
        super.onListItemClick(av, v, pos, id);
        AdjustmentItem item = (AdjustmentItem) av.getItemAtPosition(pos);
        Toast.makeText(getActivity(), item.get("item") + " selected",Toast.LENGTH_LONG).show();
    }

    private void addItem()
    {

        Toast.makeText(getActivity(), "add one more lah", Toast.LENGTH_SHORT).show();
        blank = new AdjustmentItem("","","","delete");
        adjustmentitems.add(blank);
        listItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.add_new:
                addItem();
                break;

            case R.id.adj_done:
                postdata = listItemAdapter.adj_data();
                callback.onReturnResults1(postdata);
                break;
        }
    }

    public void setCallback(AdjustmentFragment.resultInterface1 callback) {
        this.callback = callback;
    }

    public interface resultInterface1 {
        void onReturnResults1(List<AdjustmentItem> postdata);
    }
}
