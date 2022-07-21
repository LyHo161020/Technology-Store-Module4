package com.cg.repository;

import com.cg.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Modifying
    @Query("UPDATE Customer AS c SET c.status.id = 2 WHERE c.id = :id")
    void blockCustomer(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Customer AS c SET c.status.id = 1 WHERE c.id = :id")
    void unlockCustomer(@Param("id") Long id);
}
