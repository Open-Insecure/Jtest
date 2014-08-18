package com.br.dong.hashMap_hashTable;

import java.util.HashMap;
import java.util.Hashtable;

public class Dir {
	public static Hashtable HT=new Hashtable();
	public static HashMap HM=new HashMap();
	
	static {
		HT.put("key1", "ht_1");
		HT.put("key2", "ht_2");
		
		HM.put("key1", "hm_1");
		HM.put("key2", "hm_2");
	}
	
}
