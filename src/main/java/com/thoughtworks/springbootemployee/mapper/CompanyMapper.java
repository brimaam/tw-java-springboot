package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    public CompanyResponse toResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();

        BeanUtils.copyProperties(company, companyResponse);
        companyResponse.setEmployeeNumber(company.getEmployees().size());

        return companyResponse;
    }
}
