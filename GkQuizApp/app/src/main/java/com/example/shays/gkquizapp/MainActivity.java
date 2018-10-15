package com.example.shays.gkquizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    EditText user,password;
    Button login,signup;
    DatabaseReference rootRef,demoRef;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user=(EditText) findViewById(R.id.user);
        password=(EditText) findViewById(R.id.pass);
        login=(Button)findViewById(R.id.login);
        signup=(Button) findViewById(R.id.signup);

        rootRef = FirebaseDatabase.getInstance().getReference();
        //database reference pointing to demo node
        demoRef = rootRef.child("demo");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value1 = user.getText().toString();
                String value2 = password.getText().toString();
                demoRef.push().setValue(value1);
                demoRef.push().setValue(value2);
            }
        });





       signup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i=new Intent(MainActivity.this,SignupActivity.class);
               startActivity(i);
           }
       });


    }
    public void method1(View view)
    {


    }


}
