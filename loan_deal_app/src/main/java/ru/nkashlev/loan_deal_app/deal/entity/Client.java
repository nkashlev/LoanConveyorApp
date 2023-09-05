package ru.nkashlev.loan_deal_app.deal.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.nkashlev.loan_deal_app.deal.entity.util.Passport;
import ru.nkashlev.loan_deal_app.deal.model.EmploymentDTO;
import ru.nkashlev.loan_deal_app.deal.model.ScoringDataDTO;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@Table(name = "Client")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long client_id;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "middle_name")
    private String middle_name;

    @Column(name = "birth_date")
    private LocalDate birth_date;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private ScoringDataDTO.GenderEnum gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private ScoringDataDTO.MaritalStatusEnum marital_status;

    @Column(name = "dependent_amount")
    private Integer dependent_amount;

    @Type(type = "jsonb")
    @Column(name = "passport")
    private Passport passport;

    @Type(type = "jsonb")
    @Column(name = "employment")
    private EmploymentDTO employment;

    @Column(name = "account")
    private String account;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private List<Application> applications;
}
