package hr.java.web.teskera.models;

import lombok.Data;

@Data
public class Expense {

	private String name;
	private double amount;
	
	public static enum ExpenseType {
		
		FOOD,
		DRINKS,
		WASTING_MONEY_ON_STUFF_YOU_DONT_NEED
		
	}
	
}
