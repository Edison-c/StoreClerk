package com.example.storeclerk;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class RetrievalFragment extends Fragment
        implements View.OnClickListener, DatePicker.OnDateChangedListener{

    RetrievalFragment.resultInterface3 callback;

    private Context context;
    private LinearLayout llDate;
    private TextView tvDate;
    private int year, month, day;
    private StringBuffer date;
    private View v1;
    private Button search;
    private  Button ret_done;

    private ExpandableListView expandableListView;
    private CustomExpandableListAdapter expandableListAdapter;
    private List<Groupname> expandableListTitle;
    private HashMap<Groupname, List<Groupname>> expandableListDetail;
    private List<Groupname> post_retdata;

    public RetrievalFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v1= inflater.inflate(R.layout.frag_retrieval, container, false);
        return v1;
    }

    private void initView() {
//        llDate = v1.findViewById(R.id.ll_date);
//        tvDate = v1.findViewById(R.id.tv_date);
//        search = v1.findViewById(R.id.searchdate);
        ret_done = v1.findViewById(R.id.ret_done);
        ret_done.setOnClickListener(this);
//        search.setOnClickListener(this);
//        llDate.setOnClickListener(this);

    }

    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        tvDate.setText(date.append(String.valueOf(year)).append("-").append(String.valueOf(month)).append("-").append(day).append(""));
    }


    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        context =this.getActivity() ;
        date = new StringBuffer();
        initView();
//        initDateTime();

        List<Groupname> retrievalitem = null;
        Bundle bundle = getArguments();
        if (bundle != null) {
            retrievalitem = (ArrayList<Groupname>)bundle.getSerializable("retdata");
        }
        expandableListView = v1.findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData(retrievalitem);
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(context, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });
    }


    public void setCallback(RetrievalFragment.resultInterface3 callback) {
        this.callback = callback;
    }

    public interface resultInterface3 {
        void onReturnResults3(List<Groupname> postdata);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.ret_done) {
            post_retdata = expandableListAdapter.ret_data();
            callback.onReturnResults3(post_retdata);
            Toast.makeText(getActivity(),"Submited",Toast.LENGTH_SHORT).show();
//            case R.id.ll_date:
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (date.length() > 0) {
//                            date.delete(0, date.length());
//                        }
//                        tvDate.setText(date.append(String.valueOf(year)).append("-").append(String.valueOf(month+1)).append("-").append(day).append(""));
//                        dialog.dismiss();
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                final AlertDialog dialog = builder.create();
//                View dialogView = View.inflate(context, R.layout.dialog_date, null);
//                final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);
//
//                dialog.setTitle("Setting Date");
//                dialog.setView(dialogView);
//                dialog.show();
//
//                datePicker.init(year, month , day, this);
//                break;

//            case R.id.searchdate:
//                String todate = tvDate.getText().toString();
//                Toast.makeText(getActivity(),todate+" Selected",Toast.LENGTH_SHORT).show();
//                break;

        }
    }
}
