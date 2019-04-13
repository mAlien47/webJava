package hr.java.web.teskera.data;

import hr.java.web.teskera.models.Wallet;

public interface WalletRepository {

    Iterable<Wallet> findAll();

    Wallet findOne(String username);

    Wallet save(Wallet wallet, String username);

}
