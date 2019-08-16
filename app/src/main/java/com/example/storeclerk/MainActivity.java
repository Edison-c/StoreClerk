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

public class MainActivity extends FragmentActivity implements AsyncToServer.IServerResponse ,DisbursementFragment.resultInterface,View.OnClickListener {
    private String username;
    private String sessionid;
    private  TextView user;
    private ArrayList<DisbursementItem> items = new ArrayList<>();
    private ArrayList<DisbursementDetail> details = new ArrayList<>();
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

                    JSONObject jsonObj = new JSONObject();
                    try {
                        jsonObj.put("username", "guoan");
                        jsonObj.put("password", "password");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    cmd = new Command(MainActivity.this, "get",
                           "http://10.0.2.2:50240/DisbursementLists/GetDisbursementList",null);
                  new AsyncToServer().execute(cmd);
                  break;
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
        replaceFrag(new RetrievalFragment(), R.id.fragmentSpot);
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

        details.add(new DisbursementDetail("Pen", "10","1","Pending"));
        details.add(new DisbursementDetail("Rule", "9","2","Pending"));
        details.add(new DisbursementDetail("Balah", "8","3","Pending"));

        ajustments.add(new AdjustmentItem("Pen", "12","Bad","delete"));
        ajustments.add(new AdjustmentItem("Rule", "13","Wrong","delete"));
        ajustments.add(new AdjustmentItem("Balah", "31","Bad","delete"));
        adj_data = new Bundle();
        adj_data.putSerializable("ajustments", ajustments);

    }

    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.signout:
                Intent backtologin = new Intent(this, LoginActivity.class);
                startActivity(backtologin);
                break;
        }
    }

    @Override
    public void onServerResponse(JSONObject jsonObj) {
        if (jsonObj == null)
            return;

        try {
            String context = (String) jsonObj.get("context");

            if (context.compareTo("get") == 0)
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                JSONObject aa =  (JSONObject) jsonObj.get("data");
//                JSONArray bb = aa.getJSONArray("id");
                JSONArray DepartmentCode = aa.getJSONArray("DepartmentCode");
                JSONArray CollectionPointname = aa.getJSONArray("CollectionPointname");
                JSONArray Date = aa.getJSONArray("Date");
                JSONArray Status = aa.getJSONArray("Status");
                for(int i = 0;i<5;i++){
                    Long bb = Long.parseLong(Date.getString(i).substring(6,19));
                    Date date = new Date(bb);
                  items.add(new DisbursementItem(DepartmentCode.getString(i), CollectionPointname.getString(i),format.format(date),Status.getString(i)));
              }
                data = new Bundle();
                data.putSerializable("items", items);
                Fragment a = new DisbursementFragment();
                a.setArguments(data);
                replaceFrag(a, R.id.fragmentSpot);
            }
            else if (context.compareTo("set") == 0)
            {
                String status = (String)jsonObj.get("status");
                System.out.println("status:" + status);
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
    }

    @Override
    public void onReturnResults(String dept) {

        data1 = new Bundle();
        data1.putSerializable("details", details);
        data1.putString("dept", dept);
        Fragment frag = new DisbursementDetailsFragment();
        frag.setArguments(data1);
        replaceFrag(frag, R.id.fragmentSpot);
    }
}
