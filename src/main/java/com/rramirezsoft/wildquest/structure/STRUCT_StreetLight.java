package com.rramirezsoft.wildquest.structure;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class STRUCT_StreetLight extends Entity{

	public static final String structName = "Street Light";

	public STRUCT_StreetLight(GamePanel gp) {
		super(gp);


		name = structName;
		type = type_obstacle;
		down1 = setup("/objects/streetlight", (int) (gp.tileSize*1.6), (int) (gp.tileSize*1.6));
		collision = true;


		solidArea.x = 20;
		solidArea.y = 30;
		solidArea.width = 30;
		solidArea.height = 40;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
