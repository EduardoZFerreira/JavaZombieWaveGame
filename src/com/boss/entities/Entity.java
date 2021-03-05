package com.boss.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Entity {

	private int width, heigth, x, y;
	
	private BufferedImage sprite;
	
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
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
		g.drawImage(sprite, getX(), getY(), null);
	}
	
	public void tick() {
		
	}
	
}
