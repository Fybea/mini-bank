package fybea.service;

import fybea.config.AccountProperties;
import fybea.model.Account;
import fybea.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class AccountService {
    private final Map<Integer, Account> accountMap;
    private final AccountProperties accountProperties;
    private final UserService userService;

    public AccountService(Map<Integer, Account> accountMap, AccountProperties accountProperties, UserService userService) {
        this.accountMap = accountMap;
        this.accountProperties = accountProperties;
        this.userService = userService;
    }

    public void createAccount(int userId) {
        User user = userService.showUser(userId);
        int accountId = ThreadLocalRandom.current().nextInt(1, 101);
        List<Account> accountList = user.getAccountList();
        Account account = new Account(accountId, userId, accountProperties.getMoneyAmount());
        accountList.add(account);
        accountMap.put(accountId, account);
        System.out.println(accountId);
    }

    public void printAccount() {
        for (Map.Entry<Integer, Account> entry : accountMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }


    public void accountDeposit(int id, int moneyAmount) {
        Account account = accountMap.get(id);
        if (account == null) {
            throw new RuntimeException("нет счета у указанного пользователя"); // временная ошибка(переделать)
        } else {
            int temp = account.getMoneyAmount();
            account.setMoneyAmount(moneyAmount + temp);
        }
    }

    public void accountWithdraw(int id, int moneyAmount) {
        Account account = accountMap.get(id);
        if (account == null) {
            throw new RuntimeException("нет счета у указанного пользователя"); // временная ошибка(переделать)
        } else {
            int temp = account.getMoneyAmount();
            account.setMoneyAmount(moneyAmount - temp);
        }
    }

    //НЕВЕРНАЯ ЛОГИКА, ПЕРЕДЕЛАТЬ
    public void accountTransfer(int fromId, int toId, int moneyAmount) {
        Account fromAccount = accountMap.get(fromId);
        Account toAccount = accountMap.get(toId);
        toAccount.setMoneyAmount((int) (toAccount.getMoneyAmount() + moneyAmount * (1 - accountProperties.getCommission())));
        fromAccount.setMoneyAmount(fromAccount.getMoneyAmount() - moneyAmount);
    }

    public void accountClose(int id) {
        for (Map.Entry<Integer, Account> entry : accountMap.entrySet()) {
            if (entry.getValue().getId() == id) {
                Account account = entry.getValue();
                User user = userService.showUser(account.getUserId());
                List<Account> accountList = user.getAccountList();
                if (accountList.size() > 1) {
                    int toRemove = 0;
                    for (int i = 0; i < accountList.size(); i++) {
                        if (accountList.get(i).getId() == id) {
                            toRemove = i;
                        }
                    }
                    int temp = account.getMoneyAmount();
                    account.setMoneyAmount(0);
                    accountList.remove(accountList.get(toRemove));
                    List<Account> sorted = accountList.stream().sorted().toList();
                    sorted.get(0).setMoneyAmount(temp + sorted.get(0).getMoneyAmount());
                    accountMap.remove(id);
                }
                System.out.println("Аккаунт успешно закрыт");
            }
        }
    }
}
