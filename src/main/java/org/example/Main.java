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
            if (!parentDir.mkdirs()) {
                System.out.println("Не удалось создать директорию: " + parentDir);
                return;
            }
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

    public static void printShortStatistics(List<Integer> integers, List<Double> doubles, List<String> strings) {
        System.out.println("Краткая статистика:");
        System.out.println("Целые числа: " + integers.size() + " элементов");
        System.out.println("Дробные числа: " + doubles.size() + " элементов");
        System.out.println("Строки: " + strings.size() + " элементов");
    }

    public static void printFullStatistics(List<Integer> integers, List<Double> doubles, List<String> strings) {
        System.out.println("Полная статистика:");

        if (!integers.isEmpty()) {
            int minInt = integers.stream().min(Integer::compare).orElse(0);
            int maxInt = integers.stream().max(Integer::compare).orElse(0);
            int sumInt = integers.stream().mapToInt(Integer::intValue).sum();
            double avgInt = integers.stream().mapToInt(Integer::intValue).average().orElse(0);

            System.out.println("Целые числа:");
            System.out.println("  Количество: " + integers.size());
            System.out.println("  Минимальное: " + minInt);
            System.out.println("  Максимальное: " + maxInt);
            System.out.println("  Сумма: " + sumInt);
            System.out.println("  Среднее: " + avgInt);
        } else {
            System.out.println("Целые числа: нет данных");
        }

        if (!doubles.isEmpty()) {
            double minDouble = doubles.stream().min(Double::compare).orElse(0.0);
            double maxDouble = doubles.stream().max(Double::compare).orElse(0.0);
            double sumDouble = doubles.stream().mapToDouble(Double::doubleValue).sum();
            double avgDouble = doubles.stream().mapToDouble(Double::doubleValue).average().orElse(0);

            System.out.println("Дробные числа:");
            System.out.println("  Количество: " + doubles.size());
            System.out.println("  Минимальное: " + minDouble);
            System.out.println("  Максимальное: " + maxDouble);
            System.out.println("  Сумма: " + sumDouble);
            System.out.println("  Среднее: " + avgDouble);
        } else {
            System.out.println("Дробные числа: нет данных");
        }

        if (!strings.isEmpty()) {
            int minLength = strings.stream().mapToInt(String::length).min().orElse(0);
            int maxLength = strings.stream().mapToInt(String::length).max().orElse(0);

            System.out.println("Строки:");
            System.out.println("  Количество: " + strings.size());
            System.out.println("  Самая короткая строка: " + minLength + " символов");
            System.out.println("  Самая длинная строка: " + maxLength + " символов");
        } else {
            System.out.println("Строки: нет данных");
        }
    }

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Ошибка настройки кодировки вывода: " + e.getMessage());
        }


        String outputPath = "";
        String prefix = "";
        boolean shortStats = false;
        boolean fullStats = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-o") && i + 1 < args.length) {
                outputPath = args[i + 1];
                i++;
            } else if (args[i].equals("-p") && i + 1 < args.length) {
                prefix = args[i + 1];
                i++;
            } else if (args[i].equals("-s")) {
                shortStats = true;
            } else if (args[i].equals("-f")) {
                fullStats = true;
            } else {
                System.out.println("Неизвестный аргумент: " + args[i]);
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
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден: " + fileName);
            } catch (IOException e) {
                System.out.println("Ошибка при чтении файла: " + fileName);
                e.printStackTrace();
            }
        }

        String integersFile = outputPath + File.separator + prefix + "integers.txt";
        String doublesFile = outputPath + File.separator + prefix + "floats.txt";
        String stringsFile = outputPath + File.separator + prefix + "strings.txt";

        writeToFile(integersFile, integers);
        writeToFile(doublesFile, doubles);
        writeToFile(stringsFile, strings);

        if (shortStats) {
            printShortStatistics(integers, doubles, strings);
        } else if (fullStats) {
            printFullStatistics(integers, doubles, strings);
        }

        scanner.close();
    }
}










