package com.bank.dao;

import java.util.HashMap;

import com.bank.entity.Account;

public class DollarsBankDao {

	private static HashMap<String, Account> accounts;
	private static String error = null;
	
	public static boolean idIsUnique(String id) {
		return !accounts.containsKey(id);
	}
	
	public static boolean addAccount(Account account) {
		if(accounts.containsKey(account.getUserId()))
			return false;
		accounts.put(account.getUserId(), account);
		return true;
	}
	
	public static Account getAccount(String userId, String password) {
		Account account = accounts.get(userId);
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
	
	public static boolean transfer(Account from, Account to, long amount) {
		if(validateTransaction(from, amount) && to != null) {
			from.subtractAmount(amount, "transfer to " + to.getUserId().toString());
			to.addAmount(amount, "transfer from " + from.getUserId().toString());
			return true;
		}
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
}
