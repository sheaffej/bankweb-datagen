package com.wackycode.datagen.client;

import java.io.File;
import java.util.ArrayList;

import com.wackycode.datagen.FileLineReader;

public class User {
	public int Id;
	public String FirstName;
	public String LastName;
	public String StreetAddress;
	public String City;
	public String State;
	public String Zip;
	public String PhoneNumber;
	public String Email;
//	public BankUserProfile BankProfile;
	
	public static User[] loadUsers (File file, int numToLoad) {
		// FirstName,LastName,Company,Address,City,County,State,ZIP,Phone,Fax,Email,Web

		ArrayList<User> userList = new ArrayList<User>();
		User[] userArray = null;
		
		FileLineReader reader = new FileLineReader(file);
		String line;

		int linenum = 1;
		while ((line = reader.readLine()) != null && linenum <= numToLoad) {
			String[] fields = line.split("\\t");
			
			User u = new User();
			try {
				u.Id = linenum;
				u.FirstName = fields[0];
				u.LastName = fields[1];
				//u.Company = fields[2];
				u.StreetAddress = fields[3];
				u.City = fields[4];
				//u.Country = fields[5];
				u.State = fields[6];
				u.Zip = fields[7];
				u.PhoneNumber = fields[8];
				//u.Fax = fields[9];
				u.Email = fields[10];
				//u.Website = fields[11];
				
				userList.add(u);
//				System.out.println(u.toString());
			}
			catch (IndexOutOfBoundsException ex) {
				continue;
			}
			
			linenum++; 
		}
		
		// Convert to array and return
		userArray = new User[userList.size()];
		userList.toArray(userArray);
		return userArray;
	} // loadUsers
	
	public String toString() {
		User u = this;
		String str = 
				u.Id + "," +
				u.FirstName + "," +
				u.LastName + "," +
				u.StreetAddress + "," +
				u.City + "," +
				u.State + "," +
				u.Zip + "," +
				u.PhoneNumber + "," +
				u.Email;
//		if (u.BankProfile != null) {
//			BankUserProfile b = u.BankProfile;
//			str += 
//				" [" +
//				"Banking: " + b.HasBanking + "," +
//				"Life: " + b.HasLifeInsurance + "," +
//				"Invest: " + b.HasInvestments + "," +
//				"Advisor: " + b.HasAdvisor + "," +
//				"Trader: " + b.HasActiveTrader +
//				"]";
//		}
		return str;
	} // toString
	
	public String getRecordString() {
		String delim = "\t";
		String record = "";
		
		record += 
			Id + delim
			+ FirstName + delim
			+ LastName + delim
			+  StreetAddress + delim
			+  City + delim
			+  State + delim
			+  Zip + delim
			+  PhoneNumber + delim
			+  Email;
		
		return record;
	} // getRecordString
}
