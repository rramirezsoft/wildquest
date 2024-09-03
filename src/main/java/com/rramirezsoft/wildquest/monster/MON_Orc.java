package com.rramirezsoft.wildquest.monster;

import java.awt.Rectangle;
import java.util.Random;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;
import com.rramirezsoft.wildquest.object.OBJ_Leaf;
import com.rramirezsoft.wildquest.object.OBJ_Pill;
import com.rramirezsoft.wildquest.object.OBJ_Sword_Gold;
import com.rramirezsoft.wildquest.object.OBJ_WildCoin;

public class MON_Orc extends Monster{

	public static final String monName = "Orc";

	public MON_Orc(GamePanel gp) {
		super(gp);

		name = monName;
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 150;
		life = maxLife;
		attack = 28;
		defense = 4;
		exp = 30;
		knockBackPower = 5;

		solidArea = new Rectangle(4, 4, 40, 44);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		attackArea.width = 48;
		attackArea.height = 48;
		motion1_duration = 35;
		motion2_duration = 80;

		getAttackImage();
	}

	@Override
	public void getImage() {
		String down_1 = "/monster/orc_down_1";
		String down_2 = "/monster/orc_down_2";

		String up_1 = "/monster/orc_up_1";
		String up_2 = "/monster/orc_up_2";

		String left_1 = "/monster/orc_left_1";
		String left_2 = "/monster/orc_left_2";

		String right_1 = "/monster/orc_right_1";
		String right_2 = "/monster/orc_right_2";

		up1 = setup(up_1, gp.tileSize, gp.tileSize);
		up2 = setup(up_2, gp.tileSize, gp.tileSize);
		down1 = setup(down_1, gp.tileSize, gp.tileSize);
		down2 = setup(down_2, gp.tileSize, gp.tileSize);
		left1 = setup(left_1, gp.tileSize, gp.tileSize);
		left2 = setup(left_2, gp.tileSize, gp.tileSize);
		right1 = setup(right_1, gp.tileSize, gp.tileSize);
		right2 = setup(right_2, gp.tileSize, gp.tileSize);
	}
	public void getAttackImage() {

		attackUp1 = setup("/monster/orc_attack_up_1", gp.tileSize, gp.tileSize * 2);
		attackUp2 = setup("/monster/orc_attack_up_2", gp.tileSize, gp.tileSize * 2);

		attackDown1 = setup("/monster/orc_attack_down_1", gp.tileSize, gp.tileSize * 2);
		attackDown2 = setup("/monster/orc_attack_down_2", gp.tileSize, gp.tileSize * 2);

		attackLeft1 = setup("/monster/orc_attack_left_1", gp.tileSize * 2, gp.tileSize);
		attackLeft2 = setup("/monster/orc_attack_left_2", gp.tileSize * 2, gp.tileSize);

		attackRight1 = setup("/monster/orc_attack_right_1", gp.tileSize * 2, gp.tileSize);
		attackRight2 = setup("/monster/orc_attack_right_2", gp.tileSize * 2, gp.tileSize);
	}

	@Override
	public void setAction() {

		if(onPath == true) {

			// Dejan de seguirte al alejarte 15 casillas
			checkStopChasingOrNot(gp.player, 15, 100);		

			// Para que siga al jugador
			searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
		}
		else {

			//para empezar la persecucion
			checkStartChasingOrNot(gp.player, 5, 100);

			//direccion aleatoria
			getRandomDirection(120);	
		}		

		//comprobar si ataca
		if(attacking == false) {
			checkAttackOrNot(30, gp.tileSize*4, gp.tileSize);
		}
	}

	@Override
	public void checkDrop() {

		// CAST A DIE
		int i = new Random().nextInt(100)+1;
		int amountDropped = 0;
		Entity itemDropped = null;

		// SET THE MONSTER DROP
		if(i < 70) {
			itemDropped = new OBJ_WildCoin(gp);
			amountDropped = new Random().nextInt(40)+40;
		}
		if(i >= 70 && i < 85) {
			itemDropped = new OBJ_Pill(gp);
			amountDropped = new Random().nextInt(5)+2;
		}
		if(i >= 85 && i <95) {
			itemDropped = new OBJ_Leaf(gp);
			amountDropped = 1;
		}
		if(i >= 95 && i <100) {
			itemDropped = new OBJ_Sword_Gold(gp);
			amountDropped = 1;
		}
		dropItem(itemDropped, amountDropped);
	}

}
