package com.rramirezsoft.wildquest.monster;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class MON_MushroomSpawn extends Entity{

	public static final String objName = "Hongo";

	public MON_MushroomSpawn(GamePanel gp) {
		super(gp);


		type = type_obstacle;
		name = objName;
		down1 = setup("/monster/hongo",gp.tileSize, gp.tileSize);
		collision = true;
	}



}
