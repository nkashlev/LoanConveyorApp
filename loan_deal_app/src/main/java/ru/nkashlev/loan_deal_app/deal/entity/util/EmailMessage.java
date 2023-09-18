package ru.nkashlev.loan_deal_app.deal.entity.util;

import lombok.Data;
import ru.nkashlev.loan_deal_app.deal.model.ApplicationStatusHistoryDTO;
@Data
public class EmailMessage {
    private String address;

    private ApplicationStatusHistoryDTO.StatusEnum theme;

    private Long applicationId;

    public EmailMessage(String address, Long applicationId, ApplicationStatusHistoryDTO.StatusEnum statusEnum) {
        this.address = address;
        this.applicationId = applicationId;
        this.theme = statusEnum;
    }
}
