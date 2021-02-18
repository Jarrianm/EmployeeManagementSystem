package com.cognixia.jump.ems;

import java.util.List;

import com.cognixia.jump.models.Department;
import com.cognixia.jump.models.Employee;

public interface DepartmentDAO {
	List<Department> getAllDepartments();
	Department getDepartment(int id);
	boolean addDepartment(Department department);
	boolean deleteDepartment(int id);
	boolean updateDepartment(int id);
	List<Employee> printEmployees(int id);
}
