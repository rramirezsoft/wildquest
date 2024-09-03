package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Pickaxe extends Entity{

	public static final String objName = "Pico de piedra";

	public OBJ_Pickaxe(GamePanel gp) {
		super(gp);

		type = type_pickaxe;
		name = objName;
		down1 = setup("/objects/pickaxe", gp.tileSize, gp.tileSize);
		attackValue = 3;
		attackArea.width = 32;
		attackArea.height = 32;
		description = "[" + name + "]\nListo para ir a la mina.\nDaño de ataque: " + attackValue + "pts.";
		price = 75;
		maxDurability = 100;
		durability = maxDurability;
		knockBackPower = 5;
		motion1_duration = 10;
		motion2_duration = 20;
	}
}
