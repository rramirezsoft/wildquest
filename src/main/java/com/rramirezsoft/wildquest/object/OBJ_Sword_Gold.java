package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Sword_Gold extends Entity{

	public static final String objName = "Espada de oro";

	public OBJ_Sword_Gold(GamePanel gp) {
		super(gp);

		type = type_sword;
		name = objName;
		down1 = setup("/objects/gold_sword", gp.tileSize, gp.tileSize);
		attackValue = 12;
		attackArea.width = 36;
		attackArea.height = 36;
		description = "[" + name + "]\nUna espada eficaz..\nDaño de ataque: " + attackValue + "pts.";
		price = 80;
		maxDurability = 60;
		durability = maxDurability;
		knockBackPower = 3;
		motion1_duration = 6;
		motion2_duration = 27;
	}

}