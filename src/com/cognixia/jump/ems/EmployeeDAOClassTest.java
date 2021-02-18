package com.cognixia.jump.ems;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.cognixia.jump.models.Employee;

class EmployeeDAOClassTest {
	
	EmployeeDAOClass e = new EmployeeDAOClass();

	@Test
	void testUpdateEmployee() {
		
		boolean result = e.updateEmployee(1);
		boolean expected = true;
		assertEquals(result, expected);
	}

	@Test
	void testDeleteEmployee() {
		
		boolean result = e.deleteEmployee(1);
		boolean expected = true;
		assertEquals(result, expected);
		
	}

}
