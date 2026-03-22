import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class p1 {
	//storing postion and main parent for the path 
	public static class Position{
		int row;
		int col; 
		int lev; //this for teh levels
		Position mainParent;
		
	public Position(int row, int col, int lev, Position mainParent) {
		this.row = row; 
		this.col = col;
		this.lev = lev;
		this.mainParent = mainParent;
		
	}
	
}

public static void main(String[] args) throws Exception{
	boolean queueUse = false;
	boolean stackUse = false;
	boolean optimalUse = false;
	boolean showTimes = false;
	boolean coordinateIn = false;
	boolean coordinateOut = false;
	boolean EC = false;
	
	//parsing arguemnets
	
	for(int i = 0; i < args.length; i++) {
		if(args[i].equals("--Queue")) {
			queueUse = true;
		}
		else if(args[i].equals("--Stack")) {
			stackUse = true;
		}
		else if(args[i].equals("--Optimal")) {
			optimalUse = true;
		}
		else if(args[i].equals("--Time")) {
			showTimes = true;
		}
		else if(args[i].equals("--Incoordinate")) {
			coordinateIn = true;
		}
		else if(args[i].equals("--Outcoordinate")) {
			coordinateOut = true;
		}
		else if(args[i].equals("--Extra Credit")) {
			EC = true;
		}
	}
	
	//verify only one mode but this doesnt count for teh EC
	int count = 0;
	if(queueUse) {
		count ++;
	}
	if(stackUse) {
		count ++;
	}
	if(optimalUse) {
		count ++;
	}
	
	if(EC == false && count != 1) {
		throw new IllegalCommandLineInputsException();
	}
	
	String nameFile = args[args.length -1];
	Scanner sc = new Scanner(new File(nameFile));
	
	int m = sc.nextInt();
	int n = sc.nextInt();
	int r = sc.nextInt();
	
	if(m <= 0 || n <= 0 || r <= 0) {
		throw new IncorrectMapFormatException();
	}
	
	char[][][] maze = new char[r][m][n];
	
	//thsi will fill in teh default
	for(int j = 0; j < r; j++) {
		for(int i = 0; i < m; i++) {
			for(int t = 0; t < n; t++) {
				maze[j][i][t]= '.';
			}
		}
	}
	
	int sr = -1; 
	int sc2 = -1; 
	int sl = 0;
	
	sc.nextLine();
	
	//inputs
	if(!coordinateIn) {
		for(int i = 0; i < r; i++) {
			for(int k = 0; k < m; k++) {
				if(!sc.hasNextLine()) {
					throw new IncompleteMapException();
				}
				
				String line = sc.nextLine();
				
				if(line.length() < n) {
					throw new IncompleteMapException();
				}
				
				for(int j =0; j < n; j++) {
					char c = line.charAt(j);
					
					if("@.$W|".indexOf(c) == -1) {
						throw new IllegalMapCharacterException();
					}
					
					maze[i][k][j] = c;
					if(c == 'W') {
						sr = i;
						sc2 = j;
						sl = k;
					}
				}
			}
		}
	}
	
	else {
		while(sc.hasNext()) {
			char C = sc.next().charAt(0);
			int r2 = sc.nextInt();
			int c = sc.nextInt();
			int l = sc.nextInt();
			
			if("@.$W|".indexOf(C) == -1) {
				throw new IllegalMapCharacterException();
			}
			if(r2 < 0 || r >= m || c < 0 || c>= n|| l<0|| l>=r) {
				throw new IncorrectMapFormatException();
			}
			maze[l][r2][c] = C;
			if(C == 'W') {
				sr = r2;
				sc2 = c; 
				sl = l;
			}
			
		}
	}
	
	long timeStart = System.nanoTime();
	
	//this for teh EXTRA CREDITTT
	if(EC) {
		runEC(maze, sr,sc2,sl);
		return;
	}
	
	//for all teh normal modes
	
	Position finish;
	
	if(queueUse || optimalUse) {
		finish = queue(maze, sr,sc2, sl);
	}
	else {
		finish = stacks(maze, sr,sc2, sl);

	}
	
	long timeEnd = System.nanoTime();
	
	if(finish == null) {
		System.out.println("Wolverine Store is now closed.");
	}
	else {
		printOutputPaths(finish, maze, coordinateOut);
	}
	
	if(showTimes) {
		double time = (timeEnd - timeStart)/ 1e9;
		System.out.println("Runtime in Total- " + time + "seconds");
		}
	}
	
	//BFS queue implementaion
	public static Position queue(char[][][] maze, int sr, int sc, int sl) {
		int m = maze[0].length;
		int n = maze[0][0].length;
		
		boolean[][][] visitComplete = new boolean[maze.length][m][n];
		Queue<Position> q = new LinkedList<>();
		q.add(new Position(sr, sc, sl, null));
		
		visitComplete[sl][sr][sc] = true;
		
		int[] rd = {-1, 1, 0, 0};
		int[] cd = {0, 0, 1, -1};
		
		while(!q.isEmpty()) {
			Position current = q.remove();
			for(int j = 0; j < 4; j++) {
				
				int rn = current.row + rd[j];
				int cn = current.col + cd[j];
				int ln = current.lev;
				
				if(rn < 0 || cn < 0 || rn >= m || cn >= n) {
					continue;
				}
				if(visitComplete[ln][rn][cn]) {
					continue;
				}
				if(maze[ln][rn][cn] == '@') {
					continue;
				}
				if(maze[ln][rn][cn] == '|') {
					ln = ln + 1;
				}
				
				Position next = new Position(rn, cn, ln, current);
				
				if(maze[ln][rn][cn] == '$') {
					return next;
				}
				
				visitComplete[ln][rn][cn] = true;
				q.add(next);
			}
		}
		
		return null;
	}
	
	//dfs stack implementation
	public static Position stacks(char[][][] maze, int rs, int cs, int ls) {
		int M = maze[0].length;
		int N = maze[0][0].length;
		
		boolean[][][] visitDone = new boolean[maze.length][M][N];
		Stack<Position> stacks = new Stack<>();
		
		int[] rd = {-1, 1, 0, 0};
		int[] cd = {0, 0, 1, -1};
		
		while(!stacks.isEmpty()) {
			Position current = stacks.pop();
			
			for(int j = 0; j < 4; j++) {
				int rn = current.row + rd[j];
				int cn = current.col + cd[j];
				int ln = current.lev;
				
				if(rn < 0 || cn < 0 || rn >= M || cn >= N) {
					continue;
				}
				if(visitDone[ln][rn][cn]) {
					continue;
				}
				if(maze[ln][rn][cn] == '@') {
					continue; 
				}
				if(maze[ln][rn][cn] == '|') {
					ln = ln + 1;
				}
				
				Position nextOne = new Position(rn, cn, ln, current);
				if(maze[ln][rn][cn] == '$') {
					return nextOne;
				}
				visitDone[ln][rn][cn] = true;
				stacks.push(nextOne);
			}
		}
		
		return null;
	}
	
	// EXTRA CREDITT implementation
	public static void runEC(char[][][] maze, int rs, int cs, int ls) {
		Position current = new Position(rs, cs, ls, null);
		
		while(true) {
			Position findingCompleted = queue(maze, current.row, current.col, current.lev);
			
			if(findingCompleted == null) {
				break;
			}
			markPath(findingCompleted, maze);
			current = findingCompleted;
			maze[current.lev][current.row][current.col] = '.';
		}
		
		printingMaze(maze);
	}
	
	//for teh mark paths
	public static void markPath(Position end, char[][][] maze) {
		Position curr = end.mainParent;
		
		while(curr != null && maze[curr.lev][curr.row][curr.col] != 'W'){
				maze[curr.lev][curr.row][curr.col] = '+';
				curr = curr.mainParent;
		}
	}
	
	//priniting output paths
	public static void printOutputPaths(Position finished, char[][][] maze, boolean coordinateOut) {
		if(coordinateOut) {
			ArrayList<Position> paths = new ArrayList<>();
			Position current = finished;
			
			while(current.mainParent != null) {
				paths.add(current);
				current = current.mainParent;
			}
			
			Collections.reverse(paths); //source: oracleHelpCenter; this will reverse teh order of elements in the paths list 
			for(Position pos : paths) {
				System.out.println("+" + pos.row + " " + pos.col + " " + pos.lev);
			}
		}
		
		else {
			markPath(finished, maze);
			printingMaze(maze);
		}
	}
	
	public static void printingMaze(char[][][] maze) {
		for(int j = 0; j < maze.length; j++) {
			for(int k =0; k < maze[0].length; k++) {
				for(int i = 0; i < maze[0][0].length; i++) {
					System.out.println(maze[j][k][i]);
				}
				System.out.println();
			}
		}
	}
	
}

