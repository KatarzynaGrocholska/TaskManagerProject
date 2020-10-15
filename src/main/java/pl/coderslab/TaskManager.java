package pl.coderslab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.ArrayUtils;


public class TaskManager {
    static final String FILE_NAME = "tasks.csv";
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String[][] tasks;
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String RED = "\033[0;31m";//RED


    public static void main(String[] args) {
        printOptions(OPTIONS);
loadDataToTab(FILE_NAME);



        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            switch (input) { case "exit":
                    saveFile(FILE_NAME, tasks);
                    System.out.println(RED + "Bye, bye."+ RESET);
                   System.exit(0);
                    break;
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask(tasks,getTheNumber());
                    System.out.println("Value was successfully deleted.");
                    saveFile(FILE_NAME, tasks);
                    break;
                case "list":
                    printTasks(tasks);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
            printOptions(OPTIONS);
        }

        }

        public static void printOptions (String [] tab){

            System.out.println(BLUE + "Please select an option: " + RESET);
            for (String OPTIONS : OPTIONS) {
                System.out.println(OPTIONS);

            }
        }

        public static String[][] loadDataToTab (String FILE_NAME){

            Path dir = Paths.get(FILE_NAME);
            if (!Files.exists(dir)) {
                System.out.println("File not exist.");
                System.exit(0);
            }

            try {
                List<String> strings = Files.readAllLines(dir);
                tasks = new String[strings.size()][strings.get(0).split(",").length];

                for (int i = 0; i < strings.size(); i++) {
                    String[] split = strings.get(i).split(",");
                    for (int j = 0; j < split.length; j++) {
                        tasks[i][j] = split[j];
                    }
                   }

            } catch (IOException e) {
                e.printStackTrace();

            }
            return tasks;
        }
        public static void printTasks (String[][]tasks){


            for (int i = 0; i < tasks.length; i++) {

                System.out.print(i + " : ");

                for (int j = 0; j < tasks[i].length; j++) {
                    System.out.print(tasks[i][j] + " ");
                }
                System.out.println();
            }
        }


    private static void addTask () {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please add task description");
            String description = scanner.nextLine();
            System.out.println("Please add task due date");
            String dueDate = scanner.nextLine();
            System.out.println("Is your task is important: true/false");
            String isImportant = scanner.nextLine();

            tasks = Arrays.copyOf(tasks, tasks.length + 1);
            tasks[tasks.length - 1] = new String[3];
            tasks[tasks.length - 1][0] = description;
            tasks[tasks.length - 1][1] = dueDate;
            tasks[tasks.length - 1][2] = isImportant;

        }
        public static boolean isNumberGreaterEqualZero (String input){
            if (NumberUtils.isParsable(input)) {
                return Integer.parseInt(input) >= 0;
            }
            return false;
        }
        public static int getTheNumber () {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please select number to remove.");
            String n = scanner.nextLine();
            while (!isNumberGreaterEqualZero(n)) {
                System.out.println("Incorrect argument passed. Please give number greater or equal 0");
                scanner.nextLine();
            }
            return Integer.parseInt(n);
        }
        private static void removeTask (String[][]tasks,int n) {




                if (n < tasks.length) {
                    for (int i = 0; i < tasks.length; i++) {
                        if (tasks[i].equals(n)) {

                            try {
                                tasks = ArrayUtils.removeElement(tasks, n);


                            } catch (ArrayIndexOutOfBoundsException ex) {
                                System.out.println("Element not exist in file");
                            }
                        }

                    }
                }
            }



        public static void saveFile (String FILE_NAME, String[][]tasks){

            Path dir = Paths.get(FILE_NAME);
            String[] lines = new String[tasks.length];
            for (int i = 0; i < tasks.length; i++) {
                lines[i] = String.join(",", tasks[i]);
            }
            try {
                Files.write(dir, Arrays.asList(lines));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


    }
