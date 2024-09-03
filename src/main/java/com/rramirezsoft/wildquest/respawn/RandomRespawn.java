package com.rramirezsoft.wildquest.respawn;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;
import com.rramirezsoft.wildquest.monster.MON_GreenSlime;
import com.rramirezsoft.wildquest.monster.MON_Orc;
import com.rramirezsoft.wildquest.monster.MON_RedSlime;

public class RandomRespawn extends AbstractRespawn{

	public RandomRespawn(GamePanel gp) {
		super(gp);
	}

	@Override
	public void respawn() {
		int maxEnemies = 10;
		int currentEnemies = countEnemiesOnMap();
		System.out.println("Número de enemigos en el mapa: " + currentEnemies);

		if (currentEnemies < maxEnemies) {
			// Obtener las posiciones válidas para el respawn en el mapa actual
			List<Point> validPositions = new ArrayList<>();

			for (int i = 0; i < gp.tileM.mapTileNum[gp.currentMap].length; i++) {
				for (int j = 0; j < gp.tileM.mapTileNum[gp.currentMap][i].length; j++) {
					// Verificar si la posición es válida para el respawn y no tiene un enemigo
					if (isValidRespawnPosition(gp.currentMap, i, j)) {
						validPositions.add(new Point(i, j));
					}
				}
			}

			// Seleccionar una posición aleatoria válida y respawnear un enemigo
			if (!validPositions.isEmpty()) {
				Point respawnPosition = validPositions.get(random.nextInt(validPositions.size()));
				spawnEnemyAtPosition(respawnPosition);
			}
		} 
	}


	private void spawnEnemyAtPosition(Point position) {

		int randomNumber = random.nextInt(100);    
		Entity newEnemy;

		if (randomNumber < 60) { 
			newEnemy = new MON_GreenSlime(gp);
		} else if (randomNumber < 90) { 
			newEnemy = new MON_RedSlime(gp);
		} else {
			newEnemy = new MON_Orc(gp);
		}

		// Incrementar el índice de monstruos para este mapa
		int monsterIndex = 0;
		while (gp.monster[gp.currentMap][monsterIndex] != null) {
			monsterIndex++;
		}

		// Aseguramos que no exceda el límite máximo de monstruos
		if (monsterIndex < gp.monster[gp.currentMap].length) {
			// Instanciar y colocar el enemigo en la posición seleccionada
			gp.monster[gp.currentMap][monsterIndex] = newEnemy;
			gp.monster[gp.currentMap][monsterIndex].worldX = position.x * gp.tileSize;
			gp.monster[gp.currentMap][monsterIndex].worldY = position.y * gp.tileSize;
			System.out.println("Ha respawneado un " +newEnemy.name + " en la posición [" + 
					newEnemy.worldX/gp.tileSize + "][" + newEnemy.worldY/gp.tileSize + "]");
		}
	}



}
