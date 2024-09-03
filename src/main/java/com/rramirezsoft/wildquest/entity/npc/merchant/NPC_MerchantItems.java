package com.rramirezsoft.wildquest.entity.npc.merchant;

import com.rramirezsoft.wildquest.main.GamePanel;
import com.rramirezsoft.wildquest.object.OBJ_Lantern;
import com.rramirezsoft.wildquest.object.OBJ_Map;
import com.rramirezsoft.wildquest.object.OBJ_Tent;


public class NPC_MerchantItems extends NPC_Merchant{

	public static final String npcName = "Merchant Items";

	public NPC_MerchantItems(GamePanel gp) {
		super(gp);
		name = npcName;
		loadDialogues(npcName);
	}
	@Override
	public void getImage() {
		down = setup("/npc/merchant_stall_1", gp.tileSize, gp.tileSize);
	}
	@Override
	public void setItems() {

		inventory.add(new OBJ_Tent(gp));
		inventory.add(new OBJ_Lantern(gp));
		inventory.add(new OBJ_Map(gp));

	}
}
