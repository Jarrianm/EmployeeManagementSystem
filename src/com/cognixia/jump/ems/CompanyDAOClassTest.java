package com.cognixia.jump.ems;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CompanyDAOClassTest {
	CompanyDAOClass cdc = new CompanyDAOClass();
	@Test
	void testUpdateCompany() {
		boolean result = cdc.updateCompany(1);
		boolean expected = true;
		assertEquals(result,expected);
	}

	@Test
	void testDeleteCompany() {
		boolean result = cdc.deleteCompany(1);
		boolean expected = true;
		assertEquals(result,expected);
	}

}
