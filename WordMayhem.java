import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Random;
import java.util.Scanner;
/**
 * This program allows 2 users to play the word game Word Mayhem. Or 1 possibly lonely user to play said game by themselves.
 */

public class WordMayhem {
	
	//Main method, runs the game
	public static void main (String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		int[] PlayerPoints= {0,0};
		int WhichPlayerTurn = 0;
		System.out.println("[Welcome to Word Scramble. Amass up to 40 points to win.]");
		while((PlayerPoints[0] < 40) && (PlayerPoints[1] < 40)) {//Game play loop continues whilst either player has less than 40 points
			String selectedWord = ReadFile("Wordlist.txt");//PLEASE CHANGE FILE PATH IF NOT CORRECT ONE
			String scrambledWord = ScrambleWord(selectedWord,20);//Scrambles the word 20 times for good measure
			String playerInput;
			System.out.println("It is Player "+(WhichPlayerTurn+1)+"'s turn.");
			for(int i = 0; i < 5;i++) {//The loop representing the 5 tries each player gets on their turn
				System.out.println("The current scrambled word is: "+scrambledWord+". You have "+(5-i)+" turns remaining.");
				System.out.println("Input 'pass' if you would like to forfeit your turn and see the word.");
				System.out.println("Input your guess (or pass if you're stumped):");
				playerInput = (input.nextLine()).toLowerCase();	
				if(playerInput.equals("pass")) {
					System.out.println("Looks like you've given up! The correct word was "+selectedWord);
					break;
				}
				else if(playerInput.equals(selectedWord)) {
					int playerEarnedPoints = 5-i;
					System.out.println("Correct guess! You've earned "+(playerEarnedPoints)+" points!");
					PlayerPoints[WhichPlayerTurn] += playerEarnedPoints;
					break;
				}
				else {
					System.out.println("Wrong guess! Try again.");
				}
			}
			WhichPlayerTurn += 1;//This line and the line below it changes the player's turn depending on the current turn
			if(WhichPlayerTurn >1)WhichPlayerTurn = 0;
			System.out.println("It is now the next player's turn.");
			System.out.println("Current points: Player 1 has "+PlayerPoints[0]+" points. Player 2 has "+PlayerPoints[1]+" points.");
			System.out.println("----------------------------------------------------------------------------------------------");
		}
		input.close();//Final conclusions
		if(PlayerPoints[0] == PlayerPoints[1]) {
			System.out.println("It was a tie between Player 1 and Player 2.");
		}
		else if(PlayerPoints[0] > PlayerPoints[1]) {
			System.out.println("Player 1 wins.");
		}
		else {
			System.out.println("Player 2 wins.");
		}
		
	}
	
		/**
		 * The following code reads the word list file and selects a random word from the list of words. (This code is copied from the read file
		 * code provided, with the file parameter added.)
		 * @param file The file that contains the word list. Will not function properly if not a .txt file.
		 * @return A random word chosen from the list of words.
		 * @throws IOException
		 */
		public static String ReadFile(String file) throws IOException {
			File wordlist = new File(file); // Be sure to update the path on this line.
			FileReader fileReader = new FileReader(wordlist);
			LineNumberReader reader = new LineNumberReader(fileReader);

			int count = 1;

			FileInputStream fileInput = new FileInputStream(wordlist);
			Scanner scannerObject = new Scanner(fileInput);
			while (scannerObject.hasNextLine()) {
				scannerObject.nextLine();
			    count += 1;
			}
			
			// This section selects a random word from the list of words.
	        Random r = new Random();
	        int random = r.nextInt(count);
			String word = "";
			int lines = 0;
			while (word != null) {
				lines += 1;
	            word = reader.readLine();
	            if (lines == random) {
	                break;
	            }
	        }
	        
			reader.close();
	        fileReader.close();
	        scannerObject.close();
	        return word; // Returns the selected word.

		}
		
		/**Uses a loop to scramble the word given.
		 * @param word The word that is input to scramble
		 * @param repetitions The amount of repetitions the scramble loop goes through. More repetitions = more scramble
		 * @return The scrambled word.
		 * @throws IOException
		 */
		public static String ScrambleWord(String word,int repetitions) throws IOException {
			Random random = new Random();
			char[] wordLetters = new char[word.length()];//Turns string into array of characters
			word.getChars(0,word.length(),wordLetters,0);
			for(int i = 0; i < repetitions; i++) {//Loop scrambles words by swapping random characters(Letters) in the array with each other
				int slot1switch = random.nextInt(word.length());
				int slot2switch = random.nextInt(word.length());
				char tempChar = wordLetters[slot1switch];
				wordLetters[slot1switch] = wordLetters[slot2switch];
				wordLetters[slot2switch] = tempChar;
			}
			return new String(wordLetters);
		}
}
