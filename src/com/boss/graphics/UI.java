package com.boss.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.boss.main.Game;

public class UI {

	Game game;

	public UI(Game game) {
		this.game = game;
	}

	public void render(Graphics g) {
		g.setColor(getLifebarColor((int)game.player.health));
		g.fillRect(10, 8, 150, 20);
		
		g.setColor(Color.WHITE);

		g.setFont(new Font("arial", Font.BOLD, 16));
		g.drawString(getLifeMessage((int)game.player.health), 15, 24);

		if (game.player.combo > 1) {
			g.setFont(new Font("arial", Font.BOLD, 16));
			g.drawString("x" + game.player.combo, 15, 42);
		}

		g.setFont(new Font("arial", Font.BOLD, 16));
		g.drawString("Ammo: " + game.player.ammo, (Game.WIDTH * Game.SCALE) - (100), 20);


		g.setFont(new Font("arial", Font.BOLD, 16));
		g.drawString("Score: " + game.player.score, (Game.WIDTH * Game.SCALE) - (100), 40);
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
