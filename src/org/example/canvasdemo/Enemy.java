package org.example.canvasdemo;

import android.graphics.Bitmap;

public class Enemy {
	
	int xEnemy, yEnemy;
	int direction;
	Bitmap bitmap;
	
	public Enemy (Bitmap ghostblue, int xE, int yE, int direction) {
		this.bitmap = ghostblue;
		this.xEnemy = xE;
		this.yEnemy = yE;
		this.direction = direction;		
	}

	public int getX() {
		return xEnemy;
	}

	public void setX(int xE) {
		this.xEnemy = xE;
	}

	public int getY() {
		return yEnemy;
	}

	public void setY(int yE) {
		this.yEnemy = yE;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
}


