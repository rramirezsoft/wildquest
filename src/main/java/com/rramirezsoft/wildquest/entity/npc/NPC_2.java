package com.rramirezsoft.wildquest.entity.npc;

import com.rramirezsoft.wildquest.main.GamePanel;

public class NPC_2 extends NPC{

	public static final String npcName = "Nuria";

	public NPC_2(GamePanel gp) {
		super(gp);

		name = npcName;
		loadDialogues(npcName);
	}

	@Override
	public void getImage() {

		//up = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
		//up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
		//up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
		down = setup("/npc/npc2_down", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/npc2_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/npc2_down_2", gp.tileSize, gp.tileSize);
		left = setup("/npc/npc2_left", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/npc2_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/npc2_left_2", gp.tileSize, gp.tileSize);
		right = setup("/npc/npc2_right", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/npc2_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/npc2_right_2", gp.tileSize, gp.tileSize);
	}

}
