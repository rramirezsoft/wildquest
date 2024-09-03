package com.rramirezsoft.wildquest.main;

import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main {

	public static JFrame window;
	public static void main(String[] args) {

		// Creamos una ventana
		window = new JFrame();
		// La ventana se cierra al dar a la X correctamente
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// No podemos reajustar el tamaño de la pantalla
		window.setResizable(false);

		// Nombre del juego (aparece en el ejecutable)
		window.setTitle("Wild Quest");

		//icono del juego
		new Main().setIcon();

		// Añadimos la ventana al game panel
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);

		try {
			gamePanel.config.loadConfig();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(gamePanel.fullScreenOn) {
			window.setUndecorated(true);
		}

		// La ventana se ajusta a las dimensiones de los subcomponentes (game panel)
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		// Llamamos a setupGame antes que empezar el juego para tener ya los objetos
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}
	public void setIcon() {

		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("player/sprite_down.png"));
		window.setIconImage(icon.getImage());
	}
}
