package com.rramirezsoft.wildquest.structure;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class STRUCT_FallenTrunk extends Entity{

	public static final String structName = "Fallen trunk";

	public STRUCT_FallenTrunk(GamePanel gp) {
		super(gp);


		name = structName;
		type = type_obstacle;
		down1 = setup("/objects/fallen_trunk", (int) (gp.tileSize*3.5), (int) (gp.tileSize*3.5));
		collision = true;


		solidArea.x = 4;
		solidArea.y = 60;
		solidArea.width = 145;
		solidArea.height = 50;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}

}
