package com.cognixia.jump.ems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.cognixia.jump.models.Address;
import com.cognixia.jump.models.Company;
import com.cognixia.jump.models.Department;
import com.cognixia.jump.models.Employee;

public class EmployeeDAOClass implements EmployeeDAO{
	Connection conn = ConnectionManager.getConnection();
	AddressDAOClass adc = new AddressDAOClass();
	DepartmentDAOClass ddc = new DepartmentDAOClass();
	CompanyDAOClass cdc = new CompanyDAOClass();
	
	@Override
	public List<Employee> getEmployees() {
		List<Employee> employees = new ArrayList<>();
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from employee");
			
			while(rs.next()) {
				Address address = adc.getAddress(rs.getInt("address_id"));
				Department department = ddc.getDepartment(rs.getInt("dept_id"));
				Company company = cdc.getCompany(rs.getInt("company_id"));
				Employee e = new Employee(rs.getInt("employee_id"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString("gender"),
						rs.getDate("date_of_birth"),
						rs.getInt("salary"),
						address,
						department,
						company);
				employees.add(e);
				System.out.println(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	@Override
	public Employee getEmployee(int id) {
		Employee employee = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from employee where employee_id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Address address = adc.getAddress(rs.getInt("address_id"));
				Department department = ddc.getDepartment(rs.getInt("dept_id"));
				Company company = cdc.getCompany(rs.getInt("company_id"));
				employee = new Employee(rs.getInt("employee_id"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString("gender"),
						rs.getDate("date_of_birth"),
						rs.getInt("credits"),
						address,
						department,
						company);
				System.out.println(employee);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public boolean addEmployee(Employee employee) {
		int rows = 0;
		
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into employee (first_name, last_name, gender, date_of_birth, salary, address, department) "
					+ "values (?,?,?,?,?,?,?)");
			pstmt.setString(1, employee.getFirst_name());
			pstmt.setString(2, employee.getLast_name());
			pstmt.setString(3, employee.getGender());
			pstmt.setDate(4, (java.sql.Date) employee.getDob());
			pstmt.setInt(5, employee.getSalary());
			pstmt.setObject(6, employee.getAddress());
			pstmt.setObject(7, employee.getDepartment());
			
			rows = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rows == 1;
	}

	@Override
	public boolean updateEmployee(int id) {
		int rows = 0;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Select which field to update: \n1. First Name \n2. Last Name \n3. Gender \n4. Date of birth \n5. Salary \n6. Address \n7. Transfer Department \n8. Quit Company \n9. Transfer Company");
		String input = scan.nextLine();
		
		switch(input) {
		case "1": 
			System.out.println("Enter a new first name");
			String first_name = scan.next();
			try {
				PreparedStatement pstmt = conn.prepareStatement("update employee set first_name = ? where employee_id = ?");
				pstmt.setString(1, first_name);
				pstmt.setInt(2, id);
				rows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "2":
			System.out.println("Enter a new last name");
			String last_name = scan.next();
			try {
				PreparedStatement pstmt = conn.prepareStatement("update employee set last_name = ? where employee_id = ?");
				pstmt.setString(1, last_name);
				pstmt.setInt(2, id);
				rows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "3": 
			System.out.println("Enter a new gender (one character)");
			String gender = scan.next();
			try {
				PreparedStatement pstmt = conn.prepareStatement("update employee set gender = ? where employee_id = ?");
				pstmt.setString(1, gender);
				pstmt.setInt(2, id);
				rows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "4":
			System.out.println("Enter a new birth date in the format YYYY-MM-DD");
			String inputDate = scan.next();
			Date dob = Date.valueOf(inputDate);
			try {
				PreparedStatement pstmt = conn.prepareStatement("update employee set date_of_birth = ? where employee_id = ?");
				pstmt.setDate(1, dob);
				pstmt.setInt(2, id);
				rows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "5": 
			System.out.println("Enter a new number for salary");
			int credits = scan.nextInt();
			try {
				PreparedStatement pstmt = conn.prepareStatement("update employee set salary = ? where employee_id = ?");
				pstmt.setInt(1, credits);
				pstmt.setInt(2, id);
				rows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "6":
			adc.getAllAddresses();
			System.out.println("Enter a new Address ID from the above options");
			int address = scan.nextInt();
			try {
				PreparedStatement pstmt = conn.prepareStatement("update employee set address_id = ? where employee_id = ?");
				pstmt.setInt(1, address);
				pstmt.setInt(2, id);
				rows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "7": 
			ddc.getAllDepartments();
			System.out.println("Enter a new Department ID from the above options");
			int department = scan.nextInt();
			try {
				PreparedStatement pstmt = conn.prepareStatement("update employee set dept_id = ? where employee_id = ?");
				pstmt.setInt(1, department);
				pstmt.setInt(2, id);
				rows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "8":
			try {
				PreparedStatement pstmt = conn.prepareStatement("update employee set company_id = 0 where employee_id = ?");
				pstmt.setInt(1, id);
				rows = pstmt.executeUpdate();
				pstmt = conn.prepareStatement("update employee set dept_id = 0 where employee_id = ?");
				pstmt.setInt(1, id);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("You have quit your company!");
			break;
		case "9":
			try {
				cdc.getCompanies();
				System.out.println("Enter a company_id from above to transfer to that company");
				int c = scan.nextInt();
				cdc.getDepartments(c);
				System.out.println("Enter a department_id to transfer to that department within the company");
				int d = scan.nextInt();
				
				PreparedStatement pstmt = conn.prepareStatement("update employee set company_id = ? where employee_id = ?");
				pstmt.setInt(1, c);
				pstmt.setInt(2, id);
				rows = pstmt.executeUpdate();
				
				pstmt = conn.prepareStatement("update employee set dept_id = ? where employee_id = ?");
				pstmt.setInt(1, d);
				pstmt.setInt(2, id);
				rows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		default :
			System.out.println("Invalid input");
			break;
		}
		scan.close();
		return rows == 1;
	}

	@Override
	public boolean deleteEmployee(int id) {
		int rows = 0;
		
		try {
			String emp = "delete from employee where employee_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(emp);
			pstmt.setInt(1, id);
			rows = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rows == 1;
	}
}
