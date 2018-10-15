package com.example.rizwan.bloodbankmanagementsystem;

import android.app.ProgressDialog;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity {

    EditText username,password,cPassword,email;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        cPassword = (EditText)findViewById(R.id.c_password);
        email = (EditText)findViewById(R.id.email);


    }

    public void register(View v){

        final String uname = username.getText().toString().trim();
        final String pass = password.getText().toString().trim();
        final String cPass = cPassword.getText().toString().trim();
        final String uemail = email.getText().toString().trim();

        if (uname.length() < 4){
            Toast.makeText(getApplicationContext(),"Username must be at least 4 characters",Toast.LENGTH_LONG).show();
        }else if (pass.length() < 6 || cPass.length() < 6){
            Toast.makeText(getApplicationContext(),"Password must be at least 6 characters",Toast.LENGTH_LONG).show();
        }else if(!pass.equals(cPass)){
            Toast.makeText(getApplicationContext(),"Passwords not matched",Toast.LENGTH_LONG).show();
        }else{
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REGISTER_URL, new Response.Listener<String>() {
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
                    params.put("username",uname);
                    params.put("password",pass);
                    params.put("email",uemail);
                    return params;
                }

            };
            SingletonPattern.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        }
    }
}
