package com.rramirezsoft.wildquest.entity.npc;

import com.rramirezsoft.wildquest.main.GamePanel;

public class NPC_1 extends NPC{

	public static final String npcName = "NPC 1";

	public NPC_1(GamePanel gp) {
		super(gp);

		name = npcName;
		loadDialogues(npcName);
	}

	@Override
	public void getImage() {

		up = setup("/npc/npc1_up", gp.tileSize, gp.tileSize);
		up1 = setup("/npc/npc1_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/npc1_up_2", gp.tileSize, gp.tileSize);
		down = setup("/npc/npc1_down", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/npc1_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/npc1_down_2", gp.tileSize, gp.tileSize);
		left = setup("/npc/npc1_left", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/npc1_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/npc1_left_2", gp.tileSize, gp.tileSize);
		right = setup("/npc/npc1_right", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/npc1_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/npc1_right_2", gp.tileSize, gp.tileSize);
	}

	@Override
	public void speak() {
		super.speak();
		onPath = true;
	}

	@Override
	public void setAction() {

		if(onPath == true) {
			int goalCol= (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
			int goalRow= (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;

			searchPath(goalCol,goalRow);
		} else {
			super.setAction();
		}
	}

}
