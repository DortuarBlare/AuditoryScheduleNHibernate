import dataAccessObjects.AuditoryDAO;
import dataAccessObjects.GroupDAO;
import dataAccessObjects.ScheduleDAO;
import entities.*;
import services.AuditoryService;
import services.GroupService;
import services.ScheduleService;

import java.util.Scanner;

public class Main {
    public static void printMainMenu() {
        System.out.println("\nВведите один из предложенных пунктов:");
        System.out.println("1) Посмотреть");
        System.out.println("2) Добавить");
        System.out.println("4) Удалить");
        System.out.println("0) Выход");
    }

    public static void printShowMenu() {
        System.out.println("Введите один из предложенных пунктов:");
        System.out.println("1) Посмотреть всё расписание");
        System.out.println("2) Посмотреть все аудитории");
        System.out.println("3) Посмотреть все группы");
        System.out.println("0) Выход в основное меню");
    }

    public static void printAddMenu() {
        System.out.println("Введите один из предложенных пунктов:");
        System.out.println("1) Добавить расписание");
        System.out.println("2) Добавить аудиторию");
        System.out.println("3) Добавить группу");
        System.out.println("0) Выход в основное меню");
    }

    public static void printDeleteMenu() {
        System.out.println("Введите один из предложенных пунктов:");
        System.out.println("1) Удалить расписание");
        System.out.println("2) Удалить аудиторию");
        System.out.println("3) Удалить группу");
        System.out.println("0) Выход в основное меню");
    }

    public static void main(String[] args) {
        ScheduleService scheduleService = new ScheduleService();
        AuditoryService auditoryService = new AuditoryService();
        GroupService groupService = new GroupService();
        Scanner scanner = new Scanner(System.in);
        int interfaceChoiceInt = -1;
        String interfaceChoiceString;

        while (interfaceChoiceInt != 0) {
            printMainMenu();
            interfaceChoiceInt = scanner.nextInt();

            switch (interfaceChoiceInt) {
                case 1 -> {
                    printShowMenu();
                    interfaceChoiceInt = scanner.nextInt();
                    // Просмотр
                    switch (interfaceChoiceInt) {
                        case 1 -> scheduleService.showAll();
                        case 2 -> auditoryService.showAll();
                        case 3 -> groupService.showAll();
                        default -> {}
                    }
                }
                case 2 -> {
                    printAddMenu();
                    interfaceChoiceInt = scanner.nextInt();
                    // Добавление
                    switch (interfaceChoiceInt) {
                        case 1 -> {
                            System.out.print("Введите аудиторию: ");
                            scanner.nextLine();
                            String auditory = scanner.nextLine();

                            System.out.print("Введите группу: ");
                            String group = scanner.nextLine();

                            System.out.print("Введите недели: ");
                            String weeks = scanner.nextLine();

                            System.out.print("Введите день недели: ");
                            String day = scanner.nextLine();

                            System.out.print("Введите начальное время: ");
                            String startTime = scanner.nextLine();

                            System.out.print("Введите конечное время: ");
                            String endTime = scanner.nextLine();

                            try (Scanner weeksScanner = new Scanner(weeks)) {
                                while (weeksScanner.hasNextInt()) {
                                    int week = weeksScanner.nextInt();
                                    scheduleService.save(new Schedule(
                                            new Auditory(auditory),
                                            new Group(group),
                                            week,
                                            new Day(day),
                                            new Time(startTime, endTime))
                                    );
                                }
                            }
                        }
                        case 2 -> {
                            System.out.print("Введите аудиторию: ");
                            scanner.nextLine();
                            interfaceChoiceString = scanner.nextLine();
                            auditoryService.save(new Auditory(interfaceChoiceString));
                        }
                        case 3 -> {
                            System.out.print("Введите группу: ");
                            scanner.nextLine();
                            interfaceChoiceString = scanner.nextLine();
                            groupService.save(new Group(interfaceChoiceString));
                        }
                    }
                }
                case 4 -> {
                    printDeleteMenu();
                    interfaceChoiceInt = scanner.nextInt();
                    // Удаление
                    switch (interfaceChoiceInt) {
                        case 1 -> {
                            System.out.print("Введите порядковый номер расписания для удаления: ");
                            interfaceChoiceInt = scanner.nextInt();
                            Schedule scheduleToDelete = new Schedule();
                            scheduleToDelete.setId(interfaceChoiceInt);
                            scheduleService.delete(scheduleToDelete);
                        }
                        case 2 -> {
                            System.out.print("Введите аудиторию для удаления: ");
                            scanner.nextLine();
                            interfaceChoiceString = scanner.nextLine();
                            auditoryService.delete(new Auditory(interfaceChoiceString));
                        }
                        case 3 -> {
                            System.out.print("Введите группу для удаления: ");
                            scanner.nextLine();
                            interfaceChoiceString = scanner.nextLine();
                            groupService.delete(new Group(interfaceChoiceString));
                        }
                        default -> {}
                    }
                }
                default -> {}
            }
        }
    }
}