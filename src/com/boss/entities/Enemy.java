package com.boss.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.boss.main.Game;
import com.boss.world.Camera;
import com.boss.world.World;

public class Enemy extends Entity {

	// TODO: Fix flickery animation
	private double speed = 1;
	
	private int maskx = 0, masky = 0, maskw = 16, maskh = 16;
	
	public Enemy(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);
		// TODO Auto-generated constructor stub
	}

	 public void tick() {	 
		 if(!isCollidingWithPlayer()) {
			 if((int)x < Game.player.getX() 
					 && World.isFree((int)(x + speed), (int)y)
					 && !isColliding((int)(x + speed), (int)y)) {
					 x += speed;
				 } else if((int)x > Game.player.getX() 
						 && World.isFree((int)(x - speed), (int)y)
						 && !isColliding((int)(x - speed), (int)y)) {
					 x -= speed;
				 }
				 if((int)y < Game.player.getY() 
					 && World.isFree((int)x, (int)(y + speed))
					 && !isColliding((int)x, (int)(y + speed))) {
					 y += speed;
				 } else if((int)y > Game.player.getY() 
						 && World.isFree((int)x, (int)(y - speed))
						 && !isColliding((int)x, (int)(y - speed))) {
					 y -= speed;
				 }
		 } else {
			 if(Game.rand.nextInt(100) < 10) {
				 Game.player.life--;
			 }
			 
			 if(Game.player.life <= 0) {
				Game.load();
			 }
		 }
	 }
	 
	 public boolean isCollidingWithPlayer() {
		 Rectangle enemyCurrent = new Rectangle((int) x + maskx, (int) y + masky, maskw, maskh);
		 Rectangle player = new Rectangle((int) Game.player.getX(), (int) Game.player.getY(), World.TILE_SIZE, World.TILE_SIZE);
		 return enemyCurrent.intersects(player);
	 }
	 
	 public boolean isColliding(int xNext, int yNext) {
		 Rectangle enemyCurrent = new Rectangle(xNext + maskx, yNext + masky, maskw, maskh);
		 for (int i = 0; i < Game.enemies.size(); i++) {
			 Enemy e = Game.enemies.get(i);
			 if (e == this) {
				 continue;
			 }
			 Rectangle targetEnemy = new Rectangle((int)e.getX() + maskx, (int)e.getY() + masky, maskw, maskh);
			 if(enemyCurrent.intersects(targetEnemy)) {
				 return true;
			 }
		 }		 
		 return false;
	 }
	 
	 public void render(Graphics g) {
		 super.render(g);
	 }
	 
}
