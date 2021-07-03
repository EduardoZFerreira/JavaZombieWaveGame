package com.boss.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.boss.main.Game;
import com.boss.world.Camera;
import com.boss.world.World;

public class Player extends Entity {

	private boolean right, up, left, down;
	
	private double speed = 1.5;
	
	private final int right_dir = 0;
	private final int left_dir = 1;
	private final int up_dir = 2;
	private final int down_dir = 3;
	private int dir = right_dir;
	private final int weaponSpriteOffsetLeftRight = 8;
	private final int weaponSpriteOffsetUpDown = 4;

	private int frame = 0;
	private final int maxSprites = 3;
	private final int maxFrames = 5;
	private int spriteAnimationIndex = 0;
	private boolean moved = false;
	private BufferedImage[] rightPlayerSprites;
	private BufferedImage[] leftPlayerSprites;
	private BufferedImage[] upPlayerSprites;
	private BufferedImage[] downPlayerSprites;
	
	private boolean hasGun = false;
	
	public double health = 100;
	public int ammo = 0;
	public int combo = 0;
	public int score = 0;
		
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
			downPlayerSprites[i] = Game.spritesheet.getSprite(2 * World.TILE_SIZE, i * World.TILE_SIZE, World.TILE_SIZE, World.TILE_SIZE);
			upPlayerSprites[i] = Game.spritesheet.getSprite(3 * World.TILE_SIZE, i * World.TILE_SIZE, World.TILE_SIZE, World.TILE_SIZE);
			rightPlayerSprites[i] = Game.spritesheet.getSprite(4 * World.TILE_SIZE, i * World.TILE_SIZE, World.TILE_SIZE, World.TILE_SIZE);
			leftPlayerSprites[i] = Game.spritesheet.getSprite(5 * World.TILE_SIZE, i * World.TILE_SIZE, World.TILE_SIZE, World.TILE_SIZE);
		}
	}
	
	public void tick() {
		if (isTakingDamage()) {
			health -= calculateDamageTaken();
		}
		checkDeath();
		moved = false;
		move();
		checkCollisionLifePack();
		checkCollisionAmmo();
		checkCollisionGun();
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
			Entity current = Game.entities.get(i);
			if (current instanceof Weapon) {
				if (isColliding(this, current)) {
					hasGun = true;
					Game.entities.remove(current);
				}
			}
		}
	}
		
	public void checkCollisionAmmo() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if (current instanceof Bullet) {
				if (isColliding(this, current)) {
					ammo += 10;
					Game.entities.remove(current);
				}
			}
		}
	}
	
	public void checkCollisionLifePack() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if (current instanceof Lifepack) {
				if (isColliding(this, current) && health < 100) {
					health += 10;
					if (health > 100) {
						health = 100;
					}
					Game.entities.remove(current);
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

			int dx = getGunshotXDirection();
			int dy = getGunshotYDirection();
			int offsetY = getGunshotOffsetY();
			int offsetX = getGunshotOffsetX();

			Gunshot gunshot = new Gunshot((int)getX() + offsetX, (int)getY() + offsetY, 3, 3, null, dx, dy);
			Game.gunshots.add(gunshot);
		}
	}

	public void shootWithMouse(int mouseX, int mouseY) {
		if (hasGun && ammo > 0) {
			ammo--;

			int offsetY = getGunshotOffsetY();
			int offsetX = getGunshotOffsetX();

			double angle = Math.atan2(mouseY - (getY() + offsetY - Camera.y), mouseX - (getX() + offsetX - Camera.x));
			double dx = Math.cos(angle);
			double dy = Math.sin(angle);

			Gunshot gunshot = new Gunshot((int)getX() + offsetX, (int)getY() + offsetY, 3, 3, null, dx, dy);
			Game.gunshots.add(gunshot);
		}
	}

	private int getGunshotOffsetX() {
		int offset = 0;
		if (dir == right_dir) {
			offset = width + (weaponSpriteOffsetLeftRight - 3);
		} else if (dir == left_dir) {
			offset = - (weaponSpriteOffsetLeftRight);
		}else if (dir == down_dir) {
			offset = width / 5;
		} else if (dir == up_dir) {
			offset = width - weaponSpriteOffsetUpDown;
		}
		return offset;
	}

	private int getGunshotOffsetY() {
		int offset = 0;
		if (dir == right_dir) {
			offset = height / 2;
		} else if (dir == left_dir) {
			offset = height / 2;
		}else if (dir == down_dir) {
			offset = height + weaponSpriteOffsetUpDown;
		}
		return offset;
	}

	private int getGunshotYDirection() {
		if (dir == down_dir) {
			return  1;
		} else if (dir == up_dir) {
			return -1;
		} else {
			return 0;
		}
	}

	private int getGunshotXDirection() {
		if (dir == right_dir) {
			return 1;
		} else if (dir == left_dir) {
			return -1;
		} else {
			return 0;
		}
	}

	private int calculateDamageTaken() {
		int totalDamage = 0;
		Rectangle self = new Rectangle((int) x + maskx, (int) y + masky, maskw, maskh);
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			Rectangle hit = new Rectangle((int)e.getX() + maskx, (int)e.getY() + masky, e.getWidth(), e.getHeigth());
			if (hit.intersects(self)) {
				if(Game.rand.nextInt(100) < 10){
					combo = 0;
					totalDamage += e.DAMAGE;
				}
			}
		}
		return totalDamage;
	}


	private boolean isTakingDamage() {
		Rectangle self = new Rectangle((int) x + maskx, (int) y + masky, maskw, maskh);
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			Rectangle hit = new Rectangle((int)e.getX() + maskx, (int)e.getY() + masky, e.getWidth(), e.getHeigth());
			if (hit.intersects(self)) {
				return true;
			}
		}
		return false;
	}

	private void checkDeath() {
		if (health <= 0) {
			Game.load();
		}
	}
}
