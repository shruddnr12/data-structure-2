package recursion;

public class RecursionFactorial {

	public static void main(String[] args) {
		System.out.println("1! = " + factorial(1));
		System.out.println("2! = " + factorial(2));
		System.out.println("3! = " + factorial(3));
		System.out.println("4! = " + factorial(4));
		System.out.println("9! = " + factorial(9));
		
	}

	public static int factorial(int n){
		if(n == 1){
			return 1; 
		}
		else{  //의미상 명확하게 해주기 위해서 else문을 사용하였다.
			return n * factorial(n-1);			
		}
		
	}
}
