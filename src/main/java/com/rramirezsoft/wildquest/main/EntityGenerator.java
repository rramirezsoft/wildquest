package com.rramirezsoft.wildquest.main;

import com.rramirezsoft.wildquest.entity.Entity;
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
import com.rramirezsoft.wildquest.monster.MON_GreenSlime;
import com.rramirezsoft.wildquest.monster.MON_MushroomBlue;
import com.rramirezsoft.wildquest.monster.MON_MushroomRed;
import com.rramirezsoft.wildquest.monster.MON_Orc;
import com.rramirezsoft.wildquest.monster.MON_RedSlime;
import com.rramirezsoft.wildquest.monster.MON_SkeletonLord;
import com.rramirezsoft.wildquest.monster.MON_TreeLord;
import com.rramirezsoft.wildquest.object.OBJ_Arrow;
import com.rramirezsoft.wildquest.object.OBJ_Axe;
import com.rramirezsoft.wildquest.object.OBJ_Boots;
import com.rramirezsoft.wildquest.object.OBJ_Bow;
import com.rramirezsoft.wildquest.object.OBJ_BoxingGloves;
import com.rramirezsoft.wildquest.object.OBJ_Carrot;
import com.rramirezsoft.wildquest.object.OBJ_Chest;
import com.rramirezsoft.wildquest.object.OBJ_Chilli;
import com.rramirezsoft.wildquest.object.OBJ_Chocolate;
import com.rramirezsoft.wildquest.object.OBJ_Door;
import com.rramirezsoft.wildquest.object.OBJ_Door_Iron;
import com.rramirezsoft.wildquest.object.OBJ_Fireball;
import com.rramirezsoft.wildquest.object.OBJ_Heart;
import com.rramirezsoft.wildquest.object.OBJ_Key;
import com.rramirezsoft.wildquest.object.OBJ_Lantern;
import com.rramirezsoft.wildquest.object.OBJ_Leaf;
import com.rramirezsoft.wildquest.object.OBJ_ManaCrystal;
import com.rramirezsoft.wildquest.object.OBJ_Map;
import com.rramirezsoft.wildquest.object.OBJ_MedKit;
import com.rramirezsoft.wildquest.object.OBJ_Mjolnir;
import com.rramirezsoft.wildquest.object.OBJ_OldLantern;
import com.rramirezsoft.wildquest.object.OBJ_Pickaxe;
import com.rramirezsoft.wildquest.object.OBJ_Pill;
import com.rramirezsoft.wildquest.object.OBJ_Rock;
import com.rramirezsoft.wildquest.object.OBJ_Shield_Blue;
import com.rramirezsoft.wildquest.object.OBJ_Shield_Captain_America;
import com.rramirezsoft.wildquest.object.OBJ_Shield_Wood;
import com.rramirezsoft.wildquest.object.OBJ_Sword_Gold;
import com.rramirezsoft.wildquest.object.OBJ_Sword_Normal;
import com.rramirezsoft.wildquest.object.OBJ_Tent;
import com.rramirezsoft.wildquest.object.OBJ_WaterBottle;
import com.rramirezsoft.wildquest.object.OBJ_WildCoin;

public class EntityGenerator {

	GamePanel gp;

	public EntityGenerator(GamePanel gp) {
		this.gp = gp;
	}
	public Entity getObject(String itemName) {

		Entity obj = null;

		switch(itemName) {
		case OBJ_Arrow.objName: obj = new OBJ_Arrow(gp); break;
		case OBJ_Axe.objName: obj = new OBJ_Axe(gp); break;
		case OBJ_Boots.objName: obj = new OBJ_Boots(gp); break;
		case OBJ_Bow.objName: obj = new OBJ_Bow(gp); break;
		case OBJ_BoxingGloves.objName: obj = new OBJ_BoxingGloves(gp); break;
		case OBJ_Carrot.objName: obj = new OBJ_Carrot(gp); break;
		case OBJ_Chest.objName: obj = new OBJ_Chest(gp); break;
		case OBJ_Chilli.objName: obj = new OBJ_Chilli(gp); break;
		case OBJ_Chocolate.objName: obj = new OBJ_Chocolate(gp); break;
		case OBJ_WildCoin.objName: obj = new OBJ_WildCoin(gp); break;
		case OBJ_Door_Iron.objName: obj = new OBJ_Door_Iron(gp); break;
		case OBJ_Door.objName: obj = new OBJ_Door(gp); break;
		case OBJ_Fireball.objName: obj = new OBJ_Fireball(gp); break;
		case OBJ_Heart.objName: obj = new OBJ_Heart(gp); break;
		case OBJ_Key.objName: obj = new OBJ_Key(gp); break;
		case OBJ_Lantern.objName: obj = new OBJ_Lantern(gp); break;
		case OBJ_Leaf.objName: obj = new OBJ_Leaf(gp); break;
		case OBJ_ManaCrystal.objName: obj = new OBJ_ManaCrystal(gp); break;
		case OBJ_Map.objName: obj = new OBJ_Map(gp); break;
		case OBJ_MedKit.objName: obj = new OBJ_MedKit(gp); break;
		case OBJ_Mjolnir.objName: obj = new OBJ_Mjolnir(gp); break;
		case OBJ_OldLantern.objName: obj = new OBJ_OldLantern(gp); break;
		case OBJ_Pickaxe.objName: obj = new OBJ_Pickaxe(gp); break;
		case OBJ_Pill.objName: obj = new OBJ_Pill(gp); break;
		case OBJ_Rock.objName: obj = new OBJ_Rock(gp); break;
		case OBJ_Shield_Blue.objName: obj = new OBJ_Shield_Blue(gp); break;
		case OBJ_Shield_Wood.objName: obj = new OBJ_Shield_Wood(gp); break;
		case OBJ_Shield_Captain_America.objName: obj = new OBJ_Shield_Captain_America(gp); break;
		case OBJ_Sword_Normal.objName: obj = new OBJ_Sword_Normal(gp); break;
		case OBJ_Sword_Gold.objName: obj = new OBJ_Sword_Gold(gp); break;
		case OBJ_Tent.objName: obj = new OBJ_Tent(gp); break;
		case OBJ_WaterBottle.objName: obj = new OBJ_WaterBottle(gp); break;

		}
		return obj;
	}
	public Entity getNPC(String npcName) {

		Entity npc = null;

		switch(npcName) {
		case NPC_1.npcName: npc = new NPC_1(gp); break;
		case NPC_2.npcName: npc = new NPC_2(gp); break;
		case NPC_3.npcName: npc = new NPC_3(gp); break;
		case NPC_4.npcName: npc = new NPC_4(gp); break;
		case NPC_OldMan.npcName: npc = new NPC_OldMan(gp); break;
		case NPC_MerchantWeapon.npcName: npc = new NPC_MerchantWeapon(gp); break;
		case NPC_MerchantItems.npcName: npc = new NPC_MerchantItems(gp); break;
		case NPC_MerchantHealth.npcName: npc = new NPC_MerchantHealth(gp); break;
		case NPC_BigRock.npcName: npc = new NPC_BigRock(gp); break;
		case NPC_Miner.npcName: npc = new NPC_Miner(gp); break;

		}
		return npc;
	}
	public Entity getMonster(String monName) {

		Entity monster = null;

		switch(monName) {
		case MON_Bat.monName: monster = new MON_Bat(gp); break;
		case MON_ElectTree.monName: monster = new MON_ElectTree(gp); break;
		case MON_GreenSlime.monName: monster = new MON_GreenSlime(gp); break;
		case MON_MushroomBlue.monName: monster = new MON_MushroomBlue(gp); break;
		case MON_MushroomRed.monName: monster = new MON_MushroomRed(gp); break;
		case MON_Orc.monName: monster = new MON_Orc(gp); break;
		case MON_RedSlime.monName: monster = new MON_RedSlime(gp); break;
		case MON_SkeletonLord.monName: monster = new MON_SkeletonLord(gp); break;
		case MON_TreeLord.monName: monster = new MON_TreeLord(gp); break;

		}
		return monster;
	}
}
