package hr.java.web.teskera.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Expense implements Serializable { // serializable nije nužan

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime createDate;

	@NotEmpty(message = "{validation.expense.name.notEmpty}.")
	@Size(min = 2, max = 35, message = "{validation.expense.name.size}.")
	private String name;

	@NotNull(message = "{validation.expense.amount.notNull}.")
	@DecimalMin(value = "0.01", message = "{validation.expense.amount.min}.")
	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "{validation.expense.expenseType}")
	private ExpenseType expenseType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "wallet_id")
	@JsonIgnore
	private Wallet wallet;

	@PrePersist
	protected void onCreate() {
		createDate = LocalDateTime.now();
	}
	
	public static enum ExpenseType {
		
		FOOD,
		DRINKS,
		WASTING_MONEY_ON_STUFF_YOU_DONT_NEED
		
	}
}
