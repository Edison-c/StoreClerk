package com.example.storeclerk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.List;

public class DisbursementDetailsFragment extends ListFragment implements View.OnClickListener {
    public DisbursementDetailsFragment(){

    }
    private DisDetalAdapter disAdapter;
    private List<DisbursementDetail> disbursementdetails = null;
    private List<DisbursementDetail> dis_data;
    private Button dis_done;
    private View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_disbursementdetail, container, false);
        String dept = null;

        Bundle bundle = getArguments();
        if (bundle != null) {
            dept = bundle.getString("dept");
            disbursementdetails = (ArrayList<DisbursementDetail>)bundle.getSerializable("details");
        }
        disAdapter = new DisDetalAdapter(getActivity(), disbursementdetails,
                R.layout.disbursementdetail_item, new String[]{ "desc", "quantity","actual","remark" },
                new int[]{R.id.detail_desc, R.id.detail_quantity,R.id.detail_actual,R.id.detail_remark});
        setListAdapter(disAdapter);
        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
        dis_done = v.findViewById(R.id.dis_done);
        dis_done.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        if(view.getId()==R.id.dis_done){
                dis_data = disAdapter.dis_data();
        }
    }
}
