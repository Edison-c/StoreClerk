package com.example.storeclerk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.List;

public class DisbursementFragment extends ListFragment {
    DisbursementFragment.resultInterface callback;

    public DisbursementFragment() {
            }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.frag_disbursement, container, false);
        String meta = null;
        List<DisbursementItem> disbursementitem = null;

        Bundle bundle = getArguments();
        if (bundle != null) {
            meta = bundle.getString("meta");
            disbursementitem = (ArrayList<DisbursementItem>)bundle.getSerializable("items");
        }

        setListAdapter(((new SimpleAdapter(getActivity(), disbursementitem,
                R.layout.disbursement_item, new String[]{ "dept", "collectionpoint","collectiondate","status" },
                new int[]{R.id.deptView, R.id.collectionpointView,R.id.collectiondateView,R.id.statusView}))));
            return v;
            }

    @Override
    public void onListItemClick(ListView av, View v, int pos, long id)
    {
        super.onListItemClick(av, v, pos, id);
        DisbursementItem item = (DisbursementItem) av.getItemAtPosition(pos);
        callback.onReturnResults(item.get("dept").toString());

        Toast.makeText(getActivity(), item.get("dept") + " selected",Toast.LENGTH_SHORT).show();
    }

    public void setCallback(DisbursementFragment.resultInterface callback) {
        this.callback = callback;
    }

    public interface resultInterface {
        void onReturnResults(String dept);
    }
}
