package com.sea.lattice.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.sea.lattice.R;


public class RegisterActivity extends ActionBarActivity implements OnClickListener {
	private EditText register_account, register_nickname, register_email,
			register_password, register_reconfirm;
	private Button register_confirm, register_cancel;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		findView();
	}

	private void findView() {
		register_account = (EditText) findViewById(R.id.register_account);
		register_nickname = (EditText) findViewById(R.id.register_nickname);
		register_password = (EditText) findViewById(R.id.register_password);
		register_reconfirm = (EditText) findViewById(R.id.register_reconfirm);
		register_confirm = (Button) findViewById(R.id.register_confirm);
		register_cancel = (Button) findViewById(R.id.register_cancel);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		}
	}
}
