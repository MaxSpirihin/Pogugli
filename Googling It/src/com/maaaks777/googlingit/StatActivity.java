package com.maaaks777.googlingit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class StatActivity extends Activity implements OnClickListener {
	
	final int DIALOG_CLEAR_STAT = 1;

	TextView tvBest, tvAdvice, tvCountOfGames, tvMiddle;
	Button btnClear, btnExit;
	Statistics stat;
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stat);
		
		FindAllViews();
		InflateAllViews();
	}

	private void FindAllViews() {
		tvBest = (TextView) findViewById(R.id.tvBestResult);
		tvAdvice = (TextView) findViewById(R.id.tvAdvice);
		tvCountOfGames = (TextView) findViewById(R.id.tvCountOfGames);
		tvMiddle = (TextView) findViewById(R.id.tvMiddleResult);

		btnClear = (Button) findViewById(R.id.clear);
		btnClear.setOnClickListener(this);
		btnExit = (Button) findViewById(R.id.toMainMenu);
		btnExit.setOnClickListener(this);

		stat = new Statistics(this);
	}

	private void InflateAllViews() {
		tvBest.setText(getResources().getString(R.string.best_result) + " "
				+ String.valueOf(stat.GetBestResult()));

		if (stat.GetBestResult() == Statistics.LEVEL_WIN) {
			tvAdvice.setText(getResources().getString(R.string.you_are_god));
		} else {
			tvAdvice.setText(getResources().getString(R.string.advice_start)
					+ " " + Statistics.LEVEL_WIN + " "
					+ getResources().getString(R.string.advice_end));
		}
		
		tvCountOfGames.setText(getResources().getString(R.string.count_of_games) + " "
				+ String.valueOf(stat.GetCountOfGames()));
		
		tvMiddle.setText(getResources().getString(R.string.middle_result) + " "
				+ String.format("%.3g%n",stat.GetMiddleResult()));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
		case R.id.toMainMenu:
			intent = new Intent(this, MainMenuActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.clear:
			showDialog(DIALOG_CLEAR_STAT);
			break;
		}
	}
	
	@SuppressWarnings("deprecation")
	protected Dialog onCreateDialog(int id) {
		if (id == DIALOG_CLEAR_STAT) {
			AlertDialog.Builder adb = new AlertDialog.Builder(this);
			// заголовок
			adb.setTitle(getResources()
					.getString(R.string.clear_stat_dialog));
			// сообщение
			adb.setMessage(getResources().getString(R.string.are_you_sure));
			// иконка
			adb.setIcon(android.R.drawable.ic_dialog_info);
			// кнопка положительного ответа
			adb.setPositiveButton(getResources().getString(R.string.yes),
					dialogListener);
			// кнопка отрицательного ответа
			adb.setNegativeButton(getResources().getString(R.string.no),
					dialogListener);
			// создаем диалог
			return adb.create();
		}
		return super.onCreateDialog(id);
	}

	DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			// положительная кнопка
			case Dialog.BUTTON_POSITIVE:
				stat.Clear();
				InflateAllViews();
				break;
			// негаитвная кнопка
			case Dialog.BUTTON_NEGATIVE:
				break;
			}
		}
	};
	
	@Override
	public void onBackPressed()
	{
		intent = new Intent(this, MainMenuActivity.class);
		startActivity(intent);
		finish();
	}
	
	// если пользователь решил выйти на home
	@Override
	protected void onUserLeaveHint() {
		System.exit(0);
		super.onUserLeaveHint();
	}
}
