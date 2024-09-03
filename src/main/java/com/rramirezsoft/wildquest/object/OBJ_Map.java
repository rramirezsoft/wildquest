package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Map extends Entity{

	public static final String objName = "Mapa";

	public OBJ_Map(GamePanel gp) {
		super(gp);

		type = type_map;
		name = objName;
		down1 = setup("/objects/map", gp.tileSize, gp.tileSize);
		description = "[" + name + "]\nMapa antiguo encontrado\nhace más de 1000 años.";
		price = 500;

	}

}
