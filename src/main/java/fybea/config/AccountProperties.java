package fybea.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {
    @Value("${account.default-amount")
    private int moneyAmount;
    @Value("${account.transfer-commission")
    private float commission;
}
