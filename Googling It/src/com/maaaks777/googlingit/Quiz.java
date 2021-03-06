package com.maaaks777.googlingit;

import java.util.Random;

import android.content.Context;
import android.util.Log;

public class Quiz {

	static final int NUMBER_OF_ANSWER = 4;
	private final String SYMBOL_OF_DIVISION = "/";
	private final String LOG_TAG = "myLogs";

	private String question;
	private String[] answers;
	private int time;
	private int numRightAnswer;
	private Context context;

	// �����������
	public Quiz(Context cont) {
		context = cont;
		String str = this.GetQuizStringFromResourse();
		boolean strCorrect = ParseString(str);
		while (!strCorrect) {
			str = this.GetQuizStringFromResourse();
			strCorrect = ParseString(str);
		}
	}

	public String GetQuestion() {
		return question;
	}

	public String GetAnswer(int numberOfAnswer) {
		// ��� ����������� ���� ������ �� 1 �� 4
		return answers[numberOfAnswer - 1];
	}

	public int GetNumberOfRightAnswer() {
		return numRightAnswer+1;

	}

	public int GetTime() {
		return time;
	}

	// ��������� ������ �������
	// �������/������1�/������2�/������2�/������3�/������4�/������ �������
	// �������/ ������ ��� ������� �� ������/
	// �� ������ ������������
	// � ������ ������ ���������� true
	private boolean ParseString(String str) {

		try {
			// �������� ������ � �������� ������
			question = str.substring(0, str.indexOf(SYMBOL_OF_DIVISION));
			str = str.substring(str.indexOf(SYMBOL_OF_DIVISION) + 1,
					str.length());

			// ������� ������ ������� � ���������� ��������� ���
			answers = new String[NUMBER_OF_ANSWER];
			for (int answerNumber = 0; answerNumber < NUMBER_OF_ANSWER; answerNumber++) {
				answers[answerNumber] = str.substring(0,
						str.indexOf(SYMBOL_OF_DIVISION));
				str = str.substring(str.indexOf(SYMBOL_OF_DIVISION) + 1,
						str.length());
			}

			// ���������� �������� ������ ����� � ��������� ��� ������������
			// -1 �.�. ��� ����������� ����� ������ � 1 �� 4
			numRightAnswer = Integer.parseInt(str.substring(0,
					str.indexOf(SYMBOL_OF_DIVISION))) - 1;
			str = str.substring(str.indexOf(SYMBOL_OF_DIVISION) + 1,
					str.length());
			if ((numRightAnswer > 3) || (numRightAnswer < 0))
				return false;

			if (str.indexOf(SYMBOL_OF_DIVISION) != -1) {

				time = Integer.parseInt(str.substring(0,
						str.indexOf(SYMBOL_OF_DIVISION)));
			}

			else {
				// ���� ������ � ����� ������ ����������
				time = Integer.parseInt(str.substring(0, str.length()));
			}

			return true;

		} catch (Exception ex) {
			return false;
		}
	}

	// �������� ��������� ������ �� ��������.
	private String GetQuizStringFromResourse() {
		String[] stringsFromResource = context.getResources().getStringArray(
				R.array.QUESTIONS);
		Random rand = new Random();
		return stringsFromResource[rand.nextInt(stringsFromResource.length)];
	}

	// ������� � ��� ��� ������ (��� ������)
	@SuppressWarnings("unused")
	private void WriteDataToLog() {
		Log.d(LOG_TAG,
				"question = " + question + "\n ans1  =" + answers[0]
						+ "\n ans2  =" + answers[1] + "\n ans3  =" + answers[2]
						+ "\n ans4  =" + answers[3] + "\n right  ="
						+ String.valueOf(numRightAnswer) + "\n time  ="
						+ String.valueOf(time + 1));
	}
	
	public void TestAllStringInResource()
	{
		
		String[] stringsFromResource = context.getResources().getStringArray(
				R.array.QUESTIONS);
		for (int i=0;i<stringsFromResource.length;i++)
		{
			if (ParseString(stringsFromResource[i])==false) 
				Log.d(LOG_TAG,"error in "+String.valueOf(i)+" item");
		}
		Log.d(LOG_TAG,String.valueOf(stringsFromResource.length));
	}
}
