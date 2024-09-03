package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Sword_Normal extends Entity{

	public static final String objName = "Espada Antigua";

	public OBJ_Sword_Normal(GamePanel gp) {
		super(gp);

		type = type_sword;
		name = objName;
		down1 = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);
		attackValue = 5;
		attackArea.width = 36;
		attackArea.height = 36;
		description = "[" + name + "]\nLa usaban los romanos.\nDaño de ataque: " + attackValue + "pts.";
		price = 10;
		knockBackPower = 2;
		motion1_duration = 5;
		motion2_duration = 25;
	}

}