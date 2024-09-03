package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Tent extends Entity{

	public static final String objName = "Tienda de campaña";

	public OBJ_Tent(GamePanel gp) {
		super(gp);

		type = type_consumable;
		name = objName;
		down1 = setup("/objects/tent", gp.tileSize, gp.tileSize);
		description = "[" + name + "]\nPuedes saltar la noche.\nDuración: 1 uso.";
		price = 300;
		stackable = true;
	}

	@Override
	public boolean use(Entity entity) {
		gp.gameState = gp.sleepState;

		gp.playSE(16);
		gp.player.life = gp.player.maxLife;
		gp.player.getSleepingImage(down1);
		// Puedes usarlo ilimitado
		//return false;
		// Desaparece
		return true;	
	}


}
