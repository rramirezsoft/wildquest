package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Boots extends Entity {


	public static final String objName = "Boots";

	public OBJ_Boots(GamePanel gp) {
		super(gp);

		name = objName;
		down1 = setup("/objects/boots", gp.tileSize, gp.tileSize);
		price = 100;
		description = "[" + name + "]\nRun very fast";

	}
}
