import java.util.ArrayList;

public class AI {
	int[][] board;
	int player;

	AI(int[][] board, int player) {
		this.board = board;
		this.player = player;
	}

	int eval(int[][] board, int player) {
		// Evaluates the current board based on current player and returns a
		// value
		// based on how good
		// current position is.
		// Neutral is given a score of 0.
		// 2 in a row is 10. (00__) or (0_0_) or (0__0) or (_0_0) or (__00)
		// Open-ended 2-in-a-row is 20. (_00_)
		// 3 in a row is 1000. (00_0) or (0_00) or (000_) or (_000)
		// Open-ended 3-in-a-row is 2000 (_000_).
		// return sum of these values.

		// Modifier
		// Vertical = *1
		// Diagonal = *2
		// Horizontal = *3
		int v = 1;
		int d = 2;
		int h = 3;

		int twoIn = 10;
		int threeIn = 1000;

		int val = 0;
		// Check for horizontal 2-in-a-row.
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 4; col++) {
				// (xx00)
				if (board[col][row] == player
						&& board[col][row] == board[col + 1][row]
						&& board[col + 2][row] == 0 && board[col + 3][row] == 0) {
					val += twoIn * h;
				}
				// (x0x0)
				else if (board[col][row] == player
						&& board[col + 2][row] == player
						&& board[col + 1][row] == 0 && board[col + 3][row] == 0) {
					val += twoIn * h;
				}
				// (x00x)
				else if (board[col][row] == player
						&& board[col + 3][row] == player
						&& board[col + 1][row] == 0 && board[col + 2][row] == 0) {
					val += twoIn * h;
				}
				// (0xx0)
				else if (board[col][row] == 0 && board[col + 1][row] == player
						&& board[col + 2][row] == player
						&& board[col + 3][row] == 0) {
					val += 2 * twoIn * h;
				}
				// (0x0x)
				else if (board[col][row] == 0 && board[col + 1][row] == player
						&& board[col + 2][row] == 0
						&& board[col + 3][row] == player) {
					val += twoIn * h;
				}
				// (00xx)
				else if (board[col][row] == 0
						&& board[col][row] == board[col + 1][row]
						&& board[col + 2][row] == player
						&& board[col + 3][row] == player) {
					val += twoIn * h;
				}
			}
		}

		// Check for vertical spaced 2-in-a-row.
		// 0
		// x
		// x

		for (int row = 5; row > 1; row--) {
			for (int col = 0; col < 7; col++) {
				if (board[col][row] == player
						&& board[col][row] == board[col][row - 1]
						&& board[col][row - 2] == 0) {
					val += twoIn * v;
				}
			}
		}
		// Check for diagonal spaced 2-in-a-row (/).
		// 0 x x x 0 0
		// 0 0 x 0 x x
		// x 0 0 x 0 x
		// x x 0 0 x 0
		for (int row = 5; row > 2; row--) {
			for (int col = 0; col < 4; col++) {
				if (board[col][row] == player
						&& board[col][row] == board[col + 1][row - 1]
						&& board[col + 2][row - 2] == 0
						&& board[col + 3][row - 3] == 0) {
					val += twoIn * d;
				} else if (board[col][row] == player
						&& board[col + 1][row - 1] == 0
						&& board[col + 2][row - 2] == 0
						&& board[col][row] == board[col + 3][row - 3]) {
					val += twoIn * d;
				} else if (board[col][row] == 0 && board[col + 1][row - 1] == 0
						&& board[col + 2][row - 2] == player
						&& board[col + 3][row - 3] == player) {
					val += twoIn * d;
				} else if (board[col][row] == 0
						&& board[col + 1][row - 1] == player
						&& board[col][row] == board[col + 2][row - 2]
						&& board[col + 1][row - 1] == board[col + 3][row - 3]) {
					val += twoIn * d;
				} else if (board[col][row] == player
						&& board[col + 1][row - 1] == 0
						&& board[col][row] == board[col + 2][row - 2]
						&& board[col + 1][row - 1] == board[col + 3][row - 3]) {
					val += twoIn * d;
				} else if (board[col][row] == 0
						&& board[col + 1][row - 1] == player
						&& board[col + 1][row - 1] == board[col + 2][row - 2]
						&& board[col][row] == board[col + 3][row - 3]) {
					val += 2 * twoIn * d;
				}
			}
		}
		// Check for diagonal spaced 3-in-a-row (\).
		// 0 x x x
		// x 0 x x
		// x x 0 x
		// x x x 0
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 4; col++) {
				if (board[col][row] == player
						&& board[col][row] == board[col + 1][row + 1]
						&& board[col + 2][row + 2] == 0
						&& board[col + 3][row + 3] == 0) {
					val += twoIn * d;
				} else if (board[col][row] == player
						&& board[col + 1][row + 1] == 0
						&& board[col + 2][row + 2] == 0
						&& board[col][row] == board[col + 3][row + 3]) {
					val += twoIn * d;
				} else if (board[col][row] == 0 && board[col + 1][row + 1] == 0
						&& board[col + 2][row + 2] == player
						&& board[col + 3][row + 3] == player) {
					val += twoIn * d;
				} else if (board[col][row] == 0
						&& board[col + 1][row + 1] == player
						&& board[col][row] == board[col + 2][row + 2]
						&& board[col + 1][row + 1] == board[col + 3][row + 3]) {
					val += twoIn * d;
				} else if (board[col][row] == player
						&& board[col + 1][row + 1] == 0
						&& board[col][row] == board[col + 2][row + 2]
						&& board[col + 1][row + 1] == board[col + 3][row + 3]) {
					val += twoIn * d;
				} else if (board[col][row] == 0
						&& board[col + 1][row + 1] == player
						&& board[col + 1][row + 1] == board[col + 2][row + 2]
						&& board[col][row] == board[col + 3][row + 3]) {
					val += twoIn * 2 * d;
				}
			}
		}
		// Check for horizontal 3-in-a-row.
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 4; col++) {
				// (xx0x)
				if (board[col][row] == player
						&& board[col][row] == board[col + 1][row]
						&& board[col + 2][row] == 0
						&& board[col][row] == board[col + 3][row]) {
					val += threeIn * h;
				}
				// (x0xx)
				else if (board[col][row] == player && board[col + 1][row] == 0
						&& board[col][row] == board[col + 2][row]
						&& board[col][row] == board[col + 3][row]) {
					val += threeIn * h;
				}
				// (0xxx)
				else if (board[col][row] == 0 && board[col + 1][row] == player
						&& board[col + 1][row] == board[col + 2][row]
						&& board[col + 1][row] == board[col + 3][row]) {
					val += threeIn * h;
				}
				// (xxx0)
				else if (board[col][row] == player
						&& board[col][row] == board[col + 1][row]
						&& board[col][row] == board[col + 2][row]
						&& board[col + 3][row] == 0) {
					val += threeIn * h;
				}
			}
		}

		// Check for vertical spaced 3-in-a-row.
		// 0
		// x
		// x
		// x
		for (int row = 5; row > 2; row--) {
			for (int col = 0; col < 7; col++) {
				if (board[col][row] == player
						&& board[col][row] == board[col][row - 1]
						&& board[col][row] == board[col][row - 2]
						&& board[col][row - 3] == 0) {
					val += threeIn * v;
				}
			}
		}
		// Check for diagonal spaced 3-in-a-row (/).
		// 0 x x x
		// x 0 x x
		// x x 0 x
		// x x x 0
		for (int row = 5; row > 2; row--) {
			for (int col = 0; col < 4; col++) {
				if (board[col][row] == player
						&& board[col][row] == board[col + 1][row - 1]
						&& board[col][row] == board[col + 2][row - 2]
						&& board[col + 3][row - 3] == 0) {
					val += threeIn * d;
				} else if (board[col][row] == player
						&& board[col][row] == board[col + 1][row - 1]
						&& board[col + 2][row - 2] == 0
						&& board[col][row] == board[col + 3][row - 3]) {
					val += threeIn * d;
				} else if (board[col][row] == player
						&& board[col + 1][row - 1] == 0
						&& board[col][row] == board[col + 2][row - 2]
						&& board[col][row] == board[col + 3][row - 3]) {
					val += threeIn * d;
				} else if (board[col][row] == 0
						&& board[col + 1][row - 1] == player
						&& board[col + 1][row - 1] == board[col + 2][row - 2]
						&& board[col + 1][row - 1] == board[col + 3][row - 3]) {
					val += threeIn * d;
				}
			}
		}
		// Check for diagonal spaced 3-in-a-row (\).
		// 0 x x x
		// x 0 x x
		// x x 0 x
		// x x x 0
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 4; col++) {
				if (board[col][row] == 0 && board[col + 1][row + 1] == player
						&& board[col + 1][row + 1] == board[col + 2][row + 2]
						&& board[col + 1][row + 1] == board[col + 3][row + 3]) {
					val += threeIn * d;
				} else if (board[col][row] == player
						&& board[col + 1][row + 1] == 0
						&& board[col][row] == board[col + 2][row + 2]
						&& board[col][row] == board[col + 3][row + 3]) {
					val += threeIn * d;
				} else if (board[col][row] == player
						&& board[col][row] == board[col + 1][row + 1]
						&& board[col + 2][row + 2] == 0
						&& board[col][row] == board[col + 3][row + 3]) {
					val += threeIn * d;
				} else if (board[col][row] == player
						&& board[col][row] == board[col + 1][row + 1]
						&& board[col][row] == board[col + 2][row + 2]
						&& board[col + 3][row + 3] == 0) {
					val += threeIn * d;
				}
			}
		}

		// Check for open-ended 3-in-a-row. (0xxx0)
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 3; col++) {
				// horizontal
				if (board[col][row] == 0 && board[col + 1][row] == player
						&& board[col + 2][row] == player
						&& board[col + 3][row] == player
						&& board[col][row] == board[col + 4][row]) {
					val += 2 * threeIn * h;
				}
			}
		}
		for (int row = 0; row < 2; row++) {
			for (int col = 0; col < 3; col++) {
				// diag(\)
				if (board[col][row] == 0 && board[col + 1][row + 1] == player
						&& board[col][row] == board[col + 2][row + 2]
						&& board[col][row] == board[col + 3][row + 3]
						&& board[col + 4][row + 4] == 0) {
					val += 2 * threeIn * d;
				}
			}
		}
		// diag(/)
		for (int row = 5; row > 3; row--) {
			for (int col = 0; col < 3; col++) {
				if (board[col][row] == 0 && board[col + 1][row - 1] == player
						&& board[col + 2][row - 2] == player
						&& board[col + 3][row - 3] == player
						&& board[col + 4][row - 4] == 0) {
					val += 2 * threeIn * d;
				}
			}
		}
		return val;
	}

	int minimax(int depth, int alpha, int beta) {

		if (win(player) && player == 1)
			return 1000000;
		if (win(player) && player == -1)
			return -1000000;
		if (depth == 0) {
			return eval(board, player);
		}

		ArrayList<Integer> arr = getMoves();
		if (arr.size() == 0)
			return 0;
		if (player == 1) {
			/* int i = arr.get(0); */
			int mv = Integer.MIN_VALUE; // play(copy(board), i,
										// player).minimax(depth - 1, alpha,
			// beta);
			int temp;

			for (int i = 0; i < arr.size(); i++) {
				temp = play(copy(board), arr.get(i), player).minimax(depth - 1,
						alpha, beta);
				mv = Math.max(mv, temp);
				alpha = Math.max(alpha, mv);
				if (beta <= alpha)
					break;
			}
			return mv - player;
		} else {
			// arr.get(0);
			int mv = Integer.MAX_VALUE;// play(copy(board), i,
										// player).minimax(depth - 1, alpha,
			// beta);
			int temp;
			for (int i = 0; i < arr.size(); i++) {
				temp = play(copy(board), arr.get(i), player).minimax(depth - 1,
						alpha, beta);
				mv = Math.min(mv, temp);
				beta = Math.min(beta, mv);
				if (beta <= alpha)
					break;
			}
			return mv - player;
		}
	}

	int bestMove() {
		int best = -1;
		ArrayList<Integer> arr = getMoves();
		if (arr.size() == 0)
			return -1;

		int i = arr.get(0);
		int mv = play(copy(board), i, player).minimax(9, Integer.MIN_VALUE,
				Integer.MAX_VALUE);
		best = i;
		int temp;
		for (i = 1; i < arr.size(); i++) {
			temp = play(copy(board), arr.get(i), player).minimax(9,
					Integer.MIN_VALUE, Integer.MAX_VALUE);
			if ((player == 1 && temp > mv) || player == -1 && temp < mv) {
				mv = temp;
				best = arr.get(i);
			}
		}
		return best;
	}

	int[][] copy(int[][] board) {
		int[][] board2 = new int[7][6];
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 6; j++)
				board2[i][j] = board[i][j];
		return board2;
	}

	ArrayList<Integer> getMoves() {

		ArrayList<Integer> arr = new ArrayList<Integer>();
		for (int i = 0; i < 7; i++) {
			if (board[i][0] == 0) {
				arr.add(i);
			}
		}
		return arr;

	}

	boolean win(int player) {
		// int player = this.player;
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

	AI play(int[][] board, int n, int player) {

		if (board[n][0] == 0) {
			for (int i = 5; i >= 0; i--) {
				if (board[n][i] == 0) {
					board[n][i] = player;
					break;
				}
			}
		}
		return new AI(copy(board), -player);
	}

}
