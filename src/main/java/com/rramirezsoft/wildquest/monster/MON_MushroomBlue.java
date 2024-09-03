package com.rramirezsoft.wildquest.monster;

import java.util.Random;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;
import com.rramirezsoft.wildquest.object.OBJ_MedKit;
import com.rramirezsoft.wildquest.object.OBJ_WildCoin;

public class MON_MushroomBlue extends MON_BaseMushroom{

	public static final String monName = "Seta Azul";

	public MON_MushroomBlue(GamePanel gp) {
		super(gp);

		name = monName;
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 50;
		life = maxLife;
		attack = 20;
		defense = 1;
		exp = 5;
	}
	@Override
	public String getBasePath() {
		return "/monster/mushroom_boss";
	}
	@Override
	public void setAction() {

		if(onPath == true) {
			checkStopChasingOrNot(gp.player, 20, 100);			
			searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
		}
		else {
			checkStartChasingOrNot(gp.player, 10, 50);
			getRandomDirection(120);	
		}		
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
	@Override
	public int getPoisonDamage() {
		return 30;
	}

	@Override
	public int getPoisonSpeed() {
		return 3;
	}
}
