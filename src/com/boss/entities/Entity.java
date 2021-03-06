package com.boss.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Entity {

	protected int width, heigth;
	protected double x, y;
	
	protected BufferedImage sprite;
	
	public Entity(int x, int y, int width, int heigth, BufferedImage sprite) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeigth(heigth);
		setSprite(sprite);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeigth() {
		return heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(sprite, (int)getX(), (int)getY(), null);
	}
	
	public void tick() {
		
	}
	
}
