package hr.java.web.teskera.controllers;

import hr.java.web.teskera.data.ExpenseRepository;
import hr.java.web.teskera.data.WalletRepository;
import hr.java.web.teskera.models.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
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
import java.util.List;

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
			wallet = walletRepository.findByUsername(username);
		} catch (Exception e) {

				wallet = new Wallet();
				wallet.setType(Wallet.WalletType.CASH);

				wallet = walletRepository.save(wallet);
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

		var wallet = walletRepository.findByUsername(getUserName());
		var walletID = wallet.getId();

		expense.setWallet(wallet);
		expenseRepository.save(expense);

		model.addAttribute("expense", expense);

		var expenseList = new ArrayList<Expense>();
		expenseRepository.findAllByWalletId(walletID).iterator().forEachRemaining(
				e -> expenseList.add(e)
		);

		var total = expenseList.stream().map(e -> e.getAmount()).reduce((e1, e2) -> e1.add(e2)).get();

		model.addAttribute("total", total);
		return "expenseAdded";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/resetWallet")
	public String resetWallet() {

		var walletID = walletRepository.findByUsername(getUserName()).getId();

		expenseRepository.deleteByWalletId(walletID);

		log.info("Resetiran wallet.");
		return "redirect:/expense/newExpense";
	}

	@GetMapping("/pregled")
	public String pregled(Model model) {
		model.addAttribute("expense", new Expense());
		return "pregled";
	}

	@PostMapping("/pregled")
	public String pregled(Expense expense, Model model) {
		String name = expense.getName();
		List<Expense> listaTroskova = expenseRepository.findAllByNameContainingIgnoreCaseAndWallet_Username(name, getUserName());
		model.addAttribute("listaTroskova", listaTroskova);
		return "pregled";
	}
}
