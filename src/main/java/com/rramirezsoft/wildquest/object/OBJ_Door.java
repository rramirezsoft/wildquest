package com.rramirezsoft.wildquest.object;

import java.awt.Rectangle;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Door extends Entity{

	public static final String objName = "Puerta";

	public OBJ_Door(GamePanel gp) {
		super(gp);

		type = type_obstacle;
		name = objName;
		down1 = setup("/objects/door", gp.tileSize, gp.tileSize);
		collision = true;

		solidArea = new Rectangle(0, 16, 48, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		loadDialogues(objName);
	}

	@Override
	public void interact() {
		startDialogue(this, 0);

	}
}
