package edu.iastate.cs228.hw4;

import java.util.ArrayList;

/**
 *  
 * @author
 *
 */

import java.util.HashMap;
import java.util.Scanner;

/**
 * 
 * This class represents an infix expression. It implements infix to postfix
 * conversion using one stack, and evaluates the converted postfix expression.
 *
 */

public class InfixExpression extends Expression {
	private String infixExpression; // the infix expression to convert

	private boolean postfixReady = false; // postfix already generated if true
	private int rankTotal = 0; // Keeps track of the cumulative rank of the infix expression.
	private String p;
	private int right, left;

	private PureStack<Operator> operatorStack; // stack of operators
	private Scanner sc;
	private Scanner scanner;

	/**
	 * Constructor stores the input infix string, and initializes the operand stack
	 * and the hash map.
	 * 
	 * @param st     input infix string.
	 * @param varTbl hash map storing all variables in the infix expression and
	 *               their values.
	 */
	public InfixExpression(String st, HashMap<Character, Integer> varTbl) {
		super(st, varTbl);
		infixExpression = st;
//		super.postfixExpression = infixExpression;

	}

	/**
	 * Constructor supplies a default hash map.
	 * 
	 * @param s
	 */
	public InfixExpression(String s) {
		super(s);
		operatorStack = new ArrayBasedStack<Operator>();

		infixExpression = s;

	}

	/**
	 * Outputs the infix expression according to the format in the project
	 * description.
	 */
	@Override
	public String toString() {
		return InfixExpression.spaceOut(infixExpression);
	}

	/**
	 * @return equivalent postfix expression, or
	 * 
	 *         a null string if a call to postfix() inside the body (when
	 *         postfixReady == false) throws an exception.
	 */
	public String postfixString() {
		if (postfixReady == true) {
			return super.postfixExpression;
		} else {
			try {
				return super.postfixExpression;

			}

			catch (Exception ExpressionFormatException) {
				return null;
			}
		}
	}

	/**
	 * Resets the infix expression.
	 * 
	 * @param st
	 */
	public void resetInfix(String st) {
		infixExpression = st;
	}

	/**
	 * Converts infix expression to an equivalent postfix string stored at
	 * postfixExpression. If postfixReady == false, the method scans the
	 * infixExpression, and does the following (for algorithm details refer to the
	 * relevant PowerPoint slides):
	 * 
	 * 1. Skips a whitespace character. 2. Writes a scanned operand to
	 * postfixExpression. 3. When an operator is scanned, generates an operator
	 * object. In case the operator is determined to be a unary minus, store the
	 * char '~' in the generated operator object. 4. If the scanned operator has a
	 * higher input precedence than the stack precedence of the top operator on the
	 * operatorStack, push it onto the stack. 5. Otherwise, first calls
	 * outputHigherOrEqual() before pushing the scanned operator onto the stack. No
	 * push if the scanned operator is ). 6. Keeps track of the cumulative rank of
	 * the infix expression.
	 * 
	 * During the conversion, catches errors in the infixExpression by throwing
	 * ExpressionFormatException with one of the following messages:
	 * 
	 * -- "Operator expected" if the cumulative rank goes above 1; -- "Operand
	 * expected" if the rank goes below 0; -- "Missing '('" if scanning a �)�
	 * results in popping the stack empty with no '('; -- "Missing ')'" if a '(' is
	 * left unmatched on the stack at the end of the scan; -- "Invalid character" if
	 * a scanned char is neither a digit nor an operator;
	 * 
	 * If an error is not one of the above types, throw the exception with a message
	 * you define.
	 * 
	 * Sets postfixReady to true.
	 * @throws Exception 
	 */
	// deal with the rank stuff
	public void postfix() throws Exception {
		sc = new Scanner(infixExpression);
		String curr;
		int index = 0;
		Operator o;
		super.postfixExpression = "";
		
		

		while (sc.hasNext()) {
			curr = sc.next();
			if (!Expression.isOperator(curr.charAt(0)) && !Expression.isVariable(curr.charAt(0)) && !Expression.isInt(curr)) {
				throw new Exception("Invalid character");
			}

			// is operator
			if (super.isOperator(curr.charAt(0))) {
				
				switch (curr.charAt(0)) {
				case '(':
					left++;
					break;
				case ')':
					right++;
					break;
				}
				
				
				if (curr.charAt(0) != '(' && curr.charAt(0) != ')' && !isMinus(infixExpression, index))
					rankTotal--;

				if (isMinus(infixExpression, index) == true) {

					o = (new Operator('~'));
				} else {
					o = new Operator(curr.charAt(0));
				}
				// if top compare cur == -1 push

				if (operatorStack.size() != 0) {
					if (operatorStack.peek().compareTo(o) == -1) {
						operatorStack.push(o);

					}

					else {

						outputHigherOrEqual(o);

					}
				} else {
					operatorStack.push(o);
				}

			}
			// is a number or variable i.e. not operator.
			else {
				super.postfixExpression = super.postfixExpression + curr + " ";
				
				rankTotal++;
			}

			if (rankTotal > 1) {
				
				throw new Exception("Operator Expected");
//				failed = true;
//				super.postfixExpression = "";
//				break;
			}

			else if (rankTotal < -1) {
				
				throw new Exception("Operand Expected");
//				failed = true;
//				super.postfixExpression = "";
//				break;
			}
			else if (right > left) {
				throw new Exception("Missing '('");
			}

			index++;
		}

		if (operatorStack.size() != 0) {
			while (operatorStack.isEmpty() == false) {
				
				if (operatorStack.peek().operator == '(') {
					throw new Exception("Missing ')'");
				}
				
				super.postfixExpression = super.postfixExpression + operatorStack.pop().operator + " ";
				
				
			}
		}
	}

	/**
	 * This function first calls postfix() to convert infixExpression into
	 * postfixExpression. Then it creates a PostfixExpression object and calls its
	 * evaluate() method (which may throw an exception). It also passes any
	 * exception thrown by the evaluate() method of the PostfixExpression object
	 * upward the chain.
	 * 
	 * @return value of the infix expression
	 * @throws ExpressionFormatException, UnassignedVariableException
	 */
	public int evaluate() {
		int value = 0;
		PostfixExpression p = new PostfixExpression(super.postfixExpression);
		value = p.evaluate();
		return value;
	}

	/**
	 * Pops the operator stack and output as long as the operator on the top of the
	 * stack has a stack precedence greater than or equal to the input precedence of
	 * the current operator op. Writes the popped operators to the string
	 * postfixExpression.
	 * 
	 * If op is a ')', and the top of the stack is a '(', also pops '(' from the
	 * stack but does not write it to postfixExpression.
	 * 
	 * @param op current operator
	 */
	private void outputHigherOrEqual(Operator op) {
		Operator curr = operatorStack.peek();
		if (curr.compareTo(op) >= 0) {
			if (op.operator == ')') {// && curr.operator == '(') {
				while (operatorStack.peek().operator != '(') {
					super.postfixExpression += operatorStack.pop().operator + " ";
				}
				operatorStack.pop();
			} else {
				super.postfixExpression += operatorStack.pop().operator + " ";
				p = super.postfixExpression;
				operatorStack.push(op);
			}
		} else {
			operatorStack.push(op);
		}

	}

	private boolean isMinus(String s, int index) {
		ArrayList<String> temp = new ArrayList<String>();
		scanner = new Scanner(s);
		String curr = "";
		while (scanner.hasNext()) {
			curr = scanner.next();
			temp.add(curr);
		}
		if (temp.get(index).charAt(0) == '-') {
			if (index == 0) {
				return true;
			} else if (super.isOperator(temp.get(index - 1).charAt(0)) == true) {
				if (temp.get(index - 1).charAt(0) != ')') {
					return true;
				} else {
					return false;
				}

			}
			if (temp.get(index - 1).charAt(0) == '(') {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static String spaceOut(String s) {

		String newString = s;
		int length = s.length();
		for (int i = 0; i < length; i++) {
			if (newString.charAt(i) == '(') {
				newString = newString.substring(0, i+1) + newString.substring(i + 2);
				length--;
			} else if (newString.charAt(i) == ')') {
				newString = newString.substring(0, i-1) + newString.substring(i);
				i--;
				length--;

			}
		}
		return newString;
	}
}
