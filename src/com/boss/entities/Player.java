package com.boss.entities;

import java.awt.image.BufferedImage;

public class Player extends Entity {

	private boolean right, up, left, down;
	private double speed = 2;
	
	public Player(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);		
	}
	
	
	public void tick() {
		Move();
	}

	public void Move() {
		if(isRight()) {
			setX(getX() + speed);
		} else if(isLeft()) {
			setX(getX() - speed);
		}
		
		if(isUp()) {
			setY(getY() - speed);
		} else if(isDown()) {
			setY(getY() + speed);
		}
	}

	public boolean isRight() {
		return right;
	}


	public void setRight(boolean right) {
		this.right = right;
	}


	public boolean isLeft() {
		return left;
	}


	public void setLeft(boolean left) {
		this.left = left;
	}


	public boolean isDown() {
		return down;
	}


	public void setDown(boolean down) {
		this.down = down;
	}


	public boolean isUp() {
		return up;
	}


	public void setUp(boolean up) {
		this.up = up;
	}


	public double getSpeed() {
		return speed;
	}


	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
