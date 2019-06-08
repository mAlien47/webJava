package hr.java.web.teskera.data;

import hr.java.web.teskera.models.Wallet;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface WalletRepository extends CrudRepository<Wallet, Long> {

    Wallet findByUsername(String username);
}
