package com.rramirezsoft.wildquest.tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.rramirezsoft.wildquest.main.GamePanel;
import com.rramirezsoft.wildquest.main.UtilityTool;


public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	// Variable en la que guardaremos los números del archivo de texto del mapa
	public int[][][]mapTileNum;
	public boolean drawPath = true;
	ArrayList<String>fileNames = new ArrayList<>();
	ArrayList<String>collisionStatus = new ArrayList<>();



	public TileManager(GamePanel gp) {

		this.gp = gp;

		// READ TILE DATA FILE TILE EDITOR SOLAMENTE
		InputStream is = getClass().getResourceAsStream("/maps/tiledata.txt");		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		// GETTING TILE NAMES AND COLLISION INFO FROM THE FILE
		String line;

		try {

			while((line = br.readLine()) != null){
				fileNames.add(line);
				collisionStatus.add(br.readLine());
			}
			br.close();		

		}catch(IOException e) {
			e.printStackTrace();
		}

		// INITIALIZE THE TILE ARRAY BASED ON THE fileNames size
		tile = new Tile[fileNames.size()]; // Usamos esta linea solo con tile editor
		getTileImage(); // Llamamos a este método aquí solo con tilemap editor

		// GET THE maxWorldCol & row
		is = getClass().getResourceAsStream("/maps/veridia.txt");
		br = new BufferedReader(new InputStreamReader(is));

		try {
			String line2 = br.readLine();
			String maxTile[] = line2.split(" ");

			gp.maxWorldCol = maxTile.length;
			gp.maxWorldRow = maxTile.length;
			mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

			br.close();
		}
		catch(IOException e) {
			System.out.println("Exception");
		}
		// Aquí se cargan los mapas, cada cual con un número asignado
		loadMap("/maps/bosqueInicial.txt", 0);
		loadMap("/maps/interiorCottage.txt", 1);
		loadMap("/maps/dungeon01.txt", 2);
		loadMap("/maps/dungeon02.txt", 3);
		loadMap("/maps/veridia.txt", 4);
		loadMap("/maps/laberinto.txt", 5);
		loadMap("/maps/pasilloBoss.txt", 6);
		loadMap("/maps/mapaBoss.txt", 7);
		loadMap("/maps/interiorTH.txt", 8);

	}

	public void getTileImage() {

		for(int i = 0; i < fileNames.size(); i++) {

			String fileName;
			boolean collision;

			// GET file name
			fileName = fileNames.get(i);

			// GET a collision status
			if(collisionStatus.get(i).equals("true")) {
				collision = true;
			}
			else {
				collision = false;
			}

			setup(i, fileName, collision);
		}
	}
	public void setup(int index, String imageName, boolean collision) {
		UtilityTool uTool = new UtilityTool();

		try {
			tile[index] = new Tile();

			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName));// VERSION SIN TILE EDITOR
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void loadMap(String filePath, int map) {
		InputStream is = getClass().getResourceAsStream(filePath);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try {
			int col = 0;
			int row = 0;

			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();

				while(col < gp.maxWorldCol) {
					// Eliminamos los espacios en blanco
					String[] numbers = line.split(" ");

					int num = Integer.parseInt(numbers[col]);

					mapTileNum[map][col][row] = num;
					col ++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row ++;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);

		// Automatizamos proceso de creacion de tiles
		int worldCol = 0;
		int worldRow = 0;

		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

			int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;

			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
					worldX - gp.tileSize <  gp.player.worldX + gp.player.screenX &&
					worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
					worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}


			worldCol++;

			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;

				worldRow ++;

			}
		}
		//		if(drawPath == true) {
		//			g2.setColor(new Color(255,0,0,70));
		//			
		//			for(int i = 0; i < gp.pFinder.pathList.size(); i++) {
		//				
		//				int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
		//				int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
		//				int screenX = worldX - gp.player.worldX + gp.player.screenX;
		//				int screenY = worldY - gp.player.worldY + gp.player.screenY;
		//				
		//				g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
		//			}
		//		}

	}

}
