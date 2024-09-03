package com.rramirezsoft.wildquest.structure;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class STRUCT_StallWeapon extends Entity{

	public static final String structName = "Stall Weapon";

	GamePanel gp;

	public STRUCT_StallWeapon(GamePanel gp) {
		super(gp);

		this.gp = gp;
		name = structName;
		type = type_obstacle;
		down1 = setup("/objects/stall_weapon", (int) (gp.tileSize*2.2), (int) (gp.tileSize*2.2));
		collision = true;


		solidArea.x = 4;
		solidArea.y = 40;
		solidArea.width = 96;
		solidArea.height = 35;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

	}

	@Override
	public void interact() {

		if(gp.player.direction == "up") {
			gp.eHandler.speak(gp.npc[1][0]);
		}

	}
}
