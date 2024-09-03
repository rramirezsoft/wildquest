package com.rramirezsoft.wildquest.data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable{

	private static final long serialVersionUID = 1L; // Nuevo serialVersionUID


	//PLAYER STATS 
	String name;
	int level;
	int maxLife;
	int life;
	int maxProtect;
	int protect;
	int maxAmmo;
	int ammo;
	int exp;
	int nextLevelExp;
	int coin;

	//OTHERS
	boolean start; //quitar la animacion del principio
	int currentArea; //area actual del jugador

	//DAY STATE
	int dayState;
	public float filterAlpha;
	public int dayCounter;


	//PLAYER INVENTORY
	ArrayList<String> itemNames = new ArrayList<>();
	ArrayList<Integer> itemAmounts = new ArrayList<>();
	ArrayList<Integer> itemDurabilities = new ArrayList<>();
	int currentWeaponSlot;
	int currentShieldSlot;
	int currentLightSlot;
	boolean gotLantertn; //comprobacion

	//OBJECT ON MAP
	String mapObjectNames[][];
	int mapObjectWorldX[][];
	int mapObjectWorldY[][];
	String mapObjectLootNames[][];
	boolean mapObjectOpened[][];


	//PLAYER POSITION
	int playerWorldX;
	int playerWorldY;
	int currentMap;
	String direction;

	//NPC  
	public String[][] npcNames = new String[20][20];
	public int[][] npcWorldX = new int[20][20];
	public int[][] npcWorldY = new int[20][20];
	public int[][] npcCurrentMap = new int[20][20];
	public String[][] npcDirection = new String[20][20];
	public int[][] npcCurrentDialogueSet = new int[20][20];

	// MONSTER
	public String[][] monsterNames = new String[20][50];
	public int[][] monsterWorldX = new int[20][50];
	public int[][] monsterWorldY = new int[20][50];
	public int[][] monsterCurrentMap = new int[20][50];
	public String[][] monsterDirection = new String[20][50];

	//PROGRESS
	public boolean skeletonLordDefeated;
	public boolean treeLordDefeated;
}
