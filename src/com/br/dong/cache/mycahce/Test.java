package com.br.dong.cache.mycahce;

public class Test {

	public static void main(String[] args) { 
		System.out.println(CacheManagerm.getSimpleFlag("alksd")); 
		 CacheManagerm.putCache("abc", new Cache()); 
		 CacheManagerm.putCache("def", new Cache()); 
		 CacheManagerm.putCache("ccc", new Cache()); 
		 CacheManagerm.clearOnly(""); 
		 Cache c = new Cache();
		 for (int i = 0; i < 10; i++) {
		 CacheManagerm.putCache("xxxxxxx" + i, c);
		 } 
		 CacheManagerm.putCache("aaaaaaaa", c); 
		 CacheManagerm.putCache("abchcy;alskd", c); 
		 CacheManagerm.putCache("cccccccc", c); 
		 CacheManagerm.putCache("abcoqiwhcy", c); 
		 System.out.println("删除前的大小：" + CacheManagerm.getCacheSize());
         System.out.println(CacheManagerm.getCacheAllkey());
		 CacheManagerm.clearAll("aaaa");
        CacheManagerm.clearOnly("xxxxxxx8");
		 System.out.println("删除后的大小：" + CacheManagerm.getCacheSize());
        System.out.println( CacheManagerm.getCacheAllkey() );



    }
}
