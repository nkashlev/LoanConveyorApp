package ru.nkashlev.loan_dossier_app.dossier.entity.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.nkashlev.loan_dossier_app.dossier.model.ApplicationStatusHistoryDTO;


import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class EmailMessage implements Serializable {

    private String address;

    private ApplicationStatusHistoryDTO.StatusEnum theme;

    private Long applicationId;
}
