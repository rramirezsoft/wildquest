package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Arrow extends Entity {


	public static final String objName = "Flechas";

	public OBJ_Arrow(GamePanel gp) {
		super(gp);

		name = objName;
		//type = type_ammo;
		down1 = setup("/objects/arrow", gp.tileSize, gp.tileSize);
		price = 100;
		description = "[" + name + "]\nSon la munición\ndel arco.";
		stackable = true;
	}
}
