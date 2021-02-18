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

public class AddressDAOClass implements AddressDAO{
	Connection conn = ConnectionManager.getConnection();

	@Override
	public List<Address> getAllAddresses() {
		List<Address> address = new ArrayList<>();
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from address");
			
			while(rs.next()) {
				Address a = new Address(rs.getInt("address_id"),
						rs.getString("street"),
						rs.getString("city"),
						rs.getString("state"),
						rs.getString("zip_code"));
				address.add(a);
				System.out.println(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return address;
	}

	@Override
	public Address getAddress(int id) {
		
		Address address = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from address where address_id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				address = new Address(rs.getInt("address_id"),
						rs.getString("street"),
						rs.getString("city"),
						rs.getString("state"),
						rs.getString("zip_code"));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return address;
	}

	@Override
	public boolean addAddress(Address address) {
		int rows = 0;
		
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into address (street, city, state, zip_code) "
					+ "values (?,?, ?, ?)");
			pstmt.setString(1, address.getStreet());
			pstmt.setString(2, address.getCity());
			pstmt.setString(3, address.getState());
			pstmt.setString(4, address.getZip());
			
			rows = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rows == 1;
	}

	@Override
	public boolean updateAddress(int id) {
		
		int rows = 0;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Select which field to update: \n1. Street \n2. City \n3. State \n4.Zip Code");
		String input = scan.nextLine();
		
		switch(input) {
		case "1": 
			System.out.println("Enter a new street name");
			String name = scan.next();
			try {
				PreparedStatement pstmt = conn.prepareStatement("update address set street = ? where address_id = ?");
				pstmt.setString(1, name);
				pstmt.setInt(2, id);
				rows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "2":
			System.out.println("Enter a new city name");
			String city = scan.next();
			try {
				PreparedStatement pstmt = conn.prepareStatement("update address set city = ? where address_id = ?");
				pstmt.setString(1, city);
				pstmt.setInt(2, id);
				rows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "3": 
			System.out.println("Enter a new state name");
			String state = scan.next();
			try {
				PreparedStatement pstmt = conn.prepareStatement("update address set state = ? where address_id = ?");
				pstmt.setString(1, state);
				pstmt.setInt(2, id);
				rows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "4":
			System.out.println("Enter a new city name");
			String zip = scan.next();
			try {
				PreparedStatement pstmt = conn.prepareStatement("update address set zip_code = ? where address_id = ?");
				pstmt.setString(1, zip);
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
	public boolean deleteAddress(int id) {
		
		int rows = 0;
		
		try {
			String update = "update employee set address_id = 0 where address_id = ?";
			PreparedStatement pstmt = conn.prepareStatement("delete from address where address_id = ?");
			pstmt.setInt(1, id);
			rows = pstmt.executeUpdate();
			pstmt = conn.prepareStatement(update);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rows == 1;
	}
}
