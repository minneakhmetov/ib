package com.razzzil.lab5;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Lab5Application {

    private static final double P = 0.00001;
    private static final int V = 10 * 1;
    private static final int T = 1 * 30;
    private static final int S =  (int) (V * T / P) + 1;
    private static final char[] RUSSIAN_ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'};
    private static final char[] SPECIFIC_SYMBOLS = {'!', '\"', '#', '$', '%', '&', '(', ')', '*', '+', ',', '-', '.', '/', '~', '`', '@', '"', '№', '^', '=', '<', '>', '\\', '|'};
    private static final char[] BOTH = ArrayUtils.addAll(RUSSIAN_ALPHABET, SPECIFIC_SYMBOLS);
    private static final int A = a();
    private static final int L = l();

    public static void main(String[] args) {
        one();
        System.out.println("--------------------------------");
        two();
    }

    public static void one(){
        printAll();
        String password = generatePassword();
        System.out.println("Generated password: " +  password);
        double days = countBruteForce(password);
        days /= (double) V;
        System.out.println("Count brute force in days: " + days);
    }

    public static void two(){
        StringBuilder builder = new StringBuilder();
        List<String> words = Lists.newArrayList("Sony", "Hewlett", "Packard");
        char ch = words.get(0).charAt(words.get(0).length() -2);
        builder.append(ch == 'z' ? 'a' : (char) (ch + 1));
        ch = words.get(1).charAt(words.get(1).length() - 1);
        builder.append(ch == 'z' ? 'a' : (char) (ch - 1));
        ch = words.get(2).length() % 2 != 0 ? (char) (words.get(2).charAt(words.get(2).length() / 2 + 1) + 3) : (char) (words.get(2).charAt(words.get(2).length() / 2) - 1);
        builder.append(ch == 'z' ? 'a' : (char) (ch - 1));
        int sum = words.get(0).length() + words.get(1).length() + 2;
        builder.append( sum > 26 ? (char) ('a' + sum % 26 + 1) : (char) ('a' + sum + 1));
        System.out.println(builder.toString());
    }

    private static int l(){
        int i = 0;
        while (Math.pow(A, ++i) < S) {}
        return --i;
    }

    private static int a(){
        return BOTH.length;
    }

    private static void printAll(){
        System.out.println("P = " + P);
        System.out.println("V = " + V);
        System.out.println("T = " + T);
        System.out.println("A = " + A);
        System.out.println("S = " + S);
        System.out.println("L = " + L);
    }

    private static String generatePassword(){
        Random random = new Random(System.currentTimeMillis());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < L; i++)
            builder.append(BOTH[random.nextInt(A)]);
        return builder.toString();
    }

    private static int countBruteForce(String pass){
        char[] passwordCandidate = new char[L];
        char[] passwordChars = pass.toCharArray();
        return countBruteForce(0, passwordCandidate, passwordChars, 0).getCount();
    }

    @Data
    @AllArgsConstructor
    private static class Counter {
        boolean isFound;
        int count;
    }

    private static Counter countBruteForce(int step, char[] passwordCandidate, char[] passwordChars, int count){
        if(step == L - 1){
            for (char c : BOTH) {
                passwordCandidate[step] = c;
                if (Arrays.equals(passwordCandidate, passwordChars))
                    return new Counter(true, ++count);
                else count++;
            }
            return new Counter(false, count);
        } else {
            for (char c : BOTH){
                passwordCandidate[step] = c;
                int localStep = step + 1;
                Counter counter = countBruteForce(localStep, passwordCandidate, passwordChars, count);
                count += counter.getCount();
                if (counter.isFound())
                    return new Counter(true, count);
            }
        }
        return new Counter(false, count);
    }
}
