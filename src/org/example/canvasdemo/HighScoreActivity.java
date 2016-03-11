package org.example.canvasdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class HighScoreActivity extends Activity {
	MyView myView;
	MainActivity main;

	private int SavePoint;
	private String BestScore;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_highscore);
		
		Bundle extras = getIntent().getExtras();
		if (extras == null) {

		} else {
			System.out.println(extras.size());
			int points = extras.getInt("PointString");
			SavePoint = Integer.valueOf(points).intValue();
			
			final TextView textView = (TextView) 
	                findViewById(R.id.Pointscore);		
			textView.setText("Din score er: "+ SavePoint);
			
			SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
			final TextView bestscore = (TextView) 
	                findViewById(R.id.BestScore);		
			bestscore.setText(prefs.getInt("BestScore", 0) + " points");	
		}
	}

	public void Save(View view) {
		Intent myIntent = new Intent(view.getContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
		highScore(SavePoint);
		finish();
	}

	public void highScore(int newScore) {
		SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
		String name = prefs.getString("Name", "");
		int highScore = prefs.getInt("highScore", 0);
		int bestScore = prefs.getInt("bestscore", newScore);

		if (newScore > highScore) {
			SharedPreferences.Editor editor = getSharedPreferences("my_prefs",
					MODE_PRIVATE).edit();
			editor.putString("Name", "Susanne");
			editor.putInt("highScore", newScore);
			editor.putInt("bestscore", newScore);
			editor.commit();
		}
	}
}