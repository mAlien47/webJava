package hr.java.web.teskera.controllers;

import hr.java.web.teskera.models.Wallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import hr.java.web.teskera.models.Expense;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/expense")
@SessionAttributes({"types", "wallet"})
public class ExpenseController {


	@GetMapping("/newExpense")
	public String showForm(Model model) {
		model.addAttribute("expense", new Expense());
		model.addAttribute("types", Expense.ExpenseType.values());
		return "newExpense";
	}
	
	@PostMapping("/newExpense")
	public String processForm(@Validated Expense expense, Errors errors, Model model, HttpSession session) {

		if (errors.hasErrors()) {
			log.info("Model ima errore.");
			return "newExpense";
		}

		var wallet = (Wallet)session.getAttribute("wallet");
		wallet.addExpense(expense);

		model.addAttribute("expense", expense);
		model.addAttribute("total", wallet.getTotal());
		return "expenseAdded";
	}

	@GetMapping("/resetWallet")
	public String resetWallet(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		log.info("Resetiran wallet.");
		return "redirect:/expense/newExpense";
	}

	// when a request comes in, the first thing Spring will do is to notice @SessionAttributes
	// and then attempt to find the value  in javax.servlet.http.HttpSession.
	// If it doesn't find the value, then the method with @ModelAttribute having the same name
	// will be invoked
	@ModelAttribute("wallet")
	public Wallet setWallet(Model model){
		log.info("Novi wallet.");
		return new Wallet();
	}
}
