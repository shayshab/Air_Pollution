package com.example.rizwan.bloodbankmanagementsystem;

import android.app.ProgressDialog;
import android.content.Intent;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText log_username,log_password;
    boolean logo=true;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (logo){
            logo=false;
            startLogo();
        }

        if(SharedPref.getInstance(getApplicationContext()).isLogged()){
            finish();
            startActivity(new Intent(MainActivity.this,ProfileActivity.class));
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        log_username = (EditText)findViewById(R.id.log_username);
        log_password = (EditText)findViewById(R.id.log_password);


    }

    public void startLogo(){
        startActivity(new Intent(MainActivity.this,StrartLogo.class));
    }

    public void login(View v){
        progressDialog.show();
        final String username = log_username.getText().toString().trim();
        final String password = log_password.getText().toString().trim();
        if (username.length() < 1 || password.length() < 1){
            Toast.makeText(getApplicationContext(),"PLease fill username and password",Toast.LENGTH_LONG).show();
        }else{
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOGIN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (!jsonObject.getBoolean("error")){
                            SharedPref.getInstance(getApplicationContext()).userLogin(
                                    Integer.parseInt(jsonObject.getString("id")),
                                    jsonObject.getString("username"),
                                    jsonObject.getString("email"));
                            finish();
                            startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
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
                    params.put("username",username);
                    params.put("password",password);
                    return params;
                }
            };
            SingletonPattern.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        }

    }

    public void register(View v){
        startActivity(new Intent(MainActivity.this,RegisterActivity.class));
    }
}
