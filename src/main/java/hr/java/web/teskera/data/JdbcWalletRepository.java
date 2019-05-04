package hr.java.web.teskera.data;

import hr.java.web.teskera.models.Expense;
import hr.java.web.teskera.models.Wallet;
import org.apache.tomcat.jni.Local;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
@Repository
public class JdbcWalletRepository implements WalletRepository {

    private JdbcTemplate jdbc;
    private SimpleJdbcInsert jdbcInsert;

    public JdbcWalletRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.jdbcInsert = new SimpleJdbcInsert(jdbc)
        .withTableName("wallet")
        .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Wallet> findAll() {

        var wallets = jdbc.query("SELECT * FROM wallet", new BeanPropertyRowMapper(Wallet.class));

        return wallets;
    }

    @Override
    public Wallet findOne(String username) {
        return jdbc.queryForObject("SELECT * FROM wallet WHERE username = ?", new BeanPropertyRowMapper<>(Wallet.class), username);
    }

    @Override
    public Wallet save(Wallet wallet) {
        wallet.setCreateDate(LocalDateTime.now());
//        wallet.setId(saveAndReturnID(wallet, username));

        return wallet;
    }

    public long saveAndReturnID(Wallet wallet, String username) {
        Map<String, Object> vals = new HashMap<>();

        vals.put("username", username);
        vals.put("createDate", wallet.getCreateDate());
        vals.put("type", wallet.getType().toString());

        return jdbcInsert.executeAndReturnKey(vals).longValue();
    }

    private Wallet mapRowToWallet(ResultSet rs) throws SQLException {
        Wallet wallet = new Wallet();
        wallet.setType(Wallet.WalletType.valueOf(rs.getString("type")));
//        wallet.setCreateDate(LocalDateTime.parse(rs.getDate("createDate")));
        wallet.setId(rs.getLong("id"));
        return wallet;
    }

}
*/