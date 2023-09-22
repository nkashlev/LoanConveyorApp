package ru.nkashlev.loan_dossier_app.dossier.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nkashlev.loan_dossier_app.dossier.entity.Application;


@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
