package com.rramirezsoft.wildquest.object;

import com.rramirezsoft.wildquest.dialogues.DialogueUtils;
import com.rramirezsoft.wildquest.entity.Entity;
import com.rramirezsoft.wildquest.main.GamePanel;

public class OBJ_Chilli extends Entity{

	public static final String objName = "Guindilla";

	public OBJ_Chilli(GamePanel gp) {
		super(gp);

		type = type_consumable;
		value = 10;
		name = objName;
		down1 = setup("/objects/chilli",gp.tileSize, gp.tileSize);
		description = "[" + name + "]\n+" + value + " pts salud & escudo\n10% aumento velocidad.";
		price = 18;
		stackable = true;

		loadDialogues(objName);
	}

	@Override
	public String modifyDialogue(String dialogue) {
		return DialogueUtils.modifyDialogue(dialogue, "{name}", name, "{value}", String.valueOf(value));
	}

	@Override
	public boolean use(Entity entity) {

		startDialogue(this, 0);
		gp.playSE(2);
		new Thread(() -> {
			int duration = 10000; // duración del efecto en milisegundos (10 segundos)
			int interval = 1000; // intervalo entre cada incremento en milisegundos
			int iterations = duration / interval;
			int healthIncrease = 1; // cantidad de vida a aumentar en cada iteración
			int currentSpeed = 5; // cantidad de velocidad a aumentar en cada iteración
			int protectIncrease = 1;

			for (int i = 0; i < iterations; i++) {
				gp.player.life += healthIncrease;
				gp.player.protect += protectIncrease;
				gp.player.speed = currentSpeed;

				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			gp.player.speed = gp.player.defaultSpeed;
		}).start();

		return true;
	}
}
