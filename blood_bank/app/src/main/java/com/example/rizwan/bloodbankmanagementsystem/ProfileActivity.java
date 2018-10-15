package com.example.rizwan.bloodbankmanagementsystem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    TextView hi_user;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        hi_user = (TextView)findViewById(R.id.hi_user);
        hi_user.setText("Hi, "+SharedPref.getInstance(this).getUsername());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");

    }


    public void donate(View v){
        startActivity(new Intent(ProfileActivity.this,DonateActivity.class));
    }

    public void needBlood(View v){
        startActivity(new Intent(ProfileActivity.this,NeedActivity.class));
    }

    public void logout(View v){
        final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout");
        builder.setIcon(R.drawable.blooddonation);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == dialogInterface.BUTTON_POSITIVE){
                    SharedPref.getInstance(getApplicationContext()).logout();
                    dialogInterface.dismiss();
                    finish();

                }else if(i == dialogInterface.BUTTON_NEGATIVE){
                    dialogInterface.dismiss();
                }
            }
        };

        builder.setPositiveButton("Yes",listener);
        builder.setNegativeButton("No",listener);
        builder.show();
    }

    public void getMyDonars(View v){
        progressDialog.show();
        Map<String,Integer> params = new HashMap<String, Integer>();
        params.put("user_id",SharedPref.getInstance(this).getUserId());
        JSONObject jsonRequest = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.MY_DONARS, jsonRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Constants.did.clear();
                Constants.dname.clear();
                Constants.dstate.clear();
                Constants.dcity.clear();
                Constants.dmobile.clear();
                Constants.dblood.clear();
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    int count = 0;
                    while (count < jsonArray.length()){
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        Constants.did.add(jsonObject.getString("d_id"));
                        Constants.dname.add(jsonObject.getString("d_name"));
                        Constants.dstate.add(jsonObject.getString("d_state"));
                        Constants.dcity.add(jsonObject.getString("d_city"));
                        Constants.dmobile.add(jsonObject.getString("d_mobile"));
                        Constants.dblood.add(jsonObject.getString("d_blood_group"));
                        count++;
                    }
                    startActivity(new Intent(getApplicationContext(),MyDonars.class));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        SingletonPattern.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
