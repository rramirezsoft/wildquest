package com.rramirezsoft.wildquest.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	GamePanel gp;

	// Booleanos para determinar si estamos pulsando la tecla o no
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed, spacePressed;

	// DEBUG
	boolean checkDrawTime;
	public boolean checkWeapon;
	public boolean godModeOn;

	public String alphabet = "abcdefghijklmnopqrstuvwxyz"; // abecedario para elegir el nombre del usuario

	public boolean tryToLoadGame = false;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO document why this method is empty
	}
	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode(); // getKeyCode() devuelve el KeyCode entero asociado con la tecla pulsada en el
		// eveento

		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			titleState(code);
		}
		// PLAY STATE
		else if (gp.gameState == gp.playState) {
			playState(code);
		}
		// PAUSE STATE
		else if (gp.gameState == gp.pauseState) {
			pauseState(code);
		}

		// DIALOGUE STATE
		else if (gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState) {
			dialogueState(code);
		}

		//QUESTION STATE
		else if(gp.gameState == gp.questionState) {
			questionState(code);
		}

		// CHARACTER STATE
		else if (gp.gameState == gp.characterState) {
			characterState(code);
		}

		// OPTIONS STATE
		else if (gp.gameState == gp.optionsState) {
			optionsState(code);
		}

		// GAMEOVER STATE
		else if (gp.gameState == gp.gameOverState) {

			gameOverState(code);
		}

		// TRADE STATE
		else if (gp.gameState == gp.tradeState) {

			tradeState(code);
		}
		// MAP STATE
		else if (gp.gameState == gp.mapState) {

			mapState(code);
		}
		// TITLE OPTIONS STATE
		else if (gp.gameState == gp.titleOptionsState) {
			titleOptionsState(code);
		}
		// CHOOSE NAME STATE
		else if (gp.gameState == gp.chooseNameState) {
			chooseNameState(code);
		}

	}
	public void titleState(int code) {

		if(!tryToLoadGame) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;

				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
					gp.playSE(8);
				} else {
					gp.playSE(3);
				}
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;

				if (gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
					gp.playSE(8);
				} else {
					gp.playSE(3);
				}
			}

			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.aSetter.setNPC(); //colocamos los npcs por el mapa
					gp.aSetter.setMonster(); // colocamos a los enemigos base
					gp.stopMusic();
					gp.gameState = gp.beginGameState;
					gp.playMusic(9);
				}
				if (gp.ui.commandNum == 1) {
					if(gp.saveLoad.isSaveFilePresent()) {
						gp.stopMusic();
						gp.saveLoad.load();
						gp.gameState = gp.playState;
						gp.checkArea(); // comprobamos el area para poner la musica
					} else {
						tryToLoadGame = true;
					}
				}
				if (gp.ui.commandNum == 2) {
					System.exit(0);
				}
			}

			if (code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.titleOptionsState;
			}
		} else // Aquí manejamos la pantalla del mensaje de error
			if (code == KeyEvent.VK_ENTER) {
				tryToLoadGame = false;
				gp.keyH.enterPressed = false;
			}
	}
	public void optionsState(int code) {

		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}

		int maxCommandNum = 0;
		switch (gp.ui.subState) {
		case 0:
			maxCommandNum = 5;
			break;
		case 3:
			maxCommandNum = 1;
		}

		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			gp.playSE(8);
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = maxCommandNum;
				gp.playSE(3);
			}
		}

		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			gp.playSE(8);
			if (gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
				gp.playSE(3);
			}
		}

		if ((code == KeyEvent.VK_A) && (gp.ui.subState == 0)) {
			if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
				gp.music.volumeScale--;
				gp.music.checkVolume();
				gp.playSE(8);
			}

			if (gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
				gp.se.volumeScale--;
				gp.playSE(8);
			}

		}

		if ((code == KeyEvent.VK_D) && (gp.ui.subState == 0)) {
			if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
				gp.music.volumeScale++;
				gp.music.checkVolume();
				gp.playSE(3);
			}

			if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
				gp.se.volumeScale++;
				gp.playSE(3);
			}
		}

	}
	public void titleOptionsState(int code) {
		if (gp.ui.subState == 0 && code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.titleState;
		}

		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}

		int maxCommandNum = 0;
		switch (gp.ui.subState) {
		case 0:
			maxCommandNum = 3;
			break;
		case 3:
			maxCommandNum = 1;
			break;
		}

		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			gp.playSE(8);
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = maxCommandNum;
				gp.playSE(3);
			}
		}

		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			gp.playSE(8);
			if (gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
				gp.playSE(3);
			}
		}

		if ((code == KeyEvent.VK_A) && (gp.ui.subState == 0)) {
			if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
				gp.music.volumeScale--;
				gp.music.checkVolume();
				gp.playSE(8);
			}

			if (gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
				gp.se.volumeScale--;
				gp.playSE(8);
			}
		}

		if ((code == KeyEvent.VK_D) && (gp.ui.subState == 0)) {
			if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
				gp.music.volumeScale++;
				gp.music.checkVolume();
				gp.playSE(3);
			}

			if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
				gp.se.volumeScale++;
				gp.playSE(3);
			}
		}
	}
	public void playState(int code) {

		if (gp.player.moving) {

			// Si pulsamos una tecla, como la W, upPressed pasa a true.
			if (code == KeyEvent.VK_W) {
				upPressed = true;
			}
			if (code == KeyEvent.VK_S) {
				downPressed = true;
			}
			if (code == KeyEvent.VK_A) {
				leftPressed = true;
			}
			if (code == KeyEvent.VK_D) {
				rightPressed = true;
			}
			// PAUSAR
			if (code == KeyEvent.VK_P) {
				gp.gameState = gp.pauseState;
			}

			if (code == KeyEvent.VK_C) {
				gp.gameState = gp.characterState;
			}

			if (code == KeyEvent.VK_ENTER) {
				enterPressed = true;
			}

			if (code == KeyEvent.VK_L) {
				shotKeyPressed = true;
			}

			if (code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.optionsState;
			}
			// MAPA
			if ((code == KeyEvent.VK_M) && gp.gotMap) {
				gp.gameState = gp.mapState;
			}
			// MINIMAPA
			if ((code == KeyEvent.VK_X) && gp.gotMap) {
				if (gp.map.miniMapOn == false) {
					gp.map.miniMapOn = true;
				} else {
					gp.map.miniMapOn = false;
				}
			}

			// GUARD
			if (code == KeyEvent.VK_SPACE) {
				spacePressed = true;
			}

			// DEBUG
			if (code == KeyEvent.VK_T) {
				if (!checkDrawTime) {
					checkDrawTime = true;
				} else {
					checkDrawTime = false;
				}
			}
			if (code == KeyEvent.VK_Y) {
				if (!checkWeapon) {
					checkWeapon = true;
				} else {
					checkWeapon = false;
				}
			}

			if (code == KeyEvent.VK_R) {
				switch (gp.currentMap) {
				case 0:
					gp.tileM.loadMap("/maps/bosqueInicial.txt", 0);
					break;
				case 1:
					gp.tileM.loadMap("/maps/interior01.txt", 1);
					break;
				}

			}
			if (code == KeyEvent.VK_G) {

				if (!godModeOn) {
					godModeOn = true;
				} else {
					godModeOn = false;
				}

			}
		}
	}
	public void pauseState(int code) {
		if (code == KeyEvent.VK_P) {

			gp.gameState = gp.playState;
		}
	}
	public void dialogueState(int code) {
		if (code == KeyEvent.VK_ENTER) {

			enterPressed = true;
		}
	}
	public void questionState(int code) {
		if (code == KeyEvent.VK_ENTER) {
			gp.keyH.enterPressed = true;
		}

		if (gp.ui.subState == 0) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 1;
				}
				gp.playSE(8);
			}

			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 1) {
					gp.ui.commandNum = 0;
				}
				gp.playSE(3);
			}
		}

		if (gp.keyH.enterPressed && (gp.ui.subState == 0)) {
			if (gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
			} else if (gp.ui.commandNum == 1) {
				gp.gameState = gp.chooseNameState;
			}
		}
	}
	public void characterState(int code) {
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}

		if (code == KeyEvent.VK_ENTER) {
			gp.player.selectItem();
		}

		playerInventory(code);

	}
	public void gameOverState(int code) {

		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
			gp.playSE(8);
		}

		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
			gp.playSE(3);
		}
		if (code == KeyEvent.VK_ENTER) {
			if (gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.resetGame(false);
				gp.playMusic(9);
			} else if (gp.ui.commandNum == 1) {
				gp.gameState = gp.titleState;			
				gp.resetGame(true);
				gp.playMusic(27);

			}
		}

	}
	public void tradeState(int code) {

		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}

		if (gp.ui.subState == 0) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
				gp.playSE(8);
			}

			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
				gp.playSE(3);
			}
		}

		if (gp.ui.subState == 1) {
			npcInventory(code);
			if (code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}

		if (gp.ui.subState == 2) {
			playerInventory(code);
			if (code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}
	}
	public void mapState(int code) {
		if (code == KeyEvent.VK_M) {
			gp.gameState = gp.playState;
		}
	}
	public void playerInventory(int code) {

		if ((code == KeyEvent.VK_W) && (gp.ui.playerSlotRow != 0)) {
			gp.ui.playerSlotRow--;
			gp.playSE(11);
		}
		if ((code == KeyEvent.VK_A) && (gp.ui.playerSlotCol != 0)) {
			gp.ui.playerSlotCol--;
			gp.playSE(11);
		}
		if ((code == KeyEvent.VK_S) && (gp.ui.playerSlotRow != 3)) {
			gp.ui.playerSlotRow++;
			gp.playSE(11);
		}
		if ((code == KeyEvent.VK_D) && (gp.ui.playerSlotCol != 4)) {
			gp.ui.playerSlotCol++;
			gp.playSE(11);
		}
	}
	public void npcInventory(int code) {

		if ((code == KeyEvent.VK_W) && (gp.ui.npcSlotRow != 0)) {
			gp.ui.npcSlotRow--;
			gp.playSE(11);
		}
		if ((code == KeyEvent.VK_A) && (gp.ui.npcSlotCol != 0)) {
			gp.ui.npcSlotCol--;
			gp.playSE(11);
		}
		if ((code == KeyEvent.VK_S) && (gp.ui.npcSlotRow != 3)) {
			gp.ui.npcSlotRow++;
			gp.playSE(11);
		}
		if ((code == KeyEvent.VK_D) && (gp.ui.npcSlotCol != 4)) {
			gp.ui.npcSlotCol++;
			gp.playSE(11);
		}
	}
	public void chooseNameState(int code) {
		switch (code) {
		case KeyEvent.VK_A:
			if (gp.selectedLetter > 'a') {
				gp.selectedLetter--;
			}
			break;
		case KeyEvent.VK_D:

			if (gp.selectedLetter < 'z') {
				gp.selectedLetter++;
			}
			break;
		case KeyEvent.VK_W:
			if (gp.selectedLetter >= 'a' + 8) {
				gp.selectedLetter -= 8;
			}
			break;
		case KeyEvent.VK_S:
			if (gp.selectedLetter <= 'z' - 8) {
				gp.selectedLetter += 8;
			}
			break;

		case KeyEvent.VK_ENTER:
			// Tecla Enter, seleccionar la letra actual y añadirla al nombre
			if (gp.player.name.length() < 8) {
				String selectedLetter = String.valueOf(gp.selectedLetter).toUpperCase(); // Convertir a mayúscula
				gp.player.name += selectedLetter;
			}
			break;
		case KeyEvent.VK_BACK_SPACE:
			// Tecla Retroceso, borrar la última letra del nombre
			if (gp.player.name.length() > 0) {
				gp.player.name = gp.player.name.substring(0, gp.player.name.length() - 1);
				System.out.println(gp.player.name);
			}
			break;
		default:
			break;
		}
		if (gp.player.name.length() > 2 && code == KeyEvent.VK_C) {
			gp.gameState = gp.dialogueState;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();

		// Al dejar de pulsar la tecla, como la W, upPressed pasa a false.
		if (code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_L) {
			shotKeyPressed = false;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = false;
		}
		if (code == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}
	}

}
