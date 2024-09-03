package com.rramirezsoft.wildquest.monster;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.rramirezsoft.wildquest.main.GamePanel;

public abstract class MON_BaseMushroom extends Monster{

	public MON_BaseMushroom(GamePanel gp) {
		super(gp);

		solidArea = new Rectangle(3, 18, 36, 30);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}

	@Override
	public void getImage() {
		String basePath = getBasePath();
		String m0 = basePath + "_0";
		String m1 = basePath + "_1";
		String m2 = basePath + "_2";
		String m3 = basePath + "_3";
		String m4 = basePath + "_4";

		up = setup(m0, gp.tileSize, gp.tileSize);
		up1 = setup(m1, gp.tileSize, gp.tileSize);
		up2 = setup(m2, gp.tileSize, gp.tileSize);
		up3 = setup(m3, gp.tileSize, gp.tileSize);
		up4 = setup(m4, gp.tileSize, gp.tileSize);

		down = setup(m0, gp.tileSize, gp.tileSize);
		down1 = setup(m1, gp.tileSize, gp.tileSize);
		down2 = setup(m2, gp.tileSize, gp.tileSize);
		down3 = setup(m3, gp.tileSize, gp.tileSize);
		down4 = setup(m4, gp.tileSize, gp.tileSize);

		left = setup(m0, gp.tileSize, gp.tileSize);
		left1 = setup(m1, gp.tileSize, gp.tileSize);
		left2 = setup(m2, gp.tileSize, gp.tileSize);
		left3 = setup(m3, gp.tileSize, gp.tileSize);
		left4 = setup(m4, gp.tileSize, gp.tileSize);

		right = setup(m0, gp.tileSize, gp.tileSize);
		right1 = setup(m1, gp.tileSize, gp.tileSize);
		right2 = setup(m2, gp.tileSize, gp.tileSize);
		right3 = setup(m3, gp.tileSize, gp.tileSize);
		right4 = setup(m4, gp.tileSize, gp.tileSize);
	}

	public abstract String getBasePath();

	@Override
	public void update() {
		super.update();

		spriteCounterWalk++;
		if(spriteCounterWalk > 6) {
			spriteNumPlayer = (spriteNumPlayer % 5) + 1;
			spriteCounterWalk = 0;
		}
	}
	@Override
	public void draw(Graphics2D g2) {
		BufferedImage image = null;

		if (inCamera()) {
			int tempScreenX = getScreenX();
			int tempScreenY = getScreenY();

			switch (direction) {
			case "up":
				image = getSpriteImage();
				if (attacking) {
					tempScreenY = getScreenY() - up1.getHeight();
					image = (spriteNum == 1) ? attackUp1 : attackUp2;
				}
				break;
			case "down":
				image = getSpriteImage();
				if (attacking) {
					image = (spriteNum == 1) ? attackDown1 : attackDown2;
				}
				break;
			case "left":
				image = getSpriteImage();
				if (attacking) {
					tempScreenX = getScreenX() - left1.getWidth();
					image = (spriteNum == 1) ? attackLeft1 : attackLeft2;
				}
				break;
			case "right":
				image = getSpriteImage();
				if (attacking) {
					image = (spriteNum == 1) ? attackRight1 : attackRight2;
				}
				break;
			}

			if (invincible) {
				hpBarOn = true;
				hpBarCounter = 0;
				changeAlpha(g2, 0.4f);
			}

			if (dying) {
				dyingAnimation(g2);
			}

			g2.drawImage(image, tempScreenX, tempScreenY, null);
			changeAlpha(g2, 1f);
		}
	}
	private BufferedImage getSpriteImage() {
		switch (spriteNumPlayer) {
		case 1: return up;
		case 2: return up1;
		case 3: return up2;
		case 4: return up3;
		case 5: return up4;
		default: return up;
		}
	}
	@Override
	public void damagePlayer(int attack) {
		if(!gp.player.invincible) {
			int damage = attack - gp.player.defense;
			String canGuardDirection = getOppositeDirection(direction);

			if(gp.player.guarding && gp.player.direction.equals(canGuardDirection)) {
				if(gp.player.guardCounter < 10) {
					damage = 0;
					gp.playSE(17);
					setKnockBack(this, gp.player, knockBackPower);
					offBalance = true;
					spriteCounter -= 60;
				} else {
					damage /= 3;
					gp.playSE(8);
					poisoned = false;
				}
			} else {
				gp.playSE(24);
				if(damage < 1) {
					damage = 1;
				}
				new Thread(() -> {
					int damagePoison = getPoisonDamage();
					int duration = 10000;
					int interval = 1000;
					int iterations = duration / interval;
					int damagePerIteration = damagePoison / iterations;
					int speedPoisoned = getPoisonSpeed();

					for (int i = 0; i < iterations; i++) {
						gp.player.life -= damagePerIteration;
						gp.player.poisoned = true;
						gp.player.invincible = true;
						gp.player.getPoisonedAttackImage();
						gp.playSE(24);
						gp.player.speed = speedPoisoned;

						try {
							Thread.sleep(interval);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					gp.player.poisoned = false;
					gp.player.invincible = false;
					gp.player.speed = gp.player.defaultSpeed;
				}).start();
			}
			if(damage != 0) {
				setKnockBack(gp.player, this, knockBackPower);
			}
		}
	}
	public abstract int getPoisonDamage();
	public abstract int getPoisonSpeed();
}
