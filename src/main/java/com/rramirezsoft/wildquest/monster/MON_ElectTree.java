package com.rramirezsoft.wildquest.monster;

import java.awt.Rectangle;

import com.rramirezsoft.wildquest.main.GamePanel;

public class MON_ElectTree extends Monster{

	public static final String monName = "Árbol Eléctrico";

	public MON_ElectTree(GamePanel gp) {
		super(gp);

		type = type_monster;
		name = monName;
		defaultSpeed = 0;
		speed = defaultSpeed;
		maxLife = 55;
		life = maxLife;
		attack = 5;
		defense = 1;
		exp = 5;

		solidArea = new Rectangle(4, 45, 230, 190);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}

	@Override
	public void getImage() {

		up1 = setup("/monster/elec_tree", (int)(gp.tileSize * 5.2), (int)(gp.tileSize * 5.2));
		up2 = setup("/monster/elec_tree", (int)(gp.tileSize * 5.2), (int)(gp.tileSize * 5.2));
		down1 = setup("/monster/elec_tree", (int)(gp.tileSize * 5.2), (int)(gp.tileSize * 5.2));
		down2 = setup("/monster/elec_tree", (int)(gp.tileSize * 5.2), (int)(gp.tileSize * 5.2));
		left1 = setup("/monster/elec_tree", (int)(gp.tileSize * 5.2), (int)(gp.tileSize * 5.2));
		left2 = setup("/monster/elec_tree", (int)(gp.tileSize * 5.2), (int)(gp.tileSize * 5.2));
		right1 = setup("/monster/elec_tree", (int)(gp.tileSize * 5.2), (int)(gp.tileSize * 5.2));
		right2 = setup("/monster/elec_tree", (int)(gp.tileSize * 5.2), (int)(gp.tileSize * 5.2));
	}

	@Override
	public void setAction() {

	}
	@Override
	public void checkDrop() {

	}
}
