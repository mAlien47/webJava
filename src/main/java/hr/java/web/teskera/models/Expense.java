package hr.java.web.teskera.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Expense {

	private String name;
	private double amount;
	private ExpenseType expenseType;
	
	public static enum ExpenseType {
		
		FOOD,
		DRINKS,
		WASTING_MONEY_ON_STUFF_YOU_DONT_NEED
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}
	
}
