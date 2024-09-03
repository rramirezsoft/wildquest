package com.rramirezsoft.wildquest.structure;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class STRUCT_House1 extends Entity{

	public static final String structName = "House";

	public STRUCT_House1(GamePanel gp) {
		super(gp);


		name = structName;
		type = type_obstacle;
		down1 = setup("/objects/house1", gp.tileSize*5, gp.tileSize*5);
		collision = true;


		solidArea.x = 4;
		solidArea.y = 45;
		solidArea.width = 200;
		solidArea.height = 145;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
