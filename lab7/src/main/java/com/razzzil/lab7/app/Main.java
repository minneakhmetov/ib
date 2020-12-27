package com.razzzil.lab7.app;

import javax.crypto.SecretKey;
import java.io.*;
import java.sql.SQLOutput;
import java.util.*;

public class Main {

    static File englishText = new File("C:\\Users\\razil\\Desktop\\Git\\ib\\lab7\\src\\main\\resources\\Английский текст.txt");
    static File russianText = new File("C:\\Users\\razil\\Desktop\\Git\\ib\\lab7\\src\\main\\resources\\Русский текст.txt");
    static File cipherText6 = new File("C:\\Users\\razil\\Desktop\\Git\\ib\\lab7\\src\\main\\resources\\6-1.txt");
    static File cipherText14 = new File("C:\\Users\\razil\\Desktop\\Git\\ib\\lab7\\src\\main\\resources\\14-1.txt");

    public static void main(String[] args) throws IOException {
        decipher();
//        for (int i = 1; i < 34; i++){
//            experiment(i);
//            System.out.print('\n');
//        }
    }

    public static void experiment(int sdvig) throws IOException {
        List<Character> characters = readFile(cipherText6, "Cp1251");
        for (Character c : characters) {
            if ('А' <= c && c <= 'я') {
                System.out.print((char) (c + sdvig));
            } else System.out.print(c);
        }
    }

    private static void decipher() throws IOException {
        Map<Character, Integer> rusTextCount = frequency(russianText, "UTF-8");
        Map<Character, Integer> engTextCount = frequency(englishText, "UTF-8");
        Map<Character, Integer> cipher6Count = frequency(cipherText6, "Cp1251");
        Map<Character, Integer> cipher14Count = frequency(cipherText14, "Cp1251");

        Map<Character, Character> replace = new HashMap<Character, Character>();
        List<Character> keys = sort(rusTextCount, true);

        //System.out.println(keys);
        List<Character> ciphers = sort(cipher6Count, false);
        List<Character> characters = readFile(cipherText6, "Cp1251");
        Map<Character, Character> map = map(ciphers, keys);
        for (int i = 0; i < characters.size(); i++) {
            Character c = map.get(characters.get(i));
            if (c != null)
                System.out.print(c);
            else System.out.print(characters.get(i));
        }

//        for (int i = 0; i < Math.max(keys.size(), ciphers.size()); i++){
//            Character key = null, cipher = null;
//            try {
//                key = keys.get(i);
//            } catch (Exception ignore) {}
//            try {
//                cipher = ciphers.get(i);
//            } catch (Exception ignore) {}
//            replace.put(cipher, key);
//        }
//
//        replaceInText(cipherText14, replace);
//        System.out.println("-------------------------------");
//        printKey(replace);
    }

    private static Map<Character, Character> map(List<Character> cipher, List<Character> replace) {
        Map<Character, Character> map = new HashMap<Character, Character>();
        for (int i = 0; i < cipher.size(); i++) {
            try {
                map.put(cipher.get(i), replace.get(i));
            } catch (IndexOutOfBoundsException ignore) {
            }
        }
        return map;
    }

    private static void print(Map<Character, Integer> map) {
        for (Character character : map.keySet()) {
            //System.out.println(character + "," + map.get(character) + ",");
            System.out.println("Буква " + character + " встречается " + map.get(character) + " раз");
        }
    }

    private static void printKey(Map<Character, Character> map) {
        for (Character character : map.keySet()) {
            System.out.println("Буква " + character + " переходит в -> " + map.get(character));
        }
    }

    private static void replaceInText(File file, Map<Character, Character> map) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        int s;
        while ((s = bufferedReader.read()) != -1) {
            System.out.print(map.get((char) s));
        }
    }

    private static Map<Character, Integer> frequency(File file, String encoding) throws IOException {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
        int s;
        while ((s = bufferedReader.read()) != -1) {
            char key = (char) s;
            if ('А' <= key && key <= 'я')
                if (!map.containsKey(key)) {
                    map.put(key, 1);
                } else {
                    map.put(key, map.get(key) + 1);
                }
        }
        return map;
    }


    private static List<Character> readFile(File file, String encoding) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
        int s;
        List<Character> characters = new ArrayList<Character>();
        while ((s = bufferedReader.read()) != -1) {
            characters.add((char) s);
        }
        return characters;
    }

    private static List<Character> sort(Map<Character, Integer> map, boolean reverse) {
        List<Character> characters = new ArrayList<Character>();
        while (map.keySet().size() != 0) {
            int max = 0;
            int min = 99999999;
            Character result = null;
            for (Character character : map.keySet()) {
                if (!(character.equals('\n') || character.equals('\r')))
                    if (reverse) {
                        if (min > map.get(character)) {
                            min = map.get(character);
                        }
                    } else {
                        if (max < map.get(character)) {
                            max = map.get(character);
                        }
                    }
                result = character;
            }
            map.remove(result);
            characters.add(result);
        }
        return characters;
    }


}
