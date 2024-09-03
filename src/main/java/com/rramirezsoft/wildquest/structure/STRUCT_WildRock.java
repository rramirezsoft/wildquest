package com.rramirezsoft.wildquest.structure;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class STRUCT_WildRock extends Entity{

	public static final String structName = "Wild Rock";

	public STRUCT_WildRock(GamePanel gp) {
		super(gp);


		name = structName;
		type = type_obstacle;
		down1 = setup("/objects/rock", gp.tileSize*2, gp.tileSize*2);
		collision = true;


		solidArea.x = 4;
		solidArea.y = 40;
		solidArea.width = 80;
		solidArea.height = 40;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
