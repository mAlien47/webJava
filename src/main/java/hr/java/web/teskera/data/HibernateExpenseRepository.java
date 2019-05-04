package hr.java.web.teskera.data;

import hr.java.web.teskera.models.Expense;
import hr.java.web.teskera.models.Wallet;
import org.hibernate.Session;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Primary
@Repository
@Transactional
public class HibernateExpenseRepository implements ExpenseRepository {

    //  you can also get Hibernate's session by calling getDelegate() method
    private final EntityManager em;

    public HibernateExpenseRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Expense> findAll() {
        return ((Session)em.getDelegate())
                .createQuery("SELECT e FROM Expense e", Expense.class)
                .getResultList();
    }

    @Override
    public List<Expense> findAllByWalletId(Long id) {
        var query = ((Session)em.getDelegate()).createQuery("FROM Expense where wallet_id = ?0");

        query.setParameter(0, id);

        return (List<Expense>) query.getResultList(); // isto ko list()
    }

    @Override
    public Expense findOne(Long id) {
        return ((Session)em.getDelegate()).find(Expense.class, id);
    }

    @Override
    public Expense save(Expense expense, Long walletID) {
        var session = (Session) em.getDelegate();

        var wallet = session.load(Wallet.class, walletID);

        expense.setWallet(wallet);

        session.save(expense);

        return expense;
    }

    public Expense update(Expense expense) {
        var session = (Session) em.getDelegate();
        session.update(expense);
        return  expense;
    }

    @Override
    public void removeExpense(Long id) {
        var q = ((Session)em.getDelegate()).createQuery("DELETE FROM Expense WHERE id = ?1");
        q.setParameter(1, id);

        q.executeUpdate();
    }

    @Override
    public void removeExpensesFromWallet(Long walletID) {
        var query = ((Session)em.getDelegate()).createQuery("DELETE FROM Expense WHERE wallet_id = ?1");
        query.setParameter(1, walletID);

        query.executeUpdate();
    }
}
