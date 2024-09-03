package com.rramirezsoft.wildquest.monster;

import java.awt.Rectangle;
import java.util.Random;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;
import com.rramirezsoft.wildquest.object.OBJ_MedKit;
import com.rramirezsoft.wildquest.object.OBJ_WildCoin;


public class MON_Bat extends Monster{

	public static final String monName = "Murciélago";

	public MON_Bat(GamePanel gp) {
		super(gp);

		name = monName;
		defaultSpeed = 4;
		speed = defaultSpeed;
		maxLife = 45;
		life = maxLife;
		attack = 12;
		defense = 2;
		exp = 10;

		solidArea = new Rectangle(3, 15, 42, 21);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
	@Override
	public void getImage() {
		String down_1 = "/monster/bat_down_1";
		String down_2 = "/monster/bat_down_2";

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
		getRandomDirection(15);					
	}
	@Override
	public void checkDrop() {

		// CAST A DIE
		int i = new Random().nextInt(100) + 1;
		int amountDropped = 0;
		Entity itemDropped = null;

		// SET THE MONSTER DROP
		if (i < 60) {
			itemDropped = new OBJ_WildCoin(gp);
			amountDropped = new Random().nextInt(15) + 15;
		}
		if (i >= 60 && i < 90) {
			itemDropped = new OBJ_WildCoin(gp);
			amountDropped = new Random().nextInt(20) + 30;
		}
		if (i >= 90 && i < 100) {
			itemDropped = new OBJ_MedKit(gp);
			amountDropped = new Random().nextInt(2) + 1;
		}
		dropItem(itemDropped, amountDropped);
	}
}
