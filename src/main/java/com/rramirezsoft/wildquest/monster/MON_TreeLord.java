package com.rramirezsoft.wildquest.monster;

import com.rramirezsoft.wildquest.data.Progress;
import com.rramirezsoft.wildquest.main.GamePanel;
import com.rramirezsoft.wildquest.object.OBJ_Door_Iron;


public class MON_TreeLord extends Monster{

	public static final String monName = "Tree Lord";

	public MON_TreeLord(GamePanel gp) {
		super(gp);

		boss = true;
		name = monName;
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 40;
		life = maxLife;
		attack = 4;
		defense = 0;
		exp = 2;
		sleep = true;

		int size = gp.tileSize*5;
		solidArea.setBounds(48, 48, size - 48 * 2, size - 48);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		loadDialogues(monName);
	}

	@Override
	public void getImage() {

		int i = 5;

		up = setup("/monster/boss_tree_up", gp.tileSize*i, gp.tileSize*i);
		up1 = setup("/monster/boss_tree_up", gp.tileSize*i, gp.tileSize*i);
		up2 = setup("/monster/boss_tree_up", gp.tileSize*i, gp.tileSize*i);
		down = setup("/monster/boss_tree_down", gp.tileSize*i, gp.tileSize*i);
		down1 = setup("/monster/boss_tree_down", gp.tileSize*i, gp.tileSize*i);
		down2 = setup("/monster/boss_tree_down", gp.tileSize*i, gp.tileSize*i);
		left = setup("/monster/boss_tree_up", gp.tileSize*i, gp.tileSize*i);
		left1 = setup("/monster/boss_tree_up", gp.tileSize*i, gp.tileSize*i);
		left2 = setup("/monster/boss_tree_up", gp.tileSize*i, gp.tileSize*i);
		right = setup("/monster/boss_tree_up", gp.tileSize*i, gp.tileSize*i);
		right1 = setup("/monster/boss_tree_up", gp.tileSize*i, gp.tileSize*i);
		right2 = setup("/monster/boss_tree_up", gp.tileSize*i, gp.tileSize*i);
	}
	@Override
	public void setAction() {

		if(getTileDistance(gp.player) < 10) {
			moveTowardPlayer(60);
		}
		else {
			//direccion aleatoria
			getRandomDirection(120);	
		}		
		//comprobar si ataca
		if(attacking == false) {
			checkAttackOrNot(60, gp.tileSize*7, gp.tileSize*5);
		}
	}
	@Override
	public void damageReaction() {
		actionLockCounter = 0;
	}

	@Override
	public void checkDrop() {

		gp.bossBattleOn = false;
		Progress.treeLordDefeated = true;

		//poner la musica original
		gp.stopMusic();
		gp.playMusic(0);

		//eliminar las puertas
		for (int i = 0; i < gp.obj[1].length; i++) {

			if(gp.obj[gp.currentMap][i] != null &&
					gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)) {

				gp.playSE(22);
				gp.obj[gp.currentMap][i] = null;
			}
		}
		//eliminamos al minero
		gp.npc[4][3] = null;
	}
}
