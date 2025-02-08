package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;


public class Main {
    public static void processLine(String line, List<Integer> integers, List<Double> doubles, List<String> strings) {
        if (line == null || line.isEmpty()) return;

        try {
            integers.add(Integer.parseInt(line));
        } catch (NumberFormatException e1) {
            try {
                doubles.add(Double.parseDouble(line));
            } catch (NumberFormatException e2) {
                strings.add(line);
            }
        }
    }

    public static <T> void writeToFile(String fileName, List<T> data) {
        if (fileName == null || data == null || data.isEmpty()) {
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (T item : data) {
                bw.write(item.toString());
                bw.newLine();
            }
            System.out.println("Данные записаны в файл: " + fileName);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + fileName);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try {
            OutputStream out = System.out;
            PrintStream ps = new PrintStream(out, true, StandardCharsets.UTF_8);
            System.setOut(ps);
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<Integer> integers = new ArrayList<>();
        List<Double> doubles = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имена файлов для обработки (пустая строка для завершения):");

        while (true) {
            String fileName = scanner.nextLine().trim();
            if (fileName.isEmpty()) {
                break;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    processLine(line.trim(), integers, doubles, strings);
                }
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла: " + fileName);
                e.printStackTrace();
            }
        }


        writeToFile("integers.txt", integers);
        writeToFile("floats.txt", doubles);
        writeToFile("strings.txt", strings);

        scanner.close();
    }
}










