package com.rramirezsoft.wildquest.structure;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class STRUCT_TownHall extends Entity{

	public static final String structName = "Town Hall";

	public STRUCT_TownHall(GamePanel gp) {
		super(gp);


		name = structName;
		type = type_obstacle;
		down1 = setup("/objects/th1", (int) (gp.tileSize*3.8), (int) (gp.tileSize*3.6));
		collision = true;


		solidArea.x = 4;
		solidArea.y = 45;
		solidArea.width = 170;
		solidArea.height = 108;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
