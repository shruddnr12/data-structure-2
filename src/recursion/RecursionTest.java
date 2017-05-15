package recursion;

public class RecursionTest {

	public static void main(String[] args) {
		int count = 0;
		recursive(count);
	}
	public static void recursive(int num){
		if(num >= 3){
			return ;
		}
		num ++;
		System.out.println("recursive() called :" + num );
		recursive(num);
	}
}
