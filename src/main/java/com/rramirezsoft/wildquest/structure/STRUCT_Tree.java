package com.rramirezsoft.wildquest.structure;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class STRUCT_Tree extends Entity{

	public static final String structName = "Tree";

	public STRUCT_Tree(GamePanel gp) {
		super(gp);


		name = structName;
		type = type_obstacle;
		down1 = setup("/objects/tree", (int) (gp.tileSize*5.2), (int) (gp.tileSize*5.2));
		collision = true;


		solidArea.x = 4;
		solidArea.y = 45;
		solidArea.width = 230;
		solidArea.height = 190;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}


}
