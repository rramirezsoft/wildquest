package com.rramirezsoft.wildquest.entity.player;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.rramirezsoft.wildquest.dialogues.DialogueUtils;
import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;
import com.rramirezsoft.wildquest.main.KeyHandler;
import com.rramirezsoft.wildquest.object.OBJ_Axe;
import com.rramirezsoft.wildquest.object.OBJ_BoxingGloves;
import com.rramirezsoft.wildquest.object.OBJ_Fireball;
import com.rramirezsoft.wildquest.object.OBJ_Lantern;
import com.rramirezsoft.wildquest.object.OBJ_Map;
import com.rramirezsoft.wildquest.object.OBJ_Mjolnir;
import com.rramirezsoft.wildquest.object.OBJ_Pickaxe;
import com.rramirezsoft.wildquest.object.OBJ_Shield_Captain_America;
import com.rramirezsoft.wildquest.object.OBJ_Shield_Wood;
import com.rramirezsoft.wildquest.object.OBJ_Sword_Gold;
import com.rramirezsoft.wildquest.object.OBJ_Sword_Normal;

public class Player extends Entity {

	KeyHandler keyH;
	public final int screenX;
	public final int screenY;
	public boolean attackCanceled = false;
	public boolean lightUpdated = false;
	public boolean moving = true; //flag para verificar si el jugador se puede mover

	public Player(GamePanel gp, KeyHandler keyH) { 
		super(gp);
		this.keyH = keyH;

		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		solidArea = new Rectangle(8, 16, 32, 32);// rectángulo para la zona de colisión del sprite (x, y, w, h)

		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		setDefaultValues();
		loadDialogues("Player");

	}

	public void setDefaultValues() {

		setDefaultPositions();
		defaultSpeed = 4;
		speed = defaultSpeed;

		// ESTADO DEL JUGADOR
		name = "";
		level = 1;
		maxLife = 200;
		life = maxLife;
		maxProtect = 100;
		protect = 0;
		maxAmmo = 10;
		ammo = 0;
		exp = 0;
		nextLevelExp = 50;
		coin = 0;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		currentLight = null;
		currentMapItem = null;
		projectile = new OBJ_Fireball(gp);
		// projectile = new OBJ_Rock(gp);// test
		attack = getAttack();//el ataque total se calcula con el ataque y el arma
		defense = getDefense();//la defensa se calcula con el valor de defensa del escudo

		getImage();
		getAttackImage();
		getGuardImage();
		getPoisonedImage();
		getGlovesImage();
		getPoisonedAttackImage();
		getPoisonedGuardImage();
		setItems();
	}

	public void setDefaultPositions() {

		gp.currentMap = 0;
		worldX = gp.tileSize * 12;
		worldY = gp.tileSize * 43 - 2;
		direction = "down";
	}

	@Override
	public String modifyDialogue(String dialogue) {

		Map<String, String> params = new HashMap<>();
		params.put("{level}", String.valueOf(level));
		params.put("{currentWeaponName}", currentWeapon.name);
		params.put("{currentShieldName}", currentShield.name);

		if (currentLight != null) {
			params.put("{currentLightName}", currentLight.name);
		}
		return DialogueUtils.modifyDialogue(dialogue, params);
	}

	public void restoreStatus() {

		life = maxLife;
		protect = 0;
		ammo = 0;
		speed = defaultSpeed;
		invincible = false;
		transparent = false;
		attacking = false;
		guarding = false;
		knockBack = false;
		lightUpdated = true;
	}

	public void setItems() {

		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new OBJ_Pickaxe(gp));
		//		inventory.add(new OBJ_Axe(gp));
		//		inventory.add(new OBJ_Shield_Blue(gp));
		//		inventory.add(new OBJ_WaterBottle(gp));
		//		inventory.add(new OBJ_MedKit(gp));
		//		inventory.add(new OBJ_Carrot(gp));
		//		inventory.add(new OBJ_Tent(gp));
		//		inventory.add(new OBJ_Chilli(gp));
		inventory.add(new OBJ_Mjolnir(gp));
		//		inventory.add(new OBJ_Chocolate(gp));
		//		inventory.add(new OBJ_Pill(gp));
		//		inventory.add(new OBJ_Leaf(gp));
		inventory.add(new OBJ_Shield_Captain_America(gp));
		//		inventory.add(new OBJ_Sword_Gold(gp));
		//		inventory.add(new OBJ_BoxingGloves(gp));
		//		inventory.add(new OBJ_Bow(gp));
		//		inventory.add(new OBJ_Arrow(gp));
		inventory.add(new OBJ_Map(gp));
		inventory.add(new OBJ_Lantern(gp));

	}

	public void setDefaultWeapon() {

		startDialogue(this, 1);
		inventory.remove(currentWeapon);
		currentWeapon = inventory.get(0);
		getAttackImage();
		getAttack();
	}
	public void setDefaultShield() {

		startDialogue(this, 2);
		inventory.remove(currentShield);
		currentShield = inventory.get(1);
		getGuardImage();
		getDefense();
	}
	public int getAttack() {

		attackArea = currentWeapon.attackArea;
		motion1_duration = currentWeapon.motion1_duration;
		motion2_duration = currentWeapon.motion2_duration;
		return attack = currentWeapon.attackValue;
	}

	public int getDefense() {
		return defense = currentShield.defenseValue;
	}

	public int getCurrentWeaponSlot() {
		int currentWeaponSlot = 0;
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) == currentWeapon) {
				currentWeaponSlot = i;
			}
		}
		return currentWeaponSlot;
	}

	public int getCurrentShieldSlot() {
		int currentShiedlSlot = 0;
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) == currentShield) {
				currentShiedlSlot = i;
			}
		}
		return currentShiedlSlot;
	}

	public int getCurrentLightSlot() {
		int currentLightSlot = 0;
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) == currentLight) {
				currentLightSlot = i;
			}
		}
		System.out.println(currentLightSlot);
		return currentLightSlot;
	}

	public void getImage() {

		up = setup("/player/sprite_up", gp.tileSize, gp.tileSize);
		up1 = setup("/player/sprite_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/sprite_up_2", gp.tileSize, gp.tileSize);
		down = setup("/player/sprite_down", gp.tileSize, gp.tileSize);
		down1 = setup("/player/sprite_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/sprite_down_2", gp.tileSize, gp.tileSize);
		left = setup("/player/sprite_left", gp.tileSize, gp.tileSize);
		left1 = setup("/player/sprite_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/sprite_left_2", gp.tileSize, gp.tileSize);
		right = setup("/player/sprite_right", gp.tileSize, gp.tileSize);
		right1 = setup("/player/sprite_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/sprite_right_2", gp.tileSize, gp.tileSize);

	}

	public void getSleepingImage(BufferedImage image) {

		up = image;
		up1 = image;
		up2 = image;
		down = image;
		down1 = image;
		down2 = image;
		left = image;
		left1 = image;
		left2 = image;
		right = image;
		right1 = image;
		right2 = image;
	}

	public void getAttackImage() {

		if (currentWeapon.type == type_sword) {

			if (currentWeapon.name.equals(OBJ_Sword_Normal.objName)) {

				attackUp1 = setup("/player/sprite_attack_up_1", gp.tileSize, gp.tileSize * 2);
				attackUp2 = setup("/player/sprite_attack_up_2", gp.tileSize, gp.tileSize * 2);

				attackDown1 = setup("/player/sprite_attack_down_1", gp.tileSize, gp.tileSize * 2);
				attackDown2 = setup("/player/sprite_attack_down_2", gp.tileSize, gp.tileSize * 2);

				attackLeft1 = setup("/player/sprite_attack_left_1", gp.tileSize * 2, gp.tileSize);
				attackLeft2 = setup("/player/sprite_attack_left_2", gp.tileSize * 2, gp.tileSize);

				attackRight1 = setup("/player/sprite_attack_right_1", gp.tileSize * 2, gp.tileSize);
				attackRight2 = setup("/player/sprite_attack_right_2", gp.tileSize * 2, gp.tileSize);
			}

			if (currentWeapon.name.equals(OBJ_Sword_Gold.objName)) {

				attackUp1 = setup("/player/sprite_gold_sword_up_1", gp.tileSize, gp.tileSize * 2);
				attackUp2 = setup("/player/sprite_gold_sword_up_2", gp.tileSize, gp.tileSize * 2);

				attackDown1 = setup("/player/sprite_gold_sword_down_1", gp.tileSize, gp.tileSize * 2);
				attackDown2 = setup("/player/sprite_gold_sword_down_2", gp.tileSize, gp.tileSize * 2);

				attackLeft1 = setup("/player/sprite_gold_sword_left_1", gp.tileSize * 2, gp.tileSize);
				attackLeft2 = setup("/player/sprite_gold_sword_left_2", gp.tileSize * 2, gp.tileSize);

				attackRight1 = setup("/player/sprite_gold_sword_right_1", gp.tileSize * 2, gp.tileSize);
				attackRight2 = setup("/player/sprite_gold_sword_right_2", gp.tileSize * 2, gp.tileSize);
			}
		}
		if (currentWeapon.type == type_axe) {

			if (currentWeapon.name.equals(OBJ_Axe.objName)) {

				attackUp1 = setup("/player/sprite_axe_up_1", gp.tileSize, gp.tileSize * 2);
				attackUp2 = setup("/player/sprite_axe_up_2", gp.tileSize, gp.tileSize * 2);

				attackDown1 = setup("/player/sprite_axe_down_1", gp.tileSize, gp.tileSize * 2);
				attackDown2 = setup("/player/sprite_axe_down_2", gp.tileSize, gp.tileSize * 2);

				attackLeft1 = setup("/player/sprite_axe_left_1", gp.tileSize * 2, gp.tileSize);
				attackLeft2 = setup("/player/sprite_axe_left_2", gp.tileSize * 2, gp.tileSize);

				attackRight1 = setup("/player/sprite_axe_right_1", gp.tileSize * 2, gp.tileSize);
				attackRight2 = setup("/player/sprite_axe_right_2", gp.tileSize * 2, gp.tileSize);
			}
			if (currentWeapon.name.equals(OBJ_Mjolnir.objName)) {

				attackUp1 = setup("/player/sprite_mjolnir_up_1", gp.tileSize, gp.tileSize * 2);
				attackUp2 = setup("/player/sprite_mjolnir_up_2", gp.tileSize, gp.tileSize * 2);

				attackDown1 = setup("/player/sprite_mjolnir_down_1", gp.tileSize, gp.tileSize * 2);
				attackDown2 = setup("/player/sprite_mjolnir_down_2", gp.tileSize, gp.tileSize * 2);

				attackLeft1 = setup("/player/sprite_mjolnir_left_1", gp.tileSize * 2, gp.tileSize);
				attackLeft2 = setup("/player/sprite_mjolnir_left_2", gp.tileSize * 2, gp.tileSize);

				attackRight1 = setup("/player/sprite_mjolnir_right_1", gp.tileSize * 2, gp.tileSize);
				attackRight2 = setup("/player/sprite_mjolnir_right_2", gp.tileSize * 2, gp.tileSize);
			}

		}
		if (currentWeapon.type == type_pickaxe) {

			attackUp1 = setup("/player/sprite_mjolnir_up_1", gp.tileSize, gp.tileSize * 2);
			attackUp2 = setup("/player/sprite_mjolnir_up_2", gp.tileSize, gp.tileSize * 2);

			attackDown1 = setup("/player/sprite_pickaxe_down_1", gp.tileSize, gp.tileSize * 2);
			attackDown2 = setup("/player/sprite_pickaxe_down_2", gp.tileSize, gp.tileSize * 2);

			attackLeft1 = setup("/player/sprite_pickaxe_left_1", gp.tileSize * 2, gp.tileSize);
			attackLeft2 = setup("/player/sprite_pickaxe_left_2", gp.tileSize * 2, gp.tileSize);

			attackRight1 = setup("/player/sprite_pickaxe_right_1", gp.tileSize * 2, gp.tileSize);
			attackRight2 = setup("/player/sprite_pickaxe_right_2", gp.tileSize * 2, gp.tileSize);
		}

		if (currentWeapon.type == type_gloves) {

			attackUp1 = setup("/player/boy_pick_up_1", gp.tileSize, gp.tileSize * 2);
			attackUp2 = setup("/player/boy_pick_up_2", gp.tileSize, gp.tileSize * 2);

			attackDown1 = setup("/player/sprite_pickaxe_down_1", gp.tileSize, gp.tileSize * 2);
			attackDown2 = setup("/player/sprite_pickaxe_down_2", gp.tileSize, gp.tileSize * 2);

			attackLeft1 = setup("/player/sprite_gloves_attack_left_1", gp.tileSize * 2, gp.tileSize);
			attackLeft2 = setup("/player/sprite_gloves_attack_left_2", gp.tileSize * 2, gp.tileSize);

			attackRight1 = setup("/player/sprite_pickaxe_right_1", gp.tileSize * 2, gp.tileSize);
			attackRight2 = setup("/player/sprite_pickaxe_right_2", gp.tileSize * 2, gp.tileSize);
		}
	}

	public void getGuardImage() {

		if (currentShield.name == OBJ_Shield_Wood.objName) {
			guardUp = setup("/player/sprite_guard_up", gp.tileSize, gp.tileSize);
			guardDown = setup("/player/sprite_guard_down", gp.tileSize, gp.tileSize);
			guardLeft = setup("/player/sprite_guard_left", gp.tileSize, gp.tileSize);
			guardRight = setup("/player/sprite_guard_right", gp.tileSize, gp.tileSize);
		}
		if (currentShield.name == OBJ_Shield_Captain_America.objName) {

			guardUp = setup("/player/sprite_guard_ca_up", gp.tileSize, gp.tileSize);
			guardDown = setup("/player/sprite_guard_ca_down", gp.tileSize, gp.tileSize);
			guardLeft = setup("/player/sprite_guard_ca_left", gp.tileSize, gp.tileSize);
			guardRight = setup("/player/sprite_guard_ca_right", gp.tileSize, gp.tileSize);

		}
	}

	public void getPoisonedImage() {

		poisonedUp = setup("/player/sprite_poisoned_up", gp.tileSize, gp.tileSize);
		poisonedUp1 = setup("/player/sprite_poisoned_up_1", gp.tileSize, gp.tileSize);
		poisonedUp2 = setup("/player/sprite_poisoned_up_2", gp.tileSize, gp.tileSize);
		poisonedDown = setup("/player/sprite_poisoned_down", gp.tileSize, gp.tileSize);
		poisonedDown1 = setup("/player/sprite_poisoned_down_1", gp.tileSize, gp.tileSize);
		poisonedDown2 = setup("/player/sprite_poisoned_down_2", gp.tileSize, gp.tileSize);
		poisonedLeft = setup("/player/sprite_poisoned_left", gp.tileSize, gp.tileSize);
		poisonedLeft1 = setup("/player/sprite_poisoned_left_1", gp.tileSize, gp.tileSize);
		poisonedLeft2 = setup("/player/sprite_poisoned_left_2", gp.tileSize, gp.tileSize);
		poisonedRight = setup("/player/sprite_poisoned_right", gp.tileSize, gp.tileSize);
		poisonedRight1 = setup("/player/sprite_poisoned_right_1", gp.tileSize, gp.tileSize);
		poisonedRight2 = setup("/player/sprite_poisoned_right_2", gp.tileSize, gp.tileSize);
	}

	public void getGlovesImage() {

		glovesUp = setup("/player/sprite_gloves_up", gp.tileSize, gp.tileSize);
		glovesUp1 = setup("/player/sprite_gloves_up_1", gp.tileSize, gp.tileSize);
		glovesUp2 = setup("/player/sprite_gloves_up_2", gp.tileSize, gp.tileSize);
		glovesDown = setup("/player/sprite_gloves_down", gp.tileSize, gp.tileSize);
		glovesDown1 = setup("/player/sprite_gloves_down_1", gp.tileSize, gp.tileSize);
		glovesDown2 = setup("/player/sprite_gloves_down_2", gp.tileSize, gp.tileSize);
		glovesLeft = setup("/player/sprite_gloves_left", gp.tileSize, gp.tileSize);
		glovesLeft1 = setup("/player/sprite_gloves_left_1", gp.tileSize, gp.tileSize);
		glovesLeft2 = setup("/player/sprite_gloves_left_2", gp.tileSize, gp.tileSize);
		glovesRight = setup("/player/sprite_gloves_right", gp.tileSize, gp.tileSize);
		glovesRight1 = setup("/player/sprite_gloves_right_1", gp.tileSize, gp.tileSize);
		glovesRight2 = setup("/player/sprite_gloves_right_2", gp.tileSize, gp.tileSize);
	}

	public void getPoisonedAttackImage() {

		if (currentWeapon.type == type_sword) {

			poisonedAttackUp1 = setup("/player/sprite_poisoned_attack_up_1", gp.tileSize, gp.tileSize * 2);
			poisonedAttackUp2 = setup("/player/sprite_poisoned_attack_up_2", gp.tileSize, gp.tileSize * 2);

			poisonedAttackDown1 = setup("/player/sprite_poisoned_attack_down_1", gp.tileSize, gp.tileSize * 2);
			poisonedAttackDown2 = setup("/player/sprite_poisoned_attack_down_2", gp.tileSize, gp.tileSize * 2);

			poisonedAttackLeft1 = setup("/player/sprite_poisoned_attack_left_1", gp.tileSize * 2, gp.tileSize);
			poisonedAttackLeft2 = setup("/player/sprite_poisoned_attack_left_2", gp.tileSize * 2, gp.tileSize);

			poisonedAttackRight1 = setup("/player/sprite_poisoned_attack_right_1", gp.tileSize * 2, gp.tileSize);
			poisonedAttackRight2 = setup("/player/sprite_poisoned_attack_right_2", gp.tileSize * 2, gp.tileSize);
		}
		if (currentWeapon.type == type_axe) {

			poisonedAttackUp1 = setup("/player/sprite_poisoned_axe_up_1", gp.tileSize, gp.tileSize * 2);
			poisonedAttackUp2 = setup("/player/sprite_poisoned_axe_up_2", gp.tileSize, gp.tileSize * 2);

			poisonedAttackDown1 = setup("/player/sprite_poisoned_axe_down_1", gp.tileSize, gp.tileSize * 2);
			poisonedAttackDown2 = setup("/player/sprite_poisoned_axe_down_2", gp.tileSize, gp.tileSize * 2);

			poisonedAttackLeft1 = setup("/player/sprite_poisoned_axe_left_1", gp.tileSize * 2, gp.tileSize);
			poisonedAttackLeft2 = setup("/player/sprite_poisoned_axe_left_2", gp.tileSize * 2, gp.tileSize);

			poisonedAttackRight1 = setup("/player/sprite_poisoned_axe_right_1", gp.tileSize * 2, gp.tileSize);
			poisonedAttackRight2 = setup("/player/sprite_poisoned_axe_right_2", gp.tileSize * 2, gp.tileSize);
		}

	}

	public void getPoisonedGuardImage() {

		poisonedGuardUp = setup("/player/sprite_poisoned_guard_up", gp.tileSize, gp.tileSize);
		poisonedGuardDown = setup("/player/sprite_poisoned_guard_down", gp.tileSize, gp.tileSize);
		poisonedGuardLeft = setup("/player/sprite_poisoned_guard_left", gp.tileSize, gp.tileSize);
		poisonedGuardRight = setup("/player/sprite_poisoned_guard_right", gp.tileSize, gp.tileSize);
	}

	@Override
	public void update() {

		if (knockBack) {

			collisionOn = false;
			gp.cChecker.checkTile(this);
			gp.cChecker.checkObject(this, true);
			gp.cChecker.checkStruct(this, true);
			gp.cChecker.checkEntity(this, gp.npc);
			gp.cChecker.checkEntity(this, gp.monster);
			gp.cChecker.checkEntity(this, gp.iTile);

			if (collisionOn) {
				knockBackCounter = 0;
				knockBack = false;
				speed = defaultSpeed;
			} else {

				switch (knockBackDirection) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}
			knockBackCounter++;
			// The more we increase the number 10, more knockback
			if (knockBackCounter == 10) {
				knockBackCounter = 0;
				knockBack = false;
				speed = defaultSpeed;
			}
		}

		else if (attacking) {
			attacking();
		} else if (keyH.spacePressed) {
			guarding = true;
			guardCounter++;
		}
		// Con esta línea hacemos que los sprites no cambien si no pulsamos nada
		else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {

			if (keyH.upPressed) {// Movemos al personaje. Al pulsar arriba, la coordenada y cambia en relación a
				// la velocidad
				direction = "up";
			}
			// Si ponemos else if en vez de else, no podremos movernos diagonalmente
			else if (keyH.downPressed) {
				direction = "down";
			} else if (keyH.leftPressed) {
				direction = "left";
			} else if (keyH.rightPressed) {
				direction = "right";
			}
			// CHECK THE TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);

			// CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);

			// CHECK STRUCT COLLISION
			int structIndex = gp.cChecker.checkStruct(this, true);
			contactStruct(structIndex);

			// CHECK THE NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);

			// CHECK MONSTER COLLISION
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);

			// CHECK INTERACTIVE TILE COLLISION
			gp.cChecker.checkEntity(this, gp.iTile);

			// CHECK EVENT
			gp.eHandler.checkEvent();

			// IF COLLISION IS FALSE, PLAYER CON MOVE
			if (!collisionOn && !keyH.enterPressed) {

				switch (direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}
			if (keyH.enterPressed && !attackCanceled) {
				gp.playSE(23);
				attacking = true;
				spriteCounter = 0;
				spriteCounterWalk = 0;
			}
			attackCanceled = false;
			gp.keyH.enterPressed = false;
			guarding = false;
			guardCounter = 0;

			// contador para atacar
			spriteCounter++;
			if (spriteCounter > 12) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}

			// contador para atacar con guantes
			spriteGlovesCount++;
			if (spriteGlovesCount > 12) {
				if (spriteNumGloves == 1) {
					spriteNumGloves = 2;
					if (!differentAttackGloves) {
						differentAttackGloves = true;
					} else {
						differentAttackGloves = false;
					}
				} else if (spriteNumGloves == 2) {
					spriteNumGloves = 1;
					if (!differentAttackGloves) {
						differentAttackGloves = true;
					} else {
						differentAttackGloves = false;
					}
				}
				spriteGlovesCount = 0;
			}

			// contador para caminar
			spriteCounterWalk++;
			if (spriteCounterWalk > 8) {
				switch (spriteNumPlayer) {
				case 1:
					spriteNumPlayer = 2;
					break;
				case 2:
					spriteNumPlayer = 3;
					break;
				case 3:
					spriteNumPlayer = 4;
					break;
				case 4:
					spriteNumPlayer = 1;
					break;
				default:
					break;
				}
				spriteCounterWalk = 0;
			}

		}
		// sprite para defender
		else {
			standCounter++;
			if (standCounter == 20) {
				spriteNum = 1;
				standCounter = 0;
			}
			guarding = false;
			guardCounter = 0;
		}

		if (gp.keyH.shotKeyPressed && !projectile.alive && shotAvailableCounter == 30
				&& projectile.haveResource(this)) {// Con la segunda condición evitamos que se puedan disparar varios
			// projectiles a la vez
			// SET DEFAULT COORDINATES, DIRECTION AND USER
			projectile.set(worldX, worldY, direction, true, this);
			// SUBSTRACT THE COST (MANA, AMMO, ETC.)
			projectile.substractResource(this);

			// CHECK VACANCY
			for (int i = 0; i < gp.projectile[1].length; i++) {
				if (gp.projectile[gp.currentMap][i] == null) {
					gp.projectile[gp.currentMap][i] = projectile;
					break;
				}
			}

			shotAvailableCounter = 0;
			// PLAY SOUND EFFECT
			gp.playSE(12);
		}
		// This needs to be outside of key if statement
		if (invincible) {
			invincibleCounter++;
			if (invincibleCounter > 60) {
				invincible = false;
				transparent = false;
				invincibleCounter = 0;
			}
		}
		if (shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}
		if (life > maxLife) {
			life = maxLife;
		}
		if (protect > maxProtect) {
			protect = maxProtect;
		}
		if (ammo > maxAmmo) {
			ammo = maxAmmo;
		}
		if (!keyH.godModeOn) {

			if (protect <= 0) {
				protect = 0;
			}

			if (life <= 0) {
				gp.gameState = gp.gameOverState;
				gp.ui.commandNum = -1;
				gp.stopMusic();
				gp.playSE(14);
			}
		}

		//decrementamos la durabilidad de la linterna
		updateLightDurability();

	}

	public void updateLightDurability() {
		if(gp.playState == gp.gameState) {
			if (currentLight != null) {
				lightDurabilityCounter++;
				if (lightDurabilityCounter >= 60) { 
					currentLight.durability--;
					lightDurabilityCounter = 0;
					if (currentLight.durability <= 0) {
						inventory.remove(currentLight);				
						startDialogue(this, 3);
						currentLight = null;							
						lightUpdated = true;
					}
				}
			} else {
				lightDurabilityCounter = 0; // Reset counter if no lantern is equipped
			}
		}
	}


	public void pickUpObject(int i) {
		// Si el i es distinto a 999, hemos tocado un objeto, pues pusimos 999 por
		// defecto, no pertenece a ningun objeto dicho id en el array.
		if (i != 999) {
			// PICKUP ONLY ITEMS
			if (gp.obj[gp.currentMap][i].type == type_pickupOnly) {
				gp.obj[gp.currentMap][i].use(this);
				gp.obj[gp.currentMap][i] = null;
			}
			// OBSTACLE
			else if (gp.obj[gp.currentMap][i].type == type_obstacle) {
				if (keyH.enterPressed) {
					attackCanceled = true;
					gp.obj[gp.currentMap][i].interact();
				}
			}

			else {
				// INVENTORY ITEMS
				String text;
				if (canObtainItem(gp.obj[gp.currentMap][i])) {
					gp.playSE(1);
					text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
				} else {
					text = "You cannot carry anymore";
				}
				gp.ui.addMessage(text);
				gp.obj[gp.currentMap][i] = null;// DDONT FORGET THIS
			}
		}
	}
	public void contactStruct(int i) {
		if (((i!=999) && (gp.struct[gp.currentMap][i].type == type_obstacle)) && keyH.enterPressed) {
			attackCanceled = true;
			gp.struct[gp.currentMap][i].interact();
		}
	}

	public void interactNPC(int i) {

		if (i != 999) {

			if (gp.keyH.enterPressed) {
				attackCanceled = true;
				gp.npc[gp.currentMap][i].speak();
			}
			gp.npc[gp.currentMap][i].move(direction);
		}
	}

	public void contactMonster(int i) {

		if (i != 999 && !invincible && !gp.monster[gp.currentMap][i].dying) {
			gp.playSE(24);
			int damage = gp.monster[gp.currentMap][i].attack - defense;
			if (damage < 1) {
				damage = 1;
			}
			invincible = true;
			transparent = true;
			if (protect <= 0) {
				life -= damage;
			} else {
				protect -= damage;
			}
		}

	}

	public void damageMonster(int i, Entity attacker, int attack, int knockBackPower) {

		if ((i != 999) && !gp.monster[gp.currentMap][i].invincible) {
			gp.playSE(5);
			if(currentWeapon.name != new OBJ_Sword_Normal(gp).name) {
				currentWeapon.durability--;
				if(currentWeapon.durability == 0) {			
					setDefaultWeapon();		    
				}
			}
			if (knockBackPower > 0) {
				setKnockBack(gp.monster[gp.currentMap][i], attacker, knockBackPower);

			}

			if (gp.monster[gp.currentMap][i].offBalance) {
				attack *= 1.5;
			}

			int damage = attack - gp.monster[gp.currentMap][i].defense;

			if (damage < 0) {
				damage = 0;
			}
			gp.monster[gp.currentMap][i].life -= damage;
			gp.ui.addMessage(damage + " damage!");

			gp.monster[gp.currentMap][i].invincible = true;
			gp.monster[gp.currentMap][i].damageReaction();
			if (gp.monster[gp.currentMap][i].life <= 0) {
				gp.monster[gp.currentMap][i].dying = true;
				gp.ui.addMessage("killed the " + gp.monster[gp.currentMap][i].name + "!");
				gp.ui.addMessage("Exp " + gp.monster[gp.currentMap][i].exp);
				exp += gp.monster[gp.currentMap][i].exp;
				checkLevelUp();
			}
		}

	}

	public void damageInteractiveTile(int i) {

		if (i != 999 && gp.iTile[gp.currentMap][i].destructible && gp.iTile[gp.currentMap][i].isCorrectItem(this)
				&& !gp.iTile[gp.currentMap][i].invincible) {

			gp.iTile[gp.currentMap][i].playSE();
			gp.iTile[gp.currentMap][i].life--;
			gp.iTile[gp.currentMap][i].invincible = true;
			currentWeapon.durability--;
			// Partículas
			generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

			if (gp.iTile[gp.currentMap][i].life == 0) {
				gp.iTile[gp.currentMap][i].checkDrop();
				gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
			}
		}
	}

	public void damageProjectile(int i) {
		if (i != 999) {
			Entity projectile = gp.projectile[gp.currentMap][i];
			projectile.alive = false;
			generateParticle(projectile, projectile);
		}
	}

	public void checkLevelUp() {
		if (exp >= nextLevelExp) {
			level++;
			nextLevelExp = (int) (nextLevelExp * 1.5);
			maxLife += 2;
			attack = getAttack();
			defense = getDefense();
			gp.playSE(2);
			gp.gameState = gp.dialogueState;
			startDialogue(this, 0);
		}
	}

	public void selectItem() {
		int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
		if (itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);

			if (selectedItem.type == type_sword || selectedItem.type == type_axe || selectedItem.type == type_pickaxe
					|| selectedItem.type == type_gloves) {
				currentWeapon = selectedItem;
				attack = getAttack();
				if (poisoned) {
					getPoisonedAttackImage();
				}
				getAttackImage();

			}
			if (selectedItem.type == type_shield) {
				currentShield = selectedItem;
				defense = getDefense();
				getGuardImage();
			}
			if (selectedItem.type == type_light) {

				if (currentLight == selectedItem) {
					currentLight = null;
				} else {
					currentLight = selectedItem;
				}
				lightUpdated = true;
			}
			//controlamos que el usuario se pueda equipar el mapa
			if(selectedItem.type == type_map) {
				if(currentMapItem == selectedItem) {
					currentMapItem = null;
					gp.gotMap = false;

				}
				else {
					currentMapItem = selectedItem;
					gp.gotMap = true; //si el item mapa esta equiado habilitamos gotMap para que pueda abrir el minimapa
				}

			}
			if ((selectedItem.type == type_consumable) && selectedItem.use(this)) {
				if (selectedItem.amount > 1) {
					selectedItem.amount--;
				} else {
					inventory.remove(itemIndex);
				}

			}
		}
	}

	public int searchItemInInventory(String itemName) {

		int itemIndex = 999;

		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).name.equals(itemName)) {
				itemIndex = i;
				break;
			}
		}
		return itemIndex;
	}
	public boolean canObtainItem(Entity item) {
		boolean canObtain = false;

		// CHECK IF ITEM IS STACKABLE
		if (item.stackable) {
			int index = searchItemInInventory(item.name);

			if (index != 999) {
				// Sumar la cantidad del ítem en el suelo a la cantidad existente en el inventario
				if(gp.gameState == gp.tradeState) {
					inventory.get(index).amount++;
				} else {
					inventory.get(index).amount += item.amount;
				}

				System.out.println("Se han añadido " + item.amount + " " + item.name);
				canObtain = true;
			} else if (inventory.size() != maxInventorySize) {
				inventory.add(item);
				index = searchItemInInventory(item.name);
				inventory.get(index).amount = item.amount;
				System.out.println("Has obtenido " + item.amount + " " + item.name);
				canObtain = true;
			}
		} else if (inventory.size() != maxInventorySize) {
			inventory.add(item);
			System.out.println("Has obtenido " + item.name); // No necesitas mostrar la cantidad para ítems no apilables
			canObtain = true;
		}
		return canObtain;
	}
	@Override
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;

		switch (direction) {
		case "up":
			if (!poisoned) {

				if (!attacking) {
					if (!currentWeapon.name.equals(OBJ_BoxingGloves.objName)) {

						if (!gp.keyH.upPressed) {
							image = up;
						} else {
							if (spriteNumPlayer == 1) {
								image = up;
							}
							if (spriteNumPlayer == 2) {
								image = up1;
							}
							if (spriteNumPlayer == 3) {
								image = up;
							}
							if (spriteNumPlayer == 4) {
								image = up2;
							}
						}
					} else if (!gp.keyH.upPressed) {
						image = glovesUp;
					} else {
						if (spriteNumPlayer == 1) {
							image = glovesUp;
						}
						if (spriteNumPlayer == 2) {
							image = glovesUp1;
						}
						if (spriteNumPlayer == 3) {
							image = glovesUp;
						}
						if (spriteNumPlayer == 4) {
							image = glovesUp2;
						}
					}

				}
				if (attacking) {
					tempScreenY = screenY - gp.tileSize;
					if (!currentWeapon.name.equals(OBJ_BoxingGloves.objName)) {
						if (spriteNum == 1) {
							image = attackUp1;
						}
						if (spriteNum == 2) {
							image = attackUp2;
						}
					} else {
						System.out.println("hola");
					}
				}
				if (guarding) {
					image = guardUp;
				}
			}
			if (poisoned) {

				if (!attacking) {
					if (!gp.keyH.upPressed) {
						image = poisonedUp;
					} else {
						if (spriteNumPlayer == 1) {
							image = poisonedUp;
						}
						if (spriteNumPlayer == 2) {
							image = poisonedUp1;
						}
						if (spriteNumPlayer == 3) {
							image = poisonedUp;
						}
						if (spriteNumPlayer == 4) {
							image = poisonedUp2;
						}
					}
				}
				if (attacking) {
					tempScreenY = screenY - gp.tileSize;
					if (spriteNum == 1) {
						image = poisonedAttackUp1;
					}
					if (spriteNum == 2) {
						image = poisonedAttackUp2;
					}
				}
				if (guarding) {
					image = poisonedGuardUp;
				}
			}
			break;
		case "down":
			if (!poisoned) {

				if (!attacking) {
					if (!currentWeapon.name.equals(OBJ_BoxingGloves.objName)) {

						if (!gp.keyH.downPressed) {
							image = down;
						} else {
							if (spriteNumPlayer == 1) {
								image = down;
							}
							if (spriteNumPlayer == 2) {
								image = down1;
							}
							if (spriteNumPlayer == 3) {
								image = down;
							}
							if (spriteNumPlayer == 4) {
								image = down2;
							}
						}
					} else if (!gp.keyH.downPressed) {
						image = glovesDown;
					} else {
						if (spriteNumPlayer == 1) {
							image = glovesDown;
						}
						if (spriteNumPlayer == 2) {
							image = glovesDown1;
						}
						if (spriteNumPlayer == 3) {
							image = glovesDown;
						}
						if (spriteNumPlayer == 4) {
							image = glovesDown2;
						}
					}
				}
				if (attacking) {
					if (spriteNum == 1) {
						image = attackDown1;
					}
					if (spriteNum == 2) {
						image = attackDown2;
					}
				}
				if (guarding) {
					image = guardDown;
				}
			}
			if (poisoned) {

				if (!attacking) {

					if (!gp.keyH.downPressed) {
						image = poisonedDown;
					} else {
						if (spriteNumPlayer == 1) {
							image = poisonedDown;
						}
						if (spriteNumPlayer == 2) {
							image = poisonedDown1;
						}
						if (spriteNumPlayer == 3) {
							image = poisonedDown;
						}
						if (spriteNumPlayer == 4) {
							image = poisonedDown2;
						}
					}
				}
				if (attacking) {
					if (spriteNum == 1) {
						image = poisonedAttackDown1;
					}
					if (spriteNum == 2) {
						image = poisonedAttackDown2;
					}
				}
				if (guarding) {
					image = poisonedGuardDown;
				}
			}
			break;
		case "left":
			if (!poisoned) {

				if (!attacking) {
					if (!currentWeapon.name.equals(OBJ_BoxingGloves.objName)) {

						if (!gp.keyH.leftPressed) {
							image = left;
						} else {
							if (spriteNumPlayer == 1) {
								image = left;
							}
							if (spriteNumPlayer == 2) {
								image = left1;
							}
							if (spriteNumPlayer == 3) {
								image = left;
							}
							if (spriteNumPlayer == 4) {
								image = left2;
							}
						}
					} else if (!gp.keyH.leftPressed) {
						image = glovesLeft;
					} else {
						if (spriteNumPlayer == 1) {
							image = glovesLeft;
						}
						if (spriteNumPlayer == 2) {
							image = glovesLeft1;
						}
						if (spriteNumPlayer == 3) {
							image = glovesLeft;
						}
						if (spriteNumPlayer == 4) {
							image = glovesLeft2;
						}
					}
				}
				if (attacking) {
					tempScreenX = screenX - gp.tileSize;
					if (!currentWeapon.name.equals(OBJ_BoxingGloves.objName)) {
						if (spriteNum == 1) {
							image = attackLeft1;
						}
						if (spriteNum == 2) {
							image = attackLeft2;
						}
					} else if (!differentAttackGloves) {
						if (spriteNumGloves == 1) {
							image = attackLeft2;
						}
						if (spriteNumGloves == 2) {
							image = attackLeft2;
						}
					} else {
						if (spriteNumGloves == 1) {
							image = attackLeft1;
						}
						if (spriteNumGloves == 2) {
							image = attackLeft1;
						}
					}
				}
				if (guarding) {
					image = guardLeft;
				}
			}
			if (poisoned) {

				if (!attacking) {
					if (!gp.keyH.leftPressed) {
						image = poisonedLeft;
					} else {
						if (spriteNumPlayer == 1) {
							image = poisonedLeft;
						}
						if (spriteNumPlayer == 2) {
							image = poisonedLeft1;
						}
						if (spriteNumPlayer == 3) {
							image = poisonedLeft;
						}
						if (spriteNumPlayer == 4) {
							image = poisonedLeft2;
						}
					}
				}
				if (attacking) {
					tempScreenX = screenX - gp.tileSize;
					if (spriteNum == 1) {
						image = poisonedAttackLeft1;
					}
					if (spriteNum == 2) {
						image = poisonedAttackLeft2;
					}
				}
				if (guarding) {
					image = poisonedGuardLeft;
				}
			}
			break;
		case "right":
			if (!poisoned) {

				if (!attacking) {
					if (!currentWeapon.name.equals(OBJ_BoxingGloves.objName)) {

						if (!gp.keyH.rightPressed) {
							image = right;
						} else {
							if (spriteNumPlayer == 1) {
								image = right;
							}
							if (spriteNumPlayer == 2) {
								image = right1;
							}
							if (spriteNumPlayer == 3) {
								image = right;
							}
							if (spriteNumPlayer == 4) {
								image = right2;
							}
						}
					} else if (!gp.keyH.rightPressed) {
						image = glovesRight;
					} else {
						if (spriteNumPlayer == 1) {
							image = glovesRight;
						}
						if (spriteNumPlayer == 2) {
							image = glovesRight1;
						}
						if (spriteNumPlayer == 3) {
							image = glovesRight;
						}
						if (spriteNumPlayer == 4) {
							image = glovesRight2;
						}
					}
				}
				if (attacking) {
					if (spriteNum == 1) {
						image = attackRight1;
					}
					if (spriteNum == 2) {
						image = attackRight2;
					}
				}
				if (guarding) {
					image = guardRight;
				}
			}
			if (poisoned) {

				if (!attacking) {
					if (!gp.keyH.rightPressed) {
						image = poisonedRight;
					} else {
						if (spriteNumPlayer == 1) {
							image = poisonedRight;
						}
						if (spriteNumPlayer == 2) {
							image = poisonedRight1;
						}
						if (spriteNumPlayer == 3) {
							image = poisonedRight;
						}
						if (spriteNumPlayer == 4) {
							image = poisonedRight2;
						}
					}
				}
				if (attacking) {
					if (spriteNum == 1) {
						image = poisonedAttackRight1;
					}
					if (spriteNum == 2) {
						image = poisonedAttackRight2;
					}
				}
				if (guarding) {
					image = poisonedGuardRight;
				}
			}
			break;
		}
		if (transparent) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}

		if (poisoned) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
		}

		if (drawing) {
			g2.drawImage(image, tempScreenX, tempScreenY, null);
		}

		// RESET ALPHA
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		// DEBUG pulsando Y
		if (keyH.checkWeapon) {
			g2.setFont(new Font("Arial", Font.PLAIN, 26));
			g2.setColor(Color.white);
			g2.drawString("Invincible " + invincibleCounter, 10, 400);
			tempScreenX = screenX + solidArea.x;
			tempScreenY = screenY + solidArea.y;

			switch (direction) {
			case "up":
				tempScreenY = screenY - attackArea.height;
				break;
			case "down":
				tempScreenY = screenY + gp.tileSize;
				break;
			case "left":
				tempScreenX = screenX - attackArea.width; 
				break;
			case "right":
				tempScreenX = screenX + gp.tileSize;
				break;
			}
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(1));
			g2.drawRect(tempScreenX, tempScreenY, attackArea.width, attackArea.height);
		}
	}
}
