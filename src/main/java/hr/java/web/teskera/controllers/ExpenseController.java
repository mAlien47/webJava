package hr.java.web.teskera.controllers;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.java.web.teskera.models.Expense;

@Controller
@RequestMapping("/expense")
public class ExpenseController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8366543859848255803L;

	@GetMapping("/new")
	public String newExpense(Model model) {
		model.addAttribute("expense", new Expense());
		model.addAttribute("expenseType", Expense.ExpenseType.values());
		return "newExpense";
	}
	

	
}
