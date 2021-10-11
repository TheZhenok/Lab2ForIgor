package com.company;

import java.io.Console;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static int poolSeed[] = new int[3];
    public static int seed = 2;

    public static void getRandSeed(){
        for (int i = 0; i < 3; i++) {
            poolSeed[i] = (int)(Math.random()*32+1);
        }
        System.out.println("A = " + poolSeed[0]);
        System.out.println("C = " + poolSeed[1]);
        System.out.println("M = " + poolSeed[2]);
    }

    public static int getRand(int a, int c, int m) {
        seed = (a * seed + c) % m;
        return seed;
    }

    private static char vernam(char s, int key) {
        char res = (char) (s ^ key);
        return res;
    }

    public static int countPlus(int count){
        return count++;
    }

    public static void main(String[] args) {
        try(FileWriter writer = new FileWriter("notes2.txt", false)) {
            //Ввод сообщения
            System.out.println("Enter your message: ");
            Scanner scanner = new Scanner(System.in);
            if(scanner == null){
                System.out.println("Not have a message");
                return;
            }
            String message = scanner.nextLine();
            System.out.println("Your message " + message);
            int lengthKey = message.length();
            writer.write("Message: " + message + "\n");

            //Генерация ключа
            int count = 0;
            getRandSeed();
            System.out.println("-----------------");
            int[] randomSeedValue = new int[lengthKey];
            int[] testingRandomSeed = new int[15];
            for (int i = 0; i < lengthKey; i++) {
                randomSeedValue[i] = getRand(poolSeed[0], poolSeed[1], poolSeed[2]);
                if (i > 0) {
                    for (int j = 1; j < lengthKey; j++) {
                        if (randomSeedValue[i] == randomSeedValue[j - 1]) {
                            testingRandomSeed[countPlus(count)] = randomSeedValue[i];
                        }
                    }
                }
            }
            writer.write("Key: ");
            for (int i = 0; i < lengthKey; i++) {
                writer.write(randomSeedValue[i] + " ");
                System.out.println('\n');
            }

            //Шифровка и дешифровка
            char[] cryptMessage = new char[lengthKey];
            char[] unCryptMessage = new char[lengthKey];
            System.out.println("Encode: ");
            writer.write("\nEncode text: ");
            for (int i = 0; i < lengthKey; i++) {
                cryptMessage[i] = vernam(message.charAt(i), randomSeedValue[i]);
                writer.write(cryptMessage[i]);
                System.out.print(cryptMessage[i]);
            }
            writer.write("\nDecode text: ");
            System.out.println("\nDecode: ");
            for (int i = 0; i < lengthKey; i++) {
                unCryptMessage[i] = vernam(cryptMessage[i], randomSeedValue[i]);
                writer.write(unCryptMessage[i]);
                System.out.print(unCryptMessage[i]);
            }

        } catch (IOException e) {


        }

//        System.out.println("Testing:");
//        for (int i = 0; i < 15; i++) {
//            System.out.println(testingRandomSeed[i]);
//        }
    }
}
