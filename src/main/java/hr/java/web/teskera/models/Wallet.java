package hr.java.web.teskera.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Wallet implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    private WalletType type;

//    @JsonIgnore
    @OneToMany(mappedBy = "wallet", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Expense> expenses;

    private String username;


    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
        this.username = SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public enum WalletType {
        CASH,
        CARD
    }
}
