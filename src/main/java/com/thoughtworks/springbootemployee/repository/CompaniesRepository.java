package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompaniesRepository extends JpaRepository<Company, Integer> {

}
