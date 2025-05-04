package com.employ.crud_employ_api.employees;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.employ.crud_employ_api.exceptions.DuplicateEmailException;

@Service
public class EmployeeService {
    private  final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public ResponseEntity<Object> newEmployee(Employee employee){
        Optional<Employee> existingEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (existingEmployee.isPresent()) {
            throw new DuplicateEmailException("Email must be unique");
        }

        employeeRepository.save(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    public List<Employee> getEmployee(){
        return this.employeeRepository.findAll();
    }

    public ResponseEntity<Object> updateEmployee(Integer id, Employee updatedEmployee){
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if(employeeOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Employee existingEmpolyee = employeeOptional.get();
        existingEmpolyee.setEmail(updatedEmployee.getEmail());
        existingEmpolyee.setName(updatedEmployee.getName());
        existingEmpolyee.setPhonenumber(updatedEmployee.getPhonenumber());
        existingEmpolyee.setPosition(updatedEmployee.getPosition());
        existingEmpolyee.setSalary(updatedEmployee.getSalary());

        employeeRepository.save(existingEmpolyee);
        return  ResponseEntity.ok(existingEmpolyee);
    }

    public ResponseEntity<Object> getEmployeeById(Integer id){
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if(employeeOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok(employeeOptional);
    }

    public ResponseEntity<Object> deleteEmployee(Integer id){
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if(employeeOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        employeeRepository.deleteById(id);
        return  ResponseEntity.ok(Map.of("message", "Deleted"));
    }
}
