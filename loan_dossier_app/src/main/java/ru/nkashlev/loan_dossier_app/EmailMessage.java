package ru.nkashlev.loan_dossier_app;

import lombok.Data;

@Data
public class EmailMessage {

    private String address;

    private StatusEnum theme;

    private Long applicationId;

    public EmailMessage(String address, Long applicationId, StatusEnum statusEnum) {
        this.address = address;
        this.applicationId = applicationId;
        this.theme = statusEnum;
    }
}
