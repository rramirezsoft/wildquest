package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_WildCoin extends Entity{

	public static final String objName = "Wildcoin";

	public OBJ_WildCoin(GamePanel gp) {
		super(gp);

		type = type_pickupOnly;
		name = objName;
		value = 1;
		down1 = setup("/objects/wildcoin", gp.tileSize, gp.tileSize);
	}

	@Override
	public boolean use(Entity entity) {
		gp.playSE(1);
		gp.player.coin += value;
		return true;
	}

}
