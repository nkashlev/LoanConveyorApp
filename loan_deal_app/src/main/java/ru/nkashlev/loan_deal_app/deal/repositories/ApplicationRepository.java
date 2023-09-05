package ru.nkashlev.loan_deal_app.deal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nkashlev.loan_deal_app.deal.entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
