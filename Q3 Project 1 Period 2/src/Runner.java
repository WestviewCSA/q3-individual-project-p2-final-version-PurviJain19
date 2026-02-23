import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Runner {
	
	public static String[][] mapArr;
	private static int rows; 
	private static int cols;
	private static int nums;

	public static void main(String[] args) {
		readFile("hardMap2");
		
	}
	
	public static void readFile(String fileName) { //map based input file
		try {
			File file = new File(fileName);
			Scanner scanner = new Scanner(file);
			
			rows = Integer.parseInt(scanner.next());
			cols = Integer.parseInt(scanner.next());
			nums = Integer.parseInt(scanner.next());
			mapArr= new String[rows*nums][cols];
			
			String newRow = scanner.next();

			for(int r = 0; r <mapArr.length; r++) {
				for(int c = 0; c < cols; c++) {
					mapArr[r][c] = newRow.substring(c,c+1);
					
				}
			}			
			
			System.out.println(Arrays.deepToString(mapArr));
			scanner.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("File Not Found!");
		}
	}
	
	public static void readCoorFile(String fileName) {// IN PROGESS= coordinate based input file
		try {
			File file = new File(fileName);
			Scanner scanner = new Scanner(file);
			
			rows = Integer.parseInt(scanner.next());
			cols = Integer.parseInt(scanner.next());
			nums = Integer.parseInt(scanner.next());
			mapArr= new String[rows*nums][cols];
			
			String newRow = scanner.next();

			for(int r = 0; r <mapArr.length; r++) {
				for(int c = 0; c < cols; c++) {
					mapArr[r][c] = newRow.substring(c,c+1);
					
				}
			}			
			
			System.out.println(Arrays.deepToString(mapArr));
			scanner.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("File Not Found!");
		}
	}

}
