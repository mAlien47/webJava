package hr.java.web.teskera.data;

import hr.java.web.teskera.models.Expense;
import hr.java.web.teskera.models.Wallet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@Transactional
@DataJpaTest
class ExpenseRepositoryTest {

    @Autowired
    private ExpenseRepository repository;

    private SessionFactory sessionFactory;
    private Session session = null;


    @Before
    void setUp() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Expense.class)
                .addAnnotatedClass(Wallet.class);
        configuration.setProperty("hibernate.dialect",
                "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class",
                "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url",
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.hbm2ddl.auto", "none");
        configuration.setProperty("username", "sa");
        configuration.setProperty("password", "");
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
    }

    @Test
    void findAllByWalletId() {
        var result = repository.findAllByWalletId(1L);

        assertEquals(3, result.size());
    }
}