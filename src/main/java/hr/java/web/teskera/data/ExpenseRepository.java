package hr.java.web.teskera.data;

import hr.java.web.teskera.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByWalletId(Long id);

    List<Expense> findAllByWallet_Username(String username);

    List<Expense> findAllByNameContainingIgnoreCaseAndWallet_Username(String name, String username);

    void deleteByWalletId(Long id);
}
