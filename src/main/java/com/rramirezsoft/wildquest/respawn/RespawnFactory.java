package com.rramirezsoft.wildquest.respawn;

import com.rramirezsoft.wildquest.main.GamePanel;

public class RespawnFactory implements RespawneableFactory{

	GamePanel gp;

	public RespawnFactory(GamePanel gp) {
		this.gp = gp;
	}

	public Respawneable createRespawn(String type, GamePanel gp) {
		switch (type) {
		case "Random":
			return new RandomRespawn(gp);
		case "Area":
			return new AreaRespawn();
		default:
			throw new IllegalArgumentException("Tipo de respawn " + type + " no válido");

		}
	}
}
