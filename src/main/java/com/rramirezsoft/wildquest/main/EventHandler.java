package com.rramirezsoft.wildquest.main;

import com.rramirezsoft.wildquest.data.Progress;
import com.rramirezsoft.wildquest.entity.Entity;

public class EventHandler {

	GamePanel gp;
	EventRect[][][] eventRect;
	Entity eventMaster;

	// Ponemos un margern en el que el jugador nio puede volver a activar el evento
	int previousEventX;
	int previousEventY;
	boolean canTouchEvent = true;
	int tempMap, tempCol, tempRow;

	public EventHandler(GamePanel gp) {
		this.gp = gp;

		eventMaster = new Entity(gp);

		eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

		int map = 0;
		int col = 0;
		int row = 0;

		while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
			eventRect[map][col][row] = new EventRect();
			eventRect[map][col][row].x = 23;
			eventRect[map][col][row].y = 23;
			eventRect[map][col][row].width = 2;
			eventRect[map][col][row].height = 2;
			eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;

				if(row == gp.maxWorldRow) {
					row = 0;
					map++;
				}
			}
		}		

	}
	public void checkEvent() {
		// Check if the player character is more than 1 tile away from the last event
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);

		if(distance > gp.tileSize) {
			canTouchEvent = true;
		}

		if(canTouchEvent) {
			// del bosque a la aldea
			if(hit(4, 10, 18, "left")) {teleport(0, 39, 12, gp.outside);}
			else if(hit(0, 39, 12, "right")) {teleport(4, 10, 18, gp.village);}

			//del bosque al laberinto
			else if(hit(0, 10, 6, "up")) {teleport(5, 17, 43, gp.outside);}
			else if(hit(5, 17, 43, "down")) {teleport(0, 10, 6, gp.outside);}

			//del laberinto al pasillo previo al boss
			else if(hit(5, 39, 6, "up")) {teleport(6, 15, 43, gp.outside);}
			else if(hit(6, 15, 43, "down")) {teleport(5, 39, 6, gp.outside);}

			//del pasillo del boss al campo de batalla
			else if(hit(6, 34, 43, "down")) {teleport(7, 24, 6, gp.outside);}
			else if(hit(7, 24, 6, "up")) {teleport(6, 34, 43, gp.outside);}

			//escena del boss
			else if(hit(7, 24, 13, "any")) { treeLord();} //BOSS

			//del campo de batalla a la aldea de vuelta
			else if(hit(7, 24, 43, "down")) {teleport(4, 13, 6, gp.village);}

			// a la cottage1
			else if(hit(4, 32, 32, "up")) {teleport(1, 24, 29, gp.indor);} //al mercader
			else if(hit(1, 24, 29, "down")) {teleport(4, 32, 32, gp.village);} //para salir
			else if(hit(1, 19, 26, "up")) {speak(gp.npc[1][1]);} //para comerciar

			//a la house1
			else if(hit(4, 22, 21, "up")) {teleport(1, 24, 29, gp.indor);}

			// al th
			else if(hit(4, 24, 31, "up")) {teleport(8, 24, 29, gp.indor);} //al mercader
			else if(hit(8, 24, 29, "down")) {teleport(4, 24, 31, gp.village);} //para salir

			//dungeon
			else if(hit(4, 28, 5, "up")) { teleport(2, 9, 41, gp.dungeon);} // a la dungeon
			else if(hit(2, 9, 41, "any")) { teleport(4, 28, 5, gp.outside);} //para salir
			else if(hit(2, 8, 7, "any")) { teleport(3, 26, 41, gp.dungeon);} //planta B2
			else if(hit(3, 26, 41, "any")) { teleport(2, 8, 7, gp.dungeon);} //planta B1
			else if(hit(3, 25, 27, "any")) { skeletonLord();} //BOSS

		}

	}
	public boolean hit(int map, int col, int row, String reqDirection) {
		boolean hit = false;


		if(map == gp.currentMap) {
			gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
			gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
			eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
			eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;

			if((gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) &&
					(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any"))){

				hit = true;
				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;

			}

			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
			eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
			eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
		}
		return hit;
	}
	public void teleport(int map, int col, int row, int area) {

		gp.gameState = gp.transitionState;
		gp.nextArea = area;
		tempMap = map;
		tempCol = col;
		tempRow = row;
		canTouchEvent = false;
		gp.playSE(15);

	}
	public void damagePit(int gameState) {

		gp.gameState = gameState;
		gp.playSE(24);
		eventMaster.startDialogue(eventMaster, 0);
		gp.player.life -= 1;
		canTouchEvent = false;
	}
	public void speak(Entity entity) {

		if(gp.keyH.enterPressed == true) {
			gp.gameState = gp.dialogueState;
			gp.player.attackCanceled = true;
			entity.speak();
		}
	}
	public void skeletonLord() {

		if(gp.bossBattleOn == false && Progress.skeletonLordDefeated == false) {
			gp.gameState = gp.cutsceneState;
			gp.csManager.sceneNum = gp.csManager.skeletonLord;
		}
	}
	public void treeLord() {

		if(gp.bossBattleOn == false && Progress.treeLordDefeated == false) {
			gp.gameState = gp.cutsceneState;
			gp.csManager.sceneNum = gp.csManager.treeLord;
		}
	}	
}
