package com.br.dong.cache;

import com.whirlycott.cache.Cache;
import com.whirlycott.cache.CacheException;
import com.whirlycott.cache.CacheManager;

public class TestWhirlyCache {
	 public static void main(String[] args) throws CacheException {
			 Cache cache = CacheManager.getInstance().getCache("default");
         cache.store("key", new String("korpton"));
         cache.store("yahoo", "yahoo search");
         String s = (String) cache.retrieve("key");
         System.out.println(s);
	}
}
