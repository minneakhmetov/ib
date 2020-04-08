package com.razzzil.lab7.app;

import java.io.*;
import java.sql.SQLOutput;
import java.util.*;

public class Main {

    static File englishText = new File("C:\\Users\\razil\\Desktop\\Git\\ib\\lab7\\src\\main\\resources\\Английский текст.txt");
    static File russianText = new File("C:\\Users\\razil\\Desktop\\Git\\ib\\lab7\\src\\main\\resources\\Русский текст.txt");
    static File cipherText6 = new File("C:\\Users\\razil\\Desktop\\Git\\ib\\lab7\\src\\main\\resources\\6-1.txt");
    static File cipherText14 = new File("C:\\Users\\razil\\Desktop\\Git\\ib\\lab7\\src\\main\\resources\\14-1.txt");

    public static void main(String[] args) throws IOException {
        Map<Character, Integer> rusTextCount = frequency(russianText);
        Map<Character, Integer> engTextCount = frequency(englishText);
        Map<Character, Integer> cipher6Count = frequency(cipherText6);
        Map<Character, Integer> cipher14Count = frequency(cipherText14);

        Map<Character, Character> replace = new HashMap<Character, Character>();
        List<Character> keys = sort(engTextCount);
        List<Character> ciphers = sort(cipher14Count);
        for (int i = 0; i < Math.max(keys.size(), ciphers.size()); i++){
            Character key = null, cipher = null;
            try {
                key = keys.get(i);
            } catch (Exception ignore) {}
            try {
                cipher = ciphers.get(i);
            } catch (Exception ignore) {}
            replace.put(cipher, key);
        }

        replaceInText(cipherText14, replace);
        System.out.println("-------------------------------");
        printKey(replace);

    }

    private static void print(Map<Character, Integer> map){
        for(Character character : map.keySet()){
            //System.out.println(character + "," + map.get(character) + ",");
            System.out.println("Буква " + character + " встречается " + map.get(character) + " раз");
        }
    }

    private static void printKey(Map<Character, Character> map){
        for(Character character : map.keySet()){
            System.out.println("Буква " + character + " переходит в -> " + map.get(character));
        }
    }

    private static void replaceInText(File file, Map<Character, Character> map) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        int s;
        while ((s = bufferedReader.read()) != -1){
            System.out.print(map.get((char) s));
        }
    }

    private static Map<Character, Integer> frequency(File file) throws IOException {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        int s;
        while ((s = bufferedReader.read()) != -1){
            char key = (char) s;
            if (!map.containsKey(key)){
                map.put(key, 1);
            } else {
                map.put(key, map.get(key) + 1);
            }
        }
        return map;
    }

    private static List<Character> sort(Map<Character, Integer> map){
        List<Character> characters = new ArrayList<Character>();
        while (map.keySet().size() != 0) {
            int max = 0;
            Character result = null;
            for (Character character : map.keySet()) {
                if (max < map.get(character)) {
                    max = map.get(character);
                    result = character;
                }
            }
            map.remove(result);
            characters.add(result);
        }
        return characters;
    }

}
