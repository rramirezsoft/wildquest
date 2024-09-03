package com.rramirezsoft.wildquest.object;

import java.awt.Rectangle;

import com.rramirezsoft.wildquest.dialogues.DialogueUtils;
import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Chest extends Entity {

	public static final String objName = "Cofre";

	public OBJ_Chest(GamePanel gp) {
		super(gp);

		type = type_obstacle;
		name = objName;
		image = setup("/objects/chest",gp.tileSize, gp.tileSize);
		image2 = setup("/objects/chest_opened",gp.tileSize, gp.tileSize);

		down1 = image;
		collision = true;

		solidArea = new Rectangle(4, 16, 40, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		loadDialogues(objName);


	}
	@Override
	public void setLoot(Entity loot) {
		this.loot = loot;
	}
	@Override
	public String modifyDialogue(String dialogue) {
		return DialogueUtils.modifyDialogue(dialogue, "{lootName}", loot.name);
	}
	@Override
	public void interact() {

		if(opened == false) {
			gp.playSE(3);

			if(gp.player.canObtainItem(loot) == false) {
				startDialogue(this, 0);
			}
			else {
				startDialogue(this, 1);
				down1 = image2;
				opened = true;
			}
		}
		else {
			startDialogue(this, 2);
		}
	}

}
