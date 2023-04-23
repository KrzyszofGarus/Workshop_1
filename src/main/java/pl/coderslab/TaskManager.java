package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.*;
import java.lang.*;
import java.util.*;


public class TaskManager {
    static String[][] tasks;
    static String fileName = "tasks.csv";

    public static void main(String[] args){
        tasks = readData(fileName);
        showOptions();
        Scanner scan = new Scanner(System.in);

        while (scan.hasNext()) {
            String input = scan.nextLine();
            switch (input) {
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask(tasks);
                    break;
                case "list":
                    showTasks(tasks);
                    break;
                case "exit":
                    System.out.println(ConsoleColors.RED + "Bye, bye.");
                    saveData(tasks, fileName);
                    System.exit(0);
                default:
                    System.out.println("Please select a correct option");
            }
            showOptions();
        }
    }
    // wyświetlanie dostępnych opcji programu
    public static void showOptions(){
        System.out.printf(ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + "Please select an option: ");
        System.out.println(ConsoleColors.RESET + "add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");
    }
    // wczytywanie danych z pliku
    public static String[][] readData(String fileName) {
        String[][] tab = null;
        File file = new File(fileName);
        Path path = Paths.get(fileName);
        try {
            int lineCount = (int) Files.lines(path).count();
            Scanner scan = new Scanner(file);
            tab = new String[lineCount][3];
            for (int i = 0; i < lineCount; i++) {
                String line = scan.nextLine();
                String[] parts = line.split(", ");
                for (int j = 0; j < 3; j++){
                    tab[i][j] = parts[j];
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Brak pliku.");
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return tab;
        }

    // zapisywanie danych do pliku
    public static void saveData(String [][] tab, String fileName) {
        try (PrintWriter printWriter = new PrintWriter(fileName)) {
            for (int i = 0; i < tab.length; i++){
                printWriter.println(StringUtils.join(tab[i], ", "));
                }
            }
        catch(FileNotFoundException exception){
            exception.printStackTrace();
        }
    }
    // wyświetlanie zadań
    public static void showTasks(String[][] tab){
        for (int i = 0; i < tasks.length; i++){
            System.out.printf(i + " :");
            for (int j = 0; j < 3; j++){
                System.out.printf(" " + tab[i][j]);
                if (j == 2){
                    System.out.println();
                }
            }
        }
    }
    // dodawanie zadania
    public static void addTask(){
        Scanner input = new Scanner(System.in);
        tasks = Arrays.copyOf(tasks, tasks.length +1);
        tasks[tasks.length - 1] = new String[3];
        System.out.println("Please add task description");
        tasks[tasks.length - 1][0] = input.nextLine();
        System.out.println("Please add task due date");
        tasks[tasks.length - 1][1] = input.nextLine();
        System.out.println("Is your task important: true/false");
        tasks[tasks.length - 1][2] = input.nextLine();
    }
    // usuwanie zadania ze sprawdzeniem poprawności numeru zadania do usunięcia
    public static void removeTask(String[][] tab){
        Scanner input = new Scanner(System.in);
        System.out.println("Please select task number to remove.");
        try {
            int numberToRemove = input.nextInt();
            if ((numberToRemove > tab.length - 1) || (numberToRemove < 0)) {
                System.out.println("Number out of bounds 0 to " + (tasks.length - 1));
            } else {
                tasks = ArrayUtils.remove(tab, numberToRemove);
                System.out.println("Task was succesfully removed");
            }
        } catch (InputMismatchException exception){
            System.out.println("Input is not a valid number");
        }
    }

}



