package com.boss.entities;

import com.boss.main.Game;

import java.awt.image.BufferedImage;

public class Bullet  extends Entity {

	public Bullet(int x, int y, int width, int heigth, BufferedImage sprite, Game game) {
		super(x, y, width, heigth, sprite, game);
	}

}
