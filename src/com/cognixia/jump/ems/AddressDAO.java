package com.cognixia.jump.ems;

import java.util.List;

import com.cognixia.jump.models.Address;

public interface AddressDAO {
	List<Address> getAllAddresses();
	Address getAddress(int id);
	boolean addAddress(Address address);
	boolean updateAddress(int id);
	boolean deleteAddress(int id);
}
