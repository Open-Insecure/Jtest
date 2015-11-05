package com.br.dong.random;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: hexor
 * Date: 2015-10-09
 * Time: 14:03
 */
public class RandomTest {
    public static void main(String[] args) {
        int max=20;
        int min=10;
        Random random = new Random();

        int s = random.nextInt(max)%(max-min+1) + min;
        System.out.println(s);
    }
}
