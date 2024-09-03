package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.dialogues.DialogueUtils;
import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Pill extends Entity{

	public static final String objName = "Píldora de Escudo";

	public OBJ_Pill(GamePanel gp) {
		super(gp);

		type = type_consumable;
		value = 25;
		name = objName;
		down1 = setup("/objects/pill",gp.tileSize, gp.tileSize);
		description = "[" + name + "]\nAumenta tu escudo " + value + " pts.";
		price = 25;
		stackable = true;

		loadDialogues(objName);
	}
	@Override
	public String modifyDialogue(String dialogue) {
		return DialogueUtils.modifyDialogue(dialogue, "{name}", name, "{value}", String.valueOf(value));
	}
	@Override
	public boolean use(Entity entity) {

		if(gp.player.protect == (gp.player.maxProtect / 2)) {

			startDialogue(this, 0);
			return false;
		}
		startDialogue(this, 1);
		entity.protect += value;
		gp.playSE(2);

		return true;
	}
}
