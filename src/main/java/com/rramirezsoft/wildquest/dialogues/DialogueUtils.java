package com.rramirezsoft.wildquest.dialogues;

import java.util.Map;

/**
 *  @author Raul Ramirez. clase encargada de implementar sobrecarga de metodos con 
 *  el metodo modifyDialogue para aceptar n parametros en cualquier contexto de dialogos
 */
public class DialogueUtils {

	public static String modifyDialogue(String dialogue, String param1, String value1) {
		if (dialogue.contains(param1)) {
			dialogue = dialogue.replace(param1, value1);
		}
		return dialogue;
	}

	public static String modifyDialogue(String dialogue, String param1, String value1, String param2, String value2) {
		dialogue = modifyDialogue(dialogue, param1, value1);
		if (dialogue.contains(param2)) {
			dialogue = dialogue.replace(param2, value2);
		}
		return dialogue;
	}

	public static String modifyDialogue(String dialogue, Map<String, String> params) {
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (dialogue.contains(entry.getKey())) {
				dialogue = dialogue.replace(entry.getKey(), entry.getValue());
			}
		}
		return dialogue;
	}
}
