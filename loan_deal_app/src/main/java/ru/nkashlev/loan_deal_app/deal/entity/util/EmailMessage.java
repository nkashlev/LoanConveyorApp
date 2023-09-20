package ru.nkashlev.loan_deal_app.deal.entity.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class EmailMessage implements Serializable {
    private String address;

    private ApplicationStatusHistoryDTO.StatusEnum theme;

    private Long applicationId;

    public EmailMessage(String address, Long applicationId, ApplicationStatusHistoryDTO.StatusEnum statusEnum) {
        this.address = address;
        this.applicationId = applicationId;
        this.theme = statusEnum;
    }
}
