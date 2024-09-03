package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Shield_Blue extends Entity{

	public static final String objName = "Escudo Azul";

	public OBJ_Shield_Blue(GamePanel gp) {
		super(gp);

		type = type_shield;
		name = objName;
		down1 = setup("/objects/shield_blue", gp.tileSize, gp.tileSize);
		defenseValue = 2;
		description = "[" + name + "]\nUn bonito escudo azul.\nValor de defensa " + defenseValue + "pts.";
		price = 175;
		maxDurability = 70;
		durability = maxDurability;
	}

}
