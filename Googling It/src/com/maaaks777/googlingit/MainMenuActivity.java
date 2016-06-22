package com.maaaks777.googlingit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainMenuActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener {

	Button play, stat, info, toBack;
	TextView tvEmail, tvVk, tvTouch;
	ToggleButton soundOn;
	Intent intent;
	Statistics statis;

	boolean isInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		
		//раскомментировать для теста новых вопросов
		//Quiz q = new Quiz(this);
		//q.TestAllStringInResource();
		
		play = (Button) findViewById(R.id.play);
		play.setOnClickListener(this);
		stat = (Button) findViewById(R.id.stat_in_m);
		stat.setOnClickListener(this);
		info = (Button) findViewById(R.id.info);
		info.setOnClickListener(this);
		soundOn = (ToggleButton) findViewById(R.id.sound_on_off);
		soundOn.setOnCheckedChangeListener(this);
		statis = new Statistics(this);
		isInfo = false;

		if (statis.IsSoundOn()) {
			soundOn.setChecked(true);
		} else {
			soundOn.setChecked(false);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.play:
			intent = new Intent(this, GameActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.stat_in_m:
			intent = new Intent(this, StatActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.info:
			setContentView(R.layout.info);
			toBack = (Button) findViewById(R.id.to_back);
			toBack.setOnClickListener(this);
			tvEmail = (TextView) findViewById(R.id.textEMail);
			tvVk = (TextView) findViewById(R.id.textVkPage);
			tvTouch = (TextView) findViewById(R.id.textTouchGame);
			tvEmail.setOnClickListener(this);
			tvVk.setOnClickListener(this);
			tvTouch.setOnClickListener(this);
			isInfo = true;
			break;
		case R.id.to_back:
			setContentView(R.layout.main_menu);
			play = (Button) findViewById(R.id.play);
			play.setOnClickListener(this);
			stat = (Button) findViewById(R.id.stat_in_m);
			stat.setOnClickListener(this);
			info = (Button) findViewById(R.id.info);
			info.setOnClickListener(this);
			soundOn = (ToggleButton) findViewById(R.id.sound_on_off);
			soundOn.setOnCheckedChangeListener(this);
			statis = new Statistics(this);
			isInfo = false;

			if (statis.IsSoundOn()) {
				soundOn.setChecked(true);
			} else {
				soundOn.setChecked(false);
			}
			break;
		case R.id.textEMail:
			intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://maaaks777@mail.ru"));
			startActivity(intent);
			break;
		case R.id.textVkPage:
			intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://vk.com/maaaks777"));
			startActivity(intent);
			break;
		case R.id.textTouchGame:
			intent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.thetouchgame"));
			startActivity(intent);
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton btn, boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked)
			statis.OnSound();// now on
		else
			statis.OffSound();// now off

	}

	@Override
	public void onBackPressed() {
		if (isInfo) {
			setContentView(R.layout.main_menu);
			play = (Button) findViewById(R.id.play);
			play.setOnClickListener(this);
			stat = (Button) findViewById(R.id.stat_in_m);
			stat.setOnClickListener(this);
			info = (Button) findViewById(R.id.info);
			info.setOnClickListener(this);
			soundOn = (ToggleButton) findViewById(R.id.sound_on_off);
			soundOn.setOnCheckedChangeListener(this);
			statis = new Statistics(this);
			isInfo = false;

			if (statis.IsSoundOn()) {
				soundOn.setChecked(true);
			} else {
				soundOn.setChecked(false);
			}
		} else {
			System.exit(0);
		}
	}

	// если пользователь решил выйти на home
	@Override
	protected void onUserLeaveHint() {
		System.exit(0);
		super.onUserLeaveHint();
	}

}
