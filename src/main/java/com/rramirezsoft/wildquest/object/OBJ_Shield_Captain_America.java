package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Shield_Captain_America extends Entity{

	public static final String objName = "Escudo Capitán América";

	public OBJ_Shield_Captain_America(GamePanel gp) {
		super(gp);

		type = type_shield;
		name = objName;
		down1 = setup("/objects/captain_america_shield", gp.tileSize, gp.tileSize);
		defenseValue = 4;
		description = "[" + name + "]\nForjado por Howard Stark.\nValor de defensa " + defenseValue + "pts.";
		price = 250;
		maxDurability = 120;
		durability = maxDurability;
	}

}