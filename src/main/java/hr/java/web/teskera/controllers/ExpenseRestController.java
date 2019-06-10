package hr.java.web.teskera.controllers;

import hr.java.web.teskera.data.ExpenseRepository;
import hr.java.web.teskera.data.WalletRepository;
import hr.java.web.teskera.models.Expense;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/expense", produces = "application/json")
public class ExpenseRestController {

    private WalletRepository walletRepository;
    private ExpenseRepository expenseRepository;

    public ExpenseRestController(WalletRepository walletRepository, ExpenseRepository expenseRepository) {
        this.walletRepository = walletRepository;
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> findOne(@PathVariable Long id) {
        var expense = expenseRepository.findById(id);

        if (expense.isPresent())
            return new ResponseEntity<>(expense.get(), HttpStatus.OK);

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(path = "/{id}", consumes = "application/json")
    public Expense save (@RequestBody Expense expense) {


        var wallet = walletRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        expense.setWallet(wallet);

        return expenseRepository.save(expense);
    }

    @PutMapping//("/{id}")
    public Expense update(@RequestBody Expense expense) {
        return expenseRepository.save(expense);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete (@PathVariable Long id) {
        expenseRepository.deleteById(id);
    }
}
