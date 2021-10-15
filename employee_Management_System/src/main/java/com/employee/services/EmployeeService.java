package com.employee.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.employee.model.Employee;

public interface EmployeeService {

	List<Employee> getallEmployee();
	void saveEMployee(Employee employee);
	
	Employee getEmployeeById(Long id);
	
	void DeleteEmplyeeById(Long id);
	Page<Employee> findPaginated(int pageno,int PageSize,String sortField,String sortDirection);
}
