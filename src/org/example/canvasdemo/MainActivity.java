package org.example.canvasdemo;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
    
	
	MyView myView;
	MyView gameView;
	
	Intent sendIntent;	
	ImageButton right, left, up, down;
	
	TextView pointView;
	TextView timertext;
	TextView leveltext;
	
	String name;
	String highscore;
	
	private boolean running = false;
	private Timer timerGhost;
	private Timer timerMove;
	private Timer timerTid;
	private int counter = 0;
	
	int direction;
	int level = 1;
	int speed = level + 2;
	int GhostDirection;
	int gDirectionvar2;
	int gcounter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); 
		myView = (MyView) findViewById(R.id.gameView);
		gameView = (MyView) findViewById(R.id.gameView);	
		
		myView.SetActivity(this);
		
		right = (ImageButton)findViewById(R.id.right);
        right.setOnClickListener(this);
        
        left = (ImageButton)findViewById(R.id.left);
        left.setOnClickListener(this);
        
        up = (ImageButton)findViewById(R.id.up);
        up.setOnClickListener(this);
        
        down = (ImageButton)findViewById(R.id.down);
        down.setOnClickListener(this);	
        
        leveltext = (TextView) findViewById(R.id.leveltext);
        timertext = (TextView) findViewById(R.id.timertext);
        pointView = (TextView) findViewById(R.id.pointView);
 		myView.setTextView(pointView);
 		myView.setTimerText(timertext);
 		myView.setTextLevel(leveltext);
 		
        myView.placeCoins();
              
        timerMove = new Timer();
        timerTid = new Timer();
        timerGhost = new Timer();
         
     	running = true;
     	 
     	timerMove.schedule(new TimerTask() {
     		public void run() {
     			TimerMethod();
     		}
     	}, 0, 30);
     	 
     	timerTid.schedule(new TimerTask() {
     		public void run() {
     			TidsTimerMethod();
     		}
     	}, 0, 1000);
     	
     	timerGhost.schedule(new TimerTask() {
       		public void run() {
       			GhostTimerMethod();
       		}
       	}, 0, 30);     	 
      	myView.setGhostTimer(timerGhost);
    }
	
	private void GhostTimerMethod() {
		this.runOnUiThread(Timer_Ghost);
	}
	
	private void TimerMethod() {
		this.runOnUiThread(Timer_Move);
	}
	
	private void TidsTimerMethod() {
		this.runOnUiThread(Timer_Time);
	}
	
	@Override
	protected void onStop() {
		running = false;
		//timerMove.cancel();
		super.onStop();
	}
	
	protected void onResume() {
		running = true;
		super.onResume();
	}
	
	protected void resetAll() {
		running = false;
		myView.reset();
		pointView.setText("Points: " + 0);
        timertext.setText("Timer: " + 0);
		leveltext.setText("Level " + 1);
		level = 1;
		myView.placeCoins();
		myView.setPoint(0);
		counter = 0;		
	}
	
	protected void HighScore() {
		Intent myIntent = new Intent(this, HighScoreActivity.class);
		myIntent.putExtra("PointString", myView.getPoint());
		startActivity(myIntent);
	}
	
	private Runnable Timer_Time = new Runnable() {
		public void run() {
			if(running) {
				counter++;
		    	timertext.setText("Timer: "+ counter); 
			}
		}
	};
	
	private Runnable Timer_Move = new Runnable() {
		public void run() {
			if(running) {
				switch (direction){
				case 1:
					myView.moveRight(3, 1);
			        break;
				case 2:
					myView.moveLeft(3, 1);
			        break;
				case 3:
					myView.moveUp(3, 1);
			        break;
				case 4: 
					myView.moveDown(3, 1);
			        break;
				}
			}
		}
	};

	public void onClick(View v) {
		
		myView = (MyView) findViewById(R.id.gameView);
		switch(v.getId()) {
		
		case R.id.right:
			running = true;
			direction = 1;			
        break;
        
		case R.id.left:
			running = true;
			direction = 2;
        break;
        
		case R.id.up:
			running = true;
			direction = 3;
        break;
        
		case R.id.down:
			running = true;
			direction = 4;
        break;
	    }
	}
	
	private Runnable Timer_Ghost = new Runnable() {
	    public void run() {    	
	    	if (gcounter==0){
	    		
	    		gcounter = 30;
	    		Random Ghost = new Random();
	    		GhostDirection = Ghost.nextInt(4) + 1;            
	    	}
	    	gcounter--;
	    	
	    	if (running)
	    	{
	    		 switch (GhostDirection){
	            
	             case 1:  myView.moveUp(speed, 2);
	             	break;
	             case 2:  myView.moveDown(speed, 2);
	                break;
	             case 3:  myView.moveLeft(speed, 2);
	                break;
	             case 4:  myView.moveRight(speed, 2);
	                break;
	    		 }        
	           }
	    	}
	    };
	    
    public void levels(){
    	level ++; 
    	speed ++; 
    	leveltext.setText("Level " + level);
    	running = false;	    	
    }

    public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.play:	
			running = true;
		return true;
		
		case R.id.pause:
			running = false;
		return true;
		
		case R.id.reset:
			resetAll();
		return true;
		
		case R.id.share:
			sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
			name = prefs.getString("name", "");
			int bedstehighScore = prefs.getInt("highScore",  0);
			sendIntent.putExtra(Intent.EXTRA_TEXT, myView.getPoint());
			sendIntent.setType("text/plain");
			startActivity(sendIntent);
		return true;
		
		case R.id.highscore:				
			HighScore();
		return true;
			
		case R.id.action_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivityForResult(intent, 1);
		return true;
		}
	return super.onOptionsItemSelected(item);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
