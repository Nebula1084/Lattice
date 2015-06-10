package com.sea.lattice.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.sea.lattice.R;
import com.sea.lattice.dao.AvosConnection;
import com.sea.lattice.entity.User;

/**
 * Created by Sea on 6/2/2015.
 */
public class LoginActivity extends Activity {
    private Integer flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new AvosConnection(this);

        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        findViewById(R.id.login_button_register).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        findViewById(R.id.login_button_login).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                LoginButton();
            }
        });
    }

    private void LoginButton(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        final EditText login_account = (EditText) findViewById(R.id.login_account);
        final EditText login_password = (EditText) findViewById(R.id.login_password);

        flag = Integer.valueOf(AvosConnection.FLAG_CONNECTING);
        builder.setTitle("登陆");
        builder.setMessage("正在登陆");
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        if (login_account.getText().length()==0){
            Toast toast = Toast.makeText(getApplicationContext(), "账号不能为空", Toast.LENGTH_SHORT);
            toast.show();
            dialog.dismiss();
            return;
        }
        if (login_password.getText().length()==0){
            Toast toast = Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT);
            toast.show();
            dialog.dismiss();
            return;
        }

        User user = new User();
        user.logInInBackground(login_account.getText().toString(), login_password.getText().toString(), new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                flag = AvosConnection.FLAG_DONE;
                if (avUser != null){
                    dialog.dismiss();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    dialog.dismiss();
                    Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
