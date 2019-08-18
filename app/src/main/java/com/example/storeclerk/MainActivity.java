package com.example.storeclerk;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends FragmentActivity implements AsyncToServer.IServerResponse ,DisbursementFragment.resultInterface,AdjustmentFragment.resultInterface1,DisbursementDetailsFragment.resultInterface2,RetrievalFragment.resultInterface3,View.OnClickListener {
    private String username;
    private String sessionid;
    private  TextView user;
    private ArrayList<DisbursementItem> items;
    private ArrayList<DisbursementDetail> details;
    private ArrayList<Groupname> retdata;
    private ArrayList<AdjustmentItem> ajustments = new ArrayList<>();

    private Button signout;

    private  Command cmd;

    private Bundle data;
    private  Bundle data1;
    private  Bundle adj_data;

    private TextView tvDate;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Date date = new Date();
            JSONObject obj = new JSONObject();
            try{
                obj.put("date",date.getTime());
            }catch (Exception e) {
                e.printStackTrace();
            }

            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    cmd = new Command(MainActivity.this, "get",
//                            "http://10.0.2.2:50240/Home/GetPerson",obj ,"retrieval");
//                    new AsyncToServer().execute(cmd);
                    replaceFrag(new RetrievalFragment(), R.id.fragmentSpot);
                    return true;
                case R.id.navigation_dashboard:
                    Fragment adj = new AdjustmentFragment();
                    adj.setArguments(adj_data);
                    replaceFrag(adj, R.id.fragmentSpot);
                    return true;
                case R.id.navigation_notifications:
                    cmd = new Command(MainActivity.this, "getdislist",
                           "http://10.0.2.2:50240/DisbursementLists/GetDisbursementList",null);
                  new AsyncToServer().execute(cmd);
                  return true;
            }
            return false;
        }
    };


    public void replaceFrag(Fragment frag, int where) {
        FragmentManager mgr = getSupportFragmentManager();
        FragmentTransaction trans = mgr.beginTransaction();
        trans.replace(R.id.fragmentSpot, frag);
        trans.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        cmd = new Command(MainActivity.this, "getRetrieval",
                           "http://10.0.2.2:50240/StationeryRetrievalForms/GetRetrieval",null);
                  new AsyncToServer().execute(cmd);


       // replaceFrag(new RetrievalFragment(), R.id.fragmentSpot);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            return;
        username = bundle.getString("username");
        sessionid = bundle.getString("sessionid");
        user = findViewById(R.id.mainpageusername);
        signout = findViewById(R.id.signout);
//        tvDate = findViewById(R.id.tv_date);
        user.setText(username);
        signout.setOnClickListener(this);

        ajustments.add(new AdjustmentItem("", "","","delete"));
        adj_data = new Bundle();
        adj_data.putSerializable("ajustments", ajustments);

    }

    @Override
    public void onClick(View view){
        if (view.getId()== R.id.signout) {
                Intent backtologin = new Intent(this, LoginActivity.class);
                startActivity(backtologin);
        }
    }

    @Override
    public void onServerResponse(JSONObject jsonObj) {
        if (jsonObj == null)
            return;

        try {
            String context = (String) jsonObj.get("context");

            if (context.compareTo("getdislist") == 0)
            {
                items  = new ArrayList<>();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                JSONObject aa =  (JSONObject) jsonObj.get("data");
                JSONArray Id = aa.getJSONArray("id");
                JSONArray DepartmentCode = aa.getJSONArray("DepartmentCode");
                JSONArray CollectionPointname = aa.getJSONArray("CollectionPointname");
                JSONArray Date = aa.getJSONArray("Date");
                JSONArray Status = aa.getJSONArray("Status");
                for(int i = 0;i<Id.length();i++){
                    Long bb = Long.parseLong(Date.getString(i).substring(6,19));
                    Date date = new Date(bb);
                  items.add(new DisbursementItem(Id.getString(i),DepartmentCode.getString(i), CollectionPointname.getString(i),format.format(date),Status.getString(i)));
              }
                data = new Bundle();
                data.putSerializable("items", items);
                Fragment a = new DisbursementFragment();
                a.setArguments(data);
                replaceFrag(a, R.id.fragmentSpot);
            }
            else if (context.compareTo("getdisdetail") == 0)
            {
                details  = new ArrayList<>();
                JSONObject bb =  (JSONObject) jsonObj.get("data");
                JSONArray detailId = bb.getJSONArray("detailId");
                JSONArray listId = bb.getJSONArray("listId");
                JSONArray ItemNumber = bb.getJSONArray("ItemNumber");
                JSONArray Category = bb.getJSONArray("Category");
                JSONArray desc = bb.getJSONArray("desc");
                JSONArray quantity = bb.getJSONArray("quantity");
                JSONArray received = bb.getJSONArray("received");
                JSONArray remark = bb.getJSONArray("remark");

                for(int i = 0;i<desc.length();i++){
                    details.add(new DisbursementDetail(detailId.getString(i), listId.getString(i),ItemNumber.getString(i),Category.getString(i),desc.getString(i), quantity.getString(i),quantity.getString(i),remark.getString(i)));
                }

                data1 = new Bundle();
                data1.putSerializable("details", details);
                Fragment frag = new DisbursementDetailsFragment();
                frag.setArguments(data1);
                replaceFrag(frag, R.id.fragmentSpot);
            }
            else if (context.compareTo("postadjustment")==0){
                ajustments = new ArrayList<AdjustmentItem>();
                ajustments.add(new AdjustmentItem("", "","","delete"));
                adj_data = new Bundle();
                adj_data.putSerializable("ajustments", ajustments);
                Fragment adj = new AdjustmentFragment();
                adj.setArguments(adj_data);
                replaceFrag(adj, R.id.fragmentSpot);
                Toast.makeText(this,"Voucher Submited Successfully",Toast.LENGTH_LONG).show();
            }
            else if(context.compareTo("postdetail")==0){
                cmd = new Command(MainActivity.this, "getdislist",
                        "http://10.0.2.2:50240/DisbursementLists/GetDisbursementList",null);
                new AsyncToServer().execute(cmd);
                Toast.makeText(this,"Detail Submited Successfully",Toast.LENGTH_LONG).show();
            }
            else if(context.compareTo("getRetrieval")==0) {
                JSONObject aa = (JSONObject) jsonObj.get("data");
                String status = (String) jsonObj.get("status");
                if (status.compareTo("fail") == 0) {
                    Toast.makeText(this, "No New Requistions", Toast.LENGTH_LONG).show();
                }
                else if(status.compareTo("success")==0){
                    Toast.makeText(this, "Retrieval Successfully", Toast.LENGTH_LONG).show();
                    retdata = new ArrayList<>();
                    JSONArray FormNumber = aa.getJSONArray("FormNumber");
                    JSONArray FormDetailsnumber = aa.getJSONArray("FormDetailsnumber");
                    JSONArray ItemNumber = aa.getJSONArray("ItemNumber");
                    JSONArray BinNumber = aa.getJSONArray("BinNumber");
                    JSONArray Description = aa.getJSONArray("Description");
                    JSONArray Dept = aa.getJSONArray("Dept");
                    JSONArray Needed = aa.getJSONArray("Needed");
                    JSONArray Actual = aa.getJSONArray("Actual");

                    for (int i = 0; i < Dept.length(); i++) {
                        retdata.add(new Groupname(FormNumber.getString(i), FormDetailsnumber.getString(i), ItemNumber.getString(i), BinNumber.getString(i), Description.getString(i), Dept.getString(i), Needed.getString(i), Actual.getString(i)));
                    }
                    data = new Bundle();
                    data.putSerializable("retdata", retdata);
                    Fragment retrieval = new RetrievalFragment();
                    retrieval.setArguments(data);
                    replaceFrag(retrieval, R.id.fragmentSpot);
                }
            }
            else if (context.compareTo("postretrieval")==0){
                String status = (String)jsonObj.get("status");
                if(status.compareTo("success")==0) {
                    Toast.makeText(this, "Updated Data Successfully", Toast.LENGTH_LONG).show();
                    cmd = new Command(MainActivity.this, "getRetrieval",
                            "http://10.0.2.2:50240/StationeryRetrievalForms/GetRetrieval",null);
                    new AsyncToServer().execute(cmd);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttachFragment(Fragment frag) {
        super.onAttachFragment(frag);
        if (frag instanceof DisbursementFragment) {
            ((DisbursementFragment)frag).setCallback(this);
        }
        else if(frag instanceof AdjustmentFragment){
            ((AdjustmentFragment)frag).setCallback(this);
        }
        else if(frag instanceof DisbursementDetailsFragment){
            ((DisbursementDetailsFragment)frag).setCallback(this);
        }
        else if(frag instanceof RetrievalFragment){
            ((RetrievalFragment)frag).setCallback(this);
        }

    }

    @Override
    public void onReturnResults(String id) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", id);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
         cmd = new Command(MainActivity.this, "getdisdetail",
                            "http://10.0.2.2:50240/DisbursementListDetails/GetDetails",jsonObj);
         new AsyncToServer().execute(cmd);
    }

    @Override
    public void onReturnResults1(List<AdjustmentItem> postdata) {

        boolean flag = true;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        JSONObject tempobj=null;
        int count = postdata.size();
        for(int i =0;i<count;i++){
            tempobj = new JSONObject();
            if(postdata.get(i).get("ItemNumber").toString().isEmpty() || postdata.get(i).get("QuantityAdjusted").toString().isEmpty() || postdata.get(i).get("Reason").toString().isEmpty() )
            {
                flag = false;
                Toast.makeText(this,"Please Fill Up Fully",Toast.LENGTH_SHORT).show();
                break;
            }
            else{
            try {
                tempobj.put("ItemNumber",postdata.get(i).get("ItemNumber"));
                tempobj.put("QuantityAdjusted",postdata.get(i).get("QuantityAdjusted"));
                tempobj.put("Reason",postdata.get(i).get("Reason"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            jsonArray.put(tempobj);
        }
        }
        if(flag) {
            String infos = jsonArray.toString();
            try {
                jsonObj.put("Infos", infos);
            } catch (Exception e) {
                e.printStackTrace();
            }

            cmd = new Command(MainActivity.this, "postadjustment",
                    "http://10.0.2.2:50240/StockAdjustmentVouchers/PostAdjustment", jsonObj);
            new AsyncToServer().execute(cmd);
        }
    }


    @Override
    public void onReturnResults2(List<DisbursementDetail> postdata) {
        boolean flag = true;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        JSONObject tempobj=null;
        int count = postdata.size();
        for(int i =0;i<count;i++){
            tempobj = new JSONObject();
            int act = Integer.parseInt(postdata.get(i).get("actual").toString());
            int quant = Integer.parseInt(postdata.get(i).get("quantity").toString());
            if(act<0 || act>quant){
                flag = false;
                Toast.makeText(this,"Please Check Number Range",Toast.LENGTH_SHORT).show();
                break;
            }
            else {
                try {
                    tempobj.put("DetailId", postdata.get(i).get("detailId"));
                    tempobj.put("ListId", postdata.get(i).get("listId"));
                    tempobj.put("ItemNumber", postdata.get(i).get("ItemNumber"));
                    tempobj.put("Category", postdata.get(i).get("Category"));
                    tempobj.put("Desc", postdata.get(i).get("desc"));
                    tempobj.put("Quantity", postdata.get(i).get("quantity"));
                    tempobj.put("Actual", postdata.get(i).get("actual"));
                    tempobj.put("Remark", postdata.get(i).get("remark"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                jsonArray.put(tempobj);
            }
        }
        if(flag) {
            String infos = jsonArray.toString();
            try {
                jsonObj.put("Infos1", infos);
            } catch (Exception e) {
                e.printStackTrace();
            }

            cmd = new Command(MainActivity.this, "postdetail",
                    "http://10.0.2.2:50240/DisbursementListDetails/PostDetails", jsonObj);
            new AsyncToServer().execute(cmd);
        }
    }

    @Override
    public void onReturnResults3(List<Groupname> postdata) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        JSONObject tempobj=null;
        int count = postdata.size();
        for(int i =0;i<count;i++){
            tempobj = new JSONObject();
            try {
                tempobj.put("FormNumber",postdata.get(i).getFormnumber());
                tempobj.put("FormDetailsnumber",postdata.get(i).getFormdetailnumber());
                tempobj.put("ItemNumber",postdata.get(i).getItemnumber());
                tempobj.put("BinNumber",postdata.get(i).getBinnumber());
                tempobj.put("Description",postdata.get(i).getDesc());
                tempobj.put("Dept",postdata.get(i).getDept());
                tempobj.put("Needed",postdata.get(i).getNeeded());
                tempobj.put("Actual",postdata.get(i).getActual());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            jsonArray.put(tempobj);
        }
        String infos = jsonArray.toString();
        try {
            jsonObj.put("RetData", infos);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        cmd = new Command(MainActivity.this, "postretrieval",
                "http://10.0.2.2:50240/StationeryRetrievalForms/PostRetrieval",jsonObj);
        new AsyncToServer().execute(cmd);
    }
}
