package com.example.rizwan.bloodbankmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class Donarlist extends AppCompatActivity {

    ListView listView;
    static Donarlist donarlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donarlist);

        donarlist=this;

        listView = (ListView)findViewById(R.id.lview);

        listView.setAdapter(new CustomAdapter());
    }
}
