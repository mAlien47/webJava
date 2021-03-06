package hr.java.web.teskera.controllers;

import com.fasterxml.classmate.AnnotationConfiguration;
import hr.java.web.teskera.models.Expense;
import hr.java.web.teskera.models.Wallet;
import org.apache.tomcat.jni.User;
import org.hamcrest.Matchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private SessionFactory sessionFactory;
    private Session session = null;

    @Before
    public void before() {
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

    @After
    public void after() {
        session.close();
        sessionFactory.close();
    }

    @Test
    public void testLogin() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
        .andExpect(view().name("login"));
    }

    @Test
    public void testShowForm() throws Exception {
        this.mockMvc.perform(get("/expense/newExpense").with(user("admin")
                .password(new BCryptPasswordEncoder().encode("adminpass"))
                .roles("ADMIN", "USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("newExpense"))
                .andExpect(model().attribute("types", Expense.ExpenseType.values()));
    }

    @Test
    @Transactional
    public void testPostExpense() throws Exception {
        this.mockMvc.perform(post("/expense/newExpense").with(user("admin")
                .password(new BCryptPasswordEncoder().encode("adminpass"))
                .roles("ADMIN", "USER"))
                .with(csrf())
                .param("name", "naziv")
                .param("amount", "7")
                .param("expenseType", "FOOD"))
                .andExpect(status().isOk())
                .andExpect(view().name("expenseAdded"));
    }

    @Test
    public void testExpenseSearch_ExpenseName() throws Exception {
        this.mockMvc.perform(post("/expense/pregled").with(user("admin")
                .password(new BCryptPasswordEncoder().encode("adminpass"))
                .roles("ADMIN", "USER"))
                .with(csrf())
                .param("name", "kava"))
                .andExpect(model().attribute("listaTroskova", Matchers.hasSize(2)));
    }

    @Test
    public void testExpenseSearch_NoExpenseName() throws Exception {
        this.mockMvc.perform(post("/expense/pregled").with(user("admin")
                .password(new BCryptPasswordEncoder().encode("adminpass"))
                .roles("ADMIN", "USER"))
                .with(csrf())
                .param("name", ""))
                .andExpect(model().attribute("listaTroskova", Matchers.hasSize(3)));
    }

}