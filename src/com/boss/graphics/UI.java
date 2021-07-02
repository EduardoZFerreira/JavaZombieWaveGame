package com.boss.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.boss.main.Game;

public class UI {

	public void render(Graphics g) {
		
		g.setColor(getLifebarColor((int)Game.player.health));
		g.fillRect(10, 8, 150, 20);
		
		g.setColor(Color.WHITE);

		g.setFont(new Font("arial", Font.BOLD, 16));
		g.drawString(getLifeMessage((int)Game.player.health), 15, 24);
		
		g.setFont(new Font("arial", Font.BOLD, 16));
		g.drawString("Ammo: " + Game.player.ammo, (Game.WIDTH * Game.SCALE) - (100), 20);
	}
	
	public Color getLifebarColor(int life) {
		if (life <= 70 && life > 20) {
			return Color.ORANGE;
		} else if (life <= 20) {
			return Color.RED;
		}
		return Color.GREEN;
	}
	
	public String getLifeMessage(int life) {
		if (life <= 70 && life > 20) {
			return "Caution";
		} else if (life <= 20) {
			return "Danger";
		}
		return "Fine";
	}
	
}
