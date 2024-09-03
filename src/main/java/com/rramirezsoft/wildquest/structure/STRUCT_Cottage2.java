package com.rramirezsoft.wildquest.structure;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class STRUCT_Cottage2 extends Entity{

	public static final String structName = "Cottage2";

	public STRUCT_Cottage2(GamePanel gp) {
		super(gp);


		name = structName;
		type = type_obstacle;
		down1 = setup("/objects/cottage2", (int) (gp.tileSize*2.7), (int) (gp.tileSize*2.6));
		collision = true;


		solidArea.x = 4;
		solidArea.y = 25;
		solidArea.width = 120;
		solidArea.height = 80;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
