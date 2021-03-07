package com.boss.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.boss.main.Game;
import com.boss.world.Camera;

public class Entity {

	protected int width, height;
	protected double x, y;
	
	protected BufferedImage sprite;
	
	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(6 * 16, 0, 16, 16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(7 * 16, 0, 16, 16);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(6 * 16, 16, 16, 16);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(8 * 16, 0, 16, 16);
	
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
		return height;
	}

	public void setHeigth(int heigth) {
		this.height = heigth;
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
		g.drawImage(sprite, (int)getX() - Camera.x, (int)getY() - Camera.y, null);
	}
	
	public void tick() {
		
	}
	
}
