package com.boss.main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;

import com.boss.entities.Enemy;
import com.boss.entities.Entity;
import com.boss.entities.Gunshot;
import com.boss.entities.Player;
import com.boss.graphics.Spritesheet;
import com.boss.graphics.UI;
import com.boss.world.Camera;
import com.boss.world.World;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	public static JFrame frame;
	
	public static final int WIDTH = 240; 
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	private Thread thread;
	private boolean isRunning;

	
	private BufferedImage image;
	
	public static List<Entity> entities = new ArrayList<>();
	
	public static List<Enemy> enemies = new ArrayList<>();
	
	public static List<Gunshot> gunshots =  new ArrayList<>();
	
	public static Spritesheet spritesheet;
	public static World world;
	public static UI ui;
	
	public Player player;
	
	public static Random rand = new Random();

	public Controls controls;
	
	public Game() {
		initFrame();
		load();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		controls = new Controls(this);
		addKeyListener(controls);
		addMouseListener(controls);
	}
	
	public void load() {
		spritesheet = new Spritesheet("/img/spritesheet.png");
		ui = new UI(this);
		player = new Player(0, 0, World.TILE_SIZE, World.TILE_SIZE, spritesheet.getSprite(2 * World.TILE_SIZE, 0, World.TILE_SIZE, World.TILE_SIZE), this);
		entities.add(player);
		world = new World("/img/map.png", this);
	}
	
	public void initFrame() {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		frame = new JFrame("A M O G V S 2");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		tickEntities();
		Camera.followActor(player);
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = image.getGraphics();

		world.render(g);
		renderEntities(g);

		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		ui.render(g);
		bs.show();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				delta--;
			}
		}
		stop();
	}

	public void setPlayerSpawnPoint(double x, double y) {
		player.setX(x);
		player.setY(y);
	}

	private void tickEntities() {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}

		for (int i = 0; i < gunshots.size(); i++) {
			gunshots.get(i).tick();
		}
	}

	private void renderEntities(Graphics g) {
		for (int i = 0; i < gunshots.size(); i++) {
			gunshots.get(i).render(g);
		}

		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
	}
}