package com.br.dong.jsoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DocBody {
	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.connect("http://www.baidu.com").get();
		String title = doc.title();
	}
}
