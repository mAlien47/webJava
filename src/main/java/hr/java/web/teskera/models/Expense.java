package hr.java.web.teskera.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class Expense implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private double amount;
	
	public static enum ExpenseType {
		
		FOOD,
		DRINKS,
		WASTING_MONEY_ON_STUFF_YOU_DONT_NEED
		
	}
	
}
