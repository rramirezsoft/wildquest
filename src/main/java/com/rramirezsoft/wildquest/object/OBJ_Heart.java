package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Heart extends Entity {

	public static final String objName = "Heart";

	public OBJ_Heart(GamePanel gp) {
		super(gp);

		type = type_pickupOnly;
		name = objName;
		value = 2;
		down1 = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
		image = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/heart_half", gp.tileSize, gp.tileSize);
		image3 = setup("/objects/heart_blank", gp.tileSize, gp.tileSize);
	}

	@Override
	public boolean use(Entity entity) {

		gp.playSE(2);
		entity.life += value;
		return true;
	}
}
