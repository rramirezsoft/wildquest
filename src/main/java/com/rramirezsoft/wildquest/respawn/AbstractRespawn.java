package com.rramirezsoft.wildquest.respawn;

import java.util.Random;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public abstract class AbstractRespawn implements Respawneable{

	GamePanel gp;
	Random random;

	public AbstractRespawn(GamePanel gp) {
		this.gp = gp;
		random = new Random();
	}

	@Override
	public abstract void respawn();

	public boolean isValidRespawnPosition(int mapNum, int row, int col) {
		// Verificar que la posición esté dentro de los límites del mapa
		if (row < 0 || col < 0 || row >= gp.tileM.mapTileNum[mapNum].length || col >= gp.tileM.mapTileNum[mapNum][row].length) {
			return false;
		}

		// Verificar que la posición no esté ocupada por un tile con colision
		if (gp.tileM.tile[gp.tileM.mapTileNum[mapNum][row][col]].collision) {
			return false;
		}

		// Verificar que la posición no esté encima de un objeto o estructura
		for (Entity entity : gp.entityList) {
			int entityRow = entity.worldY / gp.tileSize;
			int entityCol = entity.worldX / gp.tileSize;
			// Calcula la distancia entre la posición de respawn y la posición de la entidad
			int distance = Math.abs(row - entityRow) + Math.abs(col - entityCol);
			// Si la distancia es menor o igual a 0, la posición de respawn está encima de la entidad
			if (distance <= 1) {
				return false;
			}
		}

		// Verificar que la posición no esté muy cerca del jugador
		int playerRow = gp.player.worldY / gp.tileSize;
		int playerCol = gp.player.worldX / gp.tileSize;
		if (Math.abs(row - playerRow) <= 3 && Math.abs(col - playerCol) <= 3) {
			return false;
		}

		//verificar que no haya un enemigo en esa posicion
		for (Entity enemy : gp.monster[mapNum]) {
			if (enemy != null) {
				int enemyRow = enemy.worldY / gp.tileSize;
				int enemyCol = enemy.worldX / gp.tileSize;
				// Calcula la distancia entre la posición de respawn y la posición del enemigo
				int distance = Math.abs(row - enemyRow) + Math.abs(col - enemyCol);
				// Si la distancia es menor o igual a 1, la posición de respawn está demasiado cerca del enemigo
				if (distance <= 2) {
					return false;
				}
			}
		}


		return true;
	}

	public int countEnemiesOnMap() {
		int count = 0;

		for (int i = 0; i < gp.monster[gp.currentMap].length; i++) {
			if (gp.monster[gp.currentMap][i] != null) {
				count++;
			}
		}
		return count;
	}
}
