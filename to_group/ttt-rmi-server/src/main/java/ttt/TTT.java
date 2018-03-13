package ttt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * TTT - Tic Tac Toe.
 */
public class TTT extends UnicastRemoteObject implements TTTService{
	
	protected TTT() throws RemoteException {
		
	}
	
	private char previousboard1[][];
	private char previousboard2[][];
	/** The Game Board */
	private char board[][] = {
			{ '1', '2', '3' }, /* Initial values are reference numbers */
			{ '4', '5', '6' }, /* used to select a vacant square for */
			{ '7', '8', '9' } /* a turn. */
	};
	/** Next player */
	private int nextPlayer = 0;
	/** Number of plays */
	private int numPlays = 0;
	
	private int lastPlayedRow1 = -1;
	private int lastPlayedColumn1 = -1;


	private int lastPlayedRow0 = -1;
	private int lastPlayedColumn0 = -1;

	
	/** Return a textual representation of the current game board. */
	public String currentBoard() throws RemoteException {
		StringBuilder sb = new StringBuilder();
		sb.append("\n\n ");

		// acquire lock for current object
		synchronized (this) {
			sb.append(board[0][0]).append(" | ");
			sb.append(board[0][1]).append(" | ");
			sb.append(board[0][2]).append(" ");
			sb.append("\n---+---+---\n ");
			sb.append(board[1][0]).append(" | ");
			sb.append(board[1][1]).append(" | ");
			sb.append(board[1][2]).append(" ");
			sb.append("\n---+---+---\n ");
			sb.append(board[2][0]).append(" | ");
			sb.append(board[2][1]).append(" | ");
			sb.append(board[2][2]).append(" \n");
		}
		// release lock

		return sb.toString();
	}

	/** Make a game play on behalf of provided player. */
	public boolean play(int row, int column, int player) throws RemoteException {
		
		System.out.println("recieved play from player: " + player);
		
		// outside board ?
		if (!(row >= 0 && row < 3 && column >= 0 && column < 3))
			return false;

		// lock
		synchronized (this) {
			// invalid square ?
			if (board[row][column] > '9')
				return false;
			// not player's turn ?
			if (player != nextPlayer)
				return false;
			// no more plays left ?
			if (numPlays == 9)
				return false;
			
			if (nextPlayer == 1) {
				lastPlayedRow1 = row;
				lastPlayedColumn1 = column;
					
			}
			
			else {
				lastPlayedRow0 = row;
				lastPlayedColumn0 = column;
				
			}
			
			lastPlayedRow0=row;
			lastPlayedColumn0 = column;
			/* insert player symbol */
			
			board[row][column] = (player == 1) ? 'X' : 'O';
			nextPlayer = (nextPlayer + 1) % 2;
			numPlays++;
			return true;
		}
		// unlock on return

	}

	/**
	 * Check if there is a game winner. Synchronized keyword means that the lock
	 * of the object is acquired when the method is called and released on
	 * return.
	 */
	public synchronized int checkWinner() throws RemoteException{
		int i;

		/* Check for a winning line - diagonals first */
		if ((board[0][0] == board[1][1] && board[0][0] == board[2][2])
				|| (board[0][2] == board[1][1] && board[0][2] == board[2][0])) {
			if (board[1][1] == 'X')
				return 1;
			else
				return 0;
		} else {
			/* Check rows and columns for a winning line */
			for (i = 0; i <= 2; i++) {
				if ((board[i][0] == board[i][1] && board[i][0] == board[i][2])) {
					if (board[i][0] == 'X')
						return 1;
					else
						return 0;
				}

				if ((board[0][i] == board[1][i] && board[0][i] == board[2][i])) {
					if (board[0][i] == 'X')
						return 1;
					else
						return 0;
				}
			}
		}

		if (numPlays == 9)
			/* A draw! */
			return 2;
		else
			/* Game is not over yet */
			return -1;
	}
	
	public synchronized String lastPlay() throws RemoteException {
		if(nextPlayer == 1) {
			return new String(" player:" + nextPlayer + " lastPlayedRow: " + lastPlayedRow1 + " lastPlayedColumn: " + lastPlayedColumn1);
		}
		
		else 
			return new String(" player:" + nextPlayer + " lastPlayedRow: " + lastPlayedRow0 + " lastPlayedColumn: " + lastPlayedColumn0);
			
		//		if(numPlays >=2) {
//			board = previousboard1;
//			numPlays -= 2;
//		}
	}
	
	
		

}
