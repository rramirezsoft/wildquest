package com.rramirezsoft.wildquest.structure;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class STRUCT_Cottage extends Entity{

	public static final String structName = "Cottage";

	public STRUCT_Cottage(GamePanel gp) {
		super(gp);


		name = structName;
		type = type_obstacle;
		down1 = setup("/objects/cottage", (int) (gp.tileSize*2.7), (int) (gp.tileSize*2.6));
		collision = true;


		solidArea.x = 4;
		solidArea.y = 25;
		solidArea.width = 120;
		solidArea.height = 80;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
