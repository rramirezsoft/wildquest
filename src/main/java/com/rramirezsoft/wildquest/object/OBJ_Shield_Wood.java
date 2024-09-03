package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Shield_Wood extends Entity{

	public static final String objName = "Escudo de madera";

	public OBJ_Shield_Wood(GamePanel gp) {
		super(gp);

		type = type_shield;
		name = objName;
		down1 = setup("/objects/shield_wood", gp.tileSize, gp.tileSize);
		defenseValue = 1;
		description = "[" + name + "]\nEs como no llevar nada.\nValor de defensa " + defenseValue + "pts.";
		price = 25;

	}

}