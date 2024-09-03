package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.dialogues.DialogueUtils;
import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Leaf extends Entity{

	public static final String objName = "Hierba";

	public OBJ_Leaf(GamePanel gp) {
		super(gp);

		type = type_consumable;
		value = 50;
		name = objName;
		description = "[" + name + "]\nRestaura salud & escudo\npor completo.";
		down1 = setup("/objects/leaf",gp.tileSize, gp.tileSize);
		price = 95;
		stackable = true;

		loadDialogues(objName);
	}
	@Override
	public String modifyDialogue(String dialogue) {
		return DialogueUtils.modifyDialogue(dialogue, "{name}", name);
	}
	@Override
	public boolean use(Entity entity) {

		if(gp.player.protect == gp.player.maxProtect && gp.player.life == gp.player.maxLife) {
			startDialogue(this, 0);
			return false;
		}
		startDialogue(this, 1);
		entity.protect = entity.maxProtect;
		entity.life = entity.maxLife;
		gp.playSE(2);

		return true;
	}
}
