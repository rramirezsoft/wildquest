package com.rramirezsoft.wildquest.entity.npc;

import com.rramirezsoft.wildquest.main.GamePanel;

public class NPC_3 extends NPC{

	public static final String npcName = "NPC 3";

	public NPC_3(GamePanel gp) {
		super(gp);

		name = npcName;
		loadDialogues(npcName);
	}

	@Override
	public void getImage() {

		up = setup("/npc/npc3_up", gp.tileSize, gp.tileSize);
		up1 = setup("/npc/npc3_up", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/npc3_up", gp.tileSize, gp.tileSize);
		down = setup("/npc/npc3_down", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/npc3_down", gp.tileSize, gp.tileSize);
		down2 =  setup("/npc/npc3_down", gp.tileSize, gp.tileSize);
		left = setup("/npc/npc3_left", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/npc3_left", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/npc3_left", gp.tileSize, gp.tileSize);
		right = setup("/npc/npc3_right", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/npc3_right", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/npc3_right", gp.tileSize, gp.tileSize);
	}
}
