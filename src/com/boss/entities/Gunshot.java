package com.boss.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.boss.main.Game;
import com.boss.world.Camera;

public class Gunshot extends Entity{

	private final double dy;
	private final double dx;
	private final double speed = 4;
	
	private final int maxRange = 30;
	private int range = 0;

	public final int DAMAGE = 35;

	public boolean piercingDamage = false;

	public Gunshot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy, Game game) {
		super(x, y, width, height, sprite, game);
		this.dx = dx; 
		this.dy = dy;
	}
	
	public void tick() {
		x += dx * speed;
		y += dy * speed;
		range++;
		if (range == maxRange) {
			game.gunshots.remove(this);
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval((int)x - Camera.x, (int)y - Camera.y, width, height);
	}
}
