package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.dialogues.DialogueUtils;
import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Key extends Entity{

	public static final String objName = "Llave";

	public OBJ_Key(GamePanel gp) {
		super(gp);

		type = type_consumable;
		name = objName;
		down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
		description = "[" + name + "]\nAbre una puerta.";
		price = 5;
		stackable = true;

		loadDialogues(objName);
	}
	@Override
	public String modifyDialogue(String dialogue) {
		return DialogueUtils.modifyDialogue(dialogue, "{name}", name);
	}
	@Override
	public boolean use(Entity entity) {

		int objIndex = getDetected(entity, gp.obj, "Door");

		if(objIndex != 999) {
			startDialogue(this, 0);
			gp.playSE(3);
			gp.obj[gp.currentMap][objIndex] = null;
			return true;
		}
		startDialogue(this, 1);
		return false;
	}
}
