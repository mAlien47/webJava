package hr.java.web.teskera.quartz;

import hr.java.web.teskera.data.ExpenseRepository;
import hr.java.web.teskera.models.Expense;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.stream.Collectors;

public class StatistikaTroskovaQuartzJobBean extends QuartzJobBean {

    @Autowired
    private ExpenseRepository repository;

    private String name;

    public void setName(String name) { this.name = name; }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        var troskoviPoGrupi = repository.findAll().stream()
                .collect(Collectors.groupingBy(Expense::getExpenseType));

        troskoviPoGrupi.forEach(
                (k, v) -> {
                    System.out.println(k.toString() +
                            " SUM " + v.stream().map(e -> e.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add) + " " +
                            " MIN" + v.stream().map(Expense::getAmount).min(Comparator.naturalOrder()).get() + " " +
                            " MAX" + v.stream().map(Expense::getAmount).max(Comparator.naturalOrder()).get()

                            );
                }
        );
    }
}
