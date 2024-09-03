package com.rramirezsoft.wildquest.entity.player;

import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class PlayerDummy extends Entity{

	public final static String npcName = "Dummy";

	public PlayerDummy(GamePanel gp) {
		super(gp);

		name = npcName;
		getImage();
	}
	public void getImage() {



		up = setup("/player/sprite_up", gp.tileSize, gp.tileSize);
		up1 = setup("/player/sprite_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/sprite_up_2", gp.tileSize, gp.tileSize);
		down = setup("/player/sprite_down", gp.tileSize, gp.tileSize);
		down1 = setup("/player/sprite_down", gp.tileSize, gp.tileSize);
		down2 = setup("/player/sprite_down", gp.tileSize, gp.tileSize);
		left = setup("/player/sprite_left", gp.tileSize, gp.tileSize);
		left1 = setup("/player/sprite_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/sprite_left_2", gp.tileSize, gp.tileSize);
		right = setup("/player/sprite_right", gp.tileSize, gp.tileSize);
		right1 = setup("/player/sprite_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/sprite_right_2", gp.tileSize, gp.tileSize);
	}

}
