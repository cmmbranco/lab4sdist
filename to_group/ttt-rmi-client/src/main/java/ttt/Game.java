package ttt;

import java.rmi.Naming;
import java.util.Scanner;

public class Game {
//	Scanner keyboardSc;
//	int winner = 0;
//	int player = 1;

	public Game() {
		//ttt = new TTT();
//		keyboardSc = new Scanner(System.in);
	}
//
//	int readPlay() {
//		int play;
//		do {
//			System.out.printf(
//					"\nPlayer %d, please enter the number of the square "
//							+ "where you want to place your %c (or 0 to refresh the board): \n",
//					player, (player == 1) ? 'X' : 'O');
//			play = keyboardSc.nextInt();
//		} while (play > 9 || play < 0);
//		return play;
//	}
//
//	public void playGame() {
//		int play;
//		boolean playAccepted;
//
//		do {
//			player = ++player % 2;
//			do {
//				System.out.println(ttt.currentBoard());
//				play = readPlay();
//				if (play != 0) {
//					playAccepted = ttt.play(--play / 3, play % 3, player);
//					if (!playAccepted)
//						System.out.println("Invalid play! Try again.");
//				} else
//					playAccepted = false;
//			} while (!playAccepted);
//			winner = ttt.checkWinner();
//		} while (winner == -1);
//		System.out.println(ttt.currentBoard());
//	}
//
//	public void congratulate() {
//		if (winner == 2)
//			System.out.printf("\nHow boring, it is a draw\n");
//		else
//			System.out.printf("\nCongratulations, player %d, YOU ARE THE WINNER!\n", winner);
//	}

	public static void main(String[] args) {
//		Game g = new Game();
//		g.playGame();
//		g.congratulate();
		TTTService aTTTServer = null;
		
		
		Scanner keyboardSc = new Scanner(System.in);
		int winner = -1;
		int player = 0;
		int play;
			
			
	
		
		try {
			aTTTServer = (TTTService) Naming.lookup("//localhost/TTTServer");
            System.out.println("Found server");
            
            while( (winner = aTTTServer.checkWinner()) == -1) {
            //dostuff
            	System.out.println("Current player: " + player);
            	System.out.println(aTTTServer.currentBoard());
            	do {
    				System.out.printf(
    						"\nPlayer %d, please enter the number of the square "
    								+ "where you want to place your %c (or 0 to refresh the board): \n",
    						player, (player == 1) ? 'X' : 'O');
    				play = keyboardSc.nextInt();
    			} while (play > 10 || play < 0);
            	
            	if(play==10) {
            		System.out.println("Showing last plays	!!!!!");
            		System.out.println(aTTTServer.lastPlay());	
            	}
            	
            	else if (play != 0){
            		aTTTServer.play(--play / 3, play % 3, player);
            		player = ++player % 2;
            	}
				 
            }
            
            if (winner == 2)
    			System.out.printf("\nHow boring, it is a draw\n");
    		else
    			System.out.printf("\nCongratulations, player %d, YOU ARE THE WINNER!\n", winner);

        }
        catch (Exception e) {

        }
		finally {
			
		}
	}
}
