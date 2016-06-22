package com.maaaks777.googlingit;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class Statistics {

	private final String PREF_NAME = "GooglingIt";
	private final String SOUND_ON = "sound";
	private final String BEST_RESULT = "best";
	private final String COUNT_OF_GAMES = "count";
	private final String MIDDLE_RESULT = "middle";
	private final String LOG_TAG = "myLogs";
	private static final int LEVEL1 = 1;
	private static final int LEVEL2 = 3;
	private static final int LEVEL3 = 9;
	private static final int LEVEL4 = 15;
	private static final int LEVEL5 = 20;
	public static final int LEVEL_WIN = 25;

	Context context;
	SharedPreferences sPref;

	public Statistics(Context cont) {
		context = cont;
		sPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	}

	public boolean IsSoundOn() {
		return sPref.getBoolean(SOUND_ON, true);
	}

	public void OnSound() {
		Editor ed = sPref.edit();
		ed.putBoolean(SOUND_ON, true);
		ed.commit();
	}

	public void OffSound() {
		Editor ed = sPref.edit();
		ed.putBoolean(SOUND_ON, false);
		ed.commit();
	}

	public int GetBestResult() {
		return sPref.getInt(BEST_RESULT, 0);
	}

	public int GetCountOfGames() {
		return sPref.getInt(COUNT_OF_GAMES, 0);
	}

	public float GetMiddleResult() {
		return sPref.getFloat(MIDDLE_RESULT, 0);
	}

	public void AddGameResult(int score) {
		IncrementCountOfGames();
		UpdateRecord(score);
		RecalculateMiddleScore(score);
	}

	public void Clear() {
		Editor ed = sPref.edit();
		ed.putInt(COUNT_OF_GAMES, 0);
		ed.putInt(BEST_RESULT, 0);
		ed.putFloat(MIDDLE_RESULT, 0);
		ed.commit();
	}
	
	public static int GetLevel(int score)
	{
		if (score < LEVEL1)
			return 0;
		if (score < LEVEL2)
			return 1;
		if (score < LEVEL3)
			return 2;
		if (score < LEVEL4)
			return 3;
		if (score < LEVEL5)
			return 4;
		if (score < LEVEL_WIN)
			return 5;
		return 6;
	}

	// для тестирования
	public void WriteAllToLog() {
		Log.d(LOG_TAG, "soundOn = " + this.IsSoundOn() + "\nBestresult = "
				+ this.GetBestResult() + "\nCount = " + this.GetCountOfGames()
				+ "\nMiddle Result = " + String.valueOf(this.GetMiddleResult())
				+ "\n---------------------------------------\n");
	}

	private void IncrementCountOfGames() {
		Editor ed = sPref.edit();
		ed.putInt(COUNT_OF_GAMES, this.GetCountOfGames() + 1);
		ed.commit();
	}

	private void UpdateRecord(int score) {
		if (score > this.GetBestResult()) {
			Editor ed = sPref.edit();
			ed.putInt(BEST_RESULT, score);
			ed.commit();
		}
	}

	private void RecalculateMiddleScore(int score) {
		float newMidScr = (this.GetMiddleResult() * (this.GetCountOfGames() - 1) + score)
				/ this.GetCountOfGames();
		Editor ed = sPref.edit();
		ed.putFloat(MIDDLE_RESULT, newMidScr);
		ed.commit();
	}

}
