package edu.iastate.cs228.hw4;

import java.util.HashMap;
import java.util.Scanner;

public abstract class Expression {
	protected String postfixExpression;
	protected HashMap<Character, Integer> varTable; // hash map to store variables in the
	private Scanner sc;
	private Scanner scan;
	private static Scanner scanner;

	protected Expression() {
		// no implementation needed
		// removable when you are done
	}

	/**
	 * Initialization with a provided hash map.
	 * 
	 * @param varTbl
	 */
	protected Expression(String st, HashMap<Character, Integer> varTbl) {
		postfixExpression = st;
		varTable = varTbl;
		this.varTable = varTbl;
		// TODO
	}

	/**
	 * Initialization with a default hash map.
	 * 
	 * @param st
	 */
	protected Expression(String st) {
		postfixExpression = st;
		setVarTable(new HashMap<Character, Integer>());

	}
	// TO DO

	/**
	 * Setter for instance variable varTable.
	 * 
	 * @param varTbl
	 */
	public void setVarTable(HashMap<Character, Integer> varTbl) {
		this.varTable = varTbl;
	}

	/**
	 * Evaluates the infix or postfix expression.
	 * 
	 * @return value of the expression
	 * @throws ExpressionFormatException, UnassignedVariableException
	 */
	public abstract int evaluate() throws ExpressionFormatException, UnassignedVariableException;

	// --------------------------------------------------------
	// Helper methods for InfixExpression and PostfixExpression
	// --------------------------------------------------------

	/**
	 * Checks if a string represents an integer. You may call the static method
	 * Integer.parseInt().
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}
		catch(Exception NumerFormatException) {
			return false;
		}
		
	}

	/**
	 * Checks if a char represents an operator, i.e., one of '~', '+', '-', '*',
	 * '/', '%', '^', '(', ')'.
	 * 
	 * @param c
	 * @return
	 */
	protected static boolean isOperator(char c) {
		if (c == '~' || c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^' || c == '(' || c == ')') {
			return true;
		}
		else {
		return false;
		}
	}

	/**
	 * Checks if a char is a variable, i.e., a lower case English letter.
	 * 
	 * @param c
	 * @return
	 */
	protected static boolean isVariable(char c) {

		if(Character.isLetter(c) == true) {
			return true;
		}
		else {
			return false;
		}
		
	}

	/**
	 * Removes extra blank spaces in a string.
	 * 
	 * @param s
	 * @return
	 */
	protected static String removeExtraSpaces(String s) {
	
		String n = "";
		scanner = new Scanner(s);
		while(scanner.hasNext()) {
			n += scanner.next();	
		}
		
		return n;
	}

}
