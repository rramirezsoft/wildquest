package com.rramirezsoft.wildquest.monster;

import java.awt.Rectangle;
import java.util.Random;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;
import com.rramirezsoft.wildquest.object.OBJ_Fireball;
import com.rramirezsoft.wildquest.object.OBJ_MedKit;
import com.rramirezsoft.wildquest.object.OBJ_WildCoin;

public class MON_RedSlime extends Monster{

	public static final String monName = "Slinme Rojo";

	public MON_RedSlime(GamePanel gp) {
		super(gp);

		name = monName;
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 55;
		life = maxLife;
		attack = 5;
		defense = 1;
		exp = 5;
		projectile = new OBJ_Fireball(gp);

		solidArea = new Rectangle(3, 18, 42, 30);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}

	@Override
	public void getImage() {
		String down_1 = "/monster/redslime_down_1";
		String down_2 = "/monster/redslime_down_2";

		up1 = setup(down_1, gp.tileSize, gp.tileSize);
		up2 = setup(down_2, gp.tileSize, gp.tileSize);
		down1 = setup(down_1, gp.tileSize, gp.tileSize);
		down2 = setup(down_2, gp.tileSize, gp.tileSize);
		left1 = setup(down_1, gp.tileSize, gp.tileSize);
		left2 = setup(down_2, gp.tileSize, gp.tileSize);
		right1 = setup(down_1, gp.tileSize, gp.tileSize);
		right2 = setup(down_2, gp.tileSize, gp.tileSize);
	}


	@Override
	public void setAction() {

		if(onPath == true) {
			checkStopChasingOrNot(gp.player, 18, 100);		
			searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
			checkShootOrNot(60, 30);
		}
		else {
			checkStartChasingOrNot(gp.player, 8, 20);
			getRandomDirection(120);	
		}		
	}

	@Override
	public void checkDrop() {

		// CAST A DIE
		int i = new Random().nextInt(100)+1;
		int amountDropped = 0;
		Entity itemDropped = null;

		// SET THE MONSTER DROP
		if(i < 60) {
			itemDropped = new OBJ_WildCoin(gp);
			amountDropped = new Random().nextInt(15) + 15;
		}
		if(i >= 60 && i < 90) {
			itemDropped = new OBJ_WildCoin(gp);
			amountDropped = new Random().nextInt(20) + 30;
		}
		if(i >= 90 && i <100) {
			itemDropped = new OBJ_MedKit(gp);
			amountDropped = new Random().nextInt(2) + 1;
		}
		dropItem(itemDropped, amountDropped);
	}

}
