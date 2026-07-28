package fybea.service;

import fybea.config.AccountProperties;
import fybea.model.Account;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AccountService {
    private final Map<Integer, Account> accountMap;
    private final AccountProperties accountProperties;

    public AccountService(Map<Integer, Account> accountMap, AccountProperties accountProperties) {
        this.accountMap = accountMap;
        this.accountProperties = accountProperties;
    }

    private Account createAccount(int userId) {
        return new Account(1, userId, accountProperties.getMoneyAmount());
    }

    private void accountDeposit(int id, int moneyAmount) {
        Account account = accountMap.get(id);
        if (account == null) {
            throw new RuntimeException("нет счета у указанного пользователя"); // временная ошибка(переделать)
        } else {
            account.setMoneyAmount(moneyAmount);
        }
    }

    private void accountWithdraw(int id, int moneyAmount) {
        Account account = accountMap.get(id);
        if (account == null) {
            throw new RuntimeException("нет счета у указанного пользователя"); // временная ошибка(переделать)
        } else {
            account.setMoneyAmount(moneyAmount);
        }
    }

    private void accountTransfer(int fromId, int toId, int moneyAmount) {
        Account fromAccount = accountMap.get(fromId);
        Account toAccount = accountMap.get(toId);
        if (fromAccount.getUserId() == toAccount.getUserId()) {
            toAccount.setMoneyAmount(toAccount.getMoneyAmount() + moneyAmount);
            fromAccount.setMoneyAmount(fromAccount.getMoneyAmount() - moneyAmount);
        } else {
            toAccount.setMoneyAmount((int) (moneyAmount * accountProperties.getCommission()));
            fromAccount.setMoneyAmount(fromAccount.getMoneyAmount() - moneyAmount);
        }
    }

    private void akk(int id) {
        Account account = accountMap.get(id);
        if (accountMap.size() == 1) {
            throw new RuntimeException("Нельзя закрыть счет, у вас только один счет");
        } else {

        }
    }
}
