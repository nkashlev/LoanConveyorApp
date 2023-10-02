package ru.nkashlev.loan_dossier_app.dossier.entity.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
@RequiredArgsConstructor
@Getter
@Setter
public class Passport implements Serializable {
    private String series;
    private String number;
    private String issue_branch;
    private LocalDate issue_date;
}
