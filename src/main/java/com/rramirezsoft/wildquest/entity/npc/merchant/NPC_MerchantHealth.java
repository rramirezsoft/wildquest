package com.rramirezsoft.wildquest.entity.npc.merchant;


import com.rramirezsoft.wildquest.main.GamePanel;
import com.rramirezsoft.wildquest.object.OBJ_Carrot;
import com.rramirezsoft.wildquest.object.OBJ_Chilli;
import com.rramirezsoft.wildquest.object.OBJ_Chocolate;
import com.rramirezsoft.wildquest.object.OBJ_Leaf;
import com.rramirezsoft.wildquest.object.OBJ_MedKit;
import com.rramirezsoft.wildquest.object.OBJ_Pill;
import com.rramirezsoft.wildquest.object.OBJ_WaterBottle;

public class NPC_MerchantHealth extends NPC_Merchant{

	public static final String npcName = "Merchant Health";

	public NPC_MerchantHealth(GamePanel gp) {
		super(gp);
		name = npcName;
		loadDialogues(npcName);
	}	
	@Override
	public void getImage() {}
	@Override
	public void setItems() {

		inventory.add(new OBJ_WaterBottle(gp));
		inventory.add(new OBJ_MedKit(gp));
		inventory.add(new OBJ_Carrot(gp));
		inventory.add(new OBJ_Leaf(gp));
		inventory.add(new OBJ_Chocolate(gp));
		inventory.add(new OBJ_Chilli(gp));
		inventory.add(new OBJ_Pill(gp));

	}
}
