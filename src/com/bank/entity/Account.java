package com.bank.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bank.flow.console.ConsoleExtras;
import com.bank.security.Encrypt;

public class Account {

	private String userId;
	private String password;
	
	private String name;
	private String address;
	private String contactNumber;
	private long balance = 0;
	private List<String> transactions;

	// used in phone number validation
	private static final String validPhonePatterns;
	private static final Pattern phonePattern;
	
	public static boolean validPhone(String phoneNum) {
		Matcher matcher = phonePattern.matcher(phoneNum);
		return matcher.matches();
	}
	
	// returns null if everything is fine, otherwise returns the error
	public static String validPassword(String password) {
		// check for minimum length
		if(password.length() < 8)
			return "Less Than 8 Characters";
		
		// check for required characters
		boolean lower = false, upper = false, special = false;
		for(int i = password.length() - 1; i >= 0; --i) {
			if((password.charAt(i) >= 'a') && (password.charAt(i) <= 'z'))
				lower = true;
			else if((password.charAt(i) >= 'A') && (password.charAt(i) <= 'Z'))
				upper = true;
			else
				special = true;
		}
		
		if(!lower)
			return "No Lowercase Characters";
		else if(!upper)
			return "No Uppercase Characters";
		else if(!special)
			return "No Special Characters";
		else
			return null;
	}
	
	public boolean correctPassword(String password) {
		return this.password.equals(Encrypt.encrypt(password));
	}
	
	public Account(String userId, String password, String name, String address, String contactNumber, long balance, List<String> transactions) {
		super();
		
		if(!validPhone(contactNumber))
			throw new RuntimeException("Invalid contact number supplied to Account constructor");
		
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.address = address;
		this.contactNumber = contactNumber;
		this.balance = balance;
		this.transactions = transactions;
	}
	
	public Account() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = Encrypt.encrypt(password);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNumber() {
		return contactNumber;
	}
	
	public boolean setContactNumber(String contactNumber) {
		if(!validPhone(contactNumber))
			return false;
		this.contactNumber = contactNumber;
		return true;
	}

	public long getBalance() {
		return balance;
	}

	public void addAmount(long amount, String message) {
		transactions.add("Added " + ConsoleExtras.parseAmount(amount) + " (" + message + ") [" + ConsoleExtras.getTime() + "]");
		this.balance += amount;
	}
	
	public void subtractAmount(long amount, String message) {
		transactions.add("Removed " + ConsoleExtras.parseAmount(amount) + " (" + message + ") [" + ConsoleExtras.getTime() + "]");
		this.balance -= amount;
	}
	
	public List<String> getTransactions() {
		return transactions;
	}
	
	static {
		validPhonePatterns  = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" 
							+ "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$" 
							+ "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
		phonePattern = Pattern.compile(validPhonePatterns);
	}
}
