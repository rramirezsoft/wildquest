package com.rramirezsoft.wildquest.entity.npc;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;
import com.rramirezsoft.wildquest.object.OBJ_Door_Iron;
import com.rramirezsoft.wildquest.tile.interactive.IT_MetalPlate;
import com.rramirezsoft.wildquest.tile.interactive.InteractiveTile;

public class NPC_BigRock extends NPC{

	public static final String npcName = "Roca";

	public NPC_BigRock(GamePanel gp) {
		super(gp);

		name = npcName;
		speed = 4;
		solidArea = new Rectangle(2, 6, 44, 40);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		loadDialogues(npcName);
	}

	@Override
	public void getImage() {

		up = setup("/npc/bigrock", gp.tileSize, gp.tileSize);
		down = setup("/npc/bigrock", gp.tileSize, gp.tileSize);
		left = setup("/npc/bigrock", gp.tileSize, gp.tileSize);
		right = setup("/npc/bigrock", gp.tileSize, gp.tileSize);
	}
	@Override
	public void setAction() {}
	@Override
	public void update() {}

	@Override
	public void speak() {
		if (linkedEntity != null) {
			currentDialogueSet = 1; // La roca está encajada
		} else {
			currentDialogueSet = 0; // Es una roca gigante
		}
		startDialogue(this, currentDialogueSet);
	}
	@Override
	public void move(String d) {

		this.direction = d;

		checkCollision();

		if(collisionOn == false) {

			switch(direction) {
			case "up": worldY -= speed; break;
			case "down": worldY += speed; break;
			case "left": worldX -= speed; break;
			case "right": worldX += speed; break;
			}
		}
		detectPlate();
	}
	public void detectPlate() {

		ArrayList<InteractiveTile> plateList = new ArrayList<>();
		ArrayList<Entity> rockList = new ArrayList<>();

		//create a plate list
		for (int i = 0; i < gp.iTile[1].length; i++) {
			if(gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].name != null &&
					gp.iTile[gp.currentMap][i].name.equals(IT_MetalPlate.itName)) {
				plateList.add(gp.iTile[gp.currentMap][i]);
			}
		}

		//create rock list
		for (int i = 0; i < gp.npc[1].length; i++) {
			if(gp.npc[gp.currentMap][i] != null &&
					gp.npc[gp.currentMap][i].name.equals(NPC_BigRock.npcName)) {
				rockList.add(gp.npc[gp.currentMap][i]);
			}
		}

		int count = 0;

		//scane plate list
		for (int i = 0; i < plateList.size(); i++) {

			int xDistance = Math.abs(worldX - plateList.get(i).worldX);
			int yDistance = Math.abs(worldY - plateList.get(i).worldY);
			int distance = Math.max(xDistance, yDistance);

			if(distance < 8) {

				if(linkedEntity == null) {
					linkedEntity = plateList.get(i);
					gp.playSE(3);
				}				
			} else if(linkedEntity == plateList.get(i)) {
				linkedEntity = null;
			}
		}

		//scan the rocklist
		for (int i = 0; i < rockList.size(); i++) {

			//count the rock on the plate
			if(rockList.get(i).linkedEntity != null) {
				count++;
			}
		}

		//si todas las rocas estan en los huecos, la puerta se abre
		if(count == rockList.size()) {

			for (int i = 0; i < gp.obj[1].length; i++) {
				if(gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)) {
					gp.obj[gp.currentMap][i] = null;
					gp.playSE(22);
				}
			}
		}
	}

}
