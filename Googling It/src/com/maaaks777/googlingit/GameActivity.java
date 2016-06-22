package com.maaaks777.googlingit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameActivity extends Activity implements OnClickListener {

	// элементы экрана
	RelativeLayout gameLayout;
	TextView tvQuestion, tvScore, tvTimer;
	Button btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4;
	WebView web;

	// объекты и переменные
	Quiz quiz;
	int numOfQuestion;
	CountDownTimer timer;
	Context context;
	MediaPlayer music, rightAnswerSound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);

		// обнаруживаем элементы экрана
		gameLayout = (RelativeLayout) findViewById(R.id.game_layout);
		tvScore = (TextView) findViewById(R.id.tvQuestionNumber);
		tvTimer = (TextView) findViewById(R.id.tvTimer);
		tvQuestion = (TextView) findViewById(R.id.tvQuestion);

		btnAnswer1 = (Button) findViewById(R.id.answer1);
		btnAnswer1.setOnClickListener(this);
		btnAnswer2 = (Button) findViewById(R.id.answer2);
		btnAnswer2.setOnClickListener(this);
		btnAnswer3 = (Button) findViewById(R.id.answer3);
		btnAnswer3.setOnClickListener(this);
		btnAnswer4 = (Button) findViewById(R.id.answer4);
		btnAnswer4.setOnClickListener(this);

		// открываем google в мини-браузере
		web = (WebView) this.findViewById(R.id.webGoogle);
		web.setWebViewClient(new webViewClient());
		web.loadUrl("https://www.google.com");

		// зыуковые переменные
		music = MediaPlayer.create(this.getApplicationContext(), R.raw.game);
		music.setLooping(true);
		rightAnswerSound = MediaPlayer.create(this.getApplicationContext(),
				R.raw.right_answer);
		if ((new Statistics(this)).IsSoundOn())
			music.start();

		numOfQuestion = 1;
		tvScore.setText(getResources().getString(R.string.score) + " "
				+ numOfQuestion);
		context = this;
		MakeNewQuiz();

	}

	// метод создает новый объект, мен€€ текущую викторину и выводит все ее на
	// экран
	private void MakeNewQuiz() {
		quiz = new Quiz(this);
		tvQuestion.setText(quiz.GetQuestion());
		btnAnswer1.setText(quiz.GetAnswer(1));
		btnAnswer2.setText(quiz.GetAnswer(2));
		btnAnswer3.setText(quiz.GetAnswer(3));
		btnAnswer4.setText(quiz.GetAnswer(4));
		if (timer != null)
			timer.cancel();
		createTimer();
		web.loadUrl("https://www.google.com");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.answer1:
			CheckAnswer(1);
			break;
		case R.id.answer2:
			CheckAnswer(2);
			break;
		case R.id.answer3:
			CheckAnswer(3);
			break;
		case R.id.answer4:
			CheckAnswer(4);
			break;
		}

	}

	// провер€ет правильность ответа и в зависимости от этого либо идет дальше
	// либо завершает игру
	private void CheckAnswer(int numOfAnswer) {
		if (numOfAnswer == quiz.GetNumberOfRightAnswer()) {
			// в случае верного ответа
			numOfQuestion++;
			tvScore.setText(getResources().getString(R.string.score) + " "
					+ numOfQuestion);
			if (numOfQuestion == Statistics.LEVEL_WIN + 1) {
				// набран максимум
				music.stop();
				if (timer != null)
					timer.cancel();
				Finisher.FinishGame(this, numOfQuestion);
			} else {
				// играем дальше
				MakeNewQuiz();
				if ((new Statistics(this)).IsSoundOn()) {
					rightAnswerSound.start();
				}
			}

		} else {
			// конец игры
			if (timer != null)
				timer.cancel();
			music.stop();
			rightAnswerSound.stop();
			Finisher.FinishGame(this, numOfQuestion);
		}

	}

	private void createTimer() {
		final int TICK_PERIOD = 100;
		timer = new CountDownTimer(quiz.GetTime() * 1000, TICK_PERIOD) {

			@Override
			public void onTick(long millisUntilFinished) {
				tvTimer.setText(getResources().getString(R.string.timer_left)
						+ " " + millisUntilFinished / 1000 + ","
						+ (millisUntilFinished / 100) % 10);
			}

			@Override
			public void onFinish() {
				// конец игры
				music.stop();
				rightAnswerSound.stop();
				Finisher.FinishGame((Activity) context, numOfQuestion);
			}
		};
		timer.start();
	}

	private class webViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
	
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// эти переменные по€снены ниже
		menu.add(getResources().getString(R.string.exit));
		return super.onCreateOptionsMenu(menu);
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this,MainMenuActivity.class);
		startActivity(intent);
		finish();
		music.stop();
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed()
	{
		web.goBack();
	}

	// если пользователь решил выйти на home
	@Override
	protected void onUserLeaveHint() {
		if (timer != null)
			timer.cancel();
		music.stop();
		System.exit(0);
		super.onUserLeaveHint();
	}

}
