package com.rramirezsoft.wildquest.entity.npc;

import com.rramirezsoft.wildquest.main.GamePanel;


public class NPC_Miner extends NPC{

	public static final String npcName = "Minero";

	public NPC_Miner(GamePanel gp) {
		super(gp);

		speed = 0;
		name = npcName;
		loadDialogues(npcName);

	}
	@Override
	public void setAction() {}
	@Override
	public void update() {}
	@Override
	public void getImage() {
		down = setup("/npc/minero", gp.tileSize, gp.tileSize);
		left = setup("/npc/minero", gp.tileSize, gp.tileSize);	
		right = setup("/npc/minero", gp.tileSize, gp.tileSize);	
	}
}
