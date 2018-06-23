import java.util.*;

public class CBoard {
	public static void main(String[] args) {
		int[][] board = new int[7][6];
		init(board);
		int tD = 2;
	//	board[3][5] = 1;
		Scanner in = new Scanner(System.in);
		display(board);
		while (tD <= 65) {
			/*
			 * if (win(1, board)) { System.out.println("Computer Wins!!");
			 * break; }
			 */
			if (tD % 2 == 0) {
				if (!playHuman(board)) {
					display(board);
					continue;
				}

				if (win(-1, board)) {
					display(board);
					System.out.println("You Win!!");
					display(board);
					break;
				}
			} else {
				AI a = new AI(copy(board), 1);
				System.out.println("Computer's Turn");
				a.board = copy(board);
				a.player = 1;
				int n = a.bestMove();
				play(board, n, 1);
				if (win(1, board)) {
					System.out.println("Computer Wins!!");
					display(board);
					break;
				}
			}
			display(board);
			if (tD == 64) {
				System.out.println("Draw!!");
			}
			tD++;
		}

	}

	static boolean playHuman(int[][] board) {
		Scanner in = new Scanner(System.in);
		System.out.println("Your Turn ");
		int n = in.nextInt();
		if (play(board, n - 1, -1)) {
			return true;
		}
		System.out.println("Invalid Move");
		return false;
		// in.close();
	}

	static int[][] copy(int[][] board) {
		int[][] board2 = new int[7][6];
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 6; j++)
				board2[i][j] = board[i][j];
		return board2;
	}

	static void init(int[][] board) {
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 6; j++)
				board[i][j] = board[i][j];
	}

	static void display(int[][] board) {
		for (int j = 0; j < 7; j++) {
			System.out.print((j + 1) + " ");
		}
		System.out.println();
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (board[j][i] == 1)
					System.out.print("R ");
				else if (board[j][i] == -1)
					System.out.print("Y ");
				else
					System.out.print("* ");

			}
			System.out.println();

		}
		System.out.println();
		System.out.println();
	}

	static boolean play(int[][] board, int n, int player) {

		if (board[n][0] == 0) {
			for (int i = 5; i >= 0; i--) {
				if (board[n][i] == 0) {
					board[n][i] = player;
					return true;
				}
			}
		}
		return false;
		// return new CBoard(board, -turn);
	}

	static boolean win(int player, int[][] board) {

		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 4; col++) {
				// (xx00)
				if (board[col][row] == player
						&& board[col][row] == board[col + 1][row]
						&& board[col + 2][row] == player
						&& board[col + 3][row] == player) {
					return true;
				}

			}
		}
		for (int row = 5; row > 2; row--) {
			for (int col = 0; col < 7; col++) {
				if (board[col][row] == player
						&& board[col][row] == board[col][row - 1]
						&& board[col][row] == board[col][row - 2]
						&& board[col][row - 3] == player) {
					return true;
				}
			}
			for (int i = 3; i < 7; i++) {
				for (int j = 0; j < 6 - 3; j++) {
					if (board[i][j] == player && board[i - 1][j + 1] == player
							&& board[i - 2][j + 2] == player
							&& board[i - 3][j + 3] == player)
						return true;
				}
			}
			// descendingDiagonalCheck
			for (int i = 3; i < 7; i++) {
				for (int j = 3; j < 6; j++) {
					if (board[i][j] == player && board[i - 1][j - 1] == player
							&& board[i - 2][j - 2] == player
							&& board[i - 3][j - 3] == player)
						return true;
				}
			}
		}

		return false;

	}
}
