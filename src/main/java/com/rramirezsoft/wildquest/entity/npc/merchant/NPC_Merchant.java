package com.rramirezsoft.wildquest.entity.npc.merchant;

import java.awt.Rectangle;

import com.rramirezsoft.wildquest.entity.npc.NPC;
import com.rramirezsoft.wildquest.main.GamePanel;

public abstract class NPC_Merchant extends NPC{

	public NPC_Merchant(GamePanel gp) {
		super(gp);
		speed = 0;
		spriteNumPlayer = 1;
		solidArea = new Rectangle(8, 16, 32, 32);
		setItems();
	}
	@Override
	public void setAction() {}	
	@Override
	public void update() {}
	@Override
	public void speak() {
		if (dialogues == null) {
			loadDialogues(name);
		}
		gp.ui.currentDialogue = dialogues.get(0).get(0); // Establece como predeterminado el dialogo de bienvenida.
		gp.gameState = gp.tradeState;
		gp.ui.npc = this;
	}
	public abstract void setItems();
}
