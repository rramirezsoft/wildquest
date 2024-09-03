package com.rramirezsoft.wildquest.monster;

import java.util.Random;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;
import com.rramirezsoft.wildquest.object.OBJ_MedKit;
import com.rramirezsoft.wildquest.object.OBJ_WildCoin;


public class MON_MushroomRed extends MON_BaseMushroom{

	public static final String monName = "Seta Roja";

	public MON_MushroomRed(GamePanel gp) {
		super(gp);

		name = monName;
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 35;
		life = maxLife;
		attack = 15;
		defense = 1;
		exp = 2;
	}
	@Override
	public String getBasePath() {
		return "/monster/mushroom";
	}
	@Override
	public void setAction() {

		if(onPath == true) {
			checkStopChasingOrNot(gp.player, 15, 100);		
			searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
		}
		else {	
			checkStartChasingOrNot(gp.player, 5, 50);
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
		return 20;
	}
	@Override
	public int getPoisonSpeed() {
		return 3;
	}
}
