package com.rramirezsoft.wildquest.main;


import java.awt.Graphics2D;

import com.rramirezsoft.wildquest.entity.npc.NPC_OldMan;
import com.rramirezsoft.wildquest.entity.player.PlayerDummy;
import com.rramirezsoft.wildquest.monster.MON_SkeletonLord;
import com.rramirezsoft.wildquest.monster.MON_TreeLord;
import com.rramirezsoft.wildquest.object.OBJ_Door_Iron;


public class CutsceneManager {

	GamePanel gp;
	Graphics2D g2;
	public int sceneNum;
	public int scenePhase;

	//numero de escena
	public final int NA = 0;
	public final int skeletonLord = 1;
	public final int treeLord = 2;
	public final int oldmanIntro = 3;
	public final int oldmanToTH = 4;

	public CutsceneManager(GamePanel gp) {
		this.gp = gp;

	}
	public void draw(Graphics2D g2) {
		this.g2 = g2;

		switch(sceneNum) {
		case skeletonLord: scene_skeletonLord(); break;
		case treeLord: scene_treeLord(); break;
		case oldmanIntro: scene_oldmanIntro(); break;
		case oldmanToTH: scene_oldmanToTH(); break;
		}
	}
	public void scene_skeletonLord() {

		if(scenePhase == 0) {

			gp.bossBattleOn = true;

			//cerrar la puerta
			for (int i = 0; i < gp.obj[1].length; i++) {

				if(gp.obj[gp.currentMap][i] == null) {
					gp.obj[gp.currentMap][i] = new OBJ_Door_Iron(gp);
					gp.obj[gp.currentMap][i].worldX = gp.tileSize*25;
					gp.obj[gp.currentMap][i].worldY = gp.tileSize*28;
					gp.obj[gp.currentMap][i].temp = true;
					gp.playSE(22);
					break;
				}
			}

			//buscamos un hueco para el jugador ficticio
			for (int i = 0; i < gp.npc[1].length; i++) {

				if(gp.npc[gp.currentMap][i] == null) {

					gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
					gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
					gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
					gp.npc[gp.currentMap][i].direction = gp.player.direction;
					break;
				}
			}

			gp.player.drawing = false;

			scenePhase++;
		}
		if(scenePhase == 1) {

			gp.player.worldY -=2;

			if(gp.player.worldY < gp.tileSize*16) {
				scenePhase++;
			}
		}
		if(scenePhase == 2) {

			//buscamos al boss en el array
			for (int i = 0; i < gp.monster[1].length; i++) {

				if(gp.monster[gp.currentMap][i] != null && 
						gp.monster[gp.currentMap][i].name == MON_SkeletonLord.monName) {

					gp.monster[gp.currentMap][i].sleep = false;
					gp.ui.npc = gp.monster[gp.currentMap][i];
					scenePhase++;
					break;

				}
			}
		}
		if(scenePhase == 3) {
			gp.ui.drawDialogueScreen();
		}
		if(scenePhase == 4) {

			//la camara vuelve al jugador

			//buscamos el jugador ficticio
			for (int i = 0; i < gp.npc[1].length; i++) {

				if(gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)) {

					//reseteamos a la posicion inicial
					gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
					gp.player.worldY = gp.npc[gp.currentMap][i].worldY;

					//eliminamos al jugador ficticio
					gp.npc[gp.currentMap][i] = null;
					break;

				}
			}

			//dibujamos al jugador
			gp.player.drawing = true;

			//reset
			sceneNum = NA;
			scenePhase = 0;
			gp.gameState = gp.playState;

			//cambiamos la muscia 
			gp.stopMusic();
			gp.playMusic(25);
		}

	}
	public void scene_treeLord() {

		if(scenePhase == 0) {

			gp.bossBattleOn = true;

			//cerrar la puerta
			for (int i = 0; i < gp.obj[1].length; i++) {

				if(gp.obj[gp.currentMap][i] == null) {
					gp.obj[gp.currentMap][i] = new OBJ_Door_Iron(gp);
					gp.obj[gp.currentMap][i].worldX = gp.tileSize*24;
					gp.obj[gp.currentMap][i].worldY = gp.tileSize*12;
					gp.obj[gp.currentMap][i].temp = true;
					gp.playSE(22);
					break;
				}
			}

			//buscamos un hueco para el jugador ficticio
			for (int i = 0; i < gp.npc[1].length; i++) {

				if(gp.npc[gp.currentMap][i] == null) {

					gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
					gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
					gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
					gp.npc[gp.currentMap][i].direction = gp.player.direction;
					break;
				}
			}
			gp.player.drawing = false;
			scenePhase++;
		}
		if(scenePhase == 1) {

			gp.player.worldY +=2;

			if(gp.player.worldY > gp.tileSize*23) {
				scenePhase++;
			}
		}
		if (scenePhase == 2) {
			// buscamos al boss en el array
			for (int i = 0; i < gp.monster[1].length; i++) {
				if (gp.monster[gp.currentMap][i] != null &&
						gp.monster[gp.currentMap][i].name.equals(MON_TreeLord.monName)) {

					gp.monster[gp.currentMap][i].sleep = false;
					gp.ui.npc = gp.monster[gp.currentMap][i];
					gp.ui.currentDialogue = gp.ui.npc.dialogues.get(gp.ui.npc.currentDialogueSet).get(gp.ui.npc.currentDialogueIndex);
					scenePhase++;
					break;
				}
			}
		}
		if(scenePhase == 3) {
			gp.ui.drawDialogueScreen();
		}
		if(scenePhase == 4) {

			//la camara vuelve al jugador

			//buscamos el jugador ficticio
			for (int i = 0; i < gp.npc[1].length; i++) {

				if(gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)) {

					//reseteamos a la posicion inicial
					gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
					gp.player.worldY = gp.npc[gp.currentMap][i].worldY;

					//eliminamos al jugador ficticio
					gp.npc[gp.currentMap][i] = null;
					break;

				}
			}

			//dibujamos al jugador
			gp.player.drawing = true;

			//reset
			sceneNum = NA;
			scenePhase = 0;
			gp.gameState = gp.playState;

			//cambiamos la muscia 
			gp.stopMusic();
			gp.playMusic(25);
		}

	}
	public void scene_oldmanIntro() {

		if(scenePhase == 0) {
			// Buscar al Old Man en el array de NPCs
			for (int i = 0; i < gp.npc[gp.currentMap].length; i++) {
				if (gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(NPC_OldMan.npcName)) {
					gp.ui.npc = gp.npc[gp.currentMap][i];
					break;
				}
			}
			scenePhase++;
		}

		int currentCol = (gp.ui.npc.worldX + gp.ui.npc.solidArea.x) / gp.tileSize;
		int currentRow = (gp.ui.npc.worldY - 16 + gp.ui.npc.solidArea.y) / gp.tileSize;

		int playerCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
		int playerRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

		//Old Man se acerca al jugador
		if((scenePhase == 1) && (gp.ui.npc != null)) {

			gp.ui.npc.searchPath(playerCol, playerRow);
			if (currentCol == playerCol && currentRow == playerRow) {
				scenePhase++;
			}
		}
		if (scenePhase == 2) {
			// Diálogo del Old Man
			gp.ui.npc.speed = 0;
			gp.ui.npc.direction = "left";
			gp.player.direction = "right";

			if (gp.ui.currentDialogue == null) {
				String rawDialogue = gp.ui.npc.dialogues.get(gp.ui.npc.currentDialogueSet).get(gp.ui.npc.currentDialogueIndex);
				gp.ui.currentDialogue = gp.ui.npc.modifyDialogue(rawDialogue);
			}
			gp.ui.drawDialogueScreen();
		}
		if (scenePhase == 3) {
			// Old Man se va
			gp.ui.npc.speed = gp.ui.npc.defaultSpeed;
			gp.ui.npc.searchPath(14, 35);
			if (currentCol == 14 && currentRow == 35) {
				gp.ui.npc.moveNPCToMap(gp.ui.npc, 0, 4, (gp.tileSize * 24) + 24, gp.tileSize * 32);
				gp.ui.npc.direction = "down";
				scenePhase++;
			}
		}
		if (scenePhase == 4) {
			sceneNum = NA;
			scenePhase = 0;
			gp.gameState = gp.playState;
		}
	}
	public void scene_oldmanToTH() {

		gp.player.moving = false;

		// Buscar al Old Man en el array de NPCs
		for (int i = 0; i < gp.npc[gp.currentMap].length; i++) {
			if (gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(NPC_OldMan.npcName)) {
				gp.ui.npc = gp.npc[gp.currentMap][i];
				break;
			}
		}
		//Old Man se acerca al jugador
		if(gp.ui.npc != null) {

			int currentCol = (gp.ui.npc.worldX + gp.ui.npc.solidArea.x) / gp.tileSize;
			int currentRow = (gp.ui.npc.worldY - 16 + gp.ui.npc.solidArea.y) / gp.tileSize;

			int goalCol = 24;
			int goalRow = 30;
			gp.ui.npc.searchPath(goalCol, goalRow);

			if(currentCol == goalCol && currentRow == goalRow) {
				gp.ui.npc.moveNPCToMap(gp.ui.npc, 4, 8, gp.tileSize*24, gp.tileSize*25);
				gp.player.moving = true;	
			}
		}		
	}
}
