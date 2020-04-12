package edu.iastate.cs228.hw4;

/**
 *  
 * @author
 *
 */

/**
 * 
 * This class evaluates a postfix expression using one stack.    
 *
 */

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PostfixExpression extends Expression {
	private int leftOperand; // left operand for the current evaluation step
	private int rightOperand; // right operand (or the only operand in the case of
								// a unary minus) for the current evaluation step

	private PureStack<Integer> operandStack; // stack of operands

	/**
	 * Constructor stores the input postfix string and initializes the operand
	 * stack.
	 * 
	 * @param st     input postfix string.
	 * @param varTbl hash map that stores variables from the postfix string and
	 *               their values.
	 */
	public PostfixExpression(String st, HashMap<Character, Integer> varTbl) {
		super(st,varTbl);
		leftOperand = 0;
		rightOperand = 0;
		operandStack = new ArrayBasedStack<Integer>();
	}

	/**
	 * Constructor supplies a default hash map.
	 * 
	 * @param s
	 */
	public PostfixExpression(String s) {
		super(s);		
		leftOperand = 0;
		rightOperand = 0;
		super.setVarTable(super.varTable);
		operandStack = new ArrayBasedStack<Integer>();
	}

	/**
	 * Outputs the postfix expression according to the format in the project
	 * description.
	 */
	@Override
	public String toString() {
		return super.postfixExpression;
	}

	/**
	 * Resets the postfix expression.
	 * 
	 * @param st
	 */
	public void resetPostfix(String st) {
		postfixExpression = st;
	}

	/**
	 * Scan the postfixExpression and carry out the following:
	 * 
	 * 1. Whenever an integer is encountered, push it onto operandStack. 2. Whenever
	 * a binary (unary) operator is encountered, invoke it on the two (one) elements
	 * popped from operandStack, and push the result back onto the stack. 3. On
	 * encountering a character that is not a digit, an operator, or a blank space,
	 * stop the evaluation.
	 * 
	 * @return value of the postfix expression
	 * @throws ExpressionFormatException with one of the messages below:
	 * 
	 *                                   -- "Invalid character" if encountering a
	 *                                   character that is not a digit, an operator
	 *                                   or a whitespace (blank, tab); -- "Too many
	 *                                   operands" if operandStack is non-empty at
	 *                                   the end of evaluation; -- "Too many
	 *                                   operators" if getOperands() throws
	 *                                   NoSuchElementException; -- "Divide by zero"
	 *                                   if division or modulo is the current
	 *                                   operation and rightOperand == 0; -- "0^0"
	 *                                   if the current operation is "^" and
	 *                                   leftOperand == 0 and rightOperand == 0; --
	 *                                   self-defined message if the error is not
	 *                                   one of the above.
	 * 
	 *                                   UnassignedVariableException if the operand
	 *                                   as a variable does not have a value stored
	 *                                   in the hash map. In this case, the
	 *                                   exception is thrown with the message
	 * 
	 *                                   -- "Variable <name> was not assigned a
	 *                                   value", where <name> is the name of the
	 *                                   variable.
	 * 
	 */
	//This method is still very iffy. Im not totally sure whats going down and i still need to deal with the exceptions.
	public int evaluate() {
		
		Scanner sc = new Scanner(postfixExpression);
		String curr;
		int value;
		int ov;
		
		while (sc.hasNext()) {
			
			curr = sc.next();
			
			if (super.isInt(curr) == false && super.isOperator(curr.charAt(0)) == false && super.isVariable(curr.charAt(0)) == false) break;
			
			//is value
			if (super.isInt(curr)) {
				value = Integer.parseInt(curr);
				operandStack.push(value);
			}
			//is variable
			else if (super.isVariable(curr.charAt(0))) {
				value = super.varTable.get(curr.charAt(0));
				operandStack.push(value);
			}
			//is operator
			else {
				getOperands(curr.charAt(0));
				value = compute(curr.charAt(0));
				operandStack.push(value);
				
			}
		}
		
		return operandStack.pop();
	}

	/**
	 * For unary operator, pops the right operand from operandStack, and assign it
	 * to rightOperand. The stack must have at least one entry. Otherwise, throws
	 * NoSuchElementException. For binary operator, pops the right and left operands
	 * from operandStack, and assign them to rightOperand and leftOperand,
	 * respectively. The stack must have at least two entries. Otherwise, throws
	 * NoSuchElementException.
	 * 
	 * @param op char operator for checking if it is binary or unary operator.
	 */
	private void getOperands(char op) throws NoSuchElementException {

		if (op == '~') {
			if (operandStack.size() != 0) {
				this.rightOperand = operandStack.pop();
			} else {
				throw new NoSuchElementException();
			}
		} else {
			if (operandStack.size() >= 2) {

				this.rightOperand = operandStack.pop();
				this.leftOperand = operandStack.pop();
			} else {
				throw new NoSuchElementException();
			}

		}

	}

	/**
	 * Computes "leftOperand op rightOprand" or "op rightOprand" if a unary
	 * operator.
	 * 
	 * @param op operator that acts on leftOperand and rightOperand.
	 * @return
	 */
	private int compute(char op) {
		int cur;
		int returner = 0;
		switch (op) {
		case '~':
			returner = 0 - rightOperand;
			break;
		case '+':

			returner =  leftOperand + rightOperand;
			break;
		case '-':

			returner = leftOperand - rightOperand;
			break;
		case '*':

			returner = leftOperand * rightOperand;
			break;
		case '/':

			returner = leftOperand / rightOperand;
			break;
		case '%':

			returner = leftOperand % rightOperand;
			break;
		case '^':
			cur = leftOperand;
			for (int i = 0; i<rightOperand-1; i++) {
				leftOperand *= cur;
			}
			returner = leftOperand;
			break;
		}
		return returner; 
	}
}
