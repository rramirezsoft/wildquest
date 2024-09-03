package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.dialogues.DialogueUtils;
import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Carrot extends Entity{

	public static final String objName = "Zanahoria";

	public OBJ_Carrot(GamePanel gp) {
		super(gp);

		type = type_consumable;
		value = 5;
		name = objName;
		down1 = setup("/objects/carrot",gp.tileSize, gp.tileSize);
		description = "[" + name + "]\nRecupera tu salud " + value + " pts.";
		price = 5;
		stackable = true;

		loadDialogues(objName);
	}
	@Override
	public String modifyDialogue(String dialogue) {
		return DialogueUtils.modifyDialogue(dialogue, "{name}", name, "{value}", String.valueOf(value));
	}
	@Override
	public boolean use(Entity entity) {


		if(gp.player.life == gp.player.maxLife) {		
			startDialogue(this, 0);
			return false;
		}
		startDialogue(this, 1);
		entity.life += value;
		gp.playSE(2);		
		return true;
	}
}
