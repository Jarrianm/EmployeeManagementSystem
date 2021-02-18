package com.cognixia.jump.ems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.cognixia.jump.models.Address;
import com.cognixia.jump.models.Company;
import com.cognixia.jump.models.Department;
import com.cognixia.jump.models.Employee;

public class DepartmentDAOClass implements DepartmentDAO {

	Connection conn = ConnectionManager.getConnection();
	AddressDAOClass adc = new AddressDAOClass();
	CompanyDAOClass cdc = new CompanyDAOClass();

	@Override
	public List<Department> getAllDepartments() {
		
		List<Department> depts = new ArrayList<>();
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from department");
			
			while(rs.next()) {
				Department d = new Department(rs.getInt("dept_id"),rs.getString("dept_name"),rs.getString("dept_phone"));
				depts.add(d);
				System.out.println(d);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return depts;
	}

	@Override
	public Department getDepartment(int id) {
		
		Department department = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from department where dept_id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				department = new Department(rs.getInt("dept_id"),
						rs.getString("dept_name"),
						rs.getString("dept_phone"));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return department;
	}

	@Override
	public boolean addDepartment(Department department) {
		
		int rows = 0;
		
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into department (dept_name, dept_phone) "
					+ "values (?,?)");
			pstmt.setString(1, department.getName());
			pstmt.setString(2, department.getPhone());
			
			rows = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rows == 1;
	}

	@Override
	public boolean deleteDepartment(int id) {
		
		int rows = 0;
		
		try {
			String cd = "delete from company_department where dept_id = ?";
			String de = "delete from department_employee where dept_id = ?";
			String depart = "delete from department where dept_id = ?";
			String update = "update employee set dept_id = 0 where dept_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(cd);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			pstmt = conn.prepareStatement(de);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			pstmt = conn.prepareStatement(depart);
			pstmt.setInt(1, id);
			rows = pstmt.executeUpdate();
			pstmt = conn.prepareStatement(update);
			pstmt.setInt(1, id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rows == 1;
	}

	@Override
	public boolean updateDepartment(int id) {
		
		int rows = 0;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Select which field to update: \n1. Name \n2. Phone \n3. Company \n4. Transfer Employee \n5. Remove employee");
		String input = scan.nextLine();
		
		switch(input) {
		case "1": 
			System.out.println("Enter a new department name");
			String name = scan.next();
			try {
				PreparedStatement pstmt = conn.prepareStatement("update department set dept_name = ? where dept_id = ?");
				pstmt.setString(1, name);
				pstmt.setInt(2, id);
				rows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "2":
			System.out.println("Enter a new phone number");
			String phone = scan.next();
			try {
				PreparedStatement pstmt = conn.prepareStatement("update department set dept_phone = ? where dept_id = ?");
				pstmt.setString(1, phone);
				pstmt.setInt(2, id);
				rows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "3":
			cdc.getCompanies();
			System.out.println("Enter a company_id from above to transfer this department to that company");
			int company = scan.nextInt();
			try {
				PreparedStatement pstmt = conn.prepareStatement("update company_department set company_id = ? where dept_id = ?");
				pstmt.setInt(1, company);
				pstmt.setInt(2, id);
				rows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "4":
			printEmployees(id);
			System.out.println("Enter an employee_id from above to transfer them to a new department");
			int employee = scan.nextInt();
			getAllDepartments();
			System.out.println("Enter a department_id from above to transfer the employee to that department");
			int dept = scan.nextInt();
			try {
				PreparedStatement pstmt = conn.prepareStatement("update employee set dept_id = ? where employee_id = ?");
				pstmt.setInt(1, dept);
				pstmt.setInt(2, employee);
				rows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "5":
			try {
				printEmployees(id);
				System.out.println("Enter an employee_id to remove it from this department");
				int employee_id = scan.nextInt();
				String depart = "update employee set dept_id = 0 where dept_id = ? and employee_id = ?";
				PreparedStatement pstmt = conn.prepareStatement(depart);
				pstmt.setInt(1, id);
				pstmt.setInt(2, employee_id);
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
	public List<Employee> printEmployees(int id) {
		
		String test = "select * from employee where dept_id = ?";
		List<Employee> emp = new ArrayList<>();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(test);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Address address = adc.getAddress(rs.getInt("address_id"));
				Department department = getDepartment(rs.getInt("dept_id"));
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
				emp.add(e);
				System.out.println(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emp;
	}
}
