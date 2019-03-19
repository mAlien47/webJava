package hr.java.web.teskera.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.java.web.teskera.models.Expense;

@Controller
@RequestMapping("/troskovi")
public class ExpenseController {


	@GetMapping("/novo")
	public String newExpense(Model model) {
		model.addAttribute("expense", new Expense());
		model.addAttribute("expenseType", Expense.ExpenseType.values());
		return "newExpense";
	}
	

	
}
