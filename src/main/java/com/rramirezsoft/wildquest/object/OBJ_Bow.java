package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Bow extends Entity{

	public static final String objName = "Arco";

	public OBJ_Bow(GamePanel gp) {
		super(gp);

		type = type_sword;
		name = objName;
		down1 = setup("/objects/bow", gp.tileSize, gp.tileSize);
		attackValue = 25;
		attackArea.width = 36;
		attackArea.height = 36;
		description = "[" + name + "]\nUn arco potente.\nDaño de ataque: " + attackValue + "pts.";
		price = 10;
		knockBackPower = 2;
		motion1_duration = 5;
		motion2_duration = 25;
	}

}