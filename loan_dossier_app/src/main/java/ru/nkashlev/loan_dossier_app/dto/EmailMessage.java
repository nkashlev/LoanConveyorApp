package ru.nkashlev.loan_dossier_app.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailMessage implements Serializable {
    private String address;

    private StatusEnum theme;

    private Long applicationId;

    public EmailMessage(String address, Long applicationId, StatusEnum statusEnum) {
        this.address = address;
        this.applicationId = applicationId;
        this.theme = statusEnum;
    }

    public EmailMessage() {
    }

    @Override
    public String toString() {
        return "EmailMessage{" +
                "address='" + address + '\'' +
                ", theme=" + theme +
                ", applicationId=" + applicationId +
                '}';
    }
}