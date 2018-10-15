package com.example.rizwan.bloodbankmanagementsystem;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DonateActivity extends AppCompatActivity {

    EditText dName,dMobile,dStreet,dPinCode;
    Spinner dBloodGroup,dAgeGroup;
    AutoCompleteTextView dState,dCity;
    String age,blood;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Donar Registering");

        dName = (EditText)findViewById(R.id.dname);
        dMobile = (EditText)findViewById(R.id.dmobile);
        dState = (AutoCompleteTextView)findViewById(R.id.dstate);
        dCity = (AutoCompleteTextView)findViewById(R.id.dcity);
        dStreet = (EditText)findViewById(R.id.dstreet);
        dPinCode = (EditText)findViewById(R.id.dPinCode);
        dBloodGroup = (Spinner) findViewById(R.id.dBloodGroup);
        dAgeGroup = (Spinner) findViewById(R.id.dAgeGroup);

        String[] india_state = getResources().getStringArray(R.array.india_states);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DonateActivity.this,android.R.layout.simple_spinner_item,india_state);

        dState.setAdapter(adapter);
        dState.setThreshold(1);

        String[] wb_city = getResources().getStringArray(R.array.wb_city);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(DonateActivity.this,android.R.layout.simple_spinner_item,wb_city);

        dCity.setAdapter(adapter1);
        dCity.setThreshold(1);

        String[] blood_grp = getResources().getStringArray(R.array.blood_group);
        ArrayAdapter<String> adapter_blood = new ArrayAdapter<String>(DonateActivity.this,android.R.layout.simple_spinner_item,blood_grp);
        dBloodGroup.setAdapter(adapter_blood);

        String[] age_grp = getResources().getStringArray(R.array.age_group);
        ArrayAdapter<String> adapter_age = new ArrayAdapter<String>(DonateActivity.this,android.R.layout.simple_spinner_item,age_grp);
        dAgeGroup.setAdapter(adapter_age);

        dBloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                blood = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dAgeGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                age = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void donarRegister(View v){

        final String name = dName.getText().toString().trim();
        final String mobile = dMobile.getText().toString().trim();
        final String state = dState.getText().toString().trim();
        final String city = dCity.getText().toString().trim();
        final String street = dStreet.getText().toString().trim();
        final String pincode = dPinCode.getText().toString().trim();

        if (name.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter donar name",Toast.LENGTH_LONG).show();
        }else if (mobile.length() != 10){
            Toast.makeText(getApplicationContext(),"Please enter valid mobile number",Toast.LENGTH_LONG).show();
        }else if(state.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter state",Toast.LENGTH_LONG).show();
        }else if(city.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter city",Toast.LENGTH_LONG).show();
        }else if(street.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter street",Toast.LENGTH_LONG).show();
        }else if(pincode.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter pincode",Toast.LENGTH_LONG).show();
        }else if(blood.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter blood group",Toast.LENGTH_LONG).show();
        }else if(age.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter age",Toast.LENGTH_LONG).show();
        }else{
            progressDialog.show();
            final int user_id = SharedPref.getInstance(getApplicationContext()).getUserId();
            /*Toast.makeText(getApplicationContext(),"Donar Registered",Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),mobile,Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),state,Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),city,Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),street,Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),pincode,Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),blood,Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),age,Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),String.valueOf(user_id),Toast.LENGTH_SHORT).show();


                params.put("user_id",String.valueOf(user_id));
                    params.put("d_name",name);
                    params.put("d_mobile",mobile);
                    params.put("d_state",state);
                    params.put("d_city",city);
                    params.put("d_street_address",street);
                    params.put("pin_code",pincode);
                    params.put("d_blood_group",blood);
                    params.put("d_age_group",age);
           */
           StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DONAR_REGISTER_URL, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {
                   progressDialog.dismiss();
                   try {
                       JSONObject jsonObject = new JSONObject(response);
                       if (!jsonObject.getBoolean("error")){
                           Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                           finish();
                       }else{
                           Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                       }
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
           }){
               @Override
               protected Map<String, String> getParams() throws AuthFailureError {
                   Map<String,String> params = new HashMap<>();
                   params.put("user_id",String.valueOf(user_id));
                   params.put("d_name",name);
                   params.put("d_mobile",mobile);
                   params.put("d_state",state);
                   params.put("d_city",city);
                   params.put("d_street_address",street);
                   params.put("pin_code",pincode);
                   params.put("d_blood_group",blood);
                   params.put("d_age_group",age);
                   return params;
               }
           };
           SingletonPattern.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        }


    }
}
