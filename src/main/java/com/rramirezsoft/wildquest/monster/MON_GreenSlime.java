package com.rramirezsoft.wildquest.monster;

import java.awt.Rectangle;
import java.util.Random;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;
import com.rramirezsoft.wildquest.object.OBJ_Rock;
import com.rramirezsoft.wildquest.object.OBJ_WildCoin;


public class MON_GreenSlime extends Monster{

	public static final String monName = "Slime Verde";

	public MON_GreenSlime(GamePanel gp) {
		super(gp);

		name = monName;
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 35;
		life = maxLife;
		attack = 4;
		defense = 0;
		exp = 2;
		projectile = new OBJ_Rock(gp);

		solidArea = new Rectangle(3, 18, 42, 30);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
	@Override
	public void getImage() {
		String down_1 = "/monster/greenslime_down_1";
		String down_2 = "/monster/greenslime_down_2";

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

			// Dejan de seguirte al alejarte 11 casillas
			checkStopChasingOrNot(gp.player, 10, 100);		

			// Para que siga al jugador
			searchPath(getGoalCol(gp.player),getGoalRow(gp.player));

			//por si tira un proyectil
			checkShootOrNot(220, 30);
		}
		else {

			//para empezar la persecucion
			checkStartChasingOrNot(gp.player, 5, 50);

			//direccion aleatoria
			getRandomDirection(120);	
		}		
	}

	@Override
	public void checkDrop() {

		// CAST A DIE
		int i = new Random().nextInt(100)+1;
		int amountDropped = 0;
		Entity itemDropped = new OBJ_WildCoin(gp);

		// SET THE MONSTER DROP
		if(i < 50) {
			amountDropped = new Random().nextInt(8)+1;
		}
		if(i >= 50 && i < 75) {
			amountDropped = new Random().nextInt(11)+5;
		}
		if(i >= 75 && i <100) {
			amountDropped = new Random().nextInt(15)+8;
		}
		dropItem(itemDropped, amountDropped);
	}

}
