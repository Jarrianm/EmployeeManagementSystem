package com.cognixia.jump.ems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.cognixia.jump.models.Company;
import com.cognixia.jump.models.Department;

public class CompanyDAOClass implements CompanyDAO{
	Connection conn = ConnectionManager.getConnection();
	@Override
	public List<Company> getCompanies() {
		List<Company> companies = new ArrayList<>();
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from company");
			
			while(rs.next()) {
				Company c = new Company(rs.getInt("company_id"), rs.getString("name"));
				companies.add(c);
				System.out.println(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companies;
	}

	@Override
	public Company getCompany(int id) {
		Company company = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from company where company_id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				company = new Company(rs.getInt("company_id"),
						rs.getString("name"));
				System.out.println(company);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return company;
	}

	@Override
	public boolean addCompany(Company company) {
		int rows = 0;
		
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into company (name) "
					+ "values (?)");
			pstmt.setString(1, company.getName());
			
			rows = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rows == 1;
	}

	@Override
	public boolean updateCompany(int id) {
		int rows = 0;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Select which field to update: \n1. Name \n2. Transfer department to new company \n3. Remove department \n4. Fire Employee \n5. Transfer employee to new company");
		String input = scan.nextLine();
		
		switch(input) {
		case "1": 
			System.out.println("Enter a new company name");
			String name = scan.next();
			try {
				PreparedStatement pstmt = conn.prepareStatement("update company set name = ? where company_id = ?");
				//company.setName(name);
				pstmt.setString(1, name);
				pstmt.setInt(2, id);
				rows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "2":
			getDepartments(id);
			System.out.println("Enter a department_id from above to transfer this department to a new company");
			int department = scan.nextInt();
			getCompanies();
			System.out.println("Enter a company_id from above to transfer the selected department to that company");
			int company = scan.nextInt();
			try {
				PreparedStatement pstmt = conn.prepareStatement("update company_department set company_id = ? where dept_id = ?");
				pstmt.setInt(1, company);
				pstmt.setInt(2, department);
				rows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "3":
			getDepartments(id);
			System.out.println("Enter a department_id from above to remove it from this company");
			int depart = scan.nextInt();
			try {
				String cd = "delete from company_department where dept_id = ? and company_id = ?";
				PreparedStatement pstmt = conn.prepareStatement(cd);
				pstmt.setInt(1, depart);
				pstmt.setInt(2, id);
				rows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "4":
			getAllEmployees(id);
			System.out.println("Select an employee_id from above to fire that employee");
			int fire = scan.nextInt();
			try {
				String cd = "update employee set company_id = ? where employee_id = ?";
				PreparedStatement pstmt = conn.prepareStatement(cd);
				pstmt.setInt(1, fire);
				pstmt.setInt(2, id);
				rows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "5":
			getAllEmployees(id);
			System.out.println("Select an employee_id to transfer them to a new company");
			int transfer = scan.nextInt();
			break;
		default:
			System.out.println("Invalid input");
		}
		scan.close();
		return rows == 1;
	}

	@Override
	public boolean deleteCompany(int id) {
		int rows = 0;
		
		try {
			String cd = "delete from company_department where company_id = ?";
			String comp = "delete from company where company_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(cd);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			pstmt = conn.prepareStatement(comp);
			pstmt.setInt(1, id);
			rows = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rows == 1;
	}

	@Override
	public List<Department> getDepartments(int id) {
		String sql =  "select * from department where dept_id in (select dept_id from company_department where company_id = ?)";
		
		List<Department> depts = new ArrayList<>();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
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
	public void getAllEmployees(int id) {
		
		DepartmentDAOClass ddc = new DepartmentDAOClass();
		
		List<Department> departments = getDepartments(id);
		
		//ArrayList<List<Employee>> employees = new ArrayList<List<Employee>>();
		
		for(Department d : departments) {
			//employees.add(ddc.printEmployees(d.getId()));
			System.out.println(ddc.printEmployees(d.getId()));
		}
	}	
}
