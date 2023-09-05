package ru.nkashlev.loan_deal_app.deal.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO;
import ru.nkashlev.loan_deal_app.deal.model.LoanOfferDTO;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "Application")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ApplicationStatusHistoryDTO.StatusEnum status;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Type(type = "jsonb")
    @Column(name = "applied_offer")
    private LoanOfferDTO appliedOffer;

    @Column(name = "sign_date")
    private LocalDate signDate;

    @Column(name = "ses_code")
    private Long sesCode;

    @Type(type = "jsonb")
    @Column(name = "status_history")
    private List<ApplicationStatusHistoryDTO> statusHistory = List.of(new ApplicationStatusHistoryDTO());

    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    @Fetch(FetchMode.JOIN)
    private Client client;

    @OneToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "credit_id", referencedColumnName = "credit_id")
    @Fetch(FetchMode.JOIN)
    private Credit credit;
}
