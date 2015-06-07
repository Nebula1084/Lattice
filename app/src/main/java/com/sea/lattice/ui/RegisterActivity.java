package com.sea.lattice.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SignUpCallback;
import com.sea.lattice.R;
import com.sea.lattice.dao.AvosConnection;
import com.sea.lattice.entity.User;


public class RegisterActivity extends ActionBarActivity {
	private Button register_confirm, register_cancel;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		new AvosConnection(this);
		register_confirm = (Button) findViewById(R.id.register_confirm);
		register_confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RegisterButton();
			}
		});
		register_cancel = (Button) findViewById(R.id.register_cancel);
		register_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void RegisterButton(){
		final EditText register_account = (EditText) findViewById(R.id.register_account);
		final EditText register_nickname = (EditText) findViewById(R.id.register_nickname);
		final EditText register_password = (EditText) findViewById(R.id.register_password);
		final EditText register_reconfirm = (EditText) findViewById(R.id.register_reconfirm);
		final EditText register_email = (EditText) findViewById(R.id.register_email);
		final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);

		builder.setTitle("注册");
		builder.setMessage("正在注册");
		builder.setCancelable(false);
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		final AlertDialog dialog=builder.create();
		dialog.show();

		User user = new User();
		user.setUsername(register_account.getText().toString());
		String p1 = register_password.getText().toString();
		String p2 = register_reconfirm.getText().toString();
        if (register_account.getText().length()==0) {
            dialog.setMessage("账号不能为空");
            return;
        }
        if (register_nickname.getText().length()==0) {
            dialog.setMessage("昵称不能为空");
            return;
        }
        if (register_password.getText().length()==0) {
            dialog.setMessage("密码不能为空");
            return;
        }
        if (register_reconfirm.getText().length()==0) {
            dialog.setMessage("确认密码不能为空");
            return;
        }
        if (register_email.getText().length()==0) {
            dialog.setMessage("电子邮件不能为空");
            return;
        }
		if (!p1.equals(p2)) {
			dialog.setMessage("密码不一致");
			return;
		}
		user.setPassword(p1);
		user.setNickName(register_nickname.getText().toString());
		user.setEmail(register_email.getText().toString());

		user.signUpInBackground(new SignUpCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {
					dialog.setMessage("注册成功");
				} else {
					dialog.setMessage(e.getMessage());
				}
			}
		});
	}

}
