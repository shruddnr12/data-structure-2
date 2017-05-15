package stack.calc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import list.ArrayList;
import list.Iterator;
import list.List;
import stack.Stack;
import stack.StackException;
import tree.LinkedTree;
import tree.LinkedTree.TreeNode;
public class Calculator {

	private static Calculator instance;

	private Calculator() {
	}

	// singleton method
	public static Calculator getInstance() {
		if( instance == null ) {
			instance = new Calculator();
		}
		return instance;
	}

	public double calculate( String exp ) {
		double result = 0f;

		try {

			List<String> tokens = parseExp(exp.replaceAll("\\s+", ""));
			System.out.println( Arrays.toString( tokens.toArray() ) );

			//강사님꺼
			LinkedTree<String> tree = 
					LinkedTree.toExpressionTree( tokens );
			result = LinkedTree.evaluteExpression( tree.getRoot() );

			
			
			// 내가 한거
			//LinkedTree<String> tree = makeExpTree( tokens );  //수식 트리 생성 
			//result = evaluteExp( tree );  // 계산


			result = operateArimatics( tokens );
		} catch( Exception e ) {
			e.printStackTrace();
		}

		return result;
	}
	public LinkedTree<String> makeExpTree(List<String> tokens){
		Stack<TreeNode<String>> treeList = new Stack<TreeNode<String>>();  //수식 트리를 만들기 위해 임시적으로 저장하는 stack
		LinkedTree<String> linkedTree = new LinkedTree<String>();

		Iterator<String> it = tokens.iterator();
		while(it.hasNext()){
			String str = it.next();
			LinkedTree<String> tree = new LinkedTree<String>(); // 최초의 트리노드. 토큰의 값을 넣어 임시 node로 생성? or 기본생성자로 생성.
			LinkedTree.TreeNode<String> root = tree.getRoot();


			// 토큰은 한번에 추가되는 양은 2개 이하이다.
			// 토큰은 숫자가 아니면 추가를 멈추고 부호 토큰이 들어오면 밑의 자식토큰들을 pop하여 순서대로 오른쪽 왼쪽에 담아야한다.
			// 그리고나서 부호토큰이 든 treenode를 담고 재귀 함수 호출을 통해 다시 treenode를 추가 해야한다.
			if( '1' <= str.charAt(0) && str.charAt(0) <= '9')
				treeList.push(root);  //스택에 treenode형으로 토큰 추가 
			else{
				LinkedTree.TreeNode<String> leftNode;  
				LinkedTree.TreeNode<String> rightNode;
				try {
					root.setData(str);
					leftNode = tree.insertLeft(root, treeList.pop().getData()) ;
					rightNode = tree.insertRight(root, treeList.pop().getData()) ;
					treeList.push(root);

				} catch (StackException e) {
					e.printStackTrace();
				}
			}

		}  //while문 종료
		return linkedTree;
	}
	private double evaluteExp( List<String> tokens ) throws CalculatorException {
		double result = 0.;

		try {
			Stack<Double> arithStack = new Stack<Double>();
			Iterator<String> it = tokens.iterator();

			while( it.hasNext() ) {
				String token = it.next();

				if( Util.isNumeric( token ) ) {
					arithStack.push( Double.parseDouble( token ) );
				} else {
					Double rValue = arithStack.pop();
					Double lValue = arithStack.pop();
					arithStack.push( Util.arith( token, lValue, rValue) );
				}
			}

			if( arithStack.size() != 1 ) {
				throw new CalculatorException( "Abnormal Expression" );
			}

			result = arithStack.pop();

		} catch( StackException e ) {
			throw new CalculatorException( "Abnormal Expression" );
		}


		return result;
	}

	


	private double operateArimatics( List<String> tokens ) throws CalculatorException {
		double result = 0.;

		try {
			Stack<Double> arithStack = new Stack<Double>();
			Iterator<String> it = tokens.iterator();

			while( it.hasNext() ) {
				String token = it.next();

				if( Util.isNumeric( token ) ) {
					arithStack.push( Double.parseDouble( token ) );
				} else {
					Double rValue = arithStack.pop();
					Double lValue = arithStack.pop();
					arithStack.push( Util.arith( token, lValue, rValue) );
				}
			}

			if( arithStack.size() != 1 ) {
				throw new CalculatorException( "Abnormal Expression" );
			}

			result = arithStack.pop();

		} catch( StackException e ) {
			throw new CalculatorException( "Abnormal Expression" );
		}


		return result;
	}

	private List<String> parseExp( String infix ) throws StackException {
		List<String> tokens = new ArrayList<String>();
		Stack<Character> operatorStack = new Stack<Character>();

		for(int i = 0; i < infix.length(); i++) {

			char c = infix.charAt( i );
			switch (c) {
			case '*':
			case '/':
			case '+':
			case '-':
			case '(':
			case ')':
				tokenizeOperator( c, tokens, operatorStack );
				//System.out.println( "operatorStack:" + Arrays.toString( operatorStack.toArray() ) );
				break;
			default:
				tokens.add( String.valueOf( c ) );
				break;
			}
		}

		// 마지막 처리, operator stack 비우기
		tokenizeOperator( tokens, operatorStack );

		return tokens;
	}

	/**
	 * operatorStack 을 비우면서 tokens에 차례대로 담는다.
	 * 
	 * @param tokens
	 * @param operatorStack
	 * @throws StackException
	 */
	private void tokenizeOperator( 
			List<String> tokens, 
			Stack<Character> operatorStack ) 
					throws StackException {

		while( operatorStack.empty() == false ) {
			char operator = operatorStack.pop();
			if( operator == '(' ) {
				//발견 즉시 바닥이 아니더라도 중지
				break;
			}

			tokens.add( String.valueOf( operator ) );
		}		

	}

	/**
	 * 
	 * Postfix Notation Algorithm 에 따라
	 * operator를 기준으로 tokens 또는 operatorStack를 채우고 비운다.
	 * 
	 * @param operator
	 * @param tokens
	 * @param operatorStack
	 * @throws StackException
	 */
	private void tokenizeOperator( 
			char operator, 
			List<String> tokens, 
			Stack<Character> operatorStack ) 
					throws StackException {

		if( operatorStack.empty() || operator == '(' ) {
			operatorStack.push( operator );
			return;
		}

		if( operator == ')' ) { // top 내용이 '(' 또는 빌 때 까지 모두 pop
			tokenizeOperator( tokens, operatorStack );
			return;
		}

		char operatorTop = operatorStack.peek();
		int result = OperatorPriority.comp( operator, operatorTop );

		if( result > 0 ) { // top 연산자보다 우선순위가 크다 ( just push )
			operatorStack.push( operator );
			return;
		}

		if( result == 0 ) { // top 연산자와 우선순위가 같다( exchange top operator )
			tokens.add( String.valueOf( operatorStack.pop() ) );
			operatorStack.push( operator );
			return;
		} 

		// top 연산자와 우선순위가 작다
		// top 내용이 '(' 또는 빌 때 까지 모두 pop 그리고 push
		tokenizeOperator( tokens, operatorStack );
		operatorStack.push( operator );
	}


	/**
	 * 연산자 사이의 우선순위 차이를 비교해서 -1, 0, 1로 반환하기 위한 클래스 
	 * class OperatorPriority
	 *
	 */
	private static class OperatorPriority {
		private static final Map<Character, Integer> MAP  = new HashMap<Character, Integer>();
		static {
			MAP.put( '*', 2 );
			MAP.put( '/', 2 );
			MAP.put( '+', 1 );
			MAP.put( '-', 1 );
			MAP.put( '(', 0 );
		}

		private static int comp( char op1, char op2 ) {
			return MAP.get( op1 ) - MAP.get( op2 );
		}
	}

	private static class Util {
		private static boolean isNumeric( String s ) {
			return s.matches("-?\\d+(\\.\\d+)?");
		}

		private static double arith ( 
				String operator, 
				Double lValue, 
				Double rValue ) throws CalculatorException {

			double result = 0.;
			if( "+".equals( operator ) ) {
				result = lValue + rValue;
			} else if( "-".equals( operator ) ) {
				result = lValue - rValue;
			} else if( "*".equals( operator ) ) {
				result = lValue * rValue;
			} else if( "/".equals( operator ) ) {
				result = lValue / rValue;
			} else {
				throw new CalculatorException( "Operator Not Supported" );
			}

			return result;
		}
	}
}
