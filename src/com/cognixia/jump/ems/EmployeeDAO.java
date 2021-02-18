package com.cognixia.jump.ems;

import java.util.List;

import com.cognixia.jump.models.Employee;

public interface EmployeeDAO {
	List<Employee> getEmployees();
	Employee getEmployee(int id);
	boolean addEmployee(Employee employee);
	boolean updateEmployee(int id);
	boolean deleteEmployee(int id);
}
