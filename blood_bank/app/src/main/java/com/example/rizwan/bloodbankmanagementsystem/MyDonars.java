package com.example.rizwan.bloodbankmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MyDonars extends AppCompatActivity {

    ListView donar_lview;
    static MyDonars myDonars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donars);

        myDonars = this;

        donar_lview = (ListView)findViewById(R.id.mydonars);

        donar_lview.setAdapter(new DonarListAdapter());
    }
}
