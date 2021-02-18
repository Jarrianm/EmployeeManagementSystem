package com.cognixia.jump.ems;

import java.util.List;

import com.cognixia.jump.models.Company;
import com.cognixia.jump.models.Department;

public interface CompanyDAO {
	List<Company> getCompanies();
	Company getCompany(int id);
	boolean addCompany(Company company);
	boolean updateCompany(int id);
	boolean deleteCompany(int id);
	List<Department> getDepartments(int id);
	void getAllEmployees(int id);
}
