package com.rramirezsoft.wildquest.tile.interactive;

import java.awt.Graphics2D;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class InteractiveTile extends Entity {

	GamePanel gp;
	public boolean destructible;

	public InteractiveTile(GamePanel gp, int col, int row) {
		super(gp);
		this.gp = gp;
	}

	public boolean isCorrectItem(Entity entity) {
		return false;
	}

	public void playSE() {
		// TODO document why this method is empty
	}

	public InteractiveTile getDestroyedForm() {
		return null;
	}

	@Override
	public void update() {

		if(invincible) {
			invincibleCounter++;
			if(invincibleCounter > 20) {
				invincible = false;
				invincibleCounter = 0;
			}

		}
	}

	@Override
	public void draw(Graphics2D g2) {

		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;

		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
				&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
				&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
				&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

			g2.drawImage(down1, screenX, screenY, null);

		}

	}
}
