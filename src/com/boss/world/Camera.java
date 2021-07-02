package com.boss.world;

import com.boss.entities.Entity;
import com.boss.main.Game;

public class Camera {
	public static int x, y;
	
	private static int clamp(int currentValue, int minValue, int maxValue) {
		if(currentValue < minValue) {
			currentValue = minValue;
		} 
		
		if(currentValue > maxValue) {
			currentValue = maxValue;
		}
		return currentValue;
	}

	public static void followActor(Entity cameraTarget) {
        x = clamp((int)cameraTarget.getX() - (Game.WIDTH / 2), 0, World.WIDTH * World.TILE_SIZE - Game.WIDTH);
        y = clamp((int)cameraTarget.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * World.TILE_SIZE - Game.HEIGHT);
    }
}
