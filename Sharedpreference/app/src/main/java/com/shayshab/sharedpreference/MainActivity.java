package com.shayshab.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView details;
    Button save,load;
    EditText user,pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        details=findViewById(R.id.detailsTextview);
        save=findViewById(R.id.saveButton);
        load=findViewById(R.id.LoadButton);
        user=findViewById(R.id.usernameEditText);
        pass=findViewById(R.id.passwordEditText);

        save.setOnClickListener(this);
        load.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.saveButton) ;
        {

            String username=user.getText().toString();
            String password=pass.getText().toString();

            SharedPreferences sharedPreferences=getSharedPreferences("userDetails", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("usernameKey",username);
            editor.putString("passwordKey",password);
            editor.commit();


        }if (view.getId() == R.id.LoadButton);
        {

            SharedPreferences sharedPreferences=getSharedPreferences("userDetails", Context.MODE_PRIVATE);
            details.setText(user+"\n"+pass);



        }
    }
}
