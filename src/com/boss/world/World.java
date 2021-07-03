package com.boss.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.boss.entities.*;
import com.boss.main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;

	Game game;
	
	public World(String path, Game game) {
		this.game = game;
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			tiles = new Tile[pixels.length];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			WIDTH =  map.getWidth();
			HEIGHT =  map.getHeight();
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int color = pixels[ xx + (yy * map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_FLOOR);
					if(color == 0xFF000000) {
						// Floor
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_FLOOR);
					} else if(color == 0xFFFFFFFF) {
						// Wall
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_WALL);
					} else if(color == 0xFF00137F) {
						game.setPlayerSpawnPoint(xx * TILE_SIZE, yy * TILE_SIZE);
					} else if(color == 0xFFFF0000) {
						Enemy enemy = new Enemy(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.ENEMY_EN, game);
						Game.entities.add(enemy);
						Game.enemies.add(enemy);
						
					}  else if(color == 0xFFFFC85B) {
						// Weapon
						Game.entities.add(new Weapon(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.WEAPON_EN, game));
						
					} else if(color == 0xFFFF006E) {
						// Lifepack
						Game.entities.add(new Lifepack(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.LIFEPACK_EN, game));
					} else if(color == 0xFFFFD800) {
						// Bullet
						Game.entities.add(new Bullet(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.BULLET_EN, game));
					} 
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xNext, int yNext) {
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;
		
		int x2 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;
		
		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext + TILE_SIZE - 1) / TILE_SIZE;
		
		int x4 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (yNext + TILE_SIZE - 1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));		
	}
	
	public void render(Graphics g) {
		int startX = Camera.x / TILE_SIZE;
		int startY = Camera.y / TILE_SIZE;
		
		int finalX = startX + (Game.WIDTH / TILE_SIZE);
		int finalY = startY + (Game.HEIGHT / TILE_SIZE);
		
		for(int xx = startX; xx <= finalX; xx++) {
			for(int yy = startY; yy <= finalY; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
					continue;
				}
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}
	}
	
}
