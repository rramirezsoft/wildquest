package com.rramirezsoft.wildquest.entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;

import com.rramirezsoft.wildquest.dialogues.DialogueManager;
import com.rramirezsoft.wildquest.main.GamePanel;
import com.rramirezsoft.wildquest.main.UtilityTool;
import com.rramirezsoft.wildquest.object.OBJ_Shield_Wood;

// Super class para los monstruos y NPCs
public class Entity {

	protected GamePanel gp;

	public BufferedImage stand, up, up1, up2, up3, up4, down, down1, down2, down3, down4,
	left, left1, left2, left3, left4, right, right1, right2, right3, right4;	 
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2,
	attackLeft1, attackLeft2, attackRight1, attackRight2;
	public BufferedImage guardUp, guardDown, guardLeft, guardRight;
	public BufferedImage poisonedGuardUp, poisonedGuardDown, poisonedGuardLeft, poisonedGuardRight;
	public BufferedImage image, image2, image3;
	public BufferedImage glovesUp, glovesUp1, glovesUp2, glovesDown, glovesDown1, glovesDown2, glovesLeft,
	glovesLeft1, glovesLeft2, glovesRight, glovesRight1, glovesRight2;
	public BufferedImage poisonedUp, poisonedUp1, poisonedUp2, poisonedDown, poisonedDown1, poisonedDown2,
	poisonedLeft, poisonedLeft1, poisonedLeft2, poisonedRight, poisonedRight1, poisonedRight2;
	public BufferedImage poisonedAttackUp1, poisonedAttackUp2, poisonedAttackDown1, poisonedAttackDown2,
	poisonedAttackLeft1, poisonedAttackLeft2, poisonedAttackRight1, poisonedAttackRight2;
	public BufferedImage mushroom1, mushroom2, mushroom3, mushroom4, mushroom5;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);// Hitbox
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);// hitbox del arma
	public int solidAreaDefaultX;
	public int solidAreaDefaultY;
	public boolean collisionOn = false;
	public Entity attacker;
	public Entity linkedEntity;
	public boolean temp = false;

	//DIALOGUES
	public Map<Integer, Map<Integer, String>> dialogues;
	public int currentDialogueSet;
	public int currentDialogueIndex;


	// STATE
	public int worldX, worldY;
	public String direction = "down";
	public int spriteNum = 1;
	public int spriteNumPlayer = 1;
	public int spriteNumGloves = 1;
	public boolean collision = false;
	public boolean invincible = false;
	public boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	public boolean hpBarOn = false;
	public boolean onPath = false;
	public boolean knockBack = false;
	public String knockBackDirection;
	public boolean guarding = false;
	public boolean transparent = false;
	public boolean poisoned = false;
	public boolean offBalance = false;
	public Entity loot;
	public boolean opened = false;
	public boolean inRage = false;
	public boolean sleep = false;
	public boolean drawing = true;
	public boolean goToDungeon = true;
	public boolean differentAttackGloves = false;


	// COUNTER
	public int spriteCounter;
	public int spriteCounterWalk;
	public int spriteGlovesCount;
	public int actionLockCounter;
	public int invincibleCounter;
	public int shotAvailableCounter;
	public int dyingCounter;
	public int hpBarCounter;
	public int knockBackCounter;
	public int standCounter;
	public int guardCounter;
	public int offBalanceCounter;
	public int lightDurabilityCounter;

	// CHARACTER ATTRIBUTES
	public String name;
	public int defaultSpeed;
	public int speed;
	public int maxLife;
	public int life;
	public int maxProtect;
	public int protect;
	public int maxAmmo;
	public int ammo;
	public int level;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public int motion1_duration;
	public int motion2_duration;
	public Entity currentWeapon;
	public Entity currentShield;
	public Entity currentLight;
	public Entity currentMapItem;
	public Projectile projectile;
	public boolean boss;

	// ITEM ATTRIBUTES
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	public int value;
	public int attackValue;
	public int defenseValue;
	public String description;
	public int useCost;
	public int price;
	public int knockBackPower = 0;
	public boolean stackable = false; 
	public int amount = 1;
	public int lightRadius;
	public int numFallenTrunk;
	public int durability;
	public int maxDurability;

	// TYPE
	public int type; //player: 0, npc = 1, monster = 2
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_monster = 2;
	public final int type_sword = 3;
	public final int type_axe = 4;
	public final int type_shield = 5;
	public final int type_consumable = 6;
	public final int type_pickupOnly = 7;
	public final int type_obstacle = 8;
	public final int type_light = 9;
	public final int type_pickaxe = 10;
	public final int type_gloves = 11;
	public final int type_map = 12;
	//	public final int type_shoot = 13; //implementacion en un futuro
	//	public final int type_ammo = 14;

	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	public int getScreenX() {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		return screenX;
	}
	public int getScreenY() {
		int screenY =worldY - gp.player.worldY + gp.player.screenY;
		return screenY;
	}
	public int getLeftX() {
		return worldX + solidArea.x;
	}
	public int getRightX() {
		return worldX + solidArea.x + solidArea.width;
	}
	public int getTopY() {
		return worldY + solidArea.y;
	}
	public int getBottomY() {
		return worldY + solidArea.y + solidArea.height;
	}
	public int getCol() {
		return (worldX + solidArea.x)/gp.tileSize;
	}
	public int getRow() {
		return (worldY + solidArea.y)/gp.tileSize;
	}
	public int getCenterX() {
		int centerX = worldX + left1.getWidth()/2;
		return centerX;
	}
	public int getCenterY() {
		int centerY = worldY + up1.getHeight()/2;
		return centerY;
	}
	public int getXDistance(Entity target) {
		int xDistance = Math.abs(getCenterX() - target.getCenterX());
		return xDistance;
	}
	public int getYDistance(Entity target) {
		int yDistance = Math.abs(getCenterY() - target.getCenterY());
		return yDistance;
	}
	public int getTileDistance(Entity target) {
		int tileDistance = (getXDistance(target) + getYDistance(target))/gp.tileSize;
		return tileDistance;
	}
	public int getGoalCol(Entity target) {
		int goalCol= (target.worldX + target.solidArea.x)/gp.tileSize;
		return goalCol;
	}
	public int getGoalRow(Entity target) {
		int goalRow= (target.worldY + target.solidArea.y)/gp.tileSize;
		return goalRow;
	}
	public void resetCounter() {

		spriteCounter = 0;
		actionLockCounter = 0;
		invincibleCounter = 0;
		shotAvailableCounter = 0;
		dyingCounter = 0;
		hpBarCounter = 0;
		knockBackCounter = 0;
		guardCounter = 0;
		offBalanceCounter = 0;
	}
	public void setLoot(Entity loot) {

	}
	public void setAction() {}
	public void move(String direction) {}
	public void damageReaction() {}
	public void speak() {}
	public void facePlayer() {

		switch (gp.player.direction) {
		case "up": direction = "down"; break;
		case "down": direction = "up"; break;
		case "left": direction = "right"; break;
		case "right": direction = "left"; break;
		}
	}
	public void startDialogue(Entity entity, int setNum) {

		// Reiniciamos el buffer del texto del dialogo para los dialogos de los tradeos
		gp.ui.charIndex = 0;
		gp.ui.combineText = "";

		gp.gameState = gp.dialogueState;
		gp.ui.npc = entity;
		entity.currentDialogueSet = setNum;
		if (entity.dialogues != null && entity.dialogues.containsKey(setNum)) {
			gp.ui.currentDialogue = entity.dialogues.get(setNum).get(entity.currentDialogueIndex);

			// Modificamos el diálogo antes de asignarlo a currentDialogue (para elementos con modificaciones)
			gp.ui.currentDialogue = entity.modifyDialogue(entity.dialogues.get(setNum).get(entity.currentDialogueIndex));
		}

	}
	public String modifyDialogue(String dialogue) {
		return dialogue;
	}
	public void interact() {

	}
	public void loadDialogues(String entityName) {
		dialogues = DialogueManager.getInstance().getDialogues(entityName);
		currentDialogueSet = 0;
		currentDialogueIndex = 0;
	}
	public boolean use(Entity entity) {return false;}
	public void checkDrop() {}
	public void dropItem(Entity droppedItem, int value) {

		for(int i = 0; i < gp.obj[1].length; i++) {
			if(gp.obj[gp.currentMap][i] == null) {
				gp.obj[gp.currentMap][i] = droppedItem;
				gp.obj[gp.currentMap][i].worldX = worldX; // La moneda aparece donde murió el monstruo
				gp.obj[gp.currentMap][i].worldY = worldY; // La moneda aparece donde murió el monstruo
				if(gp.obj[gp.currentMap][i].type == type_pickupOnly) {
					gp.obj[gp.currentMap][i].value = value;
				}
				if(gp.obj[gp.currentMap][i].stackable) {
					gp.obj[gp.currentMap][i].amount = value;
					System.out.println("cantidad dropeada " + gp.obj[gp.currentMap][i].amount);
				}          
				break;
			}
		}
	}
	public Color getParticleColor() {
		return null;
	}
	public int getParticleSize() {
		return 0;
	}
	public int getParticleSpeed() {
		return 0;
	}
	public int getParticleMaxLife() {
		return 0;
	}
	public void generateParticle(Entity generator, Entity target) {

		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxLife = generator.getParticleMaxLife();

		Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
		Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
		Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
		Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
		gp.particleList.add(p1);
		gp.particleList.add(p2);
		gp.particleList.add(p3);
		gp.particleList.add(p4);
	}
	public void checkCollision() {
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkStruct(this, true);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
		gp.cChecker.checkEntity(this, gp.iTile);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);

		if(this.type == type_monster && contactPlayer) {
			damagePlayer(attack);
		}
	}
	public void update() {
		if(sleep == false) {

			if(knockBack == true) {

				checkCollision();

				if(collisionOn == true) {
					knockBackCounter = 0;
					knockBack = false;
					speed = defaultSpeed;
				} else {

					switch(knockBackDirection) {
					case "up": worldY -= speed;break;
					case "down": worldY += speed; break;
					case "left": worldX -= speed; break;
					case "right": worldX += speed; break;
					}			
				}	
				knockBackCounter++;
				// The more we increase the number 10, more knockback
				if(knockBackCounter == 10) {
					knockBackCounter = 0;
					knockBack = false;
					speed = defaultSpeed;
				}
			}
			else if(attacking == true){			
				attacking();
			}
			else  {

				setAction();
				checkCollision();


				// IF COLLISION IS FALSE, PLAYER CON MOVE
				if (!collisionOn) {

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

				spriteCounter++;
				if (spriteCounter > 24) {
					if (spriteNum == 1) {
						spriteNum = 2;
					} else if (spriteNum == 2) {
						spriteNum = 1;
					}
					spriteCounter = 0;
				}


			}
			if(invincible) {
				invincibleCounter++;
				if(invincibleCounter > 40) {
					invincible = false;
					invincibleCounter = 0;
				}
			}
			if(shotAvailableCounter < 30) {
				shotAvailableCounter++;
			}
			if(offBalance == true) {
				offBalanceCounter++;
				if(offBalanceCounter > 60) {
					offBalance = false;
					offBalanceCounter = 0;
				}
			}
		}
	}
	public String getOppositeDirection(String direction) {
		String oppsiteDirection = "";

		switch(direction) {
		case "up": oppsiteDirection = "down";break;
		case "down": oppsiteDirection = "up";break;
		case "left": oppsiteDirection = "right";break;
		case "right": oppsiteDirection = "left";break;
		}
		return oppsiteDirection;
	}
	public void attacking() {

		spriteCounter++;
		if (spriteCounter <= motion1_duration) {
			spriteNum = 1;
		}
		// Si cambiamos el 5 por 10, por ejemplos, la ventana de accion será mas pequeña, tendremos que ser más precisos
		if (spriteCounter > motion1_duration && spriteCounter <= motion2_duration) {
			spriteNum = 2;
			// GIVE THE CURRENT WORLDX, WORLDY, solidArea
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;

			// ADJUST PLAYERS WORLDX/Y FOR THE AttackArea
			switch (direction) {
			case "up":
				worldY -= attackArea.height;
				break;
			case "down":
				worldY += attackArea.height;
				break;
			case "left":
				worldX -= attackArea.width;
				break;
			case "right":
				worldX += attackArea.width;
				break;
			}
			// Attack area becomes solidArea
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;

			if(type == type_monster) {	
				if(gp.cChecker.checkPlayer(this) == true) {
					damagePlayer(attack);
				}		
			} else { //PLAYER

				// Check monster collision witg tge updated worldX, worldY and solidArea
				int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
				gp.player.damageMonster(monsterIndex, this, attack, currentWeapon.knockBackPower);

				// Check interactive tiles collision
				int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
				gp.player.damageInteractiveTile(iTileIndex);

				int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
				gp.player.damageProjectile(projectileIndex);
			}

			// After checking collision, restore the original data
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if (spriteCounter > motion2_duration) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	public void damagePlayer(int attack) {

		if(!gp.player.invincible) {

			int damage = attack - gp.player.defense;

			//obtenemos direccion opuesta a la del atacante
			String canGuardDirection = getOppositeDirection(direction);

			if(gp.player.guarding && gp.player.direction.equals(canGuardDirection)) {

				//parry
				if(gp.player.guardCounter < 10) {
					damage = 0;
					gp.playSE(17);
					setKnockBack(this, gp.player, knockBackPower);
					offBalance = true;
					spriteCounter -= 60;
				}
				else {

					//guardia normal
					damage /= 3;
					gp.playSE(8);
					gp.player.currentShield.durability--;
					if((gp.player.currentShield.name != new OBJ_Shield_Wood(gp).name) && (gp.player.currentShield.durability == 0)) {
						gp.player.setDefaultShield();
					}

				}	
			}
			else {
				//no hace guardia
				// We can give damage			
				gp.playSE(24);

				if(damage < 1) {
					damage = 1;
				}

			}
			if(damage != 0) {

				gp.player.transparent = true;
				setKnockBack(gp.player, this, knockBackPower);
			}

			if(gp.player.protect <= 0) {
				gp.player.life -= damage;
			} else {
				gp.player.protect -= damage;
			}
			gp.player.invincible = true;
		}

	}
	public void setKnockBack(Entity target, Entity attacker, int knockBackPower) {

		this.attacker = attacker;
		target.knockBackDirection = attacker.direction;
		target.speed += knockBackPower;
		target.knockBack = true;

	}
	public boolean inCamera() {

		boolean inCamera = false;

		if (worldX + gp.tileSize*5 > gp.player.worldX - gp.player.screenX
				&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
				&& worldY + gp.tileSize*5 > gp.player.worldY - gp.player.screenY
				&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			inCamera = true;
		}

		return inCamera;
	}
	public void draw(Graphics2D g2) {

		BufferedImage image = null;

		if (inCamera()) {

			int tempScreenX = getScreenX();
			int tempScreenY = getScreenY();

			switch (direction) {
			case "up":
				if (!attacking) {
					if (spriteNum == 1) {image = up1;}
					if (spriteNum == 2) {image = up2;}
				}
				if (attacking) {
					tempScreenY = getScreenY() - up1.getHeight();
					if (spriteNum == 1) {image = attackUp1;}
					if (spriteNum == 2) {image = attackUp2;}
				}
				break;
			case "down":
				if (!attacking) {
					if (spriteNum == 1) {image = down1;}
					if (spriteNum == 2) {image = down2;}				
				}
				if (attacking) {
					if (spriteNum == 1) {image = attackDown1;}
					if (spriteNum == 2) {image = attackDown2;}
				}
				break;
			case "left":
				if (!attacking) {
					if (spriteNum == 1) {image = left1;}
					if (spriteNum == 2) {image = left2;}
				}
				if (attacking) {
					tempScreenX = getScreenX() - left1.getWidth();
					if (spriteNum == 1) {image = attackLeft1;}
					if (spriteNum == 2) {image = attackLeft2;}
				}
				break;
			case "right":
				if (!attacking) {
					if (spriteNum == 1) {image = right1;}
					if (spriteNum == 2) {image = right2;}
				}
				if (attacking) {
					if (spriteNum == 1) {image = attackRight1;}
					if (spriteNum == 2) {image = attackRight2;}
				}
				break;
			}

			if(invincible) {
				hpBarOn = true;
				hpBarCounter = 0;
				changeAlpha(g2, 0.4f);
			}


			if(dying) {
				dyingAnimation(g2);
			}

			g2.drawImage(image, tempScreenX, tempScreenY, null);

			changeAlpha(g2, 1f);
		}

	}
	public void dyingAnimation(Graphics2D g2) {

		dyingCounter++;

		int i = 5;
		if(dyingCounter <= i) { changeAlpha(g2, 0f);}
		if(dyingCounter > i && dyingCounter <= i*2) { changeAlpha(g2, 1f);}
		if(dyingCounter > i*2 && dyingCounter <= i*3) { changeAlpha(g2, 0f);}
		if(dyingCounter > i*3 && dyingCounter <= i*4) { changeAlpha(g2, 1f);}
		if(dyingCounter > i*4 && dyingCounter <= i*5) { changeAlpha(g2, 0f);}
		if(dyingCounter > i*5 && dyingCounter <= i*6) { changeAlpha(g2, 1f); }
		if(dyingCounter > i*6 && dyingCounter <= i*7) { changeAlpha(g2, 0f); }
		if(dyingCounter > i*7 && dyingCounter <= i*8) { changeAlpha(g2, 1f); }
		if(dyingCounter > i*8) {
			alive = false;
		}
	}
	public void changeAlpha(Graphics2D g2, float alphaValue) {

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));

	}
	public BufferedImage setup(String imagePath, int width, int height) {

		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, width, height);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	public void searchPath(int goalCol, int goalRow) {

		int startCol = (worldX + solidArea.x)/gp.tileSize;
		int startRow = (worldY + solidArea.y)/gp.tileSize;

		gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

		if(gp.pFinder.search() == true) {

			// Next worldX & worldY
			int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
			int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

			// Entitys solidArea position
			int enLeftX = worldX + solidArea.x;
			int enRightX = worldX + solidArea.x + solidArea.width;
			int enTopY = worldY + solidArea.y;
			int enBottomY = worldY + solidArea.y + solidArea.height;

			if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				// UP
				direction="up";
			}
			else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				// DOWN
				direction="down";
			}
			else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
				// LEFT or RIGHT
				if(enLeftX > nextX) { 
					direction = "left";
				}
				if(enLeftX < nextX) {
					direction = "right";
				}
			}
			else if(enTopY > nextY && enLeftX > nextX) {
				// UP or LEFT
				direction = "up";
				checkCollision();
				if(collisionOn == true) {
					direction = "left";
				}
			}
			else if(enTopY > nextY && enLeftX < nextX) {
				// UP or RIGHT
				direction = "up";
				checkCollision();
				if(collisionOn == true) {
					direction = "right";
				}
			}
			else if(enTopY < nextY && enLeftX > nextX) {
				// DOWN or LEFT
				direction = "down";
				checkCollision();
				if(collisionOn == true) {
					direction = "left";
				}
			}
			else if(enTopY < nextY && enLeftX < nextX) {
				// DOWN or RIGHT
				direction = "down";
				checkCollision();
				if(collisionOn == true) {
					direction = "right";
				}
			}

		}
	}
	public void moveNPCToMap(Entity npc, int oldMapNum, int newMapNum, int newWorldX, int newWorldY) {
		// Elimina el NPC del mapa antiguo
		for (int i = 0; i < gp.npc[oldMapNum].length; i++) {
			if (gp.npc[oldMapNum][i] == npc) {
				gp.npc[oldMapNum][i] = null;
				break;
			}
		}

		// Coloca el NPC en el nuevo mapa
		for (int i = 0; i < gp.npc[newMapNum].length; i++) {
			if (gp.npc[newMapNum][i] == null) {
				gp.npc[newMapNum][i] = npc;
				npc.worldX = newWorldX;
				npc.worldY = newWorldY;
				break;
			}
		}
	}
	public int getDetected(Entity user, Entity target[][], String targetName) {

		int index = 999;

		// Check the surrounding object
		int nextWorldX = user.getLeftX();
		int nextWorldY = user.getTopY();


		switch(user.direction) {
		case "up": nextWorldY = user.getTopY()-gp.player.speed; break;
		case "down": nextWorldY = user.getBottomY()+gp.player.speed; break;
		case "left": nextWorldX = user.getLeftX()-gp.player.speed; break;
		case "right": nextWorldX = user.getRightX()+gp.player.speed; break;
		}
		int col = nextWorldX/gp.tileSize;
		int row = nextWorldY/gp.tileSize;

		for(int i = 0; i < target[1].length; i++) {
			if((target[gp.currentMap][i] != null) && (target[gp.currentMap][i].getCol() == col && 
					target[gp.currentMap][i].getRow() == row &&
					target[gp.currentMap][i].name.equals(targetName))) {

				index = i;
				break;
			}
		}
		return index;
	}
	public void poising() {		
	}
}
