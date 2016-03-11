package org.example.canvasdemo;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class MyView extends View{
	
	Bitmap pacimg = BitmapFactory.decodeResource(getResources(), R.drawable.pacmanright);
	Bitmap ghostimg = BitmapFactory.decodeResource(getResources(), R.drawable.ghost);
	
	ArrayList<GoldCoin> coins = new ArrayList<GoldCoin>();
	
	Enemy ghost = new Enemy(ghostimg, 200, 200, 200);

    int pacx = 50;
    int pacy = 50;   
    int pacw = pacimg.getWidth()/2;
	int pach = pacimg.getHeight()/2;
	
	int ghostw = ghostimg.getWidth()/2;
	int ghosth =  ghostimg.getHeight()/2;
    int h = 500;
    int w = 500;
    
    int point = 0;
    TextView textview;
    TextView timertext;
    TextView leveltext;
    Timer ghosttimer;
    
    MainActivity main;
    
    public void SetActivity(MainActivity main){
    	this.main = main;
    }
    
    public void moveRight(int x , int who) {
    	if (who == 1 && pacx + x + pacimg.getWidth()<w){
    		pacx = pacx + x;
    	pacimg = BitmapFactory.decodeResource(getResources(), R.drawable.pacmanright);
    }
	    if (who == 2 && ghost.xEnemy + x + ghost.bitmap.getWidth()<w){
			ghost.xEnemy = ghost.xEnemy + x;
		ghostimg = BitmapFactory.decodeResource(getResources(), R.drawable.ghostright);
	    }
    	invalidate();
    }
    
    public void moveLeft(int x, int who) {
    	if (who == 1 && pacx > 0){
    		pacx = pacx - x;
    	pacimg = BitmapFactory.decodeResource(getResources(), R.drawable.pacmanleft);
    	}
    	
    	if (who == 2 && ghost.xEnemy > 0){
			ghost.xEnemy = ghost.xEnemy - x;
		ghostimg = BitmapFactory.decodeResource(getResources(), R.drawable.ghostleft);
	    }
    	
    	invalidate();
    }
    
    public void moveUp(int y, int who) {
    	if (who == 1 && pacy > 0){
    		pacy = pacy - y;
    	pacimg = BitmapFactory.decodeResource(getResources(), R.drawable.pacmanup);
	    }
		
		if (who == 2 && ghost.yEnemy > 0){
			ghost.yEnemy = ghost.yEnemy - y;
		ghostimg = BitmapFactory.decodeResource(getResources(), R.drawable.ghost);
	    }
    	invalidate();
    }
    
    public void moveDown(int y, int who) {
    	if (who == 1 && pacy + y + pacimg.getHeight()<h){
    		pacy = pacy + y;
		pacimg = BitmapFactory.decodeResource(getResources(), R.drawable.pacmandown);
		}
		if (who == 2 && ghost.yEnemy + y + ghost.bitmap.getHeight()<h){
			ghost.yEnemy = ghost.yEnemy + y;
			ghostimg = BitmapFactory.decodeResource(getResources(), R.drawable.ghostdown);
    		}
    	invalidate();
    }
    
    
    public void reset() {
      	pacx = 50;
        pacy = 50;
        ghost = new Enemy(ghostimg, 200, 200, 100);
        pacimg = BitmapFactory.decodeResource(getResources(), R.drawable.pacmanright);
    	invalidate();
    }
    
    
    public void nextLevel() {
      	pacx = 50;
        pacy = 50;
        placeCoins();
        ghost = new Enemy(ghostimg, 200, 200, 200);
        pacimg = BitmapFactory.decodeResource(getResources(), R.drawable.pacmanright);
    	invalidate();
    	main.levels();
    }
  
    
	public MyView(Context context) {
		super(context);
	}
		
	public MyView(Context context, AttributeSet attrs) {
		super(context,attrs);
	}
		
	public MyView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context,attrs,defStyleAttr);
	}
	
	
	public void placeCoins() {
		Random random = new Random();
		coins.clear();
		for(int x = 0; x < 10; x ++) {
			int Xx = random.nextInt(w - 50);
			int Yx = random.nextInt(h - 50);
			
			if(Xx < 50) {
				Xx = 50;
			}
			
			if(Yx < 50) {
				Yx = 50;
			}
			
			if (distance(pacx + pacw, pacy + pach, Xx, Yx) < 100 ){
				Xx =+ 100;
				Yx =+ 100;
			 	}
			GoldCoin coin = new GoldCoin(Xx, Yx, false);
		    coins.add(coin);
		}
	}
	
	
	public void setTextView(TextView textView) {
		this.textview = textView;
	}
	
	public void setTimerText(TextView timertext) {
		this.timertext = timertext;
	}
	
	public void setTextLevel(TextView leveltext) {
		this.leveltext = leveltext;
	}
	
	public void setGhostTimer(Timer timer){
		ghosttimer= timer;		
	}
	
	public int getPoint() {
		return point;
	}
	
	public void setPoint( int points) {
		point = points;
	}
	
	public boolean AllTaken () {
		for (GoldCoin GC: coins) {
			if(GC.getTaken() == false) {
				return false;
				}
			}
		return true;
	}
	
	
	public double distance(int x1, int y1, int x2, int y2) {
		return Math.sqrt(((x2-x1) * (x2-x1)) + ((y2-y1) * (y2-y1)));
	}
	
	@Override
	protected void onDraw(Canvas canvas) {  
		//Here we get the height and weight
		h = canvas.getHeight();
		w = canvas.getWidth();
	//	System.out.println("h = "+h+", w = "+w);
		
		//Making a new paint object
		Paint paint = new Paint();
		Paint paint2 = new Paint();
		paint.setColor(0xffF9C126);
		
		if(AllTaken()) {
			nextLevel();
		}		
		
		for (GoldCoin GC: coins) {
		if(GC.getTaken() == false) {
			canvas.drawCircle(GC.getX(), GC.getY(), 10, paint);
			}
		
		if (distance(pacx + pacw, pacy + pach, GC.getX(), GC.getY()) < 50 ){
			if(!GC.getTaken()) {
				GC.setTaken(true);
				point++;
				textview.setText("Points: " + point);
				}
		 	}	
		
		if (distance(pacx + pacw, pacy + pach, ghost.getX() + ghostw, ghost.getY() + ghosth) < 75 ){
			main.onStop();
			main.HighScore();
			pacimg = BitmapFactory.decodeResource(getResources(), R.drawable.pacmandead);
		 	}	
		
		canvas.drawBitmap(pacimg, pacx, pacy, paint);
		canvas.drawBitmap(ghostimg, ghost.xEnemy, ghost.yEnemy, paint2);
		}
	
		
		super.onDraw(canvas);
	}
}
