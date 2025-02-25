package com.employ.crud_employ_api.employees;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
  Optional<Employee> findByEmail(String email);
}
