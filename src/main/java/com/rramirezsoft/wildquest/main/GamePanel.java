package com.rramirezsoft.wildquest.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import com.rramirezsoft.wildquest.ai.PathFinder;
import com.rramirezsoft.wildquest.data.SaveLoad;
import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.entity.player.Player;
import com.rramirezsoft.wildquest.enviroment.EnvironmentManager;
import com.rramirezsoft.wildquest.respawn.RespawnFactory;
import com.rramirezsoft.wildquest.respawn.Respawneable;
import com.rramirezsoft.wildquest.tile.Map;
import com.rramirezsoft.wildquest.tile.TileManager;
import com.rramirezsoft.wildquest.tile.interactive.InteractiveTile;



@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tileSize por defecto
	final int scale = 3;
	// Escalamos para que no se vea demasiado pequeño 16x3
	public final int tileSize = originalTileSize * scale; // 48x48 tile
	// 4/3 ratio, la pantalla mide 20 x 12 casillas
	public final int maxScreenCol = 20;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

	// WORLD SETTINGS
	public int maxWorldCol;
	public int maxWorldRow;
	public final int maxMap = 10;
	public int currentMap = 0;

	// FOR FULL SCREEN
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	public boolean fullScreenOn;

	// FPS
	int FPS = 60;

	// SYSTEM
	public TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	public EventHandler eHandler = new EventHandler(this);

	// SONIDO
	Sound music = new Sound();
	Sound se = new Sound();

	// COLLISION
	public CollisionChecker cChecker = new CollisionChecker(this);

	// ASSET SETTER
	public AssetSetter aSetter = new AssetSetter(this);

	// UI
	public UI ui = new UI(this);

	// CONFIG
	Config config = new Config(this);

	// PATHFINDER
	public PathFinder pFinder = new PathFinder(this);

	// ENVIRONMENT MANAGER
	public EnvironmentManager eManager = new EnvironmentManager(this);

	// MAP
	public Map map = new Map(this);

	//SAVELOAD
	public SaveLoad saveLoad = new SaveLoad(this);

	//ENTITY GENERATOR
	public EntityGenerator eGenerator = new EntityGenerator(this);

	//CUTSCENE MANAGER 
	public CutsceneManager csManager = new CutsceneManager(this);

	//RESPAWN FACTORY
	RespawnFactory respawnFactory = new RespawnFactory(this);
	Respawneable respawn;

	// GAMETHREAD
	// Es algo que podemos empezar y parar. Una vez empieza, el programa corre hasta
	// que lo paramos.
	// Se usa cuando queremos repetir un proceso una y otra vez.
	// Por ejemplo pintar la pantalla una y otra vez cada 60 fps
	Thread gameThread; // * Hay que implementar Runnable para utilizarlo *

	// ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	public Entity[][] obj = new Entity[maxMap][20];//Elegimos 10 como tamañp de array para poner un límite de objetos
	public Entity[][] struct = new Entity[maxMap][15]; //estructuras decorativas para el mapa
	public Entity[][] npc = new Entity[maxMap][10];// simultánamente dibujados, ahorramos recursos
	public Entity[][] monster = new Entity[maxMap][50];
	public InteractiveTile[][] iTile = new InteractiveTile[maxMap][50];
	public Entity projectile[][] = new Entity[maxMap][20];
	//public ArrayList<Entity> projectileList = new ArrayList<>();
	public ArrayList<Entity> particleList = new ArrayList<>();
	public ArrayList<Entity> entityList = new ArrayList<>();// Vamos a meter todas las entidades en el arraylist

	// GAME STATE
	public int gameState;
	public final int titleState = 0; //pantalla de inicio
	public final int playState = 1; //jugando
	public final int pauseState = 2; //pausa
	public final int dialogueState = 3; //dialogos
	public final int characterState = 4; //atributos e inventario
	public final int optionsState = 5; //menu de opciones in game
	public final int gameOverState = 6; //eliminado
	public final int transitionState = 7; //transiciones
	public final int tradeState = 8; //dialogos de compra-venta
	public final int sleepState = 9; //durmiendo
	public final int mapState = 10; //mapa grande
	public final int cutsceneState = 11; //escenas
	public final int beginGameState = 12; //animacion de inicio
	public final int titleOptionsState = 13; //menu de opciones inicio
	public final int chooseNameState = 14;//elegir el nombre del personaje
	public final int questionState = 15; //dialogos con preguntas

	//OTHERS 
	public boolean bossBattleOn = false;
	public boolean gotMap = false; //comprobar si tiene el mapa equipado
	//para elegir el nombre del jugador
	public char selectedLetter = 'a'; // Letra seleccionada actualmente
	public int selectedIndex = 0; // Índice de la letra seleccionada en el array de letras

	//RESPAWN
	public long respawnInterval = 15000; 
	public long lastRespawnTime;
	public boolean respawneable; //para verificar en que momentos pueden spawnear enemigos


	//AREA
	public int currentArea;
	public int nextArea;
	public final int outside = 50;
	public final int indor = 51;
	public final int dungeon = 52;
	public final int village = 53;

	public GamePanel() {
		// Aplicamos los screen settings
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); // Puede mejorar performance
		this.addKeyListener(keyH);
		this.setFocusable(true); // Con esta línea este GamePanel puede focusearse para recibir el input
	}

	// SETUP GAME
	public void setupGame() {

		playMusic(27);
		aSetter.setObject();
		aSetter.setStructure();
		aSetter.setInteractiveTile();
		eManager.setup();

		gameState = titleState;
		currentArea = outside;

		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
		g2 = (Graphics2D)tempScreen.getGraphics(); // Lo que pinte g2 será pintado en tempScreen

		if(fullScreenOn) {
			setFullScreen();

		}

	}
	public void resetGame(boolean restart) {

		stopMusic();
		currentArea = outside;
		removeTempEntity();
		bossBattleOn = false;
		player.setDefaultPositions();
		player.restoreStatus();
		player.resetCounter();
		aSetter.setNPC();
		aSetter.setMonster();

		if(restart) {
			player.setDefaultValues();
			aSetter.setObject();
			aSetter.setStructure();
			aSetter.setInteractiveTile();
			eManager.lighting.resetDay();
		}

	}
	public void setFullScreen() {
		// GET LOCAL SCREEN DEVICE
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Main.window);

		// GET FULL SCREEN WIDTH AND HEIGHT
		screenWidth2 = Main.window.getWidth();
		screenHeight2 = Main.window.getHeight();
	}

	public void startGameThread() {
		// Instanciamos thread
		gameThread = new Thread(this);// this referencia a esta clase osea a GamePanel
		gameThread.start();
	}

	@Override
	public void run() {
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		double lastTime = System.nanoTime();
		long currentTime;
		// Para visualizar FPS
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {

			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			// Para ver FPS
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				drawToTempScreen();
				drawToScreen(); // Draw everything to the buffered image
				delta--; // Draw the buffered image to the screen
				drawCount++;
			}
			// Para ver FPS
			if (timer >= 1000000000) {
				System.out.println("FPS:" + drawCount);

				drawCount = 0;
				timer = 0;
			}
		}
	}

	// Actualizar información
	public void update() {
		if (gameState == playState) {
			// PLAYER
			player.update();
			// NPC
			int npcLength = npc[1].length;
			for (int i = 0; i < npcLength; i++) {
				if (npc[currentMap][i] != null) {
					npc[currentMap][i].update();
				}
			}
			// MONSTER 
			respawneable = true;
			if(respawneable){
				handleRespawn(); //respawn de enemigos
			}

			int monsterLength = monster[1].length;
			for (int i = 0; i < monsterLength; i++) {
				if (monster[currentMap][i] != null) {
					if (monster[currentMap][i].alive && !monster[currentMap][i].dying) {
						monster[currentMap][i].update();
					}
					if (!monster[currentMap][i].alive) {
						monster[currentMap][i].checkDrop();
						monster[currentMap][i] = null;
					}
				}

			}

			// PROJECTILE
			for (int i = 0; i < projectile[1].length; i++) {
				if (projectile[currentMap][i] != null) {
					if (projectile[currentMap][i].alive) {
						projectile[currentMap][i].update();
					}
					if (!projectile[currentMap][i].alive) {
						projectile[currentMap][i] = null;
					}
				}

			}
			// PARTICLE
			for (int i = 0; i < particleList.size(); i++) {
				if (particleList.get(i) != null) {
					if (particleList.get(i).alive) {
						particleList.get(i).update();
					}
					if (!particleList.get(i).alive) {
						particleList.remove(i);
					}
				}

			}
			// INTERACTIVE TILES
			int iTileLength = iTile[1].length;
			for (int i = 0; i < iTileLength; i++) {
				if (iTile[currentMap][i] != null) {
					iTile[currentMap][i].update();
				}
			}

			eManager.update();
		}
		if (gameState == pauseState) {}

		if(gameState == cutsceneState) {
			// NPC
			int npcLength = npc[1].length;
			for (int i = 0; i < npcLength; i++) {
				if (npc[currentMap][i] != null) {
					npc[currentMap][i].update();
				}
			}
		}
	}

	public void drawToTempScreen() {


		// DEBUG
		long drawStart = 0;
		if (keyH.checkDrawTime) {
			drawStart = System.nanoTime();
		}

		// TITLE SCREEN
		switch (gameState) {
		case titleState:
			ui.draw(g2);
			break;
		case mapState:
			map.drawFullMapScreen(g2);
			break;
		case titleOptionsState:
			ui.draw(g2);
			break;
		default: {
			// TILE
			tileM.draw(g2);
			// INTERACTIVE TILE
			int iTileLength = iTile[1].length;
			for (int i = 0; i < iTileLength; i++) {
				if (iTile[currentMap][i] != null) {
					iTile[currentMap][i].draw(g2);
				}
			}
			// ADD ENTITIES TO THE LIST
			// PLAYER
			entityList.add(player);
			// NPCS
			int npcLength = npc[1].length;
			for (int i = 0; i < npcLength; i++) {
				if (npc[currentMap][i] != null) {
					entityList.add(npc[currentMap][i]);
				}
			}
			// OBJECT
			int objectLength = obj[1].length;
			for (int i = 0; i < objectLength; i++) {
				if (obj[currentMap][i] != null) {
					entityList.add(obj[currentMap][i]);
				}
			}
			//STRUCT
			int structLength = struct[1].length;
			for (int i = 0; i < structLength; i++) {
				if(struct[currentMap][i] != null) {
					entityList.add(struct[currentMap][i]);
				}
			}
			// MONSTER
			int monsterLength = monster[1].length;
			for (int i = 0; i < monsterLength; i++) {
				if (monster[currentMap][i] != null) {
					entityList.add(monster[currentMap][i]);
				}
			}
			// PROJECTILE
			int projectileListLength = projectile[1].length;
			for (int i = 0; i < projectileListLength; i++) {
				if (projectile[currentMap][i] != null) {
					entityList.add(projectile[currentMap][i]);
				}
			}
			// PARTICLE
			int particleListLength = particleList.size();
			for (int i = 0; i < particleListLength; i++) {
				if (particleList.get(i) != null) {
					entityList.add(particleList.get(i));
				}
			}
			// SORT
			Collections.sort(entityList, new Comparator<Entity>() {
				@Override
				public int compare(Entity e1, Entity e2) {
					return Integer.compare(e1.worldY, e2.worldY);
				}

			});
			// DRAW ENTITIES
			int entityListLength = entityList.size();
			for (int i = 0; i < entityListLength; i++) {
				entityList.get(i).draw(g2);
			}
			// EMPTY ENTITY LIST
			entityList.clear();
			// ENVIRONMENT
			eManager.draw(g2);
			// MINIMAP
			map.drawMiniMap(g2);
			// CUTSCENE
			csManager.draw(g2);
			// UI. Lo instanciamos después del jugador para que esté una capa por encima
			ui.draw(g2);
			break;
		}
		}

		// DEBUG
		if (keyH.checkDrawTime) {
			long drawEnd = System.nanoTime();
			long Passed = drawEnd - drawStart;
			int x = 10;
			int y = 400;
			int lineHeight = 30;

			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 22F));

			g2.setColor(Color.black);
			g2.drawString("Tiempo de pintura: " + Passed, x, y);
			y += lineHeight;
			g2.drawString("Coordenada X: " + (player.worldX + player.solidArea.x) / tileSize, x, y);
			y += lineHeight;
			g2.drawString("Coordenada Y: " + (player.worldY + player.solidArea.y) / tileSize, x, y);
			y += lineHeight;
			g2.drawString("Modo dios: " + keyH.godModeOn, x, y);
			y += lineHeight;

			x = 9;
			y = 399;
			g2.setColor(Color.white);
			g2.drawString("Tiempo de pintura: " + Passed, x, y);
			y += lineHeight;
			g2.drawString("Coordenada X: " + (player.worldX + player.solidArea.x) / tileSize, x, y);
			y += lineHeight;
			g2.drawString("Coordenada Y: " + (player.worldY + player.solidArea.y) / tileSize, x, y);
			y += lineHeight;
			g2.drawString("Modo dios: " + keyH.godModeOn, x, y);
			y += lineHeight;

		}
	}

	public void drawToScreen() {
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();
	}

	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}

	public void stopMusic() {
		music.stop();
	}

	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
	public void changeArea() {


		if(nextArea != currentArea) {

			stopMusic();

			if(nextArea == outside) {
				playMusic(9);
			}
			if(nextArea == dungeon) {
				playMusic(19);
			}
			if(nextArea == indor) {
				playMusic(20);
			}
			if(nextArea == village) {
				playMusic(26);
			}

		}	
		currentArea = nextArea;	

	}
	public void checkArea() {

		if(currentArea == outside) { 
			playMusic(9);
		}
		if(currentArea == dungeon) {
			playMusic(19);
		}
		if(currentArea == indor) {
			playMusic(20);
		}
		if(currentArea == village) {
			playMusic(26);
		}
	}

	public void handleRespawn() {


		if(currentMap == 0) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastRespawnTime >= respawnInterval) {
				respawn = respawnFactory.createRespawn("Random", this);
				respawn.respawn();
				lastRespawnTime = currentTime;

			}
		}

	}
	public void removeTempEntity() {

		for (int mapNum = 0; mapNum < maxMap; mapNum++) {

			for (int i = 0; i < obj[1].length; i++) {

				if(obj[mapNum][i] != null && obj[mapNum][i].temp == true) {
					obj[mapNum][i] = null;
				}
			}
		}
	} 

}
