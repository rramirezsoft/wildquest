package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_ManaCrystal extends Entity{

	public static final String objName = "Mana Crystal";

	public OBJ_ManaCrystal(GamePanel gp) {
		super(gp);

		type = type_pickupOnly;
		name = objName;
		value = 1;
		down1 = setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
		image = setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/manacrystal_blank", gp.tileSize, gp.tileSize);
	}

	@Override
	public boolean use(Entity entity) {

		gp.playSE(2);
		gp.ui.addMessage("Mana +" + value);
		entity.ammo += value;
		return true;
	}

}
