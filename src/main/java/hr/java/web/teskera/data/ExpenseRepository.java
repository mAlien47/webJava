package hr.java.web.teskera.data;

import hr.java.web.teskera.models.Expense;

public interface ExpenseRepository {

    Iterable<Expense> findAll();

    Iterable<Expense> findAllByWalletId(Long id);

    Expense findOne(Long id);

    Expense save(Expense expense, Long walletID);

    void removeExpensesFromWallet(Long walletID);
}
