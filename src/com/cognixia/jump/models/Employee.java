package com.cognixia.jump.models;

import java.util.Date;

public class Employee {

	private int id;
	private String first_name, last_name;
	private String gender;
	private Date dob;
	private int salary;
	private Address address; //and a foreign key to address
	private Department department; //employee has a foreign key to department
	private Company company;
	
	public Employee(int id, String first_name, String last_name, String gender, Date dob, int salary, Address address,
			Department department, Company company) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.gender = gender;
		this.dob = dob;
		this.salary = salary;
		this.setAddress(address);
		this.setDepartment(department);
		this.setCompany(company);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", gender=" + gender
				+ ", dob=" + dob + ", salary=" + salary + ", address=" + address + ", department=" + department
				+ ", company=" + company + "]";
	}
}
