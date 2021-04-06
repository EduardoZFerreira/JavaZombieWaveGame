package com.boss.entities;

import java.awt.image.BufferedImage;

import com.boss.main.Game;
import com.boss.world.World;

public class Enemy extends Entity {

	// TODO: Fix flickery animation
	private double speed = 1;
	
	public Enemy(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);
		// TODO Auto-generated constructor stub
	}

	 public void tick() {
		 if((int)x < Game.player.getX() && World.isFree((int)(x + speed), (int)y)) {
			 x += speed;
		 } else if((int)x > Game.player.getX() && World.isFree((int)(x - speed), (int)y)) {
			 x -= speed;
		 }
		 if((int)y < Game.player.getY() && World.isFree((int)x, (int)(y + speed))) {
			 y += speed;
		 } else if((int)y > Game.player.getY() && World.isFree((int)x, (int)(y - speed))) {
			 y -= speed;
		 }
		 
		 
	 }
	
}
