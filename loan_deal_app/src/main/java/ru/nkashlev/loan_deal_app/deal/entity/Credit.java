package ru.nkashlev.loan_deal_app.deal.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.nkashlev.loan_deal_app.deal.entity.util.CreditStatus;
import ru.nkashlev.loan_deal_app.deal.model.PaymentScheduleElement;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "Credit")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_id")
    private Long credit_id;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "term")
    private Integer term;
    @Column(name = "monthly_payment")
    private BigDecimal monthly_payment;
    @Column(name = "rate")
    private BigDecimal rate;
    @Column(name = "psk")
    private BigDecimal psk;
    @Type(type = "jsonb")
    @Column(name = "payment_schedule")
    private List<PaymentScheduleElement> payment_schedule;
    @Column(name = "insurance_enable")
    private Boolean insurance_enable;
    @Column(name = "salary_client")
    private Boolean salary_client;
    @Enumerated(EnumType.STRING)
    @Column(name = "credit_status")
    private CreditStatus credit_status;
    @OneToOne(mappedBy = "credit", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Application application;
}
