package com.rramirezsoft.wildquest.structure;

import java.awt.Rectangle;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class STRUCT_Sign extends Entity{

	public static final String structName = "Cartel";

	GamePanel gp;
	public STRUCT_Sign(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type = type_obstacle;
		name = structName;
		down1 = setup("/objects/sign", gp.tileSize, gp.tileSize);
		collision = true;
		solidArea = new Rectangle(0, 16, 48, 32);
		loadDialogues(structName);

	}

	@Override
	public void interact() {
		startDialogue(this, currentDialogueSet);

	}
}
