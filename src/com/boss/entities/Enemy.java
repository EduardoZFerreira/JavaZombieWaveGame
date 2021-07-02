package com.boss.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.boss.main.Game;
import com.boss.world.World;

public class Enemy extends Entity {

	// TODO: Fix flickery animation
	private double speed = 1;


	private int health = 100;

	public final int DAMAGE = 1;

	public Enemy(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);
	}

	 public void tick() {
		if (isTakingDamage()) {
			health -= calculateDamageTaken();
		}
	 	checkDeath();
		if (!isCollidingWithPlayer()) {
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

	 private int calculateDamageTaken() {
		int totalDamage = 0;
		Rectangle self = new Rectangle((int) x + maskx, (int) y + masky, maskw, maskh);
		for (int i = 0; i < Game.gunshots.size(); i++) {
			Gunshot g = Game.gunshots.get(i);
			Rectangle hit = new Rectangle((int)g.getX() + maskx, (int)g.getY() + masky, g.getWidth(), g.getHeigth());
			if (hit.intersects(self)) {
				totalDamage += g.DAMAGE;
			}
		}
		return totalDamage;
	 }


	private boolean isTakingDamage() {
		Rectangle self = new Rectangle((int) x + maskx, (int) y + masky, maskw, maskh);
		for (int i = 0; i < Game.gunshots.size(); i++) {
			Gunshot g = Game.gunshots.get(i);
			Rectangle hit = new Rectangle((int)g.getX() + maskx, (int)g.getY() + masky, g.getWidth(), g.getHeigth());
			if (hit.intersects(self)) {
				return true;
			}
		}
		return false;
	}

	 private void checkDeath() {
		if (health <= 0) {
			dropLoot();
			Game.enemies.remove(this);
			Game.entities.remove(this);
		}
	 }

	 private void dropLoot() {
		 if (Game.rand.nextInt(100) < 50) {
		 	int itemToDrop = Game.rand.nextInt(10);
		 	if (itemToDrop > 5) {
				Game.entities.add(new Lifepack((int)getX(), (int)getY(), width, height, Entity.LIFEPACK_EN));
			} else {
				Game.entities.add(new Bullet((int)getX(), (int)getY(), width, height, Entity.BULLET_EN));
			}
		 }
	 }

}
