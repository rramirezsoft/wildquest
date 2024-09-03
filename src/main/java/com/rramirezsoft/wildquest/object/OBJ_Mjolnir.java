package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Mjolnir extends Entity{

	public static final String objName = "Mjolnir";

	public OBJ_Mjolnir(GamePanel gp) {
		super(gp);

		type = type_axe;
		name = objName;
		down1 = setup("/objects/mjolnir", gp.tileSize, gp.tileSize);
		attackValue = 25;
		attackArea.width = 30;
		attackArea.height = 30;
		description = "[" + name + "]\nForjado por los dioses.\nDaño de ataque: " + attackValue + " pts." ;
		price = 500;
		maxDurability = 150;
		durability = maxDurability;
		knockBackPower = 5;
		motion1_duration = 22;
		motion2_duration = 44;
	}
}
