package com.rramirezsoft.wildquest.dialogues;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *  @author Raul Ramirez. Clase gestora encargada del manejo de los dialogos a traves de lectura 
 *  y escritura de ficheros
 */
public class DialogueManager {

	private static DialogueManager instance;
	private Map<String, NPCDialogues> dialogues;

	private DialogueManager() {
		loadDialogues();
	}

	public static DialogueManager getInstance() {
		if (instance == null) {
			instance = new DialogueManager();
		}
		return instance;
	}

	private void loadDialogues() {
		try {
			Gson gson = new Gson();
			InputStream is = getClass().getClassLoader().getResourceAsStream("dialogues/dialogues.json");
			if (is == null) {
				throw new FileNotFoundException("Resource not found: dialogues/dialogues.json");
			}
			Reader reader = new InputStreamReader(is);
			dialogues = gson.fromJson(reader, new TypeToken<Map<String, NPCDialogues>>() {}.getType());
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<Integer, Map<Integer, String>> getDialogues(String npcId) {
		NPCDialogues npcDialogues = dialogues.get(npcId);
		if (npcDialogues != null) {
			return npcDialogues.dialogueSets;
		}
		return Map.of();
	}

	private static class NPCDialogues {
		Map<Integer, Map<Integer, String>> dialogueSets;
	}
}