package com.rramirezsoft.wildquest.tile;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class Map extends TileManager{

	GamePanel gp;
	BufferedImage worldMap[];
	public boolean miniMapOn = false;

	public Map(GamePanel gp) {
		super(gp);
		this.gp = gp;
		createWorldMap();

	}
	public void createWorldMap() {
		worldMap = new BufferedImage[gp.maxMap];
		int worldMapWidth = gp.tileSize * gp.maxWorldCol;
		int worldMapHeight = gp.tileSize * gp.maxWorldRow;

		for(int i = 0; i < gp.maxMap; i++) {

			worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = worldMap[i].createGraphics();

			int col = 0;
			int row = 0;

			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				int tileNum = mapTileNum[i][col][row];
				int x = gp.tileSize * col;
				int y = gp.tileSize * row;
				g2.drawImage(tile[tileNum].image, x, y, null);

				col++;

				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}	
			}
			g2.dispose();
		}
	}
	public void drawFullMapScreen(Graphics2D g2) {
		// Background color
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		// Draw map
		int mapWidth = 500; // Tamaño del mapa a dibujar
		int mapHeight = 500;
		int mapX = gp.screenWidth / 2 - mapWidth / 2; // Localización del mapa en la pantalla
		int mapY = gp.screenHeight / 2 - mapHeight / 2;
		g2.drawImage(worldMap[gp.currentMap], mapX, mapY, mapWidth, mapHeight, null);

		// Draw Player
		double scale = (double) (gp.tileSize * gp.maxWorldCol) / mapWidth;
		int playerX = (int) (mapX + gp.player.worldX / scale);
		int playerY = (int) (mapY + gp.player.worldY / scale);
		int playerSize = (int) (gp.tileSize / scale);
		g2.drawImage(gp.player.down2, playerX, playerY, playerSize, playerSize, null);

		// Draw structures (provisional, se ven un poco raro)
		for (Entity structure : gp.struct[gp.currentMap]) {
			if (structure != null) {
				int structX = (int) (mapX + structure.worldX / scale);
				int structY = (int) (mapY + structure.worldY / scale);
				int structWidth = (int) (structure.solidArea.width / scale);
				int structHeight = (int) (structure.solidArea.height / scale)*2;
				g2.drawImage(structure.down1, structX, structY, structWidth, structHeight, null);

			}
		}

		// Hint
		g2.setFont(g2.getFont().deriveFont(24F));
		g2.setColor(Color.white);
		g2.drawString("Pulsa M para cerrar", 750, 550);
	}

	public void drawMiniMap(Graphics2D g2) {
		if(miniMapOn == true) {
			// Draw map
			int width = 200; // Tamaño
			int height = 200;
			int x = gp.screenWidth - width - 50;	// Localización en la pantalla
			int y = 50;
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);	

			// Draw Player
			double scale = (double)(gp.tileSize * gp.maxWorldCol)/width;
			int playerX = (int)(x + gp.player.worldX/scale);
			int playerY = (int)(y + gp.player.worldY/scale);
			int playerSize = gp.tileSize/5;
			g2.drawImage(gp.player.down2, playerX-3, playerY-3, playerSize, playerSize, null);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
	}


}
