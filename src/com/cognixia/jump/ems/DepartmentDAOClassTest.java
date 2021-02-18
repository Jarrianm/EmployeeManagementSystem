package com.cognixia.jump.ems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DepartmentDAOClassTest {
	
	DepartmentDAOClass ddc = new DepartmentDAOClass();

	@Test
	void testDeleteDepartment() {
		boolean result = ddc.deleteDepartment(1);
		boolean expected = true;
		assertEquals(result,expected);
	}

	@Test
	void testUpdateDepartment() {
		boolean result = ddc.updateDepartment(1);
		boolean expected = true;
		assertEquals(result,expected);
	}

}
