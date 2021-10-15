package com.employee.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;

@Service
public class EmployeeServiceIMPL implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getallEmployee() {
		return employeeRepository.findAll();
	}

	@Override
	public void saveEMployee(Employee employee) {

		employeeRepository.save(employee);
	}

	@Override
	public Employee getEmployeeById(Long id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		Employee employee = null;
		if (optional.isPresent()) {
			employee = optional.get();
		} else {
			throw new RuntimeException("Employee Not Found  for id:" + id);
		}
		return employee;
	}

	@Override
	public void DeleteEmplyeeById(Long id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public Page<Employee> findPaginated(int pageno, int PageSize, String sortField, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(pageno - 1, PageSize,sort);
		return this.employeeRepository.findAll(pageable);
	}

}
