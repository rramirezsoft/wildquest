package com.rramirezsoft.wildquest.main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.entity.npc.NPC_OldMan;
import com.rramirezsoft.wildquest.object.OBJ_Heart;
import com.rramirezsoft.wildquest.object.OBJ_ManaCrystal;
import com.rramirezsoft.wildquest.object.OBJ_Shield_Wood;
import com.rramirezsoft.wildquest.object.OBJ_Sword_Normal;
import com.rramirezsoft.wildquest.object.OBJ_WildCoin;


public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font rTheory, maruMonica, purisaB;
	// Preparamos la imagen antes de que empiece el game loop, de otra manera se generaría 60 veces por segundo
	BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin, star;
	public boolean messageOn = false;// Texto que aparece al coger objetos
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameFinished = false;// Booleano para verificar si ha acabado el juego o no
	public String currentDialogue;
	public int commandNum;
	public int playerSlotCol;
	public int playerSlotRow;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	int subState = 0; // Para las opciones
	int counter = 0;
	public Entity npc;

	// DIALOGOS
	public int charIndex = 0;
	public String combineText = "";
	public boolean showAllText = false; //para cambiar la logica de los dialogos

	public UI(GamePanel gp) {
		this.gp = gp;

		try {
			//			InputStream is = getClass().getResourceAsStream("/font/bitui.ttf");
			//			rTheory = Font.createFont(Font.TRUETYPE_FONT,  is);

			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);

			//			InputStream is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
			//			purisaB = Font.createFont(Font.TRUETYPE_FONT, is);

		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		// CREATE HUD OBJECT
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		Entity crystal = new OBJ_ManaCrystal(gp);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
		Entity bronzeCoin = new OBJ_WildCoin(gp);
		coin = bronzeCoin.down1;

	}
	public void addMessage(String text) {
		message.add(text);
		messageCounter.add(0);
	}
	public void draw(Graphics2D g2) {
		this.g2 = g2;

		//g2.setFont(rTheory);
		//g2.setFont(purisaB);
		g2.setFont(maruMonica);

		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);

		// TITLE STATE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
			if(gp.keyH.tryToLoadGame) {
				drawSaveLoadMessageError();
			}
		}
		// PLAY STATE
		if(gp.gameState == gp.playState) {
			drawPlayerLife();
			drawMonsterLife();
			drawPlayerExpBar();
			drawPlayerCoin();
		}
		// PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPlayerExpBar();
			drawPauseScreen();
			drawPlayerCoin();
		}
		// DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
		}
		//QUESTION STATE
		if(gp.gameState == gp.questionState) {
			drawQuestionScreen();
		}
		// CHARACTER STATE
		if(gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory(gp.player, true);
		}
		// OPTIONS STATE
		if(gp.gameState == gp.optionsState) {
			drawOptionsScreen();
		}
		// GAMEOVER STATE
		if(gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
		// TRANSITION STATE
		if(gp.gameState == gp.transitionState) {
			drawTransition();
		}
		// TRADE STATE
		if(gp.gameState == gp.tradeState) {
			drawTradeScreen();
		}
		// SLEEP STATE
		if(gp.gameState == gp.sleepState) {
			drawSleepScreen();
		}
		//BEGIN GAME STATE
		if(gp.gameState  == gp.beginGameState) {
			drawFirstTransition();
		}
		//TITLE OPTIONS
		if(gp.gameState == gp.titleOptionsState) {
			drawTitleOptionsScreen();
		}
		//CHOOSE NAME STATE
		if(gp.gameState == gp.chooseNameState) {
			drawChoosingNameScreen();
		}
	}
	public void drawPlayerExpBar() {
		int x = gp.tileSize / 2;
		int y = gp.tileSize / 2;
		int barWidth = 300;
		int barHeigth = 24;
		double expBarValue = ((double)gp.player.exp / (double)gp.player.nextLevelExp) * (barWidth - 2);


		// Dibuja el borde de la barra de experiencia
		g2.setColor(new Color(187, 161, 116));
		g2.fillRect(x, y, barWidth, barHeigth);

		// Dibuja el fondo de la barra de experiencia
		g2.setColor(new Color(96, 81, 59));
		g2.fillRect(x + 2, y + 2, 296, 20);

		// Dibuja la barra de progreso
		GradientPaint gradient = new GradientPaint(x + 2, y + 2, new Color(40, 255, 40), x + barWidth - 2, y + 2, new Color(20, 150, 20));   
		// Dibuja la barra de progreso con el gradiente
		g2.setPaint(gradient);
		g2.fillRect(x + 2, y + 2, (int) expBarValue, 20);

		// Dibuja el texto de progreso
		g2.setColor(Color.BLACK);
		Font font = g2.getFont().deriveFont(Font.BOLD, 18);
		g2.setFont(font);
		g2.drawString(gp.player.exp + " / " + gp.player.nextLevelExp + " EXP", x + 128, y + 18);
		g2.setColor(Color.WHITE);
		g2.drawString(gp.player.exp + " / " + gp.player.nextLevelExp + " EXP", x + 127, y + 17);

		// Dibuja el cuadro de nivel con borde
		g2.setColor(new Color(187, 161, 116)); // establece el color de fondo del cuadro
		int cuadroX = x + 306;
		int cuadroY = y + 1;
		int cuadroAncho = 54;
		int cuadroAlto = 22;
		int bordeAncho = 2;
		g2.fillRect(cuadroX, cuadroY, cuadroAncho, cuadroAlto); // dibuja el fondo del cuadro
		g2.setStroke(new BasicStroke(bordeAncho));
		g2.setColor(new Color(181, 141, 85));// establece el color del borde
		g2.drawRect(cuadroX, cuadroY, cuadroAncho, cuadroAlto); // dibuja el borde del cuadro


		// Dibuja el número de nivel
		g2.setColor(Color.BLACK);
		font = g2.getFont().deriveFont(Font.BOLD, 20);
		g2.setFont(font);
		g2.drawString(Integer.toString(gp.player.level), x + 329, y + 20);
		g2.setColor(Color.WHITE);
		g2.drawString(Integer.toString(gp.player.level), x + 328, y + 19);

		//NOMBRE DEL JUGADOR ENCIMA DE LA BARRA
		if(gp.player.name != "") {
			y +=18;
			x+=10;
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16));
			g2.setColor(new Color(255, 140, 0));
			g2.drawString(gp.player.name, x, y);

			//SOMBRA
			g2.setColor(new Color(255, 178, 0));
			g2.drawString(gp.player.name, x-1, y-1);
		}
	}
	public void drawPlayerLife() {
		int x = gp.tileSize/2;
		int y = (int) (gp.tileSize * 1.5);
		int barWidth = gp.tileSize*4;
		int barHeight = 49;
		int barBorder = 3;
		int lifeBarBorder = 1;
		int lifeBarHeight = 18;

		double hpOneScale = (double)(barWidth - 2 * lifeBarBorder)/gp.player.maxLife;
		double hpBarValue = hpOneScale*gp.player.life;
		double shieldOneScale = (double)(barWidth - 2 * lifeBarBorder)/gp.player.maxProtect;
		double shieldBarValue = shieldOneScale*gp.player.protect;

		// Draw the border of the health bar and the shield bar
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		g2.setColor(new Color(100, 100, 100));
		g2.fillRect(x - barBorder, y - barBorder, barWidth + 2 * barBorder, barHeight + 2 * barBorder);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		g2.setColor(new Color(200, 200, 200));
		g2.fillRect(x - barBorder + 1, y - barBorder + 1, barWidth + 2 * barBorder - 2, barHeight + 2 * barBorder - 2);

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		// Draw the shield bar
		GradientPaint shieldGradient = new GradientPaint(x + lifeBarBorder, y + lifeBarBorder, new Color(0, 94, 196),
				x + lifeBarBorder + (int)shieldBarValue, y + lifeBarBorder, new Color(157, 211, 255));

		g2.setPaint(shieldGradient);
		g2.fillRect(x + lifeBarBorder, y + lifeBarBorder, (int)shieldBarValue, lifeBarHeight);

		// Draw the health bar
		GradientPaint hpGradient = new GradientPaint(x + lifeBarBorder, y + lifeBarBorder + lifeBarHeight
				+ lifeBarBorder, new Color(226, 0, 0), x + lifeBarBorder + (int)hpBarValue, y + lifeBarBorder
				+ lifeBarHeight + lifeBarBorder, new Color(255, 134, 134));

		g2.setPaint(hpGradient);
		g2.fillRect(x + lifeBarBorder, (int) (y + lifeBarBorder + lifeBarHeight*1.5 + lifeBarBorder), (int)hpBarValue, lifeBarHeight);

		// Draw the health and shield values as text
		g2.setColor(new Color(255, 255, 255));
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 14));
		String hpText = String.valueOf(gp.player.life) + "/" + String.valueOf(gp.player.maxLife);
		String shieldText = String.valueOf(gp.player.protect) + "/" + String.valueOf(gp.player.maxProtect);
		g2.drawString(shieldText, x + barWidth/2 - g2.getFontMetrics().stringWidth(shieldText)/2, y + lifeBarBorder + lifeBarHeight/2 + 5);
		g2.drawString(hpText, x + barWidth/2 - g2.getFontMetrics().stringWidth(hpText)/2, (int) (y + lifeBarBorder +
				lifeBarHeight*1.5 + lifeBarBorder + lifeBarHeight/2 + 5));


		gp.player.hpBarCounter++;
		if(gp.player.hpBarCounter > 600) {
			gp.player.hpBarCounter = 0;
			gp.player.hpBarOn = false;
		}
	}
	public void drawPlayerCoin() {
		// Configuración de colores
		Color bgColor = new Color(51, 51, 51);
		Color borderColor = new Color(153, 153, 153);
		Color gemColor = new Color(255, 220, 0);

		// Configuración de tamaño y posición
		int x = gp.tileSize/2;
		int y = gp.tileSize*3;
		int width = 100;
		int height = 25;
		int borderThickness = 2;
		int gemSize = 50;
		int gemMargin = 4;

		// Dibujar el fondo y el borde de la barra
		g2.setColor(bgColor);
		g2.fillRect(x, y, width, height);
		g2.setColor(borderColor);
		g2.setStroke(new BasicStroke(borderThickness));
		g2.drawRect(x, y, width, height);

		// Dibujar la imagen de la moneda
		g2.drawImage(coin, x-16 + borderThickness + gemMargin, y - 1 + (height - gemSize) / 2, gemSize, gemSize, null);

		// Dibujar el número de monedas
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 22));
		g2.setColor(gemColor);
		int coinCount = gp.player.coin;
		String coinCountStr = String.valueOf(coinCount);
		int stringWidth = g2.getFontMetrics().stringWidth(coinCountStr);
		int stringHeight = g2.getFontMetrics().getHeight();
		if(coinCount < 10) {
			g2.drawString(coinCountStr, x + width - borderThickness - stringWidth - gemMargin*9, y + (height + stringHeight) / 2 - 4);
		} else if(coinCount >= 10 && gp.player.coin < 100) {
			g2.drawString(coinCountStr, x + width - borderThickness - stringWidth - gemMargin*8, y + (height + stringHeight) / 2 - 4);
		} else if(coinCount >= 100 && gp.player.coin < 1000) {
			g2.drawString(coinCountStr, x + width - borderThickness - stringWidth - gemMargin*7, y + (height + stringHeight) / 2 - 4);
		} else if(coinCount >= 1000 && gp.player.coin < 10000) {
			g2.drawString(coinCountStr, x + width - borderThickness - stringWidth - gemMargin*6, y + (height + stringHeight) / 2 - 4);
		}
	}
	public void drawMonsterLife() {

		for (int i = 0; i < gp.monster[1].length; i++) {

			Entity monster = gp.monster[gp.currentMap][i];

			if(monster != null && monster.inCamera()) {

				// Monster HP bar
				if(monster.hpBarOn && !monster.boss) {

					double oneScale = (double)gp.tileSize/monster.maxLife;
					double hpBarValue = oneScale*monster.life;

					g2.setColor(new Color(35, 35, 35));
					g2.fillRect(monster.getScreenX()-1, monster.getScreenY()-16, gp.tileSize+2, 12);

					g2.setColor(new Color(255, 0, 30));
					g2.fillRect(monster.getScreenX(), monster.getScreenY() - 15, (int)hpBarValue, 10);

					monster.hpBarCounter++;

					if(monster.hpBarCounter > 600) {
						monster.hpBarCounter = 0;
						monster.hpBarOn = false;
					}
				} 
				else if(monster.boss) {

					double oneScale = (double)(gp.tileSize*8)/monster.maxLife;
					double hpBarValue = oneScale*monster.life;

					int x = gp.screenWidth/2 - gp.tileSize*4;
					int y = gp.tileSize*10;

					g2.setColor(new Color(35, 35, 35));
					g2.fillRect(x-1, y-1, gp.tileSize*8 + 2, 22);

					g2.setColor(new Color(255, 0, 30));
					g2.fillRect(x, y, (int)hpBarValue, 20);

					g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
					g2.setColor(Color.white);
					g2.drawString(monster.name, x + 4, y - 10);
				}
			}
		}

	}
	public void drawMessage() {

		int messageX = gp.tileSize;
		int messageY = gp.tileSize*4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

		for(int i = 0; i < message.size(); i++) {

			if(message.get(i) != null) {
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX + 2, messageY + 2);

				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);

				int counter = messageCounter.get(i) + 1;// messageCounter ++
				messageCounter.set(i, counter);// set the counter to the array
				messageY += 50;

				if(messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
	}
	public void drawTitleScreen() {

		// cargar imagen de fondo
		Image img = new ImageIcon(getClass().getResource("/title/paisaje.png")).getImage();
		g2.drawImage(img, 0, 0, gp.screenWidth, gp.screenHeight, null);

		// TITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 98F));
		String text = "Wild Quest";
		int x = getXforCenteredText(text);
		int y = gp.tileSize * 3;

		// SHADOW
		Color shadowTitleColor = new Color(120, 9, 9);
		Color selectedShadowTitleColor = new Color(79, 5, 5);
		g2.setColor(shadowTitleColor);
		g2.drawString(text, x + 5, y + 5);

		// MAIN COLOR
		Color titleColor = new Color(247, 25, 25);
		Color selectedTitleColor = new Color(200, 19, 19);
		g2.setColor(titleColor);
		g2.drawString(text, x, y);

		// SETTINGS IMAGE
		Image settings = new ImageIcon(getClass().getResource("/title/settings.png")).getImage();
		g2.drawImage(settings, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize + 24, gp.tileSize + 24, null);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
		g2.setColor(Color.black);
		text = "[ESC]";
		g2.drawString(text, gp.tileSize / 2 + 7, gp.tileSize * 2 + 31);
		g2.setColor(Color.gray);
		g2.drawString(text, gp.tileSize / 2 + 6, gp.tileSize * 2 + 30);

		// IMAGE
		x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
		y += gp.tileSize * 2;
		g2.drawImage(gp.player.down, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

		// MENU
		g2.setColor(shadowTitleColor);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

		// NEW GAME
		text = "NUEVA PARTIDA";
		x = getXforCenteredText(text);
		y += gp.tileSize * 3.8;
		g2.setColor(titleColor);
		if (commandNum == 0) {
			g2.drawString(">", x - gp.tileSize, y);
			// SHADOW
			g2.setColor(selectedShadowTitleColor);
			g2.drawString(text, x + 2, y + 2);
			// MAIN
			g2.setColor(selectedTitleColor);
		} else {
			// SHADOW
			g2.setColor(shadowTitleColor);
			g2.drawString(text, x + 2, y + 2);
			// MAIN
			g2.setColor(titleColor);
		}
		g2.drawString(text, x, y);

		// LOAD GAME
		text = "CARGAR PARTIDA";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		if (commandNum == 1) {
			g2.drawString(">", x - gp.tileSize, y);
			// SHADOW
			g2.setColor(selectedShadowTitleColor);
			g2.drawString(text, x + 2, y + 2);
			// MAIN
			g2.setColor(selectedTitleColor);
		} else {
			// SHADOW
			g2.setColor(shadowTitleColor);
			g2.drawString(text, x + 2, y + 2);
			// MAIN
			g2.setColor(titleColor);
		}
		g2.drawString(text, x, y);

		// QUIT
		text = "SALIR";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		if (commandNum == 2) {
			g2.drawString(">", x - gp.tileSize, y);
			// SHADOW
			g2.setColor(selectedShadowTitleColor);
			g2.drawString(text, x + 2, y + 2);
			// MAIN
			g2.setColor(selectedTitleColor);
		} else {
			// SHADOW
			g2.setColor(shadowTitleColor);
			g2.drawString(text, x + 2, y + 2);
			// MAIN
			g2.setColor(titleColor);
		}
		g2.drawString(text, x, y);
	}
	public void drawPauseScreen() {

		g2.setColor(new Color(0, 0, 0, 100));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));
		String text = "PAUSA";
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		int y = gp.screenHeight/2;

		//g2.drawString(text, x, y);

		g2.setColor(Color.black);
		g2.drawString(text, x+5, y+5);

		g2.setColor(Color.white);
		g2.drawString(text, x, y);
	}
	public void drawDialogueScreen() {
		// Ajustamos la posición y tamaño de la ventana de diálogo
		int padding = gp.tileSize / 2;
		int x = padding;  // Margen desde el borde izquierdo
		int height = gp.tileSize * 3;  // Altura de la ventana de diálogo
		int y = gp.screenHeight - height - padding;  // Posicionar en la parte inferior con margen
		int width = gp.screenWidth - (padding * 2);  // Ancho total menos márgenes laterales

		drawSubWindow(x, y, width, height);

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F));

		// Coordenadas iniciales del texto dentro del área blanca
		int textX = x + padding + 10;
		int textY = y + padding + 35;

		// Control del diálogo
		if (currentDialogue != null) {
			char[] characters = currentDialogue.toCharArray();

			if (charIndex < characters.length) {
				gp.playSE(18);
				combineText += characters[charIndex];
				charIndex++;
			}
			if ((gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState) && combineText.length() == currentDialogue.length()) {
				showAllText = true;

				if (gp.keyH.enterPressed && showAllText) {
					charIndex = 0;
					combineText = ""; 
					currentDialogue = null; 

					if (npc.currentDialogueIndex < npc.dialogues.get(npc.currentDialogueSet).size() - 1) {
						npc.currentDialogueIndex++;
					} else {
						npc.currentDialogueIndex = 0;
						npc.currentDialogueSet++;
						if (npc.currentDialogueSet >= npc.dialogues.size()) {
							npc.currentDialogueSet = npc.dialogues.size() - 1;
						}
						if (gp.gameState != gp.cutsceneState) {
							gp.gameState = gp.playState;
						} else {
							gp.csManager.scenePhase++;
						}
					}

					if (npc.name.equals(NPC_OldMan.npcName) && npc.currentDialogueSet == 1 && npc.currentDialogueIndex == 16) {
						gp.gameState = gp.chooseNameState;
					}

					if (npc.currentDialogueIndex < npc.dialogues.get(npc.currentDialogueSet).size()) {
						currentDialogue = npc.dialogues.get(npc.currentDialogueSet).get(npc.currentDialogueIndex);
						combineText = "";  
					} else {
						currentDialogue = null; 
					}

					gp.keyH.enterPressed = false;
					showAllText = false;
				}
			} 
		} else {

			npc.currentDialogueIndex = 0;
			if (gp.gameState == gp.dialogueState ) {
				gp.gameState = gp.playState;
			}

			if (gp.gameState == gp.cutsceneState) {
				gp.csManager.scenePhase++;
			}
		}

		// Sombra
		g2.setColor(Color.GRAY);
		for (String line : combineText.split("\n")) {
			g2.drawString(line, textX + 2, textY + 2);
			textY += 45; 
		}

		// Texto principal
		textY = y + padding + 35;
		g2.setColor(Color.BLACK); 
		for (String line : combineText.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 45; 
		}
	}
	public void drawQuestionScreen() {
		drawDialogueScreen();

		// DRAW WINDOW
		int x = (int) (gp.tileSize * 15.5);
		int y = (int) (gp.tileSize * 3.5);
		int width = (int) (gp.tileSize * 2.5);
		int height= (int) (gp.tileSize * 2.5);
		drawSubWindow(x , y, width, height);

		// DRAW TEXTS
		x += gp.tileSize;
		y += gp.tileSize;
		g2.drawString("Sí", x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPressed == true) {
				subState = 1;
			}
		}
		y += gp.tileSize;
		g2.drawString("No", x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPressed == true) {
				subState = 2;
			}
		}
	}
	public void drawCharacterScreen() {
		// CREATE A FRAME
		final int frameX = gp.tileSize*2;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize*5;
		final int frameHeight = gp.tileSize*10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		// TEXT
		g2.setColor(Color.BLACK);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 40;
		// NAMES
		g2.drawString("Nivel", textX, textY);
		textY += lineHeight;
		g2.drawString("Salud", textX, textY);
		textY += lineHeight;
		g2.drawString("Escudo", textX, textY);
		textY += lineHeight;
		g2.drawString("Munición", textX, textY);
		textY += lineHeight;
		g2.drawString("Ataque", textX, textY);
		textY += lineHeight;
		g2.drawString("Defensa", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("Wildcoins", textX, textY);
		textY += lineHeight + 10;
		g2.drawString("Arma", textX, textY);
		textY += lineHeight + 12;
		g2.drawString("Escudo", textX, textY);
		// VALUES
		int tailX = (frameX + frameWidth) - 30;
		// reset textY
		textY = frameY + gp.tileSize;
		String value;

		value = String.valueOf(gp.player.level);
		textX = getXforAlignRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
		textX = getXforAlignRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.protect + "/" + gp.player.maxProtect);
		textX = getXforAlignRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.ammo + "/" + gp.player.maxAmmo);
		textX = getXforAlignRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.attack);
		textX = getXforAlignRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.defense);
		textX = getXforAlignRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.exp);
		textX = getXforAlignRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.coin);
		textX = getXforAlignRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY-24, null );
		textY += gp.tileSize;

		g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY-24, null );
	}
	public void drawInventory(Entity entity, boolean cursor) {
		int frameX;
		int frameY;
		int frameWidth;
		int frameHeight;
		int slotCol;
		int slotRow;

		if (entity == gp.player) {
			frameX = gp.tileSize * 12;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize * 6;
			frameHeight = gp.tileSize * 5;
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		} else {
			frameX = gp.tileSize * 2;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize * 6;
			frameHeight = gp.tileSize * 5;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}

		// FRAME
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		// SLOT
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize + 3;

		// DRAW PLAYER'S ITEMS
		for (int i = 0; i < entity.inventory.size(); i++) {

			if (i % 2 == 0) {
				g2.setColor(new Color(230, 230, 230)); 
			} else {
				g2.setColor(new Color(240, 240, 240)); 
			}
			g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);

			// EQUIP CURSOR
			if (entity.inventory.get(i) == entity.currentWeapon ||
					entity.inventory.get(i) == entity.currentShield ||
					entity.inventory.get(i) == entity.currentLight ||
					entity.inventory.get(i) == entity.currentMapItem) {

				g2.setColor(new Color(240, 190, 90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}
			g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

			// DURABILITY BAR
			if (entity.inventory.get(i).type != entity.inventory.get(i).type_consumable &&
					entity.inventory.get(i).durability != entity.inventory.get(i).maxDurability &&
					!entity.inventory.get(i).name.equals(new OBJ_Sword_Normal(gp).name) &&
					!entity.inventory.get(i).name.equals(new OBJ_Shield_Wood(gp).name)) {

				int maxDurability = entity.inventory.get(i).maxDurability;
				int durability = entity.inventory.get(i).durability;
				int durabilityBarWidth = (int) ((double) durability / maxDurability * (gp.tileSize - 10));

				// Calcular el color de la barra de durabilidad
				Color barColor;
				double percentage = (double) durability / maxDurability * 100;
				if (percentage >= 75) {
					barColor = new Color(0, 255, 0);
				} else if (percentage >= 50) {
					barColor = new Color(255, 255, 0);
				} else if (percentage >= 25) {
					barColor = new Color(255, 165, 0);
				} else {
					barColor = new Color(255, 0, 0);
				}

				// Dibujar la parte vacía de la barra de durabilidad
				g2.setColor(new Color(0, 0, 0, 128));
				g2.fillRect(slotX + 5, slotY - 7 + gp.tileSize, gp.tileSize - 10, 5 - 1);

				// Dibujar la barra de durabilidad
				g2.setColor(barColor);
				g2.fillRect(slotX + 5, slotY - 7 + gp.tileSize, durabilityBarWidth, 5);

				// Dibujar borde de la barra de durabilidad
				g2.setStroke(new BasicStroke(2));
				g2.setColor(Color.BLACK);
				g2.drawRect(slotX + 5, slotY - 7 + gp.tileSize, gp.tileSize - 10, 5);
			}

			// DISPLAY AMOUNT
			if (entity == gp.player && entity.inventory.get(i).amount > 1) {
				g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32f));
				int amountX;
				int amountY;

				String s = "" + entity.inventory.get(i).amount;
				amountX = getXforAlignRightText(s, slotX + 44);
				amountY = slotY + gp.tileSize;

				// SHADOW
				g2.setColor(new Color(160, 160, 160)); // Color de sombra más claro para el fondo blanco
				g2.drawString(s, amountX + 1, amountY + 1); // Sombra ligera

				// NUMBER
				g2.setColor(Color.BLACK); // Texto en negro para contraste
				g2.drawString(s, amountX, amountY);
			}

			slotX += slotSize;

			if (i == 4 || i == 9 || i == 14) {
				slotX = slotXstart;
				slotY += slotSize;
			}
		}

		// CURSOR
		if (cursor == true) {
			int cursorX = slotXstart + (slotSize * slotCol);
			int cursorY = slotYstart + (slotSize * slotRow);
			int cursorWidth = gp.tileSize;
			int cursorHeight = gp.tileSize;

			// DRAW CURSOR
			g2.setColor(Color.BLACK); // Cursor en negro para contraste
			g2.setStroke(new BasicStroke(3));
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

			// DESCRIPTION FRAME
			int dFrameX = frameX;
			int dFrameY = frameY + frameHeight;
			int dFrameWidth = frameWidth;
			int dFrameHeight = gp.tileSize * 3;

			// DRAW DESCRIPTION TEXT
			int textX = dFrameX + 20;
			int textY = dFrameY + gp.tileSize;
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25f)); // Asegurarse de que el texto no esté en negrita

			int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

			if (itemIndex < entity.inventory.size()) {
				drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

				for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
					g2.setColor(Color.GRAY); // Sombra ligera
					g2.drawString(line, textX + 1, textY + 1);

					g2.setColor(Color.BLACK); // Texto en negro para contraste
					g2.drawString(line, textX, textY);
					textY += 32;
				}
			}
		}
	}
	public void drawGameOverScreen() {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

		text = "Game Over";
		// Shadow
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize*4;
		g2.drawString(text, x, y);
		// Main
		g2.setColor(Color.white);
		g2.drawString(text,  x-4,  y-4);
		// Retry
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Reintentar";
		x = getXforCenteredText(text);
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-40, y);
		}
		// Back to the title screen
		text = "Salir";
		x = getXforCenteredText(text);
		y += 55;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-40, y);
		}
	}
	public void drawOptionsScreen() {

		// Sub ventana
		int frameX = gp.tileSize * 6;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 8;
		int frameHeight = gp.tileSize * 10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		switch (subState) {
		case 0: options_top(frameX, frameY); break;
		case 1: options_fullScreenNotification(frameX, frameY); break;
		case 2: options_control(frameX, frameY); break;
		case 3: options_endGameConfirmation(frameX, frameY); break;
		}
		gp.keyH.enterPressed = false;
	}
	public void options_top(int frameX, int frameY) {

		int textX;
		int textY;

		// Color del texto del título "Opciones"
		g2.setColor(new Color(0, 102, 204));  // Azul oscuro
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));  // Fuente más grande para el título

		// Título
		String text = "Opciones";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		drawTextWithShadow(text, textX, textY);

		// Opciones del menú
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 34F));
		g2.setColor(new Color(60, 60, 60));  // Gris oscuro

		// Pantalla completa
		textX = frameX + gp.tileSize;
		textY += gp.tileSize * 2;
		drawTextWithShadow("Pantalla completa", textX, textY);
		if (commandNum == 0) {
			drawSelector(textX - 30, textY - 30, textX + 300, textY + 10);  // Resalta opción seleccionada
			if (gp.keyH.enterPressed) {
				gp.fullScreenOn = !gp.fullScreenOn;
				subState = 1;
			}
		}

		// Música
		textY += gp.tileSize;
		drawTextWithShadow("Música", textX, textY);
		if (commandNum == 1) {
			drawSelector(textX - 30, textY - 30, textX + 300, textY + 10);
		}

		// Efectos de sonido
		textY += gp.tileSize;
		drawTextWithShadow("ES", textX, textY);
		if (commandNum == 2) {
			drawSelector(textX - 30, textY - 30, textX + 300, textY + 10);
		}

		// Controles
		textY += gp.tileSize;
		drawTextWithShadow("Controles", textX, textY);
		if (commandNum == 3) {
			drawSelector(textX - 30, textY - 30, textX + 300, textY + 10);
			if (gp.keyH.enterPressed) {
				subState = 2;
				commandNum = 0;
			}
		}

		// Guardar y salir
		textY += gp.tileSize;
		drawTextWithShadow("Guardar y salir", textX, textY);
		if (commandNum == 4) {
			drawSelector(textX - 30, textY - 30, textX + 300, textY + 10);
			if (gp.keyH.enterPressed) {
				subState = 3;
				commandNum = 0;
			}
		}

		// Atrás
		textY += gp.tileSize * 2;
		drawTextWithShadow("Atrás", textX, textY);
		if (commandNum == 5) {
			drawSelector(textX - 30, textY - 30, textX + 300, textY + 10);
			if (gp.keyH.enterPressed) {
				gp.gameState = gp.playState;
				commandNum = 0;
			}
		}

		// Casilla de verificación de pantalla completa
		textX = frameX + (int) (gp.tileSize * 4.5);
		textY = frameY + gp.tileSize * 2 + 24;
		drawCheckBox(textX + 96, textY, gp.fullScreenOn);

		// Barra de volumen de música
		textY += gp.tileSize;
		drawVolumeBar(textX, textY, gp.music.volumeScale);

		// Barra de volumen de efectos de sonido
		textY += gp.tileSize;
		drawVolumeBar(textX, textY, gp.se.volumeScale);

		try {
			gp.config.saveConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void drawTextWithShadow(String text, int x, int y) {
		g2.setColor(new Color(30, 30, 30));  // Sombra más oscura
		g2.drawString(text, x + 2, y + 2);
		g2.setColor(new Color(0, 102, 204));  // Azul oscuro
		g2.drawString(text, x, y);
	}
	public void drawSelector(int x1, int y1, int x2, int y2) {
		g2.setColor(new Color(0, 102, 204, 150));  // Selector azul semi-transparente
		g2.fillRoundRect(x1, y1, x2 - x1, y2 - y1, 15, 15);
		g2.setColor(new Color(0, 0, 0));
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(x1, y1, x2 - x1, y2 - y1, 15, 15);
	}
	public void drawCheckBox(int x, int y, boolean checked) {
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.BLACK);
		g2.drawRoundRect(x, y, 24, 24, 5, 5);
		if (checked) {
			g2.setColor(new Color(0, 120, 255));
			g2.fillRoundRect(x + 4, y + 4, 16, 16, 5, 5);
		}
	}
	public void drawVolumeBar(int x, int y, int scale) {
		g2.setColor(new Color(200, 200, 200));
		g2.fillRoundRect(x, y, 120, 24, 10, 10);

		// Degradado para la barra de volumen
		GradientPaint gradient = new GradientPaint(x, y, new Color(0, 120, 255), x + 24 * scale, y, new Color(0, 60, 128));
		g2.setPaint(gradient);
		g2.fillRoundRect(x, y, 24 * scale, 24, 10, 10);
	}
	public void options_fullScreenNotification(int frameX, int frameY) {

		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize * 3;

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
		g2.setColor(Color.BLACK);  // Color principal del texto

		currentDialogue = "Es necesario cerrar la\naplicación para guardar\nel cambio.";

		// Dibuja cada línea de texto con sombra
		for (String line : currentDialogue.split("\n")) {
			drawTextWithShadow(line, textX, textY);
			textY += 40;
		}

		// Opción "Aceptar"
		textY = frameY + gp.tileSize * 9;
		drawTextWithShadow("Aceptar", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed) {
				subState = 0;
			}
		}
	}
	public void options_control(int frameX, int frameY) {

		int textX;
		int textY;

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
		g2.setColor(Color.BLACK);  // Color principal del texto

		// Título "Control"
		String text = "Control";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		drawTextWithShadow(text, textX, textY);

		// Opciones de control
		textX = frameX + gp.tileSize;
		textY += gp.tileSize;
		drawTextWithShadow("Moverse", textX, textY); textY += gp.tileSize;
		drawTextWithShadow("Confirmar/Atacar", textX, textY); textY += gp.tileSize;
		drawTextWithShadow("Disparar/Lanzar", textX, textY); textY += gp.tileSize;
		drawTextWithShadow("Inventario", textX, textY); textY += gp.tileSize;
		drawTextWithShadow("Pausa", textX, textY); textY += gp.tileSize;
		drawTextWithShadow("Opciones", textX, textY);

		// Teclas
		textX = frameX + gp.tileSize * 6;
		textY = frameY + gp.tileSize * 2;
		drawTextWithShadow("WASD", textX, textY); textY += gp.tileSize;
		drawTextWithShadow("ENTER", textX, textY); textY += gp.tileSize;
		drawTextWithShadow("L", textX, textY); textY += gp.tileSize;
		drawTextWithShadow("C", textX, textY); textY += gp.tileSize;
		drawTextWithShadow("P", textX, textY); textY += gp.tileSize;
		drawTextWithShadow("ESC", textX, textY);

		// Botón de "Volver"
		textX = frameX + gp.tileSize * 6;
		textY = frameY + gp.tileSize * 9;
		drawTextWithShadow("Volver", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed) {
				subState = 0;
				commandNum = 3;
			}
		}
	}
	public void options_endGameConfirmation(int frameX, int frameY) {

		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize * 3;

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
		g2.setColor(Color.BLACK);  // Color principal del texto

		currentDialogue = "¿Guardar la partida y\nvolver al menú principal?";

		// Dibuja cada línea de texto con sombra
		for (String line : currentDialogue.split("\n")) {
			drawTextWithShadow(line, textX, textY);
			textY += 40;
		}
		// Opción "Sí"
		String text = "Sí";
		textX = getXforCenteredText(text);
		textY += gp.tileSize * 3;
		drawTextWithShadow(text, textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed) {
				subState = 0;
				gp.gameState = gp.titleState;
				gp.stopMusic();
				gp.saveLoad.save();
				gp.playMusic(27);
			}
		}
		// Opción "No"
		text = "No";
		textX = getXforCenteredText(text);
		textY += gp.tileSize;
		drawTextWithShadow(text, textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed) {
				subState = 0;
				commandNum = 4;
			}
		}
	}
	public void drawTitleOptionsScreen() {


		Image img = new ImageIcon(getClass().getResource("/title/paisaje.png")).getImage();
		g2.drawImage(img, 0, 0, gp.screenWidth, gp.screenHeight, null);

		img = new ImageIcon(getClass().getResource("/title/tablon_madera.png")).getImage();
		g2.drawImage(img, gp.tileSize, 0, gp.screenWidth-gp.tileSize*2, gp.screenHeight, null);

		int frameX = gp.tileSize*5;
		int frameY = gp.tileSize;

		switch(subState) {
		case 0: title_options_top(frameX, frameY); break;
		case 1: title_options_fullScreenNotification(frameX, frameY); break;
		case 2: title_options_control(frameX, frameY); break;

		}
	}
	public void title_options_top(int frameX, int frameY) {
		int textX;
		int textY;

		Color mainColor = new Color(149, 40, 3);
		Color shadowColor = new Color(0, 0, 0, 150);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,50F));


		// FULL SCREEN ON/OFF
		textX = frameX;
		textY = gp.tileSize*3;
		g2.setColor(shadowColor);
		g2.drawString("PANTALLA COMPLETA", textX + 3, textY + 3);
		g2.setColor(mainColor);
		g2.drawString("PANTALLA COMPLETA", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed) {
				if(!gp.fullScreenOn) {
					gp.fullScreenOn = true;
				} else {
					gp.fullScreenOn = false;
				}
				subState = 1;
				gp.keyH.enterPressed = false;
			}
		}
		// MUSIC
		textY += gp.tileSize+18;
		g2.setColor(shadowColor);
		g2.drawString("MÚSICA", textX + 3, textY + 3);
		g2.setColor(mainColor);
		g2.drawString("MÚSICA", textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
		}
		// SE
		textY += gp.tileSize+18;
		g2.setColor(shadowColor);
		g2.drawString("ES", textX + 3, textY + 3);
		g2.setColor(mainColor);
		g2.drawString("ES", textX, textY);
		if(commandNum == 2) {
			g2.drawString(">", textX-25, textY);
		}
		// CONTROL
		textY += gp.tileSize+18;
		g2.setColor(shadowColor);
		g2.drawString("CONTROLES", textX + 3, textY + 3);
		g2.setColor(mainColor);
		g2.drawString("CONTROLES", textX, textY);
		if(commandNum == 3) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed) {
				subState = 2;
				commandNum = 0;
				gp.keyH.enterPressed = false;
			}
		}

		// BACK
		textX += gp.tileSize*9;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
		g2.setColor(shadowColor);
		g2.drawString("ATRÁS", textX + 2, textY + 2);
		g2.setColor(mainColor);
		g2.drawString("ATRÁS", textX, textY);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,25F));
		g2.setColor(shadowColor);
		g2.drawString("[ESC]", textX+12, textY+32);
		g2.setColor(mainColor);
		g2.drawString("[ESC]", textX+10, textY+30);

		// FULL SCREEN CHECKBOX
		textX = frameX + gp.tileSize * 7;
		textY = gp.tileSize * 2 + 18;

		// Dibujar el degradado para el fondo del checkbox
		GradientPaint gradientCheckbox = new GradientPaint(textX, textY, Color.LIGHT_GRAY, textX + 32, textY, Color.DARK_GRAY);
		g2.setPaint(gradientCheckbox);
		g2.fillRect(textX + 96, textY, 32, 32);

		// Dibujar el contorno del checkbox
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX + 96, textY, 32, 32);

		// Si la pantalla completa está activada, rellenar el checkbox con un degradado
		if (gp.fullScreenOn) {
			Color startColor = new Color(255, 100, 100); // Rojo más claro
			Color endColor = new Color(255, 0, 0);       // Rojo más oscuro
			GradientPaint gradientCheckboxFill = new GradientPaint(textX + 96, textY, startColor, textX + 128, textY + 32, endColor);
			g2.setPaint(gradientCheckboxFill);
			g2.fillRect(textX + 98, textY+2, 29, 29);
		}

		// MUSIC VOLUME
		textY += gp.tileSize + 18;
		textX -= 32;

		// Dibujar el degradado para la barra de volumen de música
		GradientPaint gradientMusic = new GradientPaint(textX, textY, Color.LIGHT_GRAY, textX + 160, textY, Color.DARK_GRAY);
		g2.setPaint(gradientMusic);
		g2.fillRect(textX, textY, 160, 32);

		// Dibujar el contorno de la barra de volumen de música
		g2.setColor(Color.BLACK);
		g2.drawRect(textX, textY, 160, 32);

		// Calcular la anchura de la barra de volumen de música según el nivel de volumen
		int volumeWidthMusic = 32 * gp.music.volumeScale;

		// Dibujar el degradado para el relleno de la barra de volumen de música
		GradientPaint gradientFillMusic = new GradientPaint(textX, textY, Color.BLUE, textX + volumeWidthMusic, textY, Color.CYAN);
		g2.setPaint(gradientFillMusic);
		g2.fillRect(textX+2, textY+2, volumeWidthMusic-3, 29);

		// SE VOLUME
		textY += gp.tileSize + 18;

		// Dibujar el degradado para la barra de volumen de efectos de sonido
		GradientPaint gradientSE = new GradientPaint(textX, textY, Color.LIGHT_GRAY, textX + 160, textY, Color.DARK_GRAY);
		g2.setPaint(gradientSE);
		g2.fillRect(textX, textY, 160, 32);

		// Dibujar el contorno de la barra de volumen de efectos de sonido
		g2.setColor(Color.BLACK);
		g2.drawRect(textX, textY, 160, 32);

		// Calcular la anchura de la barra de volumen de efectos de sonido según el nivel de volumen
		int volumeWidthSE = 32 * gp.se.volumeScale;

		// Dibujar el degradado para el relleno de la barra de volumen de efectos de sonido
		GradientPaint gradientFillSE = new GradientPaint(textX, textY, Color.RED, textX + volumeWidthSE, textY, Color.ORANGE);
		g2.setPaint(gradientFillSE);
		g2.fillRect(textX+2, textY+2, volumeWidthSE-3, 29);



		try {
			gp.config.saveConfig();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	public void title_options_fullScreenNotification(int frameX, int frameY){
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3-10;

		Color mainColor = new Color(163, 49, 0);
		Color shadowColor = new Color(0, 0, 0, 150);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,50F));
		currentDialogue = "ES NECESARIO CERRAR\nLA APLICACIÓN PARA\nGUARDAR EL CAMBIO.";

		for(String line: currentDialogue.split("\n")) {
			g2.setColor(shadowColor);
			g2.drawString(line, textX+3, textY+3);
			g2.setColor(mainColor);
			g2.drawString(line, textX, textY);
			textY += 60;
		}

		currentDialogue = null;

		textX += gp.tileSize*7+15;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,35F));
		g2.setColor(shadowColor);
		g2.drawString("ACEPTAR", textX + 2, textY + 2);
		g2.setColor(mainColor);
		g2.drawString("ACEPTAR", textX, textY);
		if(commandNum == 0) {
			g2.setColor(shadowColor);
			g2.drawString(">", textX-24, textY+1);
			g2.setColor(mainColor);
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed) {
				subState = 0;
				gp.keyH.enterPressed = false;
			}
		}
	}
	public void title_options_control(int frameX, int frameY) {

		Color mainColor = new Color(149, 40, 3);
		Color shadowColor = new Color(0, 0, 0, 150);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));

		Image img = new ImageIcon(getClass().getResource("/title/teclado.png")).getImage();
		g2.drawImage(img, frameX, frameY-gp.tileSize*2-10, gp.tileSize*10, gp.tileSize*10, null);



		int textX = frameX-20;
		int textY = frameY+gp.tileSize*6;

		//TEXTO
		//texto confirmar
		g2.setColor(shadowColor);
		g2.drawString("MOVERSE", textX + 1, textY + 1);
		g2.setColor(mainColor);
		g2.drawString("MOVERSE", textX, textY);

		//texto escudo
		textX += gp.tileSize*3+25;

		g2.setColor(shadowColor);
		g2.drawString("ESCUDO", textX + 1, textY + 1);
		g2.setColor(mainColor);
		g2.drawString("ESCUDO", textX, textY);

		//texto minimapa
		textX += gp.tileSize*3+25;

		g2.setColor(shadowColor);
		g2.drawString("MINIMAPA", textX + 1, textY + 1);
		g2.setColor(mainColor);
		g2.drawString("MINIMAPA", textX, textY);

		//texto mapa
		textX += gp.tileSize*3+25;

		g2.setColor(shadowColor);
		g2.drawString("MAPA", textX + 1, textY + 1);
		g2.setColor(mainColor);
		g2.drawString("MAPA", textX, textY);

		//texto inventario
		textX = frameX-20;
		textY += 40;

		g2.setColor(shadowColor);
		g2.drawString("INVENTARIO", textX + 1, textY + 1);
		g2.setColor(mainColor);
		g2.drawString("INVENTARIO", textX, textY);

		//texto confirmar
		textX += gp.tileSize*3+25;

		g2.setColor(shadowColor);
		g2.drawString("ATACAR/CONFIRM", textX + 1, textY + 1);
		g2.setColor(mainColor);
		g2.drawString("ATACAR/CONFIRM", textX, textY);

		//texto lanzar
		textX += gp.tileSize*3+25;

		g2.setColor(shadowColor);
		g2.drawString("LANZAR", textX + 1, textY + 1);
		g2.setColor(mainColor);
		g2.drawString("LANZAR", textX, textY);

		//texto pausa
		textX += gp.tileSize*3+25;

		g2.setColor(shadowColor);
		g2.drawString("PAUSA", textX + 1, textY + 1);
		g2.setColor(mainColor);
		g2.drawString("PAUSA", textX, textY);



		//CHECK BOX
		textX = frameX-45;
		textY = frameY+gp.tileSize*6-15;

		//moverse
		g2.setColor(new Color(208, 39, 39));
		g2.fillRect(textX, textY, 16, 16);

		g2.setColor(Color.BLACK); 
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(textX, textY, 16, 16);

		//escudo
		textX += gp.tileSize*3+25;

		g2.setColor(new Color (27, 142, 127));
		g2.fillRect(textX, textY, 16, 16);

		g2.setColor(Color.BLACK); 
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(textX, textY, 16, 16);

		//minimapa
		textX += gp.tileSize*3+25;

		g2.setColor(new Color (44, 152, 19));
		g2.fillRect(textX, textY, 16, 16);

		g2.setColor(Color.BLACK); 
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(textX, textY, 16, 16);

		//mapa
		textX += gp.tileSize*3+25;

		g2.setColor(new Color (65, 141, 14));
		g2.fillRect(textX, textY, 16, 16);

		g2.setColor(Color.BLACK); 
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(textX, textY, 16, 16);


		//inventario
		textX = frameX-45;
		textY +=40;

		g2.setColor(new Color(184, 97, 13));
		g2.fillRect(textX, textY, 16, 16);

		g2.setColor(Color.BLACK); 
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(textX, textY, 16, 16);

		//confirmar
		textX += gp.tileSize*3+25;

		g2.setColor(new Color(21, 20, 146));
		g2.fillRect(textX, textY, 16, 16);

		g2.setColor(Color.BLACK); 
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(textX, textY, 16, 16);

		//lanzar
		textX += gp.tileSize*3+25;

		g2.setColor(new Color(154, 24, 111));
		g2.fillRect(textX, textY, 16, 16);

		g2.setColor(Color.BLACK); 
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(textX, textY, 16, 16);

		//pausa
		textX += gp.tileSize*3+25;

		g2.setColor(new Color (158, 167, 36));
		g2.fillRect(textX, textY, 16, 16);

		g2.setColor(Color.BLACK); 
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(textX, textY, 16, 16);


		if(gp.keyH.enterPressed) {
			subState = 0;
			System.out.println("enter presionado. pasas al substate " + subState);
			gp.keyH.enterPressed = false;
		}

	}
	public void drawSaveLoadMessageError() {
		Image img = new ImageIcon(getClass().getResource("/title/paisaje.png")).getImage();
		g2.drawImage(img, 0, 0, gp.screenWidth, gp.screenHeight, null);

		img = new ImageIcon(getClass().getResource("/title/tablon_madera.png")).getImage();
		g2.drawImage(img, gp.tileSize, 0, gp.screenWidth-gp.tileSize*2, gp.screenHeight, null);

		Color mainColor = new Color(163, 49, 0);
		Color shadowColor = new Color(0, 0, 0, 150);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,50F));

		int textX = gp.tileSize*6;
		int textY = gp.tileSize*4-10;

		currentDialogue = "NO TIENES NINGUNA\nPARTIDA GUARDADA\nQUE CARGAR.";

		for(String line: currentDialogue.split("\n")) {
			g2.setColor(shadowColor);
			g2.drawString(line, textX+3, textY+3);
			g2.setColor(mainColor);
			g2.drawString(line, textX, textY);
			textY += 60;
		}

		currentDialogue = null;

		textX += gp.tileSize*7+15;

		g2.setFont(g2.getFont().deriveFont(Font.BOLD,35F));
		g2.setColor(shadowColor);
		g2.drawString("ACEPTAR", textX + 2, textY + 2);
		g2.setColor(mainColor);
		g2.drawString("ACEPTAR", textX, textY);

		g2.setColor(shadowColor);
		g2.drawString(">", textX-24, textY+1);
		g2.setColor(mainColor);
		g2.drawString(">", textX-25, textY);
		if(gp.keyH.enterPressed) {
			gp.keyH.tryToLoadGame = false;
			gp.keyH.enterPressed = false;
		}
	}
	public void drawTransition() {  

		counter++;
		g2.setColor(new Color(0, 0, 0,counter*5));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		if(counter == 50) { // transition is done
			counter = 0;
			gp.gameState = gp.playState;
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
			gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;		
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;
			gp.changeArea();

		}
	}
	public void drawFirstTransition() {

		counter++;
		int alpha = 255 - counter;

		g2.setColor(new Color(0, 0, 0, alpha));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		if(alpha <= 0) {
			counter = 0;
			//gp.changeArea();
			gp.gameState = gp.cutsceneState;
			gp.csManager.sceneNum = gp.csManager.oldmanIntro;
		}
	}
	public void drawChoosingNameScreen() {

		// VENTANA PRINCIPAL
		int frameX = gp.tileSize;
		int frameY = gp.tileSize;
		int frameWidth = gp.screenWidth - gp.tileSize*2;
		int frameHeight = gp.screenHeight - gp.tileSize*2;

		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		//VENTANA ICONO DEL JUGADOR
		frameX = gp.tileSize*2-5;
		frameY = gp.tileSize*2-5;
		frameWidth = gp.tileSize*2+10;
		frameHeight = gp.tileSize*2+10;

		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		//IMAGEN DEL JUGADOR
		int x = gp.tileSize*2;
		int y = gp.tileSize*2; 
		g2.drawImage(gp.player.down, x, y, gp.tileSize*2, gp.tileSize*2, null);

		//VENTANA DEL TEXTO PRINCIPAL
		frameX = gp.tileSize*4;
		frameY = gp.tileSize/2;
		frameWidth = gp.tileSize*12;
		frameHeight = gp.tileSize*2;

		drawSubWindow(frameX, frameY, frameWidth, frameHeight);


		// TEXTO PRINCIPAL
		Color titleColor = new Color(50, 50, 50, 150);
		g2.setColor(titleColor);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
		String text = "Elige un nombre";
		x = getXforCenteredText(text);
		y = frameY + gp.tileSize+20;
		g2.drawString(text, x, y);

		//SOMBRA
		Color shadowTitleColor = new Color(192, 192, 192, 255);
		g2.setColor(shadowTitleColor);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
		x = getXforCenteredText(text)-3;
		y = frameY + gp.tileSize+17;
		g2.drawString(text, x, y);

		//TECLAS
		// Dibujar teclas del teclado
		int keySize = 50; // Tamaño de cada tecla
		int margin = 20; // Margen entre las teclas y las filas
		int rowHeight = 60; // Altura de cada fila de teclas
		int currentX = frameX + margin; // Posición X inicial
		int currentY = gp.tileSize*5+30; // Posición Y inicial

		// Definir colores personalizados
		Color keyColor = new Color(192, 192, 192); // Color plateado
		Color shadowColor = new Color(128, 128, 128); // Sombreado más oscuro

		for (char c = 'a'; c <= 'z'; c++) {
			// Verificar si la letra actual es la seleccionada
			if (c == gp.selectedLetter) {
				// Dibujar la letra seleccionada de manera diferente
				g2.setColor(new Color(240, 190, 90)); 
				g2.fillRoundRect(currentX, currentY, keySize, keySize, 10, 10);
				g2.setColor(keyColor);
			} else {
				g2.setColor(keyColor);
				g2.fillRoundRect(currentX, currentY, keySize, keySize, 10, 10); // Tecla redondeada
			}
			g2.setColor(shadowColor);
			g2.drawRoundRect(currentX, currentY, keySize, keySize, 10, 10); // Borde sombreado
			// Dibujar letra en la tecla
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
			g2.setColor(Color.BLACK); // Color de la letra
			g2.drawString(String.valueOf(Character.toUpperCase(c)), currentX + keySize / 3 - 10, currentY + keySize / 2 + 15);

			// Mover a la siguiente posición X
			currentX += keySize + margin;
			// Salto de línea si llegamos al final de la fila
			if ((c - 'a' + 1) % 8 == 0) {
				currentX = frameX + margin;
				currentY += rowHeight;
			}
		}

		//LINEAS PARA EL NOMBRE
		keyColor = new Color(192, 192, 192); // Color plateado
		shadowColor = new Color(128, 128, 128); // Sombreado más oscuro
		Color lineColor = keyColor; // Color de las líneas

		// Dibujar líneas horizontales encima de las teclas
		int lineLength = keySize; // Longitud de cada línea (igual al ancho de una tecla)
		int startX = frameX + margin; // Posición X inicial (izquierda de la primera tecla)
		int startY = gp.tileSize * 4; // Posición Y inicial (encima de las teclas)

		for (int i = 0; i < 8; i++) {
			g2.setColor(lineColor); // Color de las líneas
			g2.fillRect(startX, startY, lineLength, 3); // Dibujar línea
			startX += keySize + margin; // Mover hacia la siguiente tecla
		}

		//ESCRIBIR NOMBRE DEL USUARIO EN PANTALLA
		int nameX = frameX + margin + 10; // Ajuste de margen y posición X
		int nameY = startY - 20; // Posición Y por encima de las líneas horizontales

		// Dibujar el nombre del jugador sobre las líneas horizontales
		g2.setColor(keyColor);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
		for (int i = 0; i < gp.player.name.length(); i++) {
			String letter = String.valueOf(gp.player.name.charAt(i));
			g2.drawString(letter, nameX + (keySize + margin) * i, nameY);
		}

		//BOTON ENTER CONTINUAR
		x = gp.tileSize*16+30;
		y = gp.tileSize+ 30;
		keySize = 75;
		g2.setColor(keyColor);
		g2.fillRoundRect(x, y, keySize, keySize, 10, 10);
		g2.setColor(shadowColor);
		g2.drawRoundRect(x, y, keySize, keySize, 10, 10);
		Image img = new ImageIcon(getClass().getResource("/title/icono_enter.png")).getImage();
		g2.drawImage(img, x, y, gp.tileSize*2-20, gp.tileSize*2-20, null);

		//TEXTO CONTINUAR
		x = gp.tileSize*16+20;
		y = gp.tileSize*4-15; 
		g2.setColor(keyColor);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 19F));
		g2.drawString("Para continuar", x, y);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 23F));
		g2.drawString("pulse [C]", x+5, y+22);		

		//BOTON BORRAR
		x = gp.tileSize*16+30;
		y = gp.tileSize*6; 
		g2.setColor(keyColor);
		g2.fillRoundRect(x, y, keySize, keySize, 10, 10);
		g2.setColor(shadowColor);
		g2.drawRoundRect(x, y, keySize, keySize, 10, 10);
		img = new ImageIcon(getClass().getResource("/title/icono_borrar.png")).getImage();
		g2.drawImage(img, x, y, gp.tileSize*2-20, gp.tileSize*2-20, null);

		//TEXTO BORRAR
		x = gp.tileSize*16+28;
		y = gp.tileSize*8+15; 
		g2.setColor(keyColor);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 23F));
		g2.drawString("[BORRAR]", x, y);

	}
	public void drawTradeScreen() {

		switch(subState) {
		case 0: trade_select(); break;
		case 1: trade_buy(); break;
		case 2: trade_sell(); break;
		}
		gp.keyH.enterPressed = false;
	}
	public void trade_select() {

		npc.currentDialogueSet = 0;
		drawDialogueScreen();

		// DRAW WINDOW
		int x = (int) (gp.tileSize * 16.3);
		int y = gp.tileSize * 5;
		int width = (int) (gp.tileSize * 3.2);
		int height = (int)(gp.tileSize * 3.5);
		drawSubWindow(x, y, width, height);

		// Color del texto
		g2.setFont(g2.getFont().deriveFont(36F));  // Asegurando que el tamaño de la fuente sea consistente
		int textX = x + gp.tileSize;
		int textY = y + gp.tileSize;

		// DRAW TEXTS WITH SHADOW
		g2.setColor(Color.GRAY);
		g2.drawString("Compr", textX + 2, textY + 2);
		g2.setColor(Color.BLACK);
		g2.drawString("Compr", textX, textY);

		if (commandNum == 0) {
			g2.drawString(">", textX - 24, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 1;
			}
		}

		// Siguiente opción
		textY += gp.tileSize;
		g2.setColor(Color.GRAY);
		g2.drawString("Vende", textX + 2, textY + 2);
		g2.setColor(Color.BLACK);
		g2.drawString("Vende", textX, textY);

		if (commandNum == 1) {
			g2.drawString(">", textX - 24, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 2;
			}
		}

		// Última opción
		textY += gp.tileSize;
		g2.setColor(Color.GRAY);
		g2.drawString("Salir", textX + 2, textY + 2);
		g2.setColor(Color.BLACK);
		g2.drawString("Salir", textX, textY);

		if (commandNum == 2) {
			g2.drawString(">", textX - 24, textY);
			if (gp.keyH.enterPressed == true) {
				commandNum = 0;
				npc.startDialogue(npc, 1);
			}
		}
	}
	public void trade_buy() {

		// DRAW PLAYER INVENTORY
		drawInventory(gp.player, false);

		// DRAW NPC INVENTORY
		drawInventory(npc, true);

		// DRAW HINT WINDOW
		int x = gp.tileSize * 2;
		int y = gp.tileSize * 9;
		int width = gp.tileSize * 6;
		int height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);

		// Sombra y Texto "[ESC] Atrás"
		g2.setColor(Color.GRAY);
		g2.drawString("[ESC] Atrás", x + 25, y + 61);  
		g2.setColor(Color.BLACK);
		g2.drawString("[ESC] Atrás", x + 24, y + 60);  

		// DRAW PLAYER COIN WINDOW
		x = gp.tileSize * 12;
		y = gp.tileSize * 9;
		width = gp.tileSize * 6;
		height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);

		// Sombra y Texto "Tus monedas: "
		String coinsText = "Tus monedas: " + gp.player.coin;
		g2.setColor(Color.GRAY);
		g2.drawString(coinsText, x + 25, y + 61); 
		g2.setColor(Color.BLACK);
		g2.drawString(coinsText, x + 24, y + 60);  

		// DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
		if (itemIndex < npc.inventory.size()) {
			x = (int) (gp.tileSize * 5.5);
			y = (int) (gp.tileSize * 5.5);
			width = (int) (gp.tileSize * 2.5);
			height = gp.tileSize;
			drawSubWindow(x, y, width, height);
			g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

			int price = npc.inventory.get(itemIndex).price;
			String priceText = "" + price;

			// Sombra y Texto del precio
			int priceX = getXforAlignRightText(priceText, gp.tileSize * 8 - 20);
			g2.setColor(Color.GRAY);
			g2.drawString(priceText, priceX + 1, y + 33);  
			g2.setColor(Color.BLACK);
			g2.drawString(priceText, priceX, y + 32);

			// BUY AN ITEM
			if (gp.keyH.enterPressed == true) {
				if (npc.inventory.get(itemIndex).price > gp.player.coin) {
					subState = 0;
					npc.startDialogue(npc, 2);
				} else if (gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true) {
					gp.player.coin -= npc.inventory.get(itemIndex).price;
				} else {
					subState = 0;
					npc.startDialogue(npc, 3);
				}
			}
		}
	}
	public void trade_sell() {

		// DRAW PLAYER INVENTORY
		drawInventory(gp.player, true);

		int x;
		int y;
		int width;
		int height;

		// DRAW HINT WINDOW
		x = gp.tileSize * 2;
		y = gp.tileSize * 9;
		width = gp.tileSize * 6;
		height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);

		// Sombra y Texto "[ESC] Atrás"
		g2.setColor(Color.GRAY);
		g2.drawString("[ESC] Atrás", x + 25, y + 61);
		g2.setColor(Color.BLACK);
		g2.drawString("[ESC] Atrás", x + 24, y + 60);

		// DRAW PLAYER COIN WINDOW
		x = gp.tileSize * 12;
		y = gp.tileSize * 9;
		width = gp.tileSize * 6;
		height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);

		// Sombra y Texto "Tus monedas: "
		String coinsText = "Tus monedas: " + gp.player.coin;
		g2.setColor(Color.GRAY);
		g2.drawString(coinsText, x + 25, y + 61);
		g2.setColor(Color.BLACK);
		g2.drawString(coinsText, x + 24, y + 60);

		// DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
		if (itemIndex < gp.player.inventory.size()) {

			x = (int) (gp.tileSize * 15.5);
			y = (int) (gp.tileSize * 5.5);
			width = (int) (gp.tileSize * 2.5);
			height = gp.tileSize;
			drawSubWindow(x, y, width, height);
			g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

			int price = gp.player.inventory.get(itemIndex).price / 2;
			String text = "" + price;

			// Sombra y Texto del precio
			int priceX = getXforAlignRightText(text, gp.tileSize * 18 - 20);
			g2.setColor(Color.GRAY);
			g2.drawString(text, priceX + 1, y + 33);
			g2.setColor(Color.BLACK);
			g2.drawString(text, priceX, y + 32);

			// SELL AN ITEM
			if (gp.keyH.enterPressed == true) {
				Entity item = gp.player.inventory.get(itemIndex);
				if (item == gp.player.currentWeapon ||
						item == gp.player.currentShield ||
						item == gp.player.currentLight ||
						item == gp.player.currentMapItem) {
					commandNum = 0;
					subState = 0;
					npc.startDialogue(npc, 4);
				} else if (item instanceof OBJ_Sword_Normal || item instanceof OBJ_Shield_Wood) {
					commandNum = 0;
					subState = 0;
					npc.startDialogue(npc, 5);
				} else {
					if (item.amount > 1) {
						item.amount--;
					} else {
						gp.player.inventory.remove(itemIndex);
					}
					gp.player.coin += price;
				}
			}
		}
	}
	public void drawSleepScreen() {
		counter++;

		if(counter < 120) {
			gp.eManager.lighting.filterAlpha += 0.01f;
			if(gp.eManager.lighting.filterAlpha > 1f) {
				gp.eManager.lighting.filterAlpha = 1f;
			}
		}
		if(counter >= 120) {
			gp.eManager.lighting.filterAlpha -= 0.01f;
			if(gp.eManager.lighting.filterAlpha <= 0f) {
				gp.eManager.lighting.filterAlpha = 0f;
				counter = 0;
				gp.eManager.lighting.dayState = gp.eManager.lighting.day;
				gp.eManager.lighting.dayCounter = 0;
				gp.gameState = gp.playState;
				gp.player.getImage();
			}
		}
	}
	public int getItemIndexOnSlot(int slotCol, int slotRow) {
		return slotCol + (slotRow*5);
	}
	public void drawSubWindow(int x, int y, int width, int height) {
		// Bordes externos
		Color outerBorderColor = new Color(0, 0, 0);
		int outerBorderThickness = 4;

		// Bordes internos 
		Color innerBorderColor = new Color(85, 85, 85);
		int innerBorderThickness = 2;

		// Fondo del bocadillo
		Color backgroundColor = new Color(173, 216, 230); 

		// Área de texto
		Color textAreaColor = new Color(255, 255, 255);

		// Dibuja el fondo del bocadillo
		g2.setColor(backgroundColor);
		g2.fillRoundRect(x, y, width, height, 20, 20);

		// Dibuja el borde externo
		g2.setColor(outerBorderColor);
		g2.setStroke(new BasicStroke(outerBorderThickness));
		g2.drawRoundRect(x, y, width, height, 20, 20);

		// Dibuja el borde interno
		g2.setColor(innerBorderColor);
		g2.setStroke(new BasicStroke(innerBorderThickness));
		g2.drawRoundRect(x + outerBorderThickness, y + outerBorderThickness, 
				width - (outerBorderThickness * 2), height - (outerBorderThickness * 2), 15, 15);

		// Dibuja el área de texto
		int textAreaPadding = 10;
		g2.setColor(textAreaColor);
		g2.fillRoundRect(x + outerBorderThickness + textAreaPadding, y + outerBorderThickness
				+ textAreaPadding, width - (outerBorderThickness * 2) - (textAreaPadding * 2),
				height - (outerBorderThickness * 2) - (textAreaPadding * 2), 10, 10);
	}
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return gp.screenWidth/2 - length/2;
	}
	public int getXforAlignRightText(String text, int tailX) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return tailX - length;
	}
}
