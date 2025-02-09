package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        File file = new File(fileName);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
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
        String outputPath = "";
        String prefix = "";

        // Парсинг аргументов командной строки
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-o") && i + 1 < args.length) {
                outputPath = args[i + 1];
                i++;
            } else if (args[i].equals("-p") && i + 1 < args.length) {
                prefix = args[i + 1];
                i++;
            }
        }


        if (outputPath.isEmpty()) {
            outputPath = ".";
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

        String integersFile = outputPath + File.separator + prefix + "integers.txt";
        String doublesFile = outputPath + File.separator + prefix + "floats.txt";
        String stringsFile = outputPath + File.separator + prefix + "strings.txt";

        writeToFile(integersFile, integers);
        writeToFile(doublesFile, doubles);
        writeToFile(stringsFile, strings);

        scanner.close();
    }
}











