package com.bank.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.bank.entity.Account;

public class DollarsBankDao {

	private static String error = null;
	private static String dbname;
	private static String dbpass;
	private static String dburl;
	private static String dbdriver = "com.mysql.jdbc.Driver";
	
	static {
		changeServer("jdbc:mysql://localhost/?useSSL=false", "root", "IhtSQLiaehl,lttft!0");
	}
	
	public static boolean idExists(String id) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		boolean result = false;
		try {
			stmt = conn.prepareStatement("select id from usercrud where id=?");
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			result = rs.next();
			rs.close();
			
		} catch (SQLException e) {
			error = e.getMessage();
		}
		return result;
	}
	
	public static boolean idIsUnique(String id) {
		return !idExists(id);
	}
	
	public static boolean addAccount(Account account) {
		if(idExists(account.getUserId()))
			return false;
		
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		boolean result = false;
		try {
			stmt = conn.prepareStatement("insert into accounts(id,password,name,address,contact,balance)values(?,?,?,?,?,?)");
			stmt.setString(1,  account.getUserId());
			stmt.setString(2,  account.getPassword());
			stmt.setString(3,  account.getName());
			stmt.setString(4,  account.getAddress());
			stmt.setString(5,  account.getContactNumber());
			stmt.setLong(6,  account.getBalance());
			if(stmt.executeUpdate() == 0) {
				result = false;
				error = "Account Could Not Be Added To Database";
			}
			else
				result = true;
			
		} catch (SQLException e) {
			result = false;
			error = e.getMessage();
		}
		
		return result;
	}
	
	public static Account getAccount(String userId, String password) {
		Account account = null;// = accounts.get(userId);
		
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("select * from accounts where id=?");
			stmt.setString(1, userId);
			ResultSet result = stmt.executeQuery();
			
			if(result.next())
			{
				account = new Account(userId,
									result.getString("password"),
									result.getString("name"),
									result.getString("address"),
									result.getString("contact"),
									result.getLong("country"),
									result.get);
				return emp;
			}
			else
				System.out.println("No employee of id " + id + " exists");
			
		} catch (SQLException e) {
			error = e.getMessage();
		}
		return null;
		
		if((account != null) && (account.correctPassword(password)))
			return account;
		
		error = "Invalid Credentials";
		return null;
	}
	
	public static void deposit(Account acc, long amount) {
		acc.addAmount(amount, "local deposit");
	}
	
	public static boolean withdraw(Account acc, long amount) {
		if(validateTransaction(acc, amount)) {
			acc.addAmount(amount, "local withdrawal");
			return true;
		}
		return false;
	}
	
	public static boolean transfer(Account from, String toId, long amount) {
		return transfer(from, accounts.get(toId), amount);
	}
	
	public static boolean transfer(Account from, Account to, long amount) {
		if(validateTransaction(from, amount)) {
			if(to == null)
				error = "Target Account Does Not Exist";
			else {
				from.subtractAmount(amount, "transfer to " + to.getUserId().toString());
				to.addAmount(amount, "transfer from " + from.getUserId().toString());
				return true;
			}
		}
		else
			error = "Source Account Does Not Have Sufficient Funds";
		return false;
	}
	
	public static String getError() {
		String temp = error;
		error = null;
		return temp;
	}
	
	private static boolean validateTransaction(Account account, long amount) {
		
		if(account.getBalance() < amount) {
			error = account.getUserId() + " does not have sufficient funds";
			return false;
		}
		return true;
	}
	
	private static boolean changeServer(String url, String username, String password) {
		Connection conn = null;
		Statement stmt = null;
		dbname = username;
		dbpass = password;
		dburl = url + "/dollarsbank";
		try {
			// attempt connection to server
			Class.forName(dbdriver);
			conn = DriverManager.getConnection(url, username, password);
		
			// check for 'dollarsbank' database
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("show databases like 'dollarsbank'");
			// if it doesn't exist we'll create it
			if(!rs.next())
				stmt.executeUpdate("create database dollarsbank");
			rs.close();
			
			// attempt connect to database
			conn = DriverManager.getConnection(dburl, dbname, dbpass);
			
			// check for 'accounts' table
			DatabaseMetaData meta = conn.getMetaData();
			rs = meta.getTables(null, null, "accounts", new String[] {"TABLE"});
			// if it doesn't exist we'll create it
			if(!rs.next()) {
				stmt.executeUpdate("create table accounts ("
								+ "id varchar(128), "
								+ "password varchar(128), "
								+ "name varchar(128), "
								+ "address varchar(255), "
								+ "contact varchar(20), "
								+ "balance bigint"
								+ "transactions varchar(1024))");
			}
			rs.close();
		} catch(Exception e) {
			error = e.getMessage();
		} finally {
			try {
				if(stmt != null)
					stmt.close();
			} catch(SQLException e) {
				// don't override the meaningful error with a mere symptom
				if(error == null)
					error = e.getMessage();
			}
		}
		if(conn == null)
			return false;
		else
			return true;
	}
	
	private static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(dbdriver);
			conn = DriverManager.getConnection(dburl, dbname, dbpass);
		} catch (Exception e) {
			error = e.getMessage();
			return null;
		}
		return conn;
	}
}
