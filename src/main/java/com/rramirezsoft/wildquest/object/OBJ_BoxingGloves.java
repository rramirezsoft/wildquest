package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_BoxingGloves extends Entity{

	public static final String objName = "Guantes de boxeo";

	public OBJ_BoxingGloves(GamePanel gp) {
		super(gp);

		type = type_gloves;
		name = objName;
		down1 = setup("/objects/boxing_gloves", gp.tileSize, gp.tileSize);
		attackValue = 10;
		attackArea.width = 22;
		attackArea.height = 22;
		description = "[" + name + "]\nMeten ostias como panes.\nDaño de ataque: " + attackValue + "pts.";
		price = 150;
		maxDurability = 90;
		durability = maxDurability;
		knockBackPower = 4;
		motion1_duration = 4;
		motion2_duration = 18;
	}

}