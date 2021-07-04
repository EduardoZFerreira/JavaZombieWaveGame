package com.boss.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.boss.main.Game;

public class Tile {
	public static BufferedImage TILE_FLOOR;
	public static BufferedImage TILE_WALL;
	
	private final BufferedImage sprite;
	private final int x;
    private final int y;
    private Game game;
	
	public Tile(int x, int y, BufferedImage sprite, Game game) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.game = game;
		loadSprites();
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}

	private void loadSprites() {
		TILE_FLOOR = game.spritesheet.getSprite(0, 0, World.TILE_SIZE, World.TILE_SIZE);
		TILE_WALL = game.spritesheet.getSprite(World.TILE_SIZE, 0, World.TILE_SIZE, World.TILE_SIZE);
	}
}
