package com.bank.flow.console;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleExtras {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	public static boolean validAmount(String num) {
		try {
			parseAmount(num);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	public static long parseAmount(String num) {

		int decimal = num.indexOf('.');
		int startIndex = (num.indexOf('$') != -1 ? 0 : 1);
		long amount;
		
		if(decimal == -1)
			amount = Long.parseLong(num.substring(startIndex));
		else {
			String beforeDecimal = num.substring(startIndex,  decimal);
			String afterDecimal = num.substring(decimal + 1);
			if(afterDecimal.length() > 2)
				throw new NumberFormatException();
			amount = (Long.parseLong(beforeDecimal) * 100) + Long.parseLong(afterDecimal);
		}
		if(amount < 0)
			throw new NumberFormatException();
		return amount;
	}
	
	public static String parseAmount(long num) {

		String result = Long.toString(num);
		if(result.length() <= 2)
			return "0." + result;
		else {
			return result.substring(0, result.length() - 3) + "." + result.substring(result.length() - 2);
		}
	}
	
	public static String getTime() {
		return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now());
	}
}
