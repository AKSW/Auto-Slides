package com.swt.aprt17.Auto_Slides.Sources.WikiPedia;

import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.TestCase;

public class TestExtractBattleMap extends TestCase {

	public void test1Case() {
		ExtractBattleMap ebm = new ExtractBattleMap("Battle_of_Hastings");
		System.out.println(ebm.getMap());
	}

	public void Cases() {
		ArrayList<String> battleName = new ArrayList<String>();
		
		battleName.addAll(Arrays.asList("Battle_of_Stalingrad", "Battle of Yongju", "Battle_of_Zhuolu",
				"Battle_of_Kujin", "Battle_of_Oosterweel", "Battle_of_the_Somme", "Battle_of_Orchomenus",
				"Battle_of_the_Boyne", "Battle_of_Inchon", "Battle_of_Britain"));

		for (String battle : battleName) {
			ExtractBattleMap ebm = new ExtractBattleMap(battle);
			System.out.println(ebm.getMap());
		}
	}
}
