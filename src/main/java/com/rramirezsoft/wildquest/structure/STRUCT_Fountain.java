package com.rramirezsoft.wildquest.structure;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class STRUCT_Fountain extends Entity{

	public static final String structName = "Fountain";

	public STRUCT_Fountain(GamePanel gp) {
		super(gp);


		name = structName;
		type = type_obstacle;
		down1 = setup("/objects/fountain", (int) (gp.tileSize*2.5), (int) (gp.tileSize*2.5));
		collision = true;


		solidArea.x = 14;
		solidArea.y = 45;
		solidArea.width = 85;
		solidArea.height = 70;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
