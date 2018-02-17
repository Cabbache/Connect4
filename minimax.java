import java.util.*;
import java.io.File;

public class minimax{
	
	public static int w = 7, h = 6, hyperLength = 8;
	public static int[][] table;
	public static double[] bs;
	public static Scanner sc = new Scanner(System.in);
	public static Random rnd = new Random();

	public static void main(String[] args){
		while (true){
			int p = 1;
			System.out.print("Enter CPU's depth perception [1-infinity] (7 is recommended) >> ");
			hyperLength = sc.nextInt();
			System.out.print("Human drops first? [y/n] >> ");
			char c = sc.next().charAt(0);
			if (c == 'y' || c == 'Y'){
				p = -1;
				System.out.println("Human will drop first");
			}
			else{ System.out.println("CPU will drop first"); }
			int winner = 0;
			table = new int[w][h];
			while ((winner == 0) && (!full(table, -1))){
				int choice = -1;
				switch (p){
					case -1:
						printTable(table);
						do{
							System.out.print("Your drop [1-"+w+"] >> ");
							choice = sc.nextInt()-1;
						} while (full(table, choice));
						break;
					case 1:
						System.out.println("Thinking...");
						choice = think(table, p);
						break;
					default:
						break;
				}
				drop(table, choice, p);
				winner = checkWin(table);
				p *= -1;
			}
			printTable(table);
			switch (winner){
				case -1:
					System.out.println("Human Beats minimax algorithm!");
					break;
				case 1:
					System.out.println("Minimax algorithm beats weak human brain.");
					break;
				case 0:
					System.out.println("Draw.");
					break;
				default:
					break;
			}
		}
	}
	
	public static void percieve(int[][] game, int p, int depth, int stem){
		if (depth != 0){
			int[] thr = possibles(game);
			for (int i = 0;i < thr.length;i++){
				int[][] tbl = copyArr(game);
				drop(tbl, thr[i], p);
				if (checkWin(tbl) == p){
					bs[stem] += Math.pow(w, depth-hyperLength)*p;
					continue;
				}
				percieve(tbl, -p, depth-1, stem);
			}
		}
	}

	public static int think(int[][] game, int p){
		int[] thr = possibles(game);
		bs = new double[thr.length];
		for (int i = 0;i < thr.length;i++){
			System.out.println(i + "/" + thr.length + " thoughts thought.");
			int[][] tbl = copyArr(game);
			drop(tbl, thr[i], p);
			if (checkWin(tbl) == p){
				bs[i] += 1;
				continue;
			}
			percieve(tbl, -p, hyperLength-1, i);
		}
		
		double best = -999;
		int ret = 0;
		System.out.println("CPU's desire to drop in:");
		for (int i = 0;i < thr.length;i++){
			if (bs[i] > best){
				best = bs[i];
				ret = thr[i];
			}
			System.out.println((thr[i]+1) + " -> " + (bs[i]*100.0) + "%");
		}
		int[] oks = new int[thr.length];
		int co = 0;
		for (int k = 0;k < thr.length;k++){
			if (bs[k] <= best+0.00000000000000010 && bs[k] >= best-0.00000000000000010){
				oks[co] = thr[k];
				co++;
			}
		}
		return oks[rnd.nextInt(co)];
	}

	public static int[] possibles(int[][] game){
		int[] slots = new int[w];
		int c = 0;
		for (int k = 0;k < w;k++){
			if (!full(game, k)){
				slots[c] = k;
				c++;
			}
		}
		int[] smaller = new int[c];
		for (int i = 0;i < c;i++){
			smaller[i] = slots[i];
		}
		return smaller;
	}

	public static int[][] copyArr(int[][] array){
		int[][] replica = new int[array.length][array[0].length];
		for (int j = 0;j < array.length;j++){
			for (int k = 0;k < array[0].length;k++){
				replica[j][k] = array[j][k];
			}
		}
		return replica;
	}

	public static boolean full(int[][] game, int slot){
		int loop = (slot < 0) ? w:1;
		for (int i = 0;i < loop;i++){
			int part = (slot < 0) ? i:slot;
			for (int k = 0;k < game[part].length;k++){
				if (game[part][k] == 0){
					return false;
				}
			}
		}
		return true;
	}

	public static int checkWin(int[][] game){
		for (int j = 0;j < w;j++){
			for (int k = 0;k < h;k++){
				int c = game[j][k];
				if (c == 0){
					continue;
				}
				try{
					if (c == game[j+1][k] && c == game[j+2][k] && c == game[j+3][k]){
						return c;
					}
				}	
				catch (Exception meh) {}
				try{
					if (c == game[j][k+1] && c == game[j][k+2] && c == game[j][k+3]){
						return c;
					}
				}
				catch (Exception meh) {}
				try{
					if (c == game[j+1][k+1] && c == game[j+2][k+2] && c == game[j+3][k+3]){
						return c;
					}
				}
				catch (Exception meh) {}
				try{
					if (c == game[j-1][k+1] && c == game[j-2][k+2] && c == game[j-3][k+3]){
						return c;
					}
				}
				catch (Exception meh) {}
			}
		}
		return 0;
	}

	public static void drop(int[][] game, int place, int p){
		for (int k = 0;k < h;k++){
			if (game[place][k] == 0){
				game[place][k] = p;
				break;
			}
		}
	}

	public static void printTable(int[][] game){
		for (int k = h-1;k >= 0;k--){
			for (int m = 0;m < w;m++){
				switch (game[m][k]){
					case -1:
						System.out.print("\u25CF");
						break;
					case 1:
						System.out.print("\u25A0");
						break;
					case 0:
						System.out.print("+");
					default:
						break;
				}
				System.out.print(" ");
			}
			System.out.println();
		}
		for (int u = 0;u < w;u++){
			System.out.print((u+1) + " ");
		}
		System.out.println();
	}
}
