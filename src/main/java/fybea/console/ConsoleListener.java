package fybea.console;

import fybea.service.AccountService;
import fybea.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Objects;
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
        System.out.println("Enter command: ");
        while (true) {
            String command = sc.nextLine();
            if (Objects.equals(command, "USER_CREATE")) {
                System.out.println("Enter login:");
                userService.createUser(sc.nextLine());
            }
            if (Objects.equals(command, "SHOW_ALL_USERS")) {
                userService.showAllUsers();
            }
            if (Objects.equals(command, "ACCOUNT_CREATE")) {
                System.out.println("Enter user id: ");
                accountService.createAccount(Integer.parseInt(sc.nextLine()));
            }
            if (Objects.equals(command, "ACCOUNT_DEPOSIT")) {
                System.out.println("Enter account id");
                int id = Integer.parseInt(sc.nextLine());
                System.out.println("Enter amount: ");
                int amount = Integer.parseInt(sc.nextLine());
                accountService.accountDeposit(id, amount);
            }
            if (Objects.equals(command, "ACCOUNT_WITHDRAW")) {
                System.out.println("Enter account id");
                int id = Integer.parseInt(sc.nextLine());
                System.out.println("Enter amount: ");
                int amount = Integer.parseInt(sc.nextLine());
                accountService.accountWithdraw(id, amount);
            }
            if (Objects.equals(command, "ACCOUNT_TRANSFER")) {
                System.out.println("Enter source account id");
                int sourceId = Integer.parseInt(sc.nextLine());
                System.out.println("Enter target account id: ");
                int targetId = Integer.parseInt(sc.nextLine());
                System.out.println("Enter amount: ");
                int amount = Integer.parseInt(sc.nextLine());
                accountService.accountTransfer(sourceId, targetId, amount);
            }
            if (Objects.equals(command, "ACCOUNT_MAP")) {
                accountService.printAccount();
            }
            if (Objects.equals(command, "ACCOUNT_CLOSE")) {
                System.out.println("Enter account id: ");
                int id = Integer.parseInt(sc.nextLine());
                accountService.accountClose(id);
            }
            if (Objects.equals(command, "EXIT")) {
                break;
            }
        }
    }
}
