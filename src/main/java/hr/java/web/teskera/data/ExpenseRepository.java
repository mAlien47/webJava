package hr.java.web.teskera.data;

import hr.java.web.teskera.models.Expense;

import java.util.List;

public interface ExpenseRepository {

    List<Expense> findAll();

    List<Expense> findAllByWalletId(Long id);

    Expense findOne(Long id);

    Expense save(Expense expense, Long walletID);

    void removeExpensesFromWallet(Long walletID);
}
