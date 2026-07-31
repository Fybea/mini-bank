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
        if (fromAccount.getId() == toAccount.getId()) {
            toAccount.setMoneyAmount(toAccount.getMoneyAmount() + moneyAmount);
            fromAccount.setMoneyAmount(fromAccount.getMoneyAmount() - moneyAmount);
        } else {
            toAccount.setMoneyAmount((int) (moneyAmount * accountProperties.getCommission()));
            fromAccount.setMoneyAmount(fromAccount.getMoneyAmount() - moneyAmount);
        }
    }

    public void akk(int id) {
        Account account = accountMap.get(id);
        if (accountMap.size() == 1) {
            throw new RuntimeException("Нельзя закрыть счет, у вас только один счет");
        } else {

        }
    }
}
