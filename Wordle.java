///////////////////////// TOP OF FILE COMMENT BLOCK ////////////////////////////
//
// Title:           Wordle Project
// Course:          cs 200
//
// Author:          Emili Robles
// Email:           ejrobles@wisc.edu
// Lecturer's Name: Jim Williams
//
///////////////////////////////// CITATIONS ////////////////////////////////////
//
// Source or Recipient; source
// 
// WordSearch.java; the lab on word search helped me remember how to scan files 
// by doing the try catch function.
// Lecture/DiscussionSlides; Looking over at week 12 slides also helped with 
// opening files to do the 2 array lists.
// 
// Classmates; A few classmates helped me understand how I'm supposed to generate 
// operate a random word and use hidden words to be able to get it
// ( i didn't know about that because I thought
// we had to do it ourselves but forgot there was method computed already.
//
/////////////////////////////// 80 COLUMNS WIDE ////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Wordle {

    /**
     * This returns a randomly chosen word from the words list.
     *
     * @param words The list of words passed in.
     * @param randGen A random number generator.
     * @return A randomly chosen word from the list or "" if either parameter is null or there
     * aren't any words in the list.
     */
    public static String chooseHiddenWord(ArrayList<String> words, Random randGen) {
        if ( words == null || randGen == null || words.size() == 0) {
            return "";
        }
        return words.get(randGen.nextInt(words.size()));
    }

    /**
     * Compares the guessWord to the hiddenWord and provides feedback on each character. If the
     * letter in the guessWord is correct in that position, then the capital letter is shown. If
     * the letter is in the word but the wrong position then a lowercase letter is shown. If the
     * letter is not in the word then a - is shown.  If the guessWord and hiddenWord are not the
     * same length then "" is returned.
     *
     * Example:
     * Guess 1: about
     * Ab---
     * The letter A is in the word and in the correct spot. The letter b is in the word
     * but in the wrong spot. The dashes - show those letters, o, u and t are not in the word.
     *
     * @param hiddenWord The hidden word
     * @param guessWord The guessed word
     * @return The feedback corresponding to each character
     */
    public static String getFeedback(String hiddenWord, String guessWord) {
        if (hiddenWord == null || guessWord == null || hiddenWord.length() != guessWord.length()) {
            return "";
        }
        char [] feedback = new char[guessWord.length()];
        ArrayList<Character> unmatchedHidden = new ArrayList<>();

        for (int guessIndex = 0; guessIndex < guessWord.length(); guessIndex++) {
            char guessChar = guessWord.charAt(guessIndex);
            char hiddenChar = hiddenWord.charAt(guessIndex);
            if ( guessChar == hiddenChar) {
                //character in correct spot
                feedback[guessIndex] = Character.toUpperCase(guessChar);
            } else {
                unmatchedHidden.add(hiddenChar);
                int guessCharIndexInHidden = hiddenWord.indexOf(guessChar);
                if (guessCharIndexInHidden < 0) {
                    //character not in the word
                    feedback[guessIndex] = '-';
                } else {
                    //character in the word but we haven't handled all the correct spot cases.
                    feedback[guessIndex] = '\0';
                }
            }
        }

        //handle character found but in the wrong spot
        for (int guessIndex = 0; guessIndex < guessWord.length(); guessIndex++) {
            if (feedback[guessIndex] == '\0') {
                char guessChar = guessWord.charAt(guessIndex);
                int foundIndex = unmatchedHidden.indexOf(guessChar);
                if ( foundIndex >= 0) {
                    //found character in the wrong spot
                    feedback[guessIndex] = Character.toLowerCase(guessChar);
                    unmatchedHidden.remove( foundIndex);
                } else {
                    //not found after handling correct spot cases.
                    feedback[guessIndex] = '-';
                }
            }
        }
        return String.copyValueOf(feedback);
    }

    /**
     * Tested if files/lists were first null and if they werent then they were read 
     * with scanner and went through a try catch statement to be put into 2 array lists,'
     * one with common words freq greater than 0 and the other with all the words.
     * If file couldn't be found it went through the catch loop and prints out cant be found.
     *
     * 
     * Example:
     * abote 0
     * abott 0
     * about 1815
     * above 296
     * abray
     * abram 0
     * 
     * @param wordFileName
     * @param words 
     * @param commonWords
     * @param readWordFile 
     * 
     */
    public static void readWordFile (String wordFileName, ArrayList<String>words, ArrayList<String>commonWords) {
        if(wordFileName == null||words==null||commonWords==null) {
            System.out.println("A parameter is null");
        }
            else {
                if(!words.isEmpty()) {
                    words = new ArrayList<String>();
                }
                if(!commonWords.isEmpty()) {
                    commonWords = new ArrayList<String>();
                }
                Scanner scnr;
            
            
         try {
             
            scnr = new Scanner(new File(wordFileName));

            while ( scnr.hasNext()) {
                        String line = scnr.nextLine();
                        String lineArr[] = line.split(" ");
                       
                        if(lineArr.length > 1) {
                            int freq = Integer.parseInt(lineArr[1]);
                            if(freq > 0) {
                                commonWords.add(lineArr[0]);  
                            }
 
                        }
                        words.add(lineArr[0]);
            }
                        scnr.close();
            }
            
            catch (FileNotFoundException e) {
                 System.out.println("The file " + wordFileName + "was not found or could not be opened for reading" );
            }
         }
      }

    /**
     * Opened both arrays and used common words list to use with config.seed and
     * make the program get a random common word for user to guess. Allowed user to 
     * guess word in 6 attempts or less by providing feedback with their guess and using hiddenword
     * method to demonstrate what letters are in the right space/wrong space. Or if the 
     * word is unrecognizable it prints out saying it doesn't recognize the word and to guess
     * another word. If you guess in 6 or less tries, it reads you win! if not it reads out the 
     * correct word.
     *
     * 
     * Example
     * Guess the word in six tries.
     * Example
     * Guess:   cares
     * Feedback:--rE-
     * '-' the letter from the guess isn't in the word in any spot.
     * 'r' the letter is in the word but in the wrong spot.
     *  'E' the letter is in the word and in the correct spot.
     *  Guess 1: adieu
     *  Feedback:--i--
     *  Guess 2: posty
     *  Unrecognized word: posty
     *  Guess 2: ports
     *  Feedback:--Rts
     *  Guess 3: stirs
     *  Feedback:stir-
     *  Guess 4: tires
     *  Feedback:tIR-s
     *  Guess 5: sires
     *  Feedback:sIR--
     *  Guess 6: fires
     *  Feedback:FIR-s
     *  The word was: first
     * 
     * 
     */
   public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<String> commonWords = new ArrayList<String>();

        readWordFile(Config.DICTIONARY_FILENAME, words, commonWords);
        Random randGen = new Random(Config.SEED);
        int attempts = 1;

        String hiddenWord = chooseHiddenWord(commonWords, randGen);
        System.out.println("Guess the word in six tries.");
        boolean win = false;
        while (attempts <= 6) {

            System.out.print("Guess " + (attempts) + ": ");
            String guessWord = scnr.nextLine();
            if (guessWord.equalsIgnoreCase(hiddenWord)) {
                System.out.println("You Win!");
                win = true;
                break;
            }
            String feedback = getFeedback(hiddenWord, guessWord);
            if (feedback != null && feedback.length() > 0)
                System.out.println("feedback: " + feedback);
            else
                System.out.println("Unrecognized word: " + guessWord);

            attempts++;
        }
        if (!win)
            System.out.println("The word was " + hiddenWord);
    }
}
       
                
