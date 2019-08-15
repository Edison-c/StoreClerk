package com.example.storeclerk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,AsyncToServer.IServerResponse {
    private EditText username;
    private EditText password;
    private Button login;
    private String user;
    private String pwd;
    private Command cmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    @Override
    protected  void onStart() {
        super.onStart();
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login:
                user = username.getText().toString();
                pwd = password.getText().toString();
                if(user.isEmpty() || pwd.isEmpty()){
                    Toast.makeText(this,"Username or Password can not be Null",Toast.LENGTH_SHORT).show();
                    break;
                }
                else {
                JSONObject jsonObj = new JSONObject();
                try {
                    jsonObj.put("username", user);
                    jsonObj.put("password", pwd);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                cmd = new Command(LoginActivity.this, "set",
                        "http://10.0.2.2:50240/Login/SetPerson", jsonObj);
                new AsyncToServer().execute(cmd);
                break;
                //for testing
//                Intent intent = new Intent(this,MainActivity.class);
//                intent.putExtra("username","Judy");
//                intent.putExtra("sessionid","agagag");
//                startActivity(intent);
//                finish();
                }
        }
    }

    @Override
    public void onServerResponse(JSONObject jsonObj) {
        if (jsonObj == null){
            Toast.makeText(this,"Wrong username or password",Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            String context = (String) jsonObj.get("context");


            if (context.compareTo("set") == 0)
            {
                String status = (String )jsonObj.get("status");
                if(status.compareTo("fail")==0){
                    Toast.makeText(this,"Wrong username or password",Toast.LENGTH_SHORT).show();

                }
                else if(status.compareTo("success")==0) {
                    String name = (String) jsonObj.get("username");
                    String sessionid = (String) jsonObj.get("sessionId");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username", name);
                    intent.putExtra("sessionid", sessionid);
                    startActivity(intent);
                    finish();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
