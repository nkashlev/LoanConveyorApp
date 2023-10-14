package ru.nkashlev.loan_deal_app.deal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import ru.nkashlev.loan_deal_app.deal.entity.Application;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query("SELECT с FROM Application с WHERE с.credit IS NOT NULL")
    List<Application> findAllNotNullElements();

    @Query("SELECT с FROM Application с WHERE с.applicationId = :id AND с.credit IS NOT NULL")
    Application findByIdNotNull(@PathVariable("id") Long id);
}
