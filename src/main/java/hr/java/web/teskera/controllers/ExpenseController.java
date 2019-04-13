package hr.java.web.teskera.controllers;

import hr.java.web.teskera.data.ExpenseRepository;
import hr.java.web.teskera.data.WalletRepository;
import hr.java.web.teskera.models.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import hr.java.web.teskera.models.Expense;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Slf4j
@Controller
@RequestMapping("/expense")
@SessionAttributes({"types"})
public class ExpenseController {

	private ExpenseRepository expenseRepository;
	private  WalletRepository walletRepository;

	public ExpenseController(ExpenseRepository expenseRepository, WalletRepository walletRepository) {
		this.expenseRepository = expenseRepository;
		this.walletRepository = walletRepository;
	}

	@GetMapping("/newExpense")
	public String showForm(Model model) {

		String username = getUserName();
		Wallet wallet;
		try {
			wallet = walletRepository.findOne(username);
		} catch (Exception e) {

				wallet = new Wallet();
				wallet.setType(Wallet.WalletType.CASH);

				wallet = walletRepository.save(wallet, username);
			}


		model.addAttribute("expense", new Expense());
		model.addAttribute("types", Expense.ExpenseType.values());
		return "newExpense";
	}

	private String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	@PostMapping("/newExpense")
	public String processForm(@Validated Expense expense, Errors errors, Model model, HttpSession session) {

		if (errors.hasErrors()) {
			log.info("Model ima errore.");
			return "newExpense";
		}

		var wallet = walletRepository.findOne(getUserName());
		var walletID = wallet.getId();

		// dodavanje expense u bazu
		expenseRepository.save(expense, walletID);

		// dodavanje expensea u model
		model.addAttribute("expense", expense);

		// dohvaćanje iz baze svih troškova i računanje totala
		var expenseList = new ArrayList<Expense>();
		expenseRepository.findAllByWalletId(walletID).iterator().forEachRemaining(
				e -> expenseList.add(e)
		);
		var total = expenseList.stream().map(e -> e.getAmount()).reduce((e1, e2) -> e1.add(e2)).get();

		model.addAttribute("total", total);
		return "expenseAdded";
	}

	@GetMapping("/resetWallet")
	public String resetWallet() {

		var walletID = walletRepository.findOne(getUserName()).getId();

		expenseRepository.removeExpensesFromWallet(walletID);

		log.info("Resetiran wallet.");
		return "redirect:/expense/newExpense";
	}
}
