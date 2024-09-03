package com.rramirezsoft.wildquest.tile.interactive;

import java.awt.Color;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class IT_DestructibleWall extends InteractiveTile{

	GamePanel gp;

	public IT_DestructibleWall(GamePanel gp, int col, int row) {
		super(gp, col, row);

		this.gp = gp;

		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;

		down1 = setup("/tiles_interactive/destructiblewall", gp.tileSize, gp.tileSize);
		destructible = true;
		life = 1;

	}
	@Override
	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;

		if (entity.currentWeapon.type == type_pickaxe) {
			isCorrectItem = true;
		}
		return isCorrectItem;
	}
	@Override
	public void playSE() {
		gp.playSE(21);
	}
	@Override
	public InteractiveTile getDestroyedForm() {
		return null;
	}
	@Override
	public Color getParticleColor() {
		return new Color(65, 65, 65);
	}
	@Override
	public int getParticleSize() {
		return 6;
	}
	@Override
	public int getParticleSpeed() {

		return 1;
	}
	@Override
	public int getParticleMaxLife() {
		return 20;
	}
	@Override
	public void checkDrop() {

	}

}
