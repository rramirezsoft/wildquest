package com.rramirezsoft.wildquest.entity.npc.merchant;

import com.rramirezsoft.wildquest.main.GamePanel;
import com.rramirezsoft.wildquest.object.OBJ_Arrow;
import com.rramirezsoft.wildquest.object.OBJ_Axe;
import com.rramirezsoft.wildquest.object.OBJ_Bow;
import com.rramirezsoft.wildquest.object.OBJ_BoxingGloves;
import com.rramirezsoft.wildquest.object.OBJ_Mjolnir;
import com.rramirezsoft.wildquest.object.OBJ_Shield_Blue;
import com.rramirezsoft.wildquest.object.OBJ_Shield_Captain_America;
import com.rramirezsoft.wildquest.object.OBJ_Sword_Gold;

public class NPC_MerchantWeapon extends NPC_Merchant{

	public static final String npcName = "Merchant Weapon";

	public NPC_MerchantWeapon(GamePanel gp) {
		super(gp);
		name = npcName;
		loadDialogues(npcName);
	}
	@Override
	public void getImage() {}
	@Override
	public void setItems() {

		inventory.add(new OBJ_Sword_Gold(gp));
		inventory.add( new OBJ_Axe(gp));
		inventory.add( new OBJ_Mjolnir(gp));
		inventory.add( new OBJ_BoxingGloves(gp));
		inventory.add( new OBJ_Shield_Blue(gp));
		inventory.add( new OBJ_Shield_Captain_America(gp));
		inventory.add(new OBJ_Bow(gp));
		inventory.add(new OBJ_Arrow(gp));

	}
}
