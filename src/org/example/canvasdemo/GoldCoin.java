package org.example.canvasdemo;

public class GoldCoin {
	private int x;
	private int y;
	private boolean taken;

	
	public GoldCoin(int x, int y, boolean taken) {
		this.x = x;
		this.y = y;
		this.taken = taken;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return this.x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		 return this.y;
	}
	
	public void setTaken(boolean taken) {
		this.taken = taken;
	}
	
	public boolean getTaken() {
		 return this.taken;
	}
	
	@Override
	public String toString() {
		return x + "" + y + "" + taken;
	}
}
