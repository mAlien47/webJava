package hr.java.web.teskera.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Wallet {

    private List<Expense> expenseList = new ArrayList<Expense>();

    public void addExpense(Expense expense){
        expenseList.add(expense);
    }

    public BigDecimal getTotal() {
        return expenseList.stream()
                .map(expense -> expense.getAmount())
                .reduce((expense, expense2) -> expense.add(expense2))
                .get();
    }
}
