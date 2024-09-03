package com.rramirezsoft.wildquest.structure;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class STRUCT_LargeCottage extends Entity{

	public static final String structName = "Large Cottage";

	public STRUCT_LargeCottage(GamePanel gp) {
		super(gp);


		name = structName;
		type = type_obstacle;
		down1 = setup("/objects/largecottage", (int) (gp.tileSize*4.5), (int) (gp.tileSize*4.5));
		collision = true;


		solidArea.x = 4;
		solidArea.y = 65;
		solidArea.width = 180;
		solidArea.height = 120;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
