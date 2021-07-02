package com.boss.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.boss.main.Game;
import com.boss.world.Camera;
import com.boss.world.World;

public class Entity {

	protected int width, height;
	protected double x, y;
		
	protected BufferedImage sprite;

	protected int maskx = 0, masky = 0, maskw = World.TILE_SIZE, maskh = World.TILE_SIZE;

	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(6 * World.TILE_SIZE, 0, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(7 * World.TILE_SIZE, 0, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(6 * World.TILE_SIZE, World.TILE_SIZE, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(8 * World.TILE_SIZE, 0, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage WEAPON_LEFT = Game.spritesheet.getSprite(7 * World.TILE_SIZE, 1 * World.TILE_SIZE, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage WEAPON_UP = Game.spritesheet.getSprite(7 * World.TILE_SIZE, 2 * World.TILE_SIZE, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage WEAPON_DOWN = Game.spritesheet.getSprite(7 * World.TILE_SIZE, 3 * World.TILE_SIZE, World.TILE_SIZE, World.TILE_SIZE);

	public Entity(int x, int y, int width, int heigth, BufferedImage sprite) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeigth(heigth);
		setSprite(sprite);
		setMask(0, 0, width, heigth);
	}

	public void setMask(int maskX, int maskY, int maskWidth, int maskHeight) {
		maskx = maskX;
		masky = maskY;
		maskw = maskWidth;
		maskh = maskHeight;
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
	
	public boolean isColliding (Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle((int) e1.getX() + maskx, (int) e1.getY() + masky, maskw, maskh);
		Rectangle e2Mask = new Rectangle((int) e2.getX() + maskx, (int) e2.getY() + masky, maskw, maskh);
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g)
	{
		g.drawImage(sprite, (int)getX() - Camera.x, (int)getY() - Camera.y, null);
	}
	
	public void tick() {
		
	}

	public void hitboxDebug(Graphics g, Color color, int x, int y, int width, int height) {
		g.setColor(color);
		g.drawRect(x - Camera.x, y - Camera.y, width, height);
	}
}
