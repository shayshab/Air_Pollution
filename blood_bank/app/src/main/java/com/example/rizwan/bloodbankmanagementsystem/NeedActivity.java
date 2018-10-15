package com.example.rizwan.bloodbankmanagementsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NeedActivity extends AppCompatActivity {

    AutoCompleteTextView actv1,actv2;
    Spinner blood_group,age_group;
    String blood,age;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need);

        actv1 = (AutoCompleteTextView)findViewById(R.id.state);
        actv2 = (AutoCompleteTextView)findViewById(R.id.city);
        blood_group = (Spinner)findViewById(R.id.blood_grp);
        age_group = (Spinner)findViewById(R.id.age_grp);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");

        String[] india_states = getResources().getStringArray(R.array.india_states);
        String[] wb_city = getResources().getStringArray(R.array.wb_city);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(NeedActivity.this,android.R.layout.simple_spinner_item,india_states);

        actv1.setAdapter(adapter1);
        actv1.setThreshold(1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(NeedActivity.this,android.R.layout.simple_spinner_item,wb_city);

        actv2.setAdapter(adapter2);
        actv2.setThreshold(1);

        blood_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                blood = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        age_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                age = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void blood_donar(View v){
        final String state = actv1.getText().toString().trim();
        final String city = actv2.getText().toString().trim();
        if(state.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter state",Toast.LENGTH_LONG).show();
        }else if(city.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter city",Toast.LENGTH_LONG).show();
        }else if(blood.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter blood group",Toast.LENGTH_LONG).show();
        }else{
            progressDialog.show();

            Map<String,String> params = new HashMap<String, String>();
            params.put("state",state);
            params.put("city",city);
            params.put("blood_group",blood);
            params.put("age_group",age);
            JSONObject jsonRequest = new JSONObject(params);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.DONAR_LIST_URL, jsonRequest, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    Constants.did.clear();
                    Constants.dname.clear();
                    Constants.dcity.clear();
                    Constants.dmobile.clear();
                    Constants.dstate.clear();
                    Constants.dmobile.clear();
                    try {
                        JSONArray jsonArray = response.getJSONArray("results");
                        int count = 0;
                        while (count<jsonArray.length()){
                            JSONObject jsonObject = jsonArray.getJSONObject(count);
                            Constants.did.add(jsonObject.getString("d_id"));
                            Constants.dname.add(jsonObject.getString("d_name"));
                            Constants.dstate.add(jsonObject.getString("d_state"));
                            Constants.dcity.add(jsonObject.getString("d_city"));
                            Constants.dmobile.add(jsonObject.getString("d_mobile"));
                            count++;
                        }
                        finish();
                        startActivity(new Intent(getApplicationContext(),Donarlist.class));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
            SingletonPattern.getInstance(this).addToRequestQueue(jsonObjectRequest);

        }

    }


}
