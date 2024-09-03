package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Axe extends Entity{

	public static final String objName = "Hacha de piedra";

	public OBJ_Axe(GamePanel gp) {
		super(gp);

		type = type_axe;
		name = objName;
		down1 = setup("/objects/diamond_axe", gp.tileSize, gp.tileSize);
		attackValue = 14;
		attackArea.width = 30;
		attackArea.height = 30;
		description = "[" + name + "]\nUn hacha un poco antigua.\nDaño de ataque: " + attackValue + " pts." ;
		price = 100;
		maxDurability = 75;
		durability = maxDurability;
		knockBackPower = 2;
		motion1_duration = 17;
		motion2_duration = 34;

	}
}
