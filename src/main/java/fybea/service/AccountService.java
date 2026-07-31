package fybea.service;

import fybea.config.AccountProperties;
import fybea.model.Account;
import fybea.model.User;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class AccountService {
    private final Map<Integer, Account> accountMap;
    private final AccountProperties accountProperties;
    private final UserService userService;
    private int id;

    public AccountService(Map<Integer, Account> accountMap, AccountProperties accountProperties, UserService userService) {
        this.accountMap = accountMap;
        this.accountProperties = accountProperties;
        this.userService = userService;
    }

    public void createAccount(int userId) {
        User user = userService.showUser(userId);
        id++;
        List<Account> accountList = user.getAccountList();
        Account account = new Account(id, userId, accountProperties.getMoneyAmount());
        accountList.add(account);
        accountMap.put(id, account);
        System.out.println("Account created with id: " + id);
    }

    public void deposit(int id, int moneyAmount) {
        if (moneyAmount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than 0. Your amount is %s"
                    .formatted(moneyAmount));
        }
        Account account = accountMap.get(id);
        if (account == null) {
            throw new IllegalArgumentException("Account with id: %s, not exist".formatted(id)); // временная ошибка(переделать)
        } else {
            int temp = account.getMoneyAmount();
            account.setMoneyAmount(moneyAmount + temp);
            System.out.println("Account deposited with id: " + id + ", deposited money amount: " + moneyAmount);
        }
    }

    public void withdraw(int id, int moneyAmount) {
        if (moneyAmount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0. Your amount is %s."
                    .formatted(moneyAmount));
        }
        Account account = accountMap.get(id);
        if (account == null) {
            throw new IllegalArgumentException("Account with id: %s, not exist".formatted(id));
        } else {
            int temp = account.getMoneyAmount();
            account.setMoneyAmount(temp - moneyAmount);
            System.out.println("Account withdrawal with id: " + id + ", withdrawal money amount: " + moneyAmount);
        }
    }

    public void transfer(int fromId, int toId, int moneyAmount) {
        if (moneyAmount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0. Your amount is %s."
                    .formatted(moneyAmount));
        }
        Account fromAccount = accountMap.get(fromId);
        Account toAccount = accountMap.get(toId);
        if (moneyAmount > fromAccount.getMoneyAmount()) {
            throw new IllegalArgumentException(
                    "Not enough money to transfer account: id=%s, moneyAmount=%s, attemptedMoneyAmount=%s."
                            .formatted(
                                    fromId,
                                    moneyAmount,
                                    fromAccount.getMoneyAmount()));
        }
        if (fromAccount.getUserId() != toAccount.getUserId()) {
            toAccount.setMoneyAmount((int) (toAccount.getMoneyAmount() + moneyAmount * (1 - accountProperties.getCommission())));
            fromAccount.setMoneyAmount(fromAccount.getMoneyAmount() - moneyAmount);
        } else {
            toAccount.setMoneyAmount(moneyAmount + toAccount.getMoneyAmount());
            fromAccount.setMoneyAmount(fromAccount.getMoneyAmount() - moneyAmount);
        }
    }

    public void close(int accId) {
        if (accountMap.get(accId) == null) {
            throw new IllegalArgumentException(
                    "Account with id: %s, not exist"
                            .formatted(accId));
        }
        Iterator<Map.Entry<Integer, Account>> iterator =
                accountMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Account> entry = iterator.next();

            if (entry.getValue().getId() == accId) {
                Account account = entry.getValue();
                User user = userService.showUser(account.getUserId());
                List<Account> accountList = user.getAccountList();
                if (accountList.size() == 1) {
                    throw new IllegalArgumentException(
                            "You have only one account with id: id=%s, for close minimum 2"
                                    .formatted(accId));
                }
                if (accountList.size() > 1) {
                    int temp = account.getMoneyAmount();
                    account.setMoneyAmount(0);
                    accountList.remove(account);
                    List<Account> sorted = accountList.stream().sorted().toList();
                    sorted.get(0).setMoneyAmount(temp + sorted.get(0).getMoneyAmount());
                    iterator.remove();
                }
                System.out.println("Account successfully closed, money amount: " +
                        account.getMoneyAmount() +
                        " transferred to another account");
                break;
            }
        }
    }
}
