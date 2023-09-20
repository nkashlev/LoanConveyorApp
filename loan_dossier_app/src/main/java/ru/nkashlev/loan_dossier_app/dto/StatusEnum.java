package ru.nkashlev.loan_dossier_app.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StatusEnum {
    PREAPPROVAL("preapproval"),

    APPROVED("approved"),

    CC_DENIED("cc_denied"),

    CC_APPROVED("cc_approved"),

    PREPARE_DOCUMENTS("prepare_documents"),

    DOCUMENT_CREATED("document_created"),

    CLIENT_DENIED("client_denied"),

    DOCUMENT_SIGNED("document_signed"),

    CREDIT_ISSUED("credit_issued");

    private String value;

    StatusEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String value) {
        for (StatusEnum b : StatusEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
