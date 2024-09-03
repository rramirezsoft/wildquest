package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_OldLantern extends Entity {

	public static final String objName = "Vieja Linterna";

	public OBJ_OldLantern(GamePanel gp) {
		super(gp);

		type = type_light;
		name = objName;
		lightRadius = 180;
		down1 = setup("/objects/old_lantern", gp.tileSize, gp.tileSize);
		description = "[" + name + "]\nUna vieja antorcha. Radio\nde ilumniación: " + lightRadius + "";
		price = 30;
		maxDurability = 500;
		durability = maxDurability;


	}
}
