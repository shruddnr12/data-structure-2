package stack.calc;

public class CalculatorApp {

	public static void main(String[] args) {
		try {
			
			Calculator calculator = Calculator.getInstance();

			double result = calculator.calculate("2 *         1");
			System.out.println(result);

			result = calculator.calculate("2 * 1 + 3 / 2  ");
			System.out.println(result);

			result = calculator.calculate("(1 + 2 * 3 ) /   7");
			System.out.println(result);

			result = calculator.calculate("( 1 + 2 ) * ( 3 / 4 ) + ( 5 + ( 6 - 7 )   )  ");
			System.out.println(result);

			// expression error!!
			result = calculator.calculate("(1 + 2 + 3   7");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
