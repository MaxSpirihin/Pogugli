package com.maaaks777.googlingit;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Finisher {

	private static Activity context;
	private static int score;
	private static MediaPlayer gameOverSound;

	private Finisher(Activity cont, int NumOfAnswer) {
	}

	public static void FinishGame(Activity cont, int numOfQues) {
		context = cont;
		numOfQues--;
		score = numOfQues;

		// производим сбор статистики и берем нужные варианты
		Statistics stat = new Statistics(context);
		stat.AddGameResult(score);
		String textForLevel = "";
		int imageId = 0;
		int soundId = 0;
		switch (Statistics.GetLevel(score)) {
		case 0:
			textForLevel = context.getResources().getString(R.string.level0);
			imageId = R.drawable.win_bad;
			soundId = R.raw.end_bad;
			break;
		case 1:
			textForLevel = context.getResources().getString(R.string.level1);
			imageId = R.drawable.win_bad;
			soundId = R.raw.end_bad;
			break;
		case 2:
			textForLevel = context.getResources().getString(R.string.level2);
			imageId = R.drawable.win_not_good;
			soundId = R.raw.end_middle;
			break;
		case 3:
			textForLevel = context.getResources().getString(R.string.level3);
			imageId = R.drawable.win_norm;
			soundId = R.raw.end_middle;
			break;
		case 4:
			textForLevel = context.getResources().getString(R.string.level4);
			imageId = R.drawable.win_good;
			soundId = R.raw.end_good;
			break;
		case 5:
			textForLevel = context.getResources().getString(R.string.level5);
			imageId = R.drawable.win_good;
			soundId = R.raw.end_good;
			break;
		case 6:
			textForLevel = context.getResources().getString(R.string.levelWin);
			imageId = R.drawable.win_great;
			soundId = R.raw.end_win;
			break;
		}

		// устанавливаем компоненты
		context.setContentView(R.layout.finish);
		TextView tvScore = (TextView) context.findViewById(R.id.tvScoreTotal);
		tvScore.setText(context.getResources().getString(
				R.string.you_found_before_score)
				+ " " + score + " " + GetAfterScore());
		TextView tvLevel = (TextView) context.findViewById(R.id.tvLevel);
		tvLevel.setText(textForLevel);
		ImageView image = (ImageView) context.findViewById(R.id.imageView1);
		image.setImageResource(imageId);

		if ((new Statistics(context)).IsSoundOn()) {
			gameOverSound = MediaPlayer.create(context.getApplicationContext(),
					soundId);
			gameOverSound.start();
		}

		// берем кнопки и вешаем обработчик
		OnClickListener listener = new OnClickListener() {
			Intent intent;

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.restart:
					intent = new Intent(context, GameActivity.class);
					context.startActivity(intent);
					context.finish();
					if (gameOverSound != null)
						gameOverSound.stop();
					break;
				case R.id.exit:
					intent = new Intent(context, MainMenuActivity.class);
					context.startActivity(intent);
					context.finish();
					if (gameOverSound != null)
						gameOverSound.stop();
					break;
				case R.id.stat:
					intent = new Intent(context, StatActivity.class);
					context.startActivity(intent);
					context.finish();
					if (gameOverSound != null)
						gameOverSound.stop();
					break;
				}

			}
		};

		Button restart = (Button) context.findViewById(R.id.restart);
		restart.setOnClickListener(listener);
		Button exit = (Button) context.findViewById(R.id.exit);
		exit.setOnClickListener(listener);
		Button statBtn = (Button) context.findViewById(R.id.stat);
		statBtn.setOnClickListener(listener);
	}

	// так как в русском языке меняется окончание, то надо его выбрать
	private static String GetAfterScore() {
		String afterScore;
		if (score % 10 == 1)
			afterScore = context.getResources().getString(
					R.string.you_found_after_score1);
		else {
			if ((score % 10 == 2) || (score % 10 == 3) || (score % 10 == 4))
				afterScore = context.getResources().getString(
						R.string.you_found_after_score234);
			else
				afterScore = context.getResources().getString(
						R.string.you_found_after_score);
		}
		return afterScore;
	}

}
