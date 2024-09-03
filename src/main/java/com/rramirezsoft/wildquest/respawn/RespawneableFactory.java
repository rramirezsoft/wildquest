package com.rramirezsoft.wildquest.respawn;

import com.rramirezsoft.wildquest.main.GamePanel;

public interface RespawneableFactory {

	Respawneable createRespawn(String type, GamePanel gp);
}
