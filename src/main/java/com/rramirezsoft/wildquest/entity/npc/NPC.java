package com.rramirezsoft.wildquest.entity.npc;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Random;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

/**
 *  @author Raul Ramirez. Clase abstracta NPC que se encarga de modelar la logica
 *  comun entre los npcs y dejando a las subclases su propia logica en determinados
 *  metodos
 */
public abstract class NPC extends Entity {

	public NPC(GamePanel gp) {
		super(gp);
		setupDefaultValues();
		getImage(); 
	}

	public void setupDefaultValues() {

		defaultSpeed = 1;
		speed = defaultSpeed;
		direction = "down";
		solidArea = new Rectangle(8, 16, 30, 30);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}

	public abstract void getImage();

	@Override
	public void setAction() {
		actionLockCounter++;
		if (actionLockCounter == 90) {
			Random random = new Random();
			int i = random.nextInt(100) + 1;
			if (i <= 25) {
				direction = "up";
			} else if (i <= 50) {
				direction = "down";
			} else if (i <= 75) {
				direction = "left";
			} else {
				direction = "right";
			}
			actionLockCounter = 0;
		}   
	}

	@Override
	public void update() {
		super.update();
		updateSpriteAnimation();
	}

	public void updateSpriteAnimation() {
		spriteCounterWalk++;
		if (spriteCounterWalk > 8) {
			spriteNumPlayer = (spriteNumPlayer % 4) + 1;
			spriteCounterWalk = 0;
		}
	}
	@Override
	public void speak() {
		// Verifica si los diálogos están cargados, si no, cárgalos
		if (dialogues == null) {
			loadDialogues(name);
		}

		if (dialogues != null && currentDialogueSet < dialogues.size()) {
			Map<Integer, String> dialogueSet = dialogues.get(currentDialogueSet);

			if (currentDialogueIndex < dialogueSet.size()) {
				String dialogue = dialogueSet.get(currentDialogueIndex);

				gp.ui.currentDialogue = dialogue;

				facePlayer();
				// Verifica si es una pregunta
				//				if (dialogues.get(currentDialogueSet).containsKey("question")) {
				//					//startQuestionDialogue(this, currentDialogueSet);
				//					System.out.println("hola");
				//				} 
				startDialogue(this, currentDialogueSet);

			} else {
				// Si se completan todos los diálogos del bloque actual
				currentDialogueIndex = 0;
				currentDialogueSet++;
				if (currentDialogueSet >= dialogues.size()) {
					currentDialogueSet = dialogues.size() - 1; // Establece el último bloque de diálogos
				}

			}
		}
	}
	@Override
	public void draw(Graphics2D g2) {
		BufferedImage image = getImageBasedOnDirection();
		if (image != null && inCamera()) {
			int tempScreenX = getScreenX();
			int tempScreenY = getScreenY();
			g2.drawImage(image, tempScreenX, tempScreenY, null);
			changeAlpha(g2, 1f);
		}
	}

	public BufferedImage getImageBasedOnDirection() {
		switch (direction) {
		case "up": return spriteNumPlayer == 1 ? up : (spriteNumPlayer == 2 ? up1 : (spriteNumPlayer == 3 ? up : up2));
		case "down": return spriteNumPlayer == 1 ? down : (spriteNumPlayer == 2 ? down1 : (spriteNumPlayer == 3 ? down : down2));
		case "left": return spriteNumPlayer == 1 ? left : (spriteNumPlayer == 2 ? left1 : (spriteNumPlayer == 3 ? left : left2));
		case "right": return spriteNumPlayer == 1 ? right : (spriteNumPlayer == 2 ? right1 : (spriteNumPlayer == 3 ? right : right2));
		default: return null;
		}
	}
}

