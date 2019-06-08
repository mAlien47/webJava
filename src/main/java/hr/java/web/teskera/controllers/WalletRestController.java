package hr.java.web.teskera.controllers;

import hr.java.web.teskera.data.ExpenseRepository;
import hr.java.web.teskera.data.WalletRepository;
import hr.java.web.teskera.models.Wallet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*
@RestController
@RequestMapping(path = "/api/wallet", produces = "application/json")
public class WalletRestController {

    private WalletRepository walletRepository;
    private ExpenseRepository expenseRepository;

    public WalletRestController(WalletRepository walletRepository, ExpenseRepository expenseRepository) {
        this.walletRepository = walletRepository;
        this.expenseRepository = expenseRepository;
    }

    @GetMapping
    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> findOne(@PathVariable Long id) {
        var wallet = walletRepository.findOne(id);

        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Wallet save(@RequestBody Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @PutMapping
    public Wallet update(@RequestBody Wallet wallet) {
        return walletRepository.update(wallet);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        walletRepository.delete(id);
    }

}
*/