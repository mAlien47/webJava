package hr.java.web.teskera.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.java.web.teskera.models.Expense;

@Controller
@RequestMapping("/expense")
public class ExpenseController {


	@GetMapping("/newExpense")
	public String showForm(Model model) {
		model.addAttribute("expense", new Expense());
		model.addAttribute("types", Expense.ExpenseType.values());
		return "newExpense";
	}
	
	@PostMapping("/newExpense")
	public String processForm(Expense expense, Model model) {
		model.addAttribute("expense", expense);
		return "expenseAdded";
	}
	
}
