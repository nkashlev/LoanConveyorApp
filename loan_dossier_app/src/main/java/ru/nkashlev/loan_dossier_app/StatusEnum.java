package ru.nkashlev.loan_dossier_app;

import lombok.Data;


public enum StatusEnum {
    PREAPPROVAL, APPROVED, CC_DENIED, CC_APPROVED, PREPARE_DOCUMENTS, DOCUMENT_CREATED, CLIENT_DENIED, DOCUMENT_SIGNED, CREDIT_ISSUED;
}
