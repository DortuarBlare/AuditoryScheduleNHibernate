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
        System.out.println("\nВведите один из предложенных пунктов главного меню:");
        System.out.println("1) Посмотреть");
        System.out.println("2) Добавить");
        System.out.println("3) Редактировать");
        System.out.println("4) Удалить");
        System.out.println("5) Поиск свободной аудитории в заданные часы в течение всего семестра");
        System.out.println("6) Поиск свободной аудитории на заданное число часов в указанную неделю");
        System.out.println("0) Выход");
    }

    public static void printShowMenu() {
        System.out.println("Введите один из предложенных пунктов просмотра:");
        System.out.println("1) Посмотреть всё расписание");
        System.out.println("2) Посмотреть все аудитории");
        System.out.println("3) Посмотреть все группы");
        System.out.println("0) Выход в основное меню");
    }

    public static void printAddMenu() {
        System.out.println("Введите один из предложенных пунктов добавления:");
        System.out.println("1) Добавить расписание");
        System.out.println("2) Добавить аудиторию");
        System.out.println("3) Добавить группу");
        System.out.println("0) Выход в основное меню");
    }

    public static void printEditMenu() {
        System.out.println("Введите один из предложенных пунктов редактирования:");
        System.out.println("1) Редактировать расписание целиком");
        System.out.println("2) Редактировать аудиторию в расписании");
        System.out.println("3) Редактировать группу в расписании");
        System.out.println("4) Редактировать неделю в расписании");
        System.out.println("5) Редактировать день недели в расписании");
        System.out.println("6) Редактировать время в расписании");
        System.out.println("7) Редактировать аудиторию");
        System.out.println("8) Редактировать группу");
        System.out.println("0) Выход в основное меню");
    }

    public static void printDeleteMenu() {
        System.out.println("Введите один из предложенных пунктов удаления:");
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

        while (interfaceChoiceInt != 0) {
            printMainMenu();
            interfaceChoiceInt = scanner.nextInt();

            switch (interfaceChoiceInt) {
                // Просмотр
                case 1 -> {
                    printShowMenu();
                    interfaceChoiceInt = scanner.nextInt();
                    switch (interfaceChoiceInt) {
                        // Расписание
                        case 1 -> scheduleService.showAll();
                        // Аудитории
                        case 2 -> auditoryService.showAll();
                        // Группы
                        case 3 -> groupService.showAll();
                        case 0 -> interfaceChoiceInt = -1;
                        default -> System.out.println("Промахнулись пунктом меню :)");
                    }
                }
                // Добавление
                case 2 -> {
                    printAddMenu();
                    interfaceChoiceInt = scanner.nextInt();
                    switch (interfaceChoiceInt) {
                        // Расписание
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
                                    scheduleService.saveSchedule(auditory, group, week, day, startTime, endTime);
                                }
                            }
                        }
                        // Аудитории
                        case 2 -> {
                            System.out.print("Введите аудиторию: ");
                            scanner.nextLine();
                            String auditoryToAdd = scanner.nextLine();
                            auditoryService.saveAuditory(auditoryToAdd);
                        }
                        // Группы
                        case 3 -> {
                            System.out.print("Введите группу: ");
                            scanner.nextLine();
                            String groupToAdd = scanner.nextLine();
                            groupService.saveGroup(groupToAdd);
                        }
                        case 0 -> interfaceChoiceInt = -1;
                        default -> System.out.println("Промахнулись пунктом меню :)");
                    }
                }
                // Редактирование
                case 3 -> {
                    printEditMenu();
                    interfaceChoiceInt = scanner.nextInt();
                    switch (interfaceChoiceInt) {
                        case 1 -> {
                            System.out.print("Введите номер расписания для редактирования: ");
                            int numberOfScheduleToUpdate = scanner.nextInt();

                            System.out.print("Введите новую аудиторию: ");
                            scanner.nextLine();
                            String auditory = scanner.nextLine();

                            System.out.print("Введите новую группу: ");
                            String group = scanner.nextLine();

                            System.out.print("Введите новую неделю: ");
                            int week = scanner.nextInt();

                            System.out.print("Введите новый день недели: ");
                            scanner.nextLine();
                            String day = scanner.nextLine();

                            System.out.print("Введите новое начальное время: ");
                            String startTime = scanner.nextLine();

                            System.out.print("Введите новое конечное время: ");
                            String endTime = scanner.nextLine();
                            scheduleService.updateSchedule(
                                    numberOfScheduleToUpdate, auditory,
                                    group, week, day, startTime, endTime
                            );
                        }
                        // Аудитории в расписании
                        case 2 -> {
                            System.out.print("Введите номер расписания для редактирования: ");
                            int numberOfScheduleToUpdate = scanner.nextInt();
                            System.out.print("Введите новую аудиторию: ");
                            scanner.nextLine();
                            String newAuditory = scanner.nextLine();
                            scheduleService.updateScheduleAuditory(numberOfScheduleToUpdate, newAuditory);
                        }
                        // Группы в расписании
                        case 3 -> {
                            System.out.print("Введите номер расписания для редактирования: ");
                            int numberOfScheduleToUpdate = scanner.nextInt();
                            System.out.print("Введите новую группу: ");
                            scanner.nextLine();
                            String newGroup = scanner.nextLine();
                            scheduleService.updateScheduleGroup(numberOfScheduleToUpdate, newGroup);
                        }
                        // Недели в расписании
                        case 4 -> {
                            System.out.print("Введите номер расписания для редактирования: ");
                            int numberOfScheduleToUpdate = scanner.nextInt();
                            System.out.print("Введите новую неделю: ");
                            int newWeek = scanner.nextInt();
                            scheduleService.updateScheduleWeek(numberOfScheduleToUpdate, newWeek);
                        }
                        // Дня недели в расписании
                        case 5 -> {
                            System.out.print("Введите номер расписания для редактирования: ");
                            int numberOfScheduleToUpdate = scanner.nextInt();
                            System.out.print("Введите новый день недели: ");
                            scanner.nextLine();
                            String newDay = scanner.nextLine();
                            scheduleService.updateScheduleDay(numberOfScheduleToUpdate, newDay);
                        }
                        // Времени в расписании
                        case 6 -> {
                            System.out.print("Введите номер расписания для редактирования: ");
                            int numberOfScheduleToUpdate = scanner.nextInt();
                            System.out.print("Введите новое начальное время: ");
                            scanner.nextLine();
                            String newStartTime = scanner.nextLine();
                            System.out.print("Введите новое конечное время: ");
                            String newEndTime = scanner.nextLine();
                            scheduleService.updateScheduleTime(numberOfScheduleToUpdate, newStartTime, newEndTime);
                        }
                        // Аудитории
                        case 7 -> {
                            System.out.print("Введите старую аудиторию: ");
                            scanner.nextLine();
                            String oldAuditory = scanner.nextLine();
                            System.out.print("Введите новую аудиторию: ");
                            String newAuditory = scanner.nextLine();
                            auditoryService.updateAuditory(newAuditory, oldAuditory);
                        }
                        // Группы
                        case 8 -> {
                            System.out.print("Введите старую группу: ");
                            scanner.nextLine();
                            String oldGroup = scanner.nextLine();
                            System.out.print("Введите новую группу: ");
                            String newGroup = scanner.nextLine();
                            groupService.updateGroup(newGroup, oldGroup);
                        }
                        case 0 -> interfaceChoiceInt = -1;
                        default -> System.out.println("Промахнулись пунктом меню :)");
                    }
                }
                // Удаление
                case 4 -> {
                    printDeleteMenu();
                    interfaceChoiceInt = scanner.nextInt();
                    switch (interfaceChoiceInt) {
                        case 1 -> {
                            System.out.print("Введите порядковый номер расписания для удаления: ");
                            int numberOfScheduleToDelete = scanner.nextInt();
                            scheduleService.deleteSchedule(numberOfScheduleToDelete);
                        }
                        case 2 -> {
                            System.out.print("Введите аудиторию для удаления: ");
                            scanner.nextLine();
                            String auditoryToDelete = scanner.nextLine();
                            auditoryService.deleteAuditory(auditoryToDelete);
                        }
                        case 3 -> {
                            System.out.print("Введите группу для удаления: ");
                            scanner.nextLine();
                            String groupToDelete = scanner.nextLine();
                            groupService.deleteGroup(groupToDelete);
                        }
                        case 0 -> interfaceChoiceInt = -1;
                        default -> System.out.println("Промахнулись пунктом меню :)");
                    }
                }
                // Поиск 1
                case 5 -> {
                    System.out.print("Введите начальное время: ");
                    scanner.nextLine();
                    String startTime = scanner.nextLine();
                    System.out.print("Введите конечное время: ");
                    String endTime = scanner.nextLine();
                    scheduleService.findByTime(startTime, endTime);
                }
                case 6 -> {
                    System.out.print("Введите количество часов: ");
                    int numberOfHours = scanner.nextInt();
                    System.out.print("Введите номер недели: ");
                    int week = scanner.nextInt();
                    scheduleService.findByNumberOfHours(numberOfHours, week);
                }
                case 0 -> System.out.println("До свидания, пользователь!");
                default -> System.out.println("Промахнулись пунктом меню :)");
            }
        }
    }
}