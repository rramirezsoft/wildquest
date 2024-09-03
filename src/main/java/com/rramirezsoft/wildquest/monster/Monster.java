package com.rramirezsoft.wildquest.monster;

import java.util.Random;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public abstract class Monster extends Entity{

	public Monster(GamePanel gp) {
		super(gp);
		type = type_monster;
		getImage();
	}

	public abstract void getImage();

	@Override
	public abstract void setAction();

	@Override
	public abstract void checkDrop();

	@Override
	public void damageReaction() {
		actionLockCounter = 0;
		onPath = true;
	}

	public void checkAttackOrNot(int rate, int straight, int horizontal) {

		boolean targetInRange = false;
		int xDis = getXDistance(gp.player);
		int yDis = getYDistance(gp.player);

		switch(direction) {
		case "up":
			if(gp.player.getCenterY() < getCenterY() && yDis < straight && xDis < horizontal) {
				targetInRange = true;
			}
			break;
		case "down":
			if(gp.player.getCenterY() > getCenterY() && yDis < straight && xDis < horizontal) {
				targetInRange = true;
			}
			break;
		case "left":
			if(gp.player.getCenterX() < getCenterX() && xDis < straight && yDis < horizontal) {
				targetInRange = true;
			}
			break;
		case "right":
			if(gp.player.getCenterX() > getCenterX() && xDis < straight && yDis < horizontal) {
				targetInRange = true;
			}
			break;
		}

		if(targetInRange == true) {
			//vemos si se inicia el ataque
			int i = new Random().nextInt(rate);
			if(i == 0) {
				attacking = true;
				spriteNum = 1;
				spriteCounter = 0;
				shotAvailableCounter = 0;
			}
		}
	}
	public void checkShootOrNot(int rate, int shotInterval) {

		int i = new Random().nextInt(rate);
		if(i == 0 && !projectile.alive && shotAvailableCounter == shotInterval) {
			projectile.set(worldX, worldY, direction, true, this);


			// CHECK VACANCY
			for(int ii = 0; ii < gp.projectile[1].length; ii++) {
				if(gp.projectile[gp.currentMap][ii] == null) {
					gp.projectile[gp.currentMap][ii] = projectile;
					break;
				}
			} 

			shotAvailableCounter = 0;
		}

	}
	public void checkStartChasingOrNot(Entity target, int distance, int rate) {

		if(getTileDistance(target) < distance) {
			int i = new Random().nextInt(rate);
			if(i == 0) {
				onPath = true;
			}
		}
	}
	public void checkStopChasingOrNot(Entity target, int distance, int rate) {

		if(getTileDistance(target) > distance) {
			int i = new Random().nextInt(rate);
			if(i == 0) {
				onPath = false;
			}
		}
	}
	public void getRandomDirection(int interval) {
		actionLockCounter++;

		if(actionLockCounter > interval) {
			Random random = new Random();
			int i = random.nextInt(100)+1;// 0 al 99, sumamos 1 para que sea del 1 al 100

			if(i <= 25) {direction = "up";}
			if(i > 25 && i <= 50) {direction = "down";}
			if(i > 50 && i <= 75) {direction = "left";}
			if(i > 75 && i <= 100) {direction = "right";}
			actionLockCounter = 0;
		}
	}
	public void moveTowardPlayer(int interval) {

		actionLockCounter ++;

		if(actionLockCounter > interval) {

			if(getXDistance(gp.player) > getYDistance(gp.player)) {

				if(gp.player.getCenterX() < getCenterX()) {
					direction = "left";
				} else {
					direction = "right";
				}
			}
			else if(getXDistance(gp.player) < getYDistance(gp.player)) {

				if(gp.player.getCenterY() < getCenterY()) {
					direction = "up";
				} else {
					direction = "down";
				}
			}
			actionLockCounter = 0;
		}
	}

}

