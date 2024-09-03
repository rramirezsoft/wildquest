package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.dialogues.DialogueUtils;
import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Chocolate extends Entity{

	public static final String objName = "Chocolate";

	public OBJ_Chocolate(GamePanel gp) {
		super(gp);

		type = type_consumable;
		value = 50;
		name = objName;
		down1 = setup("/objects/chocolate",gp.tileSize, gp.tileSize);
		description = "[" + name + "]\nAumenta tu escudo " + value + " pts.\n25% disminución ataque.";
		price = 30;
		stackable = true;

		loadDialogues(objName);
	}
	@Override
	public String modifyDialogue(String dialogue) {
		return DialogueUtils.modifyDialogue(dialogue, "{name}", name, "{value}", String.valueOf(value));
	}
	@Override
	public boolean use(Entity entity) {

		if(gp.player.protect == gp.player.maxProtect) {

			startDialogue(this, 0);
			return false;
		}
		startDialogue(this, 1);
		entity.protect += value;
		gp.playSE(2);

		new Thread(() -> {
			int duration = 20000; // duración del efecto en milisegundos (10 segundos)
			int interval = 1000; // intervalo entre cada incremento en milisegundos
			int iterations = duration / interval;
			int attackDecrease = gp.player.attack - (gp.player.attack/4);
			int defaultAttack = gp.player.attack;

			for (int i = 0; i < iterations; i++) {
				gp.player.attack = attackDecrease;	

				try {
					Thread.sleep(interval);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			gp.player.attack = defaultAttack; 

		}).start();

		return true;
	}
}
