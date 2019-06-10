package hr.java.web.teskera.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.java.web.teskera.models.Expense;
import hr.java.web.teskera.models.Wallet;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.Matchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.regex.Matcher;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.is;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class WalletRestControllerTest {

    private String password = new BCryptPasswordEncoder().encode("adminpass");
    private String user = "admin";

    @Autowired
    private MockMvc mockMvc;


    private SessionFactory sessionFactory;
    private Session session = null;


    @Before
    void setUp() {
        System.out.println("\n\nBefore Each initEach() method called\n\n");
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
    void findOne() throws Exception {
        this.mockMvc.perform(get("/api/wallet/1").with(user(this.user).password(password).roles("ADMIN", "USER")))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void findAll() throws Exception {
        this.mockMvc.perform(get("/api/wallet/").with(user(this.user).password(password).roles("ADMIN", "USER")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void save() throws Exception {
        var wallet = new Wallet();
        wallet.setUsername("admin");
        wallet.setType(Wallet.WalletType.CASH);

        this.mockMvc.perform(post("/api/wallet/").with(user(this.user).password(password).roles("ADMIN", "USER"))
                .content(asJsonString(wallet))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
    }

    @Test
    void delete() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/wallet/2").with(user(this.user).password(password).roles("ADMIN", "USER"))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}