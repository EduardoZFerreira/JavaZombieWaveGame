package com.boss.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.boss.main.Game;
import com.boss.world.Camera;
import com.boss.world.World;

public class Player extends Entity {

	private boolean right, up, left, down;
	
	private double speed = 1.5;
	
	private int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	private int dir = right_dir;
	
	private int frame = 0, maxSprites = 3, maxFrames = 5, spriteAnimationIndex = 0;
	private boolean moved = false;
	private BufferedImage[] rightPlayerSprites;
	private BufferedImage[] leftPlayerSprites;
	private BufferedImage[] upPlayerSprites;
	private BufferedImage[] downPlayerSprites;
	
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
		} else if(dir == left_dir) {
			g.drawImage(leftPlayerSprites[spriteAnimationIndex], (int)getX() - Camera.x, (int)getY() - Camera.y, null);
		} else if(dir == up_dir) {
			g.drawImage(upPlayerSprites[spriteAnimationIndex], (int)getX() - Camera.x, (int)getY() - Camera.y, null);
		} else if(dir == down_dir) {
			g.drawImage(downPlayerSprites[spriteAnimationIndex], (int)getX() - Camera.x, (int)getY() - Camera.y, null);
		} 
	}
	
	public void move() {
		if(isRight()) {
			moved = true;
			dir = right_dir;
			setX(getX() + speed);
		} else if(isLeft()) {
			moved = true;
			dir = left_dir;
			setX(getX() - speed);
		}
		
		if(isUp()) {
			moved = true;
			dir = up_dir;
			setY(getY() - speed);
		} else if(isDown()) {
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

}
