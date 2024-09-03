package com.rramirezsoft.wildquest.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {

	GamePanel gp;

	public Config(GamePanel gp) {
		this.gp = gp;
	}

	public void saveConfig() throws IOException {
		FileWriter fw = new FileWriter("config.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		try {
			// Full screen
			if(gp.fullScreenOn) {
				bw.write("On");
			}
			if(!gp.fullScreenOn) {
				bw.write("Off");
			}
			bw.newLine();

			// Music volume
			bw.write(String.valueOf(gp.music.volumeScale));
			bw.newLine();

			// SE volume
			bw.write(String.valueOf(gp.se.volumeScale));
			bw.newLine();

		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void loadConfig()  throws FileNotFoundException {
		File configFile = new File("config.txt");
		if (!configFile.exists()) {
			System.out.println("El archivo de configuración no existe. Se creará uno nuevo con valores predeterminados.");
			try {
				configFile.createNewFile();
				FileWriter fw = new FileWriter(configFile);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("Off"); // pantalla completa desactivada
				bw.newLine();
				bw.write("3"); // volumen de la música predeterminado
				bw.newLine();
				bw.write("3"); // volumen de efectos de sonido predeterminado
				bw.newLine();
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
				return; // Si hay un error al crear el archivo, no intentamos cargar la configuración
			}
		}

		try (FileReader fr = new FileReader(configFile);
				BufferedReader br = new BufferedReader(fr)) {
			String s;
			// Full screen
			if ((s = br.readLine()) != null) {
				if (s.equals("On")) {
					gp.fullScreenOn = true;
				} else if (s.equals("Off")) {
					gp.fullScreenOn = false;
				}
			}
			// Music volume
			if ((s = br.readLine()) != null) {
				gp.music.volumeScale = Integer.parseInt(s);
			}
			// SE volume
			if ((s = br.readLine()) != null) {
				gp.se.volumeScale = Integer.parseInt(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
