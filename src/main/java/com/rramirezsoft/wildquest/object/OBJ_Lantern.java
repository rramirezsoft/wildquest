package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Lantern extends Entity{

	public static final String objName = "Antorcha";

	public OBJ_Lantern(GamePanel gp) {
		super(gp);

		type = type_light;
		name = objName;
		lightRadius = 350;
		down1 = setup("/objects/lantern2", gp.tileSize, gp.tileSize);
		description = "[" + name + "]\nUna vieja antorcha. Radio\nde ilumniación: " + lightRadius + "";
		price = 200;
		maxDurability = 2000;
		durability = maxDurability;


	}
}
