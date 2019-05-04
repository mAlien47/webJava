package hr.java.web.teskera.data;

import hr.java.web.teskera.models.Wallet;

import java.util.List;

public interface WalletRepository {

    List<Wallet> findAll();

    Wallet findOne(String username);

    Wallet findOne(Long id);

    Wallet save(Wallet wallet);

    Wallet update(Wallet wallet);

    void delete(Long id);
}
