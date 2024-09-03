package com.rramirezsoft.wildquest.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.rramirezsoft.wildquest.main.GamePanel;

public class SaveLoad {

	GamePanel gp;
	private static final String SAVE_FILE = "save.dat";

	public SaveLoad(GamePanel gp) {
		this.gp = gp;
	}
	public void save() {

		try {

			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(SAVE_FILE)));
			DataStorage ds = new DataStorage();

			//PLAYER STATS
			ds.name = gp.player.name;
			ds.level = gp.player.level;
			ds.maxLife = gp.player.maxLife;
			ds.life = gp.player.life;
			ds.maxProtect = gp.player.maxProtect;
			ds.protect = gp.player.protect;
			ds.maxAmmo = gp.player.maxAmmo;
			ds.ammo = gp.player.ammo;
			ds.exp = gp.player.exp;
			ds.nextLevelExp = gp.player.nextLevelExp;
			ds.coin = gp.player.coin;

			ds.start = true; //quitamos la animacion inicial
			ds.currentArea = gp.currentArea;

			//DAY STATE
			ds.dayState = gp.eManager.lighting.dayState; //momento del dia
			ds.dayCounter = gp.eManager.lighting.dayCounter;
			ds.filterAlpha = gp.eManager.lighting.filterAlpha;

			//PLAYER INVENTORY
			for(int i = 0; i < gp.player.inventory.size(); i++) {
				ds.itemNames.add(gp.player.inventory.get(i).name);
				ds.itemAmounts.add(gp.player.inventory.get(i).amount);	    	
			}

			//PLAYER EQUIPMENT
			ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
			ds.currentShieldSlot = gp.player.getCurrentShieldSlot();

			if(gp.player.currentLight != null) {
				ds.gotLantertn = true;
				ds.currentLightSlot = gp.player.getCurrentLightSlot();
			}

			//OBJECTS DURABILITIES
			for (int i = 0; i < gp.player.inventory.size(); i++) {
				ds.itemDurabilities.add(gp.player.inventory.get(i).durability);
			}

			//OBJECTS ON MAP
			ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
			ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
			ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
			ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[1].length];
			ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[1].length];


			for(int mapNum = 0; mapNum < gp.maxMap; mapNum++) {

				for (int i = 0; i < gp.obj[1].length; i++) {

					if(gp.obj[mapNum][i] == null) {
						ds.mapObjectNames[mapNum][i] = "NA";
					} else {
						ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
						ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
						ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
						if(gp.obj[mapNum][i].loot != null) {
							ds.mapObjectLootNames[mapNum][i] = gp.obj[mapNum][i].loot.name;
						}
						ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
					}
				}
			}

			//POSITION PLAYER
			ds.playerWorldX = gp.player.worldX;
			ds.playerWorldY = gp.player.worldY;
			ds.currentMap = gp.currentMap;
			ds.direction = gp.player.direction;

			// NPC DATA
			for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
				for (int i = 0; i < gp.npc[mapNum].length; i++) {
					if (gp.npc[mapNum][i] != null) {
						ds.npcNames[mapNum][i] = gp.npc[mapNum][i].name;
						ds.npcWorldX[mapNum][i] = gp.npc[mapNum][i].worldX;
						ds.npcWorldY[mapNum][i] = gp.npc[mapNum][i].worldY;
						ds.npcDirection[mapNum][i] = gp.npc[mapNum][i].direction;
						ds.npcCurrentDialogueSet[mapNum][i] = gp.npc[mapNum][i].currentDialogueSet; 						
						ds.npcCurrentMap[mapNum][i] = mapNum;						
					}
				}
			}

			//MONSTER DATA
			for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
				for (int i = 0; i < gp.monster[mapNum].length; i++) {
					if (gp.monster[mapNum][i] != null) {
						ds.monsterNames[mapNum][i] = gp.monster[mapNum][i].name;
						ds.monsterWorldX[mapNum][i] = gp.monster[mapNum][i].worldX;
						ds.monsterWorldY[mapNum][i] = gp.monster[mapNum][i].worldY;
						ds.monsterDirection[mapNum][i] = gp.monster[mapNum][i].direction;
						ds.monsterCurrentMap[mapNum][i] = mapNum;						
					}
				}
			}
			//PROGRESS
			ds.skeletonLordDefeated = Progress.skeletonLordDefeated;
			ds.treeLordDefeated = Progress.treeLordDefeated;
			System.out.println("Progreso guardado = " + ds.treeLordDefeated);

			//escribir objeto DataStorage
			oos.writeObject(ds);
			System.out.println("partida guardada");

		} catch (Exception e) {
			System.out.println("Save Exception!!!");
			e.printStackTrace();
		}

	}
	public void load() {

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(SAVE_FILE)));

			//leer el objeto DataStorage
			DataStorage ds = (DataStorage)ois.readObject();

			//PLAYER STATS 
			gp.player.name = ds.name;
			gp.player.level = ds.level;
			gp.player.maxLife = ds.maxLife;
			gp.player.life = ds.life;
			gp.player.maxProtect = ds.maxProtect;
			gp.player.protect = ds.protect;
			gp.player.maxAmmo = ds.maxAmmo;
			gp.player.ammo = ds.ammo;
			gp.player.exp = ds.exp;
			gp.player.nextLevelExp = ds.nextLevelExp;
			gp.player.coin = ds.coin;   
			gp.currentArea = ds.currentArea;

			//DAY STATE
			gp.eManager.lighting.dayState = ds.dayState;	
			gp.eManager.lighting.dayCounter = ds.dayCounter;
			gp.eManager.lighting.filterAlpha = ds.filterAlpha;

			//PLAYER INVENTORY
			gp.player.inventory.clear();
			for (int i = 0; i < ds.itemNames.size(); i++) {
				gp.player.inventory.add(gp.eGenerator.getObject(ds.itemNames.get(i)));
				gp.player.inventory.get(i).amount = ds.itemAmounts.get(i);
			}

			//OBJECTS DURABILITIES
			for (int i = 0; i < ds.itemDurabilities.size(); i++) {
				gp.player.inventory.get(i).durability = ds.itemDurabilities.get(i);
			}

			//PLAYER EQUIPMENT
			gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
			gp.player.currentShield = gp.player.inventory.get(ds.currentShieldSlot);

			if(ds.gotLantertn) {
				gp.player.currentLight = gp.player.inventory.get(ds.currentLightSlot);
				gp.player.lightUpdated = true;
			}

			gp.player.getAttack();
			gp.player.getDefense();
			gp.player.getAttackImage();
			gp.player.getGuardImage();


			//OBJECTS ON MAP
			for(int mapNum = 0; mapNum < gp.maxMap; mapNum++) {

				for (int i = 0; i < gp.obj[1].length; i++) {

					if(ds.mapObjectNames[mapNum][i].equals("NA")) {
						gp.obj[mapNum][i] = null;
					}
					else {
						gp.obj[mapNum][i] = gp.eGenerator.getObject(ds.mapObjectNames[mapNum][i]);
						if (gp.obj[mapNum][i] != null) {
							gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
							gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];

						} else {
							System.err.println("El objeto gp.obj[" + mapNum + "][" + i + "] es null y no se puede asignar ningún valor.");

						}		    				    			
						if(ds.mapObjectLootNames[mapNum][i] != null) {
							gp.obj[mapNum][i].setLoot(gp.eGenerator.getObject(ds.mapObjectLootNames[mapNum][i]));
						}
						if (gp.obj[mapNum][i] != null) {
							gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];	

							//si el cofre esta abierto cambiamos la imagen
							if(gp.obj[mapNum][i].opened == true) {
								gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
							}
						} else {
							System.err.println("El objeto gp.obj[" + mapNum + "][" + i + "] es null y no se puede asignar ningún valor.");

						}
					}
				}
			}
			//POSITION PLAYER
			gp.player.worldX = ds.playerWorldX;
			gp.player.worldY = ds.playerWorldY;
			gp.currentMap = ds.currentMap;
			gp.player.direction = ds.direction;

			// NPC DATA
			for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
				for (int i = 0; i < gp.npc[mapNum].length; i++) {
					if (ds.npcNames[mapNum][i] != null) {
						gp.npc[mapNum][i] = gp.eGenerator.getNPC(ds.npcNames[mapNum][i]);
						gp.npc[mapNum][i].worldX = ds.npcWorldX[mapNum][i];
						gp.npc[mapNum][i].worldY = ds.npcWorldY[mapNum][i];
						gp.npc[mapNum][i].direction = ds.npcDirection[mapNum][i];
						gp.npc[mapNum][i].currentDialogueSet = ds.npcCurrentDialogueSet[mapNum][i];
					}
				}
			}
			//MONSTER DATA
			for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
				for (int i = 0; i < gp.monster[mapNum].length; i++) {
					if (ds.monsterNames[mapNum][i] != null) {
						gp.monster[mapNum][i] = gp.eGenerator.getMonster(ds.monsterNames[mapNum][i]);
						gp.monster[mapNum][i].worldX = ds.monsterWorldX[mapNum][i];
						gp.monster[mapNum][i].worldY = ds.monsterWorldY[mapNum][i];
						gp.monster[mapNum][i].direction = ds.monsterDirection[mapNum][i];						
					}
				}
			}
			//PROGRESS
			Progress.skeletonLordDefeated = ds.skeletonLordDefeated;
			Progress.treeLordDefeated = ds.treeLordDefeated;
			System.out.println("Progreso cargado = " + Progress.treeLordDefeated);

		} catch (Exception e) {

			System.out.println("Load Exception!!!");
			e.printStackTrace();
		}
	}
	public boolean isSaveFilePresent() {
		File file = new File(SAVE_FILE);
		return file.exists() && file.length() > 0;
	}
}
