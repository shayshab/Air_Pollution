package com.example.rizwan.bloodbankmanagementsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by rizwan on 3/9/17.
 */

public class DonarListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return Constants.did.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(MyDonars.myDonars);
        View v = inflater.inflate(R.layout.custom_donar_list,null);

        TextView name = (TextView)v.findViewById(R.id.donar_name);
        TextView state = (TextView)v.findViewById(R.id.donar_state);
        TextView city = (TextView)v.findViewById(R.id.donar_city);
        final TextView mobile = (TextView)v.findViewById(R.id.donar_mobile);
        TextView donar_id = (TextView) v.findViewById(R.id.donar_id);
        ImageView update_donar = (ImageView)v.findViewById(R.id.donar_update);
        TextView blood_group = (TextView)v.findViewById(R.id.donar_blood_group);

        name.setText(Constants.dname.get(i));
        state.setText(Constants.dstate.get(i));
        city.setText(Constants.dcity.get(i));
        mobile.setText(Constants.dmobile.get(i));
        blood_group.setText(Constants.dblood.get(i));

        return v;
    }
}
