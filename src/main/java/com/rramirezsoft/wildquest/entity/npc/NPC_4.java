package com.rramirezsoft.wildquest.entity.npc;

import com.rramirezsoft.wildquest.main.GamePanel;

public class NPC_4 extends NPC{

	public static final String npcName = "NPC 4";

	public NPC_4(GamePanel gp) {
		super(gp);

		name = npcName;
	}

	@Override
	public void getImage() {

		up = setup("/npc/npc4_up", gp.tileSize, gp.tileSize);
		up1 = setup("/npc/npc4_up", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/npc4_up", gp.tileSize, gp.tileSize);
		down = setup("/npc/npc4_down", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/npc4_down", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/npc4_down", gp.tileSize, gp.tileSize);
		left = setup("/npc/npc4_left", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/npc4_left", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/npc4_left", gp.tileSize, gp.tileSize);
		//		right = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
		//		right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
		//		right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
	}
}
