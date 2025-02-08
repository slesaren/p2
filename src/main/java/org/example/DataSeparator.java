
package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataSeparator {
    public static void main(String[] args) {
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
                    Main.processLine(line.trim(), integers, doubles, strings);
                }
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла: " + fileName);
                e.printStackTrace();
            }
        }

        scanner.close();
    }
}

