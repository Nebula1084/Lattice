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


public class RegisterActivity extends ActionBarActivity implements OnClickListener {
	private EditText register_account, register_nickname, register_email,
			register_password, register_reconfirm;
	private Button register_confirm, register_cancel;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		findView();
		new AvosConnection(this);
	}

	private void findView() {
		register_account = (EditText) findViewById(R.id.register_account);
		register_nickname = (EditText) findViewById(R.id.register_nickname);
		register_password = (EditText) findViewById(R.id.register_password);
		register_reconfirm = (EditText) findViewById(R.id.register_reconfirm);
		register_email = (EditText) findViewById(R.id.register_email);
		register_confirm = (Button) findViewById(R.id.register_confirm);
		register_confirm.setOnClickListener(this);
		register_cancel = (Button) findViewById(R.id.register_cancel);
		register_cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.register_confirm:
				User user = new User();
				user.setUsername(register_account.getText().toString());
				String p1 = register_password.getText().toString();
				String p2 = register_reconfirm.getText().toString();
				if (!p1.equals(p2)) break;
				user.setPassword(p1);
				user.setNickName(register_nickname.getText().toString());
				user.setEmail(register_email.getText().toString());
				user.signUpInBackground(new SignUpCallback() {
					@Override
					public void done(AVException e) {
						if (e == null) {
							AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
							builder.setTitle("注册");
							builder.setMessage("注册成功");
							builder.setCancelable(false);
							builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
									RegisterActivity.this.finish();
								}
							});
							builder.create().show();
						} else {
							AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
							builder.setTitle("注册");
							builder.setMessage(e.getMessage());
							builder.setCancelable(false);
							builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							});
							builder.create().show();
						}
					}
				});
				break;
			case R.id.register_cancel:
				finish();
				break;
		}
	}
}
