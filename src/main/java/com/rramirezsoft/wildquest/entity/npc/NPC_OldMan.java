package com.rramirezsoft.wildquest.entity.npc;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.rramirezsoft.wildquest.dialogues.DialogueUtils;
import com.rramirezsoft.wildquest.main.GamePanel;

public class NPC_OldMan extends NPC {

	public static final String npcName = "Anciano";

	public NPC_OldMan(GamePanel gp) {
		super(gp);
		name = npcName;
		loadDialogues(npcName);
	}
	@Override
	public void getImage() {
		up = setup("/npc/oldman_up", gp.tileSize, gp.tileSize);
		up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
		down = setup("/npc/oldman_down", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
	}
	@Override
	public void setAction() {

		if(gp.gameState != gp.cutsceneState) {
			if (gp.player.name.equals("")) {
				speed = 0;
			} else {
				speed = defaultSpeed;
				performRandomMovement();
			}
		}
	}
	public void performRandomMovement() {
		actionLockCounter++;

		if (actionLockCounter == 85) {
			Random random = new Random();
			int i = random.nextInt(100) + 1;

			if (i <= 50) {
				speed = 0;
				direction = getDirection(i, 12, 24, 36);
			} else {
				speed = 1;
				direction = getDirection(i, 62, 74, 86);
			}

			actionLockCounter = 0;
		}
	}
	private String getDirection(int value, int upLimit, int downLimit, int leftLimit) {
		if (value <= upLimit) {
			return "up";
		}
		if (value <= downLimit) {
			return "down";
		}
		if (value <= leftLimit) {
			return "left";
		}
		return "right";
	}
	@Override
	public String modifyDialogue(String dialogue) {
		return DialogueUtils.modifyDialogue(dialogue, "{playerName}", gp.player.name, "{name}", name);	
	}
	@Override
	public void draw(Graphics2D g2) {
		BufferedImage image = getImageBasedOnDirectionAndSpeed();
		if (image != null && inCamera()) {
			int tempScreenX = getScreenX();
			int tempScreenY = getScreenY();
			g2.drawImage(image, tempScreenX, tempScreenY, null);
			changeAlpha(g2, 1f);
		}
	}
	private BufferedImage getImageBasedOnDirectionAndSpeed() {
		switch (direction) {
		case "up":
			return speed == 0 ? up : getWalkingImage(up, up1, up2);
		case "down":
			return speed == 0 ? down : getWalkingImage(down, down1, down2);
		case "left":
			return speed == 0 ? left1 : getWalkingImage(left1, left2, left1);
		case "right":
			return speed == 0 ? right1 : getWalkingImage(right1, right2, right1);
		default:
			return null;
		}
	}
	private BufferedImage getWalkingImage(BufferedImage first, BufferedImage second, BufferedImage third) {
		switch (spriteNumPlayer) {
		case 1:
			return first;
		case 2:
			return second;
		case 3:
			return first;
		default:
			return third;
		}
	}
}
