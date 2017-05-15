package stack.calc;

public class CalculatorException extends Exception {
	private static final long serialVersionUID = 1L;

	public CalculatorException() {
		super( "CalculatorException Exception Occurs" );
	}

	public CalculatorException( String message ) {
		super( message );
	}
	
}
