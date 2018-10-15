package com.example.rizwan.bloodbankmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by rizwan on 30/8/17.
 */

public class CustomAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return Constants.dname.size();
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
        LayoutInflater inflater = LayoutInflater.from(Donarlist.donarlist);
        final View v = inflater.inflate(R.layout.customlist,null);

        TextView name = (TextView)v.findViewById(R.id.name);
        TextView state = (TextView)v.findViewById(R.id.state);
        TextView city = (TextView)v.findViewById(R.id.city);
        final TextView mobile = (TextView)v.findViewById(R.id.mobile1);
        ImageView call_btn = (ImageView) v.findViewById(R.id.call);

        name.setText(Constants.dname.get(i));
        state.setText(Constants.dstate.get(i));
        city.setText(Constants.dcity.get(i));
        mobile.setText(Constants.dmobile.get(i));

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+mobile.getText()));
                v.getContext().startActivity(i);

            }
        });


        return v;
    }
}
