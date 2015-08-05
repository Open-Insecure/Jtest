package com.br.dong.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-08-01
 * Time: 11:22
 * java 分词工具
 */
public class TrieUtil {
    private Vertex root = new Vertex();

    protected class Vertex {
        protected int words; // 单词个数
        protected int prefixes; // 前缀个数
        protected HashMap<Integer, Vertex> edges; // 子节点

        Vertex() {
            this.words = 0;
            this.prefixes = 0;
            edges = new HashMap<Integer, TrieUtil.Vertex>();
        }
    }

    /**
     * 获取tire树中所有的词
     *
     * @return
     */

    public List<String> listAllWords() {

        List<String> words = new ArrayList<String>();
        HashMap<Integer, Vertex> edges = root.edges;

        for (Iterator iterator = edges.entrySet().iterator(); iterator.hasNext();) {
            Entry<Integer, Vertex> entry = (Entry<Integer, Vertex>) iterator.next();
            String word = "" + (char)('a'+entry.getKey());
            depthFirstSearchWords(words, entry.getValue(), word);
        }

        return words;
    }

    /**
     *
     * @param words
     * @param vertex
     * @param wordSegment
     */

    private void depthFirstSearchWords(List words, Vertex vertex,
                                       String wordSegment) {
        if (vertex.words != 0) {
            words.add(wordSegment);
        }
        for (Iterator iterator = vertex.edges.entrySet().iterator(); iterator.hasNext();) {
            Entry<Integer, Vertex> entry = (Entry<Integer, Vertex>) iterator.next();
            String word = "" + (char)('a'+entry.getKey());
            depthFirstSearchWords(words, entry.getValue(), word);
        }
    }

    /**
     * 计算指定前缀单词的个数
     *
     * @param prefix
     * @return
     */
    public int countPrefixes(String prefix) {
        return countPrefixes(root, prefix);
    }

    private int countPrefixes(Vertex vertex, String prefixSegment) {
        if (prefixSegment.length() == 0) { // reach the last character of the
            // word
            return vertex.prefixes;
        }

        char c = prefixSegment.charAt(0);
        Integer index = c - 'a';
        if (!vertex.edges.containsKey(index)) { // the word does NOT exist
            return 0;
        } else {
            return countPrefixes(vertex.edges.get(index),
                    prefixSegment.substring(1));

        }

    }

    /**
     * 计算完全匹配单词的个数
     *
     * @param word
     * @return
     */
    public int countWords(String word) {
        return countWords(root, word);
    }

    private int countWords(Vertex vertex, String wordSegment) {
        if (wordSegment.length() == 0) { // reach the last character of the word
            return vertex.words;
        }

        char c = wordSegment.charAt(0);
        int index = c - 'a';
        if (!vertex.edges.containsKey(index)) { // the word does NOT exist
            return 0;
        } else {
            return countWords(vertex.edges.get(index), wordSegment.substring(1));

        }

    }

    /**
     * 向tire树添加一个词
     *
     * @param word
     *
     */

    public void addWord(String word) {
        addWord(root, word);
    }

    /**
     * Add the word from the specified vertex.
     *
     * @param vertex
     *            The specified vertex.
     * @param word
     *            The word to be added.
     */

    private void addWord(Vertex vertex, String word) {
        if (word.length() == 0) { // if all characters of the word has been
            // added
            vertex.words++;
        } else {
            vertex.prefixes++;
            char c = word.charAt(0);
            c = Character.toLowerCase(c);
            int index = c - 'a';
            if (!vertex.edges.containsKey(index)) { // if the edge does NOT exist
                vertex.edges.put(index,new Vertex());
            }

            addWord(vertex.edges.get(index), word.substring(1)); // go the the next
            // character
        }
    }

    public String replaceAll(String str){
        char[] w = str.toCharArray();
        Vertex vertex = root;
        String ret = "";
        String s = "";
        for (int i = 0; i < w.length; i++) {
            char c = w[i];
            c = Character.toLowerCase(c);
            int index = c - 'a';
            if (!vertex.edges.containsKey(index)){// 如果没有子节点
                if (vertex.words != 0)// 如果是一个单词，则返回
                {
                    ret += "*"+c;
                    return ret + replaceAll(str.substring(i+1));
                }
                else
                {
                    ret += s+c;
                    return ret + replaceAll(str.substring(i+1));
                }
            }
            else
            {
                if (vertex.words != 0)
                    ret += s;
                s += c;
                vertex = vertex.edges.get(index);
            }
        }
        return "*";
    }

    /**
     * 返回指定字段前缀匹配最长的单词。
     *
     * @param word
     * @return
     */
    public String getMaxMatchWord(String word) {
        String s = "";
        String temp = "";// 记录最近一次匹配最长的单词
        char[] w = word.toCharArray();
        Vertex vertex = root;
        for (int i = 0; i < w.length; i++) {
            char c = w[i];
            c = Character.toLowerCase(c);
            int index = c - 'a';
            if (!vertex.edges.containsKey(index)){// 如果没有子节点
                if (vertex.words != 0)// 如果是一个单词，则返回
                    return s;
                else
                    // 如果不是一个单词则返回null
                    return null;
            } else {
                if (vertex.words != 0)
                    temp = s;
                s += c;
                vertex = vertex.edges.get(index);
            }
        }
        // trie中存在比指定单词更长（包含指定词）的单词
        if (vertex.words == 0)//
            return temp;
        return s;
    }

    public static void main(String args[]) // Just used for test
    {
        TrieUtil trie = new TrieUtil();
        trie.addWord("我草");
        trie.addWord("我日");
        trie.addWord("操你妈");
        trie.addWord("江泽民");
        trie.addWord("胡温");
        trie.addWord("狗屎");

        String ret = trie.replaceAll("从我草了你妈逼的江泽民");
        System.out.println(ret);
    }
}
