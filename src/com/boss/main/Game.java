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

public class Game extends Canvas implements Runnable, KeyListener, MouseListener {
	
	private static final long serialVersionUID = 1L;
	
	public static JFrame frame;
	
	public static final int WIDTH = 240; 
	public static final int HEIGHT = 160;
	public static final int SCALE = 3; 
	private Thread thread;
	private boolean isRunning;
	
	
	private BufferedImage image;
	
	public static List<Entity> entities;
	
	public static List<Enemy> enemies;
	
	public static List<Gunshot> gunshots;
	
	public static Spritesheet spritesheet;
	public static World world;
	public static UI ui;
	
	public static Player player;
	
	public static Random rand;
	
	public Game() {
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		initFrame();
		
		load();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		
	}
	
	public static void load() {
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		gunshots = new ArrayList<Gunshot>();
		spritesheet = new Spritesheet("/img/spritesheet.png");
		ui = new UI();
		player = new Player(0, 0, 16, 16, spritesheet.getSprite(2 * 16, 0 * 16, 16, 16));
		entities.add(player);
		world = new World("/img/map.png");
	}
	
	public void initFrame() {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		frame = new JFrame("GAME #1");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);			
			e.tick();
		}
		
		for (int i = 0; i < gunshots.size(); i++) {
			gunshots.get(i).tick();
		}
		
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		world.render(g);
		
		for (int i = 0; i < gunshots.size(); i++) {
			gunshots.get(i).render(g);
		}
		
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}

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

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.setRight(true);
			player.setLeft(false);
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.setRight(false);
			player.setLeft(true);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.setUp(true);
			player.setDown(false);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.setUp(false);
			player.setDown(true);
		}
		
		if (e.getKeyCode() == KeyEvent.VK_X) {
			player.shoot();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.setRight(false);
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.setLeft(false);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.setUp(false);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.setDown(false);
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		int mouseX = (e.getX()) / 3;
		int mouseY = (e.getY()) / 3;
		player.shootWithMouse(mouseX, mouseY);
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}