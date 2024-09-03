package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.dialogues.DialogueUtils;
import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_WaterBottle extends Entity{

	public static final String objName = "Botella de Agua";

	public OBJ_WaterBottle(GamePanel gp) {
		super(gp);

		type = type_consumable;
		value = 20;
		name = objName;
		down1 = setup("/objects/botella_agua",gp.tileSize, gp.tileSize);
		description = "[" + name + "]\nRecupera tu salud " + value + " pts.";
		price = 14;
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
