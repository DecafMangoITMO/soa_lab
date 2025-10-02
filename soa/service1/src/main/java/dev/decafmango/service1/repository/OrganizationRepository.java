package dev.decafmango.service1.repository;

import dev.decafmango.common.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {

    Optional<Organization> findById(long id);

    @Query("SELECT avg(o.employeesCount) FROM Organization o")
    Double averageEmployeeCount();

    int countAllByAnnualTurnover(double annualTurnover);

}
