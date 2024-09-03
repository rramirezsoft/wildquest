package com.rramirezsoft.wildquest.main;

import com.rramirezsoft.wildquest.entity.npc.NPC_1;
import com.rramirezsoft.wildquest.entity.npc.NPC_2;
import com.rramirezsoft.wildquest.entity.npc.NPC_3;
import com.rramirezsoft.wildquest.entity.npc.NPC_4;
import com.rramirezsoft.wildquest.entity.npc.NPC_BigRock;
import com.rramirezsoft.wildquest.entity.npc.NPC_Miner;
import com.rramirezsoft.wildquest.entity.npc.NPC_OldMan;
import com.rramirezsoft.wildquest.entity.npc.merchant.NPC_MerchantHealth;
import com.rramirezsoft.wildquest.entity.npc.merchant.NPC_MerchantItems;
import com.rramirezsoft.wildquest.entity.npc.merchant.NPC_MerchantWeapon;
import com.rramirezsoft.wildquest.monster.MON_Bat;
import com.rramirezsoft.wildquest.monster.MON_ElectTree;
import com.rramirezsoft.wildquest.monster.MON_MushroomBlue;
import com.rramirezsoft.wildquest.monster.MON_MushroomRed;
import com.rramirezsoft.wildquest.monster.MON_Orc;
import com.rramirezsoft.wildquest.monster.MON_RedSlime;
import com.rramirezsoft.wildquest.monster.MON_SkeletonLord;
import com.rramirezsoft.wildquest.monster.MON_TreeLord;
import com.rramirezsoft.wildquest.object.OBJ_Carrot;
import com.rramirezsoft.wildquest.object.OBJ_Chest;
import com.rramirezsoft.wildquest.object.OBJ_Chilli;
import com.rramirezsoft.wildquest.object.OBJ_Chocolate;
import com.rramirezsoft.wildquest.object.OBJ_Door_Iron;
import com.rramirezsoft.wildquest.object.OBJ_Lantern;
import com.rramirezsoft.wildquest.object.OBJ_Leaf;
import com.rramirezsoft.wildquest.object.OBJ_MedKit;
import com.rramirezsoft.wildquest.object.OBJ_OldLantern;
import com.rramirezsoft.wildquest.object.OBJ_Pickaxe;
import com.rramirezsoft.wildquest.object.OBJ_Pill;
import com.rramirezsoft.wildquest.object.OBJ_WaterBottle;
import com.rramirezsoft.wildquest.structure.STRUCT_Cottage;
import com.rramirezsoft.wildquest.structure.STRUCT_Cottage2;
import com.rramirezsoft.wildquest.structure.STRUCT_FallenTrunk;
import com.rramirezsoft.wildquest.structure.STRUCT_Fountain;
import com.rramirezsoft.wildquest.structure.STRUCT_House1;
import com.rramirezsoft.wildquest.structure.STRUCT_Sign;
import com.rramirezsoft.wildquest.structure.STRUCT_StallHealth;
import com.rramirezsoft.wildquest.structure.STRUCT_StallWeapon;
import com.rramirezsoft.wildquest.structure.STRUCT_StreetLight;
import com.rramirezsoft.wildquest.structure.STRUCT_TownHall;
import com.rramirezsoft.wildquest.structure.STRUCT_Tree;
import com.rramirezsoft.wildquest.structure.STRUCT_WildRock;
import com.rramirezsoft.wildquest.tile.interactive.IT_DestructibleRock;
import com.rramirezsoft.wildquest.tile.interactive.IT_DestructibleWall;
import com.rramirezsoft.wildquest.tile.interactive.IT_DryTree;
import com.rramirezsoft.wildquest.tile.interactive.IT_MetalPlate;

public class AssetSetter {

	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}

	// Creacion y colocación en el mapa de elementos
	public void setObject() {

		//BOSQUE PRINCIPAL 
		int mapNum = 0;
		int i = 0;

		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_OldLantern(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 20;
		gp.obj[mapNum][i].worldY = gp.tileSize * 42;
		i++;

		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_WaterBottle(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 35;
		gp.obj[mapNum][i].worldY = gp.tileSize * 15;
		i++;

		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Carrot(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 10;
		gp.obj[mapNum][i].worldY = gp.tileSize * 11;
		i++;

		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Chilli(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 32;
		gp.obj[mapNum][i].worldY = gp.tileSize * 40;
		i++;

		//DUNGEON 1
		mapNum = 2;
		i = 0;

		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Pickaxe(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 40;
		gp.obj[mapNum][i].worldY = gp.tileSize * 41;
		i++;

		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_MedKit(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 13;
		gp.obj[mapNum][i].worldY = gp.tileSize * 16;
		i++;

		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Chocolate(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 26;
		gp.obj[mapNum][i].worldY = gp.tileSize * 34;
		i++;

		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Chilli(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 27;
		gp.obj[mapNum][i].worldY = gp.tileSize * 15;
		i++;

		gp.obj[mapNum][i] = new OBJ_Door_Iron(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 18;
		gp.obj[mapNum][i].worldY = gp.tileSize * 23;
		i++;

		//DUNGEON 2
		mapNum = 3;
		i = 0;

		gp.obj[mapNum][i] = new OBJ_Door_Iron(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 25;
		gp.obj[mapNum][i].worldY = gp.tileSize * 15;
		i++;

		//POBLADO
		mapNum = 4;
		i = 0;

		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Pickaxe(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 12;
		gp.obj[mapNum][i].worldY = gp.tileSize * 7;
		i++;

		//LABERINTO
		mapNum = 5;
		i = 0;

		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Leaf(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 30;
		gp.obj[mapNum][i].worldY = gp.tileSize * 30;
		i++;

		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Lantern(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 26;
		gp.obj[mapNum][i].worldY = gp.tileSize * 18;
		i++;

		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new OBJ_Pill(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 39;
		gp.obj[mapNum][i].worldY = gp.tileSize * 12;
		i++;

		mapNum = 6;
		i = 0;

		mapNum = 7;
		i = 0;

	}
	public void setStructure() {

		//BOSQUE PRINCIPAL
		int mapNum = 0;
		int i = 0;

		gp.struct[mapNum][i] = new STRUCT_FallenTrunk(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 16;
		gp.struct[mapNum][i].worldY = gp.tileSize * 33;
		i++;

		gp.struct[mapNum][i] = new STRUCT_FallenTrunk(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 33;
		gp.struct[mapNum][i].worldY = gp.tileSize * 17;
		i++;

		gp.struct[mapNum][i] = new STRUCT_Sign(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 39;
		gp.struct[mapNum][i].worldY = gp.tileSize * 10 + 24;
		i++;

		//DUNGEON 1
		mapNum = 2;
		i = 0;


		//DUNGEON 2
		mapNum = 3;
		i = 0;


		//POBLADO
		mapNum = 4;
		i = 0;

		gp.struct[mapNum][i] = new STRUCT_Cottage(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 31 + 8;
		gp.struct[mapNum][i].worldY = gp.tileSize * 30;
		i++;

		gp.struct[mapNum][i] = new STRUCT_Cottage2(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 16;
		gp.struct[mapNum][i].worldY = gp.tileSize * 30;
		i++;

		gp.struct[mapNum][i] = new STRUCT_TownHall(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 23 + 5;
		gp.struct[mapNum][i].worldY = gp.tileSize * 28;
		i++;

		gp.struct[mapNum][i] = new STRUCT_StreetLight(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 14;
		gp.struct[mapNum][i].worldY = gp.tileSize * 38;
		i++;

		gp.struct[mapNum][i] = new STRUCT_StreetLight(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 22;
		gp.struct[mapNum][i].worldY = gp.tileSize * 38;
		i++;

		gp.struct[mapNum][i] = new STRUCT_StreetLight(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 27 - 24;
		gp.struct[mapNum][i].worldY = gp.tileSize * 38;
		i++;

		gp.struct[mapNum][i] = new STRUCT_StreetLight(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 35 - 24;
		gp.struct[mapNum][i].worldY = gp.tileSize * 38;
		i++;

		gp.struct[mapNum][i] = new STRUCT_StreetLight(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 21 - 24;
		gp.struct[mapNum][i].worldY = gp.tileSize * 31;
		i++;

		gp.struct[mapNum][i] = new STRUCT_StreetLight(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 28;
		gp.struct[mapNum][i].worldY = gp.tileSize * 31;
		i++;

		gp.struct[mapNum][i] = new STRUCT_StallHealth(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 16 - 6;
		gp.struct[mapNum][i].worldY = gp.tileSize * 37;
		i++;

		gp.struct[mapNum][i] = new STRUCT_StallWeapon(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 32;
		gp.struct[mapNum][i].worldY = gp.tileSize * 37;
		i++;

		gp.struct[mapNum][i] = new STRUCT_Fountain(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 19;
		gp.struct[mapNum][i].worldY = gp.tileSize * 28;
		i++;

		gp.struct[mapNum][i] = new STRUCT_Fountain(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 29 - 25;
		gp.struct[mapNum][i].worldY = gp.tileSize * 28;
		i++;

		gp.struct[mapNum][i] = new STRUCT_House1(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 20+12;
		gp.struct[mapNum][i].worldY = gp.tileSize * 17+24;
		i++;

		//LABERINTO
		mapNum = 5;
		i = 0;


		mapNum = 6;
		i = 0;

		gp.struct[mapNum][i] = new STRUCT_FallenTrunk(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 12;
		gp.struct[mapNum][i].worldY = gp.tileSize * 30;
		i++;

		gp.struct[mapNum][i] = new STRUCT_FallenTrunk(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 30;
		gp.struct[mapNum][i].worldY = gp.tileSize * 9;
		i++;

		gp.struct[mapNum][i] = new STRUCT_FallenTrunk(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 15;
		gp.struct[mapNum][i].worldY = gp.tileSize * 17;
		i++;

		gp.struct[mapNum][i] = new STRUCT_WildRock(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 12;
		gp.struct[mapNum][i].worldY = gp.tileSize * 39;
		i++;

		gp.struct[mapNum][i] = new STRUCT_WildRock(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 17;
		gp.struct[mapNum][i].worldY = gp.tileSize * 39;
		i++;

		gp.struct[mapNum][i] = new STRUCT_WildRock(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 18;
		gp.struct[mapNum][i].worldY = gp.tileSize * 27;
		i++;

		gp.struct[mapNum][i] = new STRUCT_WildRock(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 16;
		gp.struct[mapNum][i].worldY = gp.tileSize * 6;
		i++;

		gp.struct[mapNum][i] = new STRUCT_WildRock(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 29;
		gp.struct[mapNum][i].worldY = gp.tileSize * 27;
		i++;

		gp.struct[mapNum][i] = new STRUCT_Tree(gp);
		gp.struct[mapNum][i].worldX = gp.tileSize * 13;
		gp.struct[mapNum][i].worldY = gp.tileSize * 34;
		i++;

		mapNum = 7;
		i = 0;
	}
	public void setNPC() {

		int mapNum = 0;
		int i = 0;

		gp.npc[mapNum][i] = new NPC_OldMan(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 15;
		gp.npc[mapNum][i].worldY = gp.tileSize * 33;
		i++;

		mapNum = 1;
		i = 0;

		gp.npc[mapNum][i] = new NPC_MerchantWeapon(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 3;
		gp.npc[mapNum][i].worldY = gp.tileSize * 24;
		i++;

		gp.npc[mapNum][i] = new NPC_MerchantItems(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 19;
		gp.npc[mapNum][i].worldY = gp.tileSize * 24;
		i++;

		gp.npc[mapNum][i] = new NPC_MerchantHealth(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 9;
		gp.npc[mapNum][i].worldY = gp.tileSize * 4;
		i++;

		gp.npc[mapNum][i] = new NPC_2(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 24;
		gp.npc[mapNum][i].worldY = gp.tileSize * 26;
		i++;

		mapNum = 2;
		i = 0;

		gp.npc[mapNum][i] = new NPC_BigRock(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 20;
		gp.npc[mapNum][i].worldY = gp.tileSize * 25;
		i++;

		gp.npc[mapNum][i] = new NPC_BigRock(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 11;
		gp.npc[mapNum][i].worldY = gp.tileSize * 18;
		i++;

		gp.npc[mapNum][i] = new NPC_BigRock(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 23;
		gp.npc[mapNum][i].worldY = gp.tileSize * 14;
		i++;

		mapNum = 4;
		i = 0;

		gp.npc[mapNum][i] = new NPC_1(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 24;
		gp.npc[mapNum][i].worldY = gp.tileSize * 42;
		i++;

		gp.npc[mapNum][i] = new NPC_3(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 36;
		gp.npc[mapNum][i].worldY = gp.tileSize * 33;
		i++;

		gp.npc[mapNum][i] = new NPC_4(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 34;
		gp.npc[mapNum][i].worldY = gp.tileSize * 11;
		i++;

		gp.npc[mapNum][i] = new NPC_Miner(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 28;
		gp.npc[mapNum][i].worldY = (gp.tileSize * 5)+32;
		i++;

		mapNum = 8;
		i = 0;

	}
	public void setMonster() {

		int mapNum = 0;
		int i = 0;

		gp.monster[mapNum][i] = new MON_Orc(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 14;
		gp.monster[mapNum][i].worldY = gp.tileSize * 7;
		i++;

		mapNum = 2;
		i = 0;

		gp.monster[mapNum][i] = new MON_Bat(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 34;
		gp.monster[mapNum][i].worldY = gp.tileSize * 39;
		i++;

		gp.monster[mapNum][i] = new MON_Bat(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 36;
		gp.monster[mapNum][i].worldY = gp.tileSize * 25;
		i++;

		gp.monster[mapNum][i] = new MON_Bat(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 39;
		gp.monster[mapNum][i].worldY = gp.tileSize * 26;
		i++;

		gp.monster[mapNum][i] = new MON_Bat(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 28;
		gp.monster[mapNum][i].worldY = gp.tileSize * 11;
		i++;

		gp.monster[mapNum][i] = new MON_Bat(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 10;
		gp.monster[mapNum][i].worldY = gp.tileSize * 19;
		i++;

		mapNum = 3;
		i = 0;

		gp.monster[mapNum][i] = new MON_SkeletonLord(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 23;
		gp.monster[mapNum][i].worldY = gp.tileSize * 16;
		i++;


		mapNum = 4;
		i = 0;

		mapNum = 5;
		i = 0;

		gp.monster[mapNum][i] = new MON_RedSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 32;
		gp.monster[mapNum][i].worldY = gp.tileSize * 32;
		i++;

		gp.monster[mapNum][i] = new MON_RedSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 38;
		gp.monster[mapNum][i].worldY = gp.tileSize * 34;
		i++;

		gp.monster[mapNum][i] = new MON_RedSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 21;
		gp.monster[mapNum][i].worldY = gp.tileSize * 26;
		i++;

		gp.monster[mapNum][i] = new MON_RedSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 32;
		gp.monster[mapNum][i].worldY = gp.tileSize * 13;
		i++;

		gp.monster[mapNum][i] = new MON_RedSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 39;
		gp.monster[mapNum][i].worldY = gp.tileSize * 14;
		i++;

		gp.monster[mapNum][i] = new MON_RedSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 18;
		gp.monster[mapNum][i].worldY = gp.tileSize * 32;
		i++;

		gp.monster[mapNum][i] = new MON_RedSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 19;
		gp.monster[mapNum][i].worldY = gp.tileSize * 16;
		i++;

		gp.monster[mapNum][i] = new MON_RedSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 10;
		gp.monster[mapNum][i].worldY = gp.tileSize * 31;
		i++;

		gp.monster[mapNum][i] = new MON_RedSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 10;
		gp.monster[mapNum][i].worldY = gp.tileSize * 15;
		i++;

		gp.monster[mapNum][i] = new MON_RedSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 25;
		gp.monster[mapNum][i].worldY = gp.tileSize * 18;
		i++;

		mapNum = 6;
		i = 0;

		gp.monster[mapNum][i] = new MON_MushroomRed(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 11;
		gp.monster[mapNum][i].worldY = gp.tileSize * 31;
		i++;

		gp.monster[mapNum][i] = new MON_MushroomRed(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 19;
		gp.monster[mapNum][i].worldY = gp.tileSize * 30;
		i++;

		gp.monster[mapNum][i] = new MON_MushroomRed(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 12;
		gp.monster[mapNum][i].worldY = gp.tileSize * 17;
		i++;

		gp.monster[mapNum][i] = new MON_MushroomRed(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 16;
		gp.monster[mapNum][i].worldY = gp.tileSize * 8;
		i++;

		gp.monster[mapNum][i] = new MON_MushroomRed(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 34;
		gp.monster[mapNum][i].worldY = gp.tileSize * 13;
		i++;

		gp.monster[mapNum][i] = new MON_MushroomRed(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 32;
		gp.monster[mapNum][i].worldY = gp.tileSize * 39;
		i++;

		gp.monster[mapNum][i] = new MON_MushroomBlue(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 33;
		gp.monster[mapNum][i].worldY = gp.tileSize * 40;
		i++;

		gp.monster[mapNum][i] = new MON_Orc(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 15;
		gp.monster[mapNum][i].worldY = gp.tileSize * 40;
		i++;

		mapNum = 7;
		i = 0;

		gp.monster[mapNum][i] = new MON_TreeLord(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 22;
		gp.monster[mapNum][i].worldY = gp.tileSize * 23;
		i++;

		gp.monster[mapNum][i] = new MON_ElectTree(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 15 - 24;
		gp.monster[mapNum][i].worldY = gp.tileSize * 21;
		i++;

		gp.monster[mapNum][i] = new MON_ElectTree(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize * 30;
		gp.monster[mapNum][i].worldY = gp.tileSize * 21;
		i++;

	}
	public void setInteractiveTile() {

		int mapNum = 0;
		int i = 0;

		gp.iTile[mapNum][i] = new IT_DryTree(gp, 11, 8);
		i++;

		mapNum = 2;
		i = 0;

		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18, 30);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 17, 31);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 17, 32);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 17, 34);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18, 34);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18, 33);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 10, 22);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 10, 24);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 38, 18);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 38, 19);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 38, 20);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 38, 21);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18, 13);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18, 14);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 22, 28);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 30, 28);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 32, 28);
		i++;

		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 20, 22);
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 8, 17);
		i++;
		gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 39, 31);
		i++;

		mapNum = 4;
		i = 0;

		gp.iTile[mapNum][i] = new IT_DestructibleRock(gp, 15, 6);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleRock(gp, 16, 6);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleRock(gp, 17, 6);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleRock(gp, 18, 6);
		i++;
		gp.iTile[mapNum][i] = new IT_DestructibleRock(gp, 19, 6);
		i++;

		mapNum = 5;
		i = 0;

		gp.iTile[mapNum][i] = new IT_DryTree(gp, 28, 43);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 17, 35);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 10, 41);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 27, 25);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 22, 21);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 13, 16);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 22, 9);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 37, 7);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 26, 15);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 24, 15);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 24, 12);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 19, 10);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 32, 7);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 12, 14);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 10, 9);
		i++;

	}

}
