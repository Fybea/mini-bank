package fybea.console;

import fybea.service.AccountService;
import fybea.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleListener {
    private final UserService userService;
    private final AccountService accountService;
    private final Scanner sc = new Scanner(System.in);

    public ConsoleListener(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    public void listen() {
        printCommands();

        System.out.println("Enter command: ");
        boolean exit = true;
        while (exit) {
            String command = sc.nextLine();
            switch (command) {
                case "USER_CREATE" -> {
                    System.out.println("Enter login:");
                    userService.createUser(sc.nextLine());
                }
                case "SHOW_ALL_USERS" -> userService.showAllUsers();
                case "ACCOUNT_CREATE" -> {
                    System.out.println("Enter user id: ");
                    accountService.createAccount(Integer.parseInt(sc.nextLine()));
                }
                case "ACCOUNT_DEPOSIT" -> {
                    System.out.println("Enter account id");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.println("Enter amount: ");
                    int amount = Integer.parseInt(sc.nextLine());
                    accountService.deposit(id, amount);
                }
                case "ACCOUNT_WITHDRAW" -> {
                    System.out.println("Enter account id");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.println("Enter amount: ");
                    int amount = Integer.parseInt(sc.nextLine());
                    accountService.withdraw(id, amount);
                }
                case "ACCOUNT_TRANSFER" -> {
                    System.out.println("Enter source account id");
                    int sourceId = Integer.parseInt(sc.nextLine());
                    System.out.println("Enter target account id: ");
                    int targetId = Integer.parseInt(sc.nextLine());
                    System.out.println("Enter amount: ");
                    int amount = Integer.parseInt(sc.nextLine());
                    accountService.transfer(sourceId, targetId, amount);
                }
                case "ACCOUNT_CLOSE" -> {
                    System.out.println("Enter account id: ");
                    int id = Integer.parseInt(sc.nextLine());
                    accountService.close(id);
                }
                case "EXIT" -> exit = false;
                default -> throw new IllegalStateException("Unexpected value: %s".formatted(command));
            }
        }
    }

    private void printCommands() {
        System.out.println("Commands:");
        System.out.println("USER_CREATE");
        System.out.println("SHOW_ALL_USERS");
        System.out.println("ACCOUNT_CREATE");
        System.out.println("ACCOUNT_DEPOSIT");
        System.out.println("ACCOUNT_WITHDRAW");
        System.out.println("ACCOUNT_TRANSFER");
        System.out.println("ACCOUNT_CLOSE");
        System.out.println("EXIT");
        System.out.println(" ");
    }
}
