package com.boss.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.boss.main.Game;
import com.boss.world.Camera;
import com.boss.world.World;

import javax.swing.*;

public class Player extends Entity {

	private boolean right, up, left, down;
	
	private double speed = 1.5;
	
	private final int right_dir = 0;
	private final int left_dir = 1;
	private final int up_dir = 2;
	private final int down_dir = 3;
	private int dir = right_dir;
	private final int weaponSpriteOffsetLeftRight = 8;
	private int weaponSpriteOffsetUpDown = 4;

	private int frame = 0;
	private final int maxSprites = 3;
	private int maxFrames = 5;
	private int spriteAnimationIndex = 0;
	private boolean moved = false;
	private BufferedImage[] rightPlayerSprites;
	private BufferedImage[] leftPlayerSprites;
	private BufferedImage[] upPlayerSprites;
	private BufferedImage[] downPlayerSprites;
	
	private boolean hasGun = false;
	
	public double life = 100;
	public int ammo = 0;
		
	public Player(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);		
		loadSprites();
	}
	
	private void loadSprites() {
		rightPlayerSprites = new BufferedImage[maxSprites];
		leftPlayerSprites = new BufferedImage[maxSprites];
		upPlayerSprites = new BufferedImage[maxSprites];
		downPlayerSprites = new BufferedImage[maxSprites];
		
		for(int i = 0; i < maxSprites; i++) {
			downPlayerSprites[i] = Game.spritesheet.getSprite(2 * 16, i * 16, 16, 16);
			upPlayerSprites[i] = Game.spritesheet.getSprite(3 * 16, i * 16, 16, 16);
			rightPlayerSprites[i] = Game.spritesheet.getSprite(4 * 16, i * 16, 16, 16);
			leftPlayerSprites[i] = Game.spritesheet.getSprite(5 * 16, i * 16, 16, 16);
		}
	}
	
	public void tick() {
		moved = false;
		move();
		checkCollisionLifePack();
		checkCollisionAmmo();
		checkCollisionGun();
		// TODO: Move to camera class, review architecture
		Camera.x = Camera.clamp((int)x - (Game.WIDTH / 2), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp((int)y - (Game.HEIGHT / 2), 0, World.HEIGHT * 16 - Game.HEIGHT);
	}
	
	public void render(Graphics g) {
		animateMovement(g);
	}
	
	public void increaseFrame() {
		if(moved) {
			frame++;
			if(frame == maxFrames) {
				
				frame = 0;
				spriteAnimationIndex++;
				if(spriteAnimationIndex >= maxSprites) {
					spriteAnimationIndex = 0;
				}
			}
		}
	}
	
	public void animateMovement(Graphics g) {
		if(dir == right_dir) {
			g.drawImage(rightPlayerSprites[spriteAnimationIndex], (int)getX() - Camera.x, (int)getY() - Camera.y, null);
			if (hasGun) {
				g.drawImage(Entity.WEAPON_EN, (int)getX() - Camera.x + weaponSpriteOffsetLeftRight, (int)getY() - Camera.y, null);
			}
		} else if(dir == left_dir) {
			g.drawImage(leftPlayerSprites[spriteAnimationIndex], (int)getX() - Camera.x, (int)getY() - Camera.y, null);
			if (hasGun) {
				g.drawImage(Entity.WEAPON_LEFT, (int)getX() - Camera.x - weaponSpriteOffsetLeftRight, (int)getY() - Camera.y, null);
			}
		} else if(dir == up_dir) {
			if (hasGun) {
				g.drawImage(Entity.WEAPON_UP, (int)getX() - Camera.x + weaponSpriteOffsetUpDown, (int)getY() - Camera.y, null);
			}
			g.drawImage(upPlayerSprites[spriteAnimationIndex], (int)getX() - Camera.x, (int)getY() - Camera.y, null);
		} else if(dir == down_dir) {
			g.drawImage(downPlayerSprites[spriteAnimationIndex], (int)getX() - Camera.x, (int)getY() - Camera.y, null);
			if (hasGun) {
				g.drawImage(Entity.WEAPON_DOWN, (int)getX() - Camera.x - weaponSpriteOffsetUpDown, (int)getY() - Camera.y + 8, null);
			}
		} 
	}
	

	public void checkCollisionGun() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof Weapon) {
				if (isColliding(this, atual)) {
					hasGun = true;
					Game.entities.remove(atual);
				}
			}
		}
	}
		
	public void checkCollisionAmmo() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof Bullet) {
				if (isColliding(this, atual)) {
					ammo += 10;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionLifePack() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof Lifepack) {
				if (isColliding(this, atual) && life < 100) {
					life += 10;
					if (life > 100) {
						life = 100;
					}
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void move() {
		if(isRight() && World.isFree((int)(x + speed), (int)y)) {
			moved = true;
			dir = right_dir;
			setX(getX() + speed);
		} else if(isLeft() && World.isFree((int)(x - speed), (int)y)) {
			moved = true;
			dir = left_dir;
			setX(getX() - speed);
		}
		
		if(isUp() && World.isFree((int)x, (int)(y - speed))) {
			moved = true;
			dir = up_dir;
			setY(getY() - speed);
		} else if(isDown() && World.isFree((int)x, (int)(y + speed))) {
			moved = true;
			dir = !isRight() && !isLeft() ? down_dir : dir;
			setY(getY() + speed);
		}
		increaseFrame();
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

	public void shoot() {
		if (hasGun && ammo > 0) {
			ammo--;

			int dx = 0;
			int dy = 0;
			int offsetY = 0;
			int offsetX = 0;

			if (dir == right_dir) {
				dx = 1;
				offsetY = width / 2;
			} else if (dir == left_dir) {
				dx = -1;
				offsetY = width / 2;
			}else if (dir == down_dir) {
				dy = 1;
				offsetX = width / 5;
			} else if (dir == up_dir) {
				dy = -1;
				offsetX = width + (WEAPON_UP.getWidth() / 2);
				offsetY = - WEAPON_UP.getHeight();
			}

			Gunshot gunshot = new Gunshot((int)getX() + offsetX, (int)getY() + offsetY, 3, 3, null, dx, dy);
			Game.gunshots.add(gunshot);
		}
	}

	public void shootWithMouse(int mouseX, int mouseY) {
		if (hasGun && ammo > 0) {
			ammo--;

			int offsetY = 0;
			int offsetX = 0;

			if (dir == right_dir) {
				offsetY = height / 2;
				offsetX = width + (weaponSpriteOffsetLeftRight - 3);
			} else if (dir == left_dir) {
				offsetY = height / 2;
				offsetX = - (weaponSpriteOffsetLeftRight);

			}else if (dir == down_dir) {
				offsetY = height + weaponSpriteOffsetUpDown;
				offsetX = width / 5;
			} else if (dir == up_dir) {
				offsetX = width - weaponSpriteOffsetUpDown;
			}

			double angle = Math.atan2(mouseY - (getY() + offsetY - Camera.y), mouseX - (getX() + offsetX - Camera.x));
			double dx = Math.cos(angle);
			double dy = Math.sin(angle);

			Gunshot gunshot = new Gunshot((int)getX() + offsetX, (int)getY() + offsetY, 3, 3, null, dx, dy);
			Game.gunshots.add(gunshot);
		}
	}

}
