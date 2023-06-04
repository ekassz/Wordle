import java.util.ArrayList;
import java.util.Random;

/** This class contains some testing methods for methods in the Wordle class.
 * We suggest adding your own test cases here for your own methods, but it isn't required
 * and this file won't be submitted.
 *
 * @author Jim Williams
 */
public class TestWordle {

    /**
     * This method calls the testing methods.
     *
     * @param args unused
     */
    public static void main(String [] args) {
        testChooseHiddenWord();
        testGetFeedback();
    }

    /**
     * Tests the chooseHiddenWord method.
     */
    public static void testChooseHiddenWord() {
        boolean error = false;

        {
            ArrayList<String> words = new ArrayList<>();
            words.add("adopt");
            words.add("actor");
            words.add("after");
            words.add("again");
            Random randGen = new Random(239);
            String expected = "after";
            String actual = Wordle.chooseHiddenWord(words, randGen);
            if (!expected.equals(actual)) {
                error = true;
                System.out.println("testChooseHiddenWord 1) expected:" + expected + " actual:" + actual);
            }
        }

        {
            ArrayList<String> words = null;
            Random randGen = new Random(239);
            String expected = "";
            String actual = Wordle.chooseHiddenWord(words, randGen);
            if (!expected.equals(actual)) {
                error = true;
                System.out.println("testChooseHiddenWord 2) expected:" + expected + " actual:" + actual);
            }
        }

        if (error) {
            System.out.println("testChooseHiddenWord failed");
        } else {
            System.out.println("testChooseHiddenWord passed");
        }
    }

    /**
     * Tests the getFeedback method.
     */
    public static void testGetFeedback() {
        boolean error = false;

        {
            //about, cares => -a---    //a is in the word but not the 2nd letter
            String hiddenWord = "about";
            String guess = "cares";
            String expected = "-a---";
            String actual = Wordle.getFeedback(hiddenWord, guess);
            if (!expected.equals(actual)) {
                error = true;
                System.out.println("testGetFeedback 1) hidden:" + hiddenWord + " guess:" + guess + " expected:" + expected + " actual:" + actual);
            }
        }

        {
            //momma, omaha => om--A   //o and m are wrong position but a is in the correct
            // position
            String hiddenWord = "momma";
            String guess = "omaha";
            String expected = "om--A";
            String actual = Wordle.getFeedback(hiddenWord, guess);
            if (!expected.equals(actual)) {
                error = true;
                System.out.println("testGetFeedback 2) hidden:" + hiddenWord + " guess:" + guess +
                        " expected:" + expected + " actual:" + actual);
            }
        }

        {
            //about, admit => A---T
            String hiddenWord = "about";
            String guess = "admit";
            String expected = "A---T";
            String actual = Wordle.getFeedback(hiddenWord, guess);
            if (!expected.equals(actual)) {
                error = true;
                System.out.println("testGetFeedback 3) hidden:" + hiddenWord + " guess:" + guess +
                        " expected:" + expected + " actual:" + actual);
            }
        }

        {
            //about, ridge => -----
            String hiddenWord = "about";
            String guess = "ridge";
            String expected = "-----";
            String actual = Wordle.getFeedback(hiddenWord, guess);
            if (!expected.equals(actual)) {
                error = true;
                System.out.println("testGetFeedback 4) hidden:" + hiddenWord + " guess:" + guess +
                        " expected:" + expected + " actual:" + actual);
            }
        }

        {
            //about, about => ABOUT
            String hiddenWord = "about";
            String guess = "about";
            String expected = "ABOUT";
            String actual = Wordle.getFeedback(hiddenWord, guess);
            if (!expected.equals(actual)) {
                error = true;
                System.out.println("testGetFeedback 5) hidden:" + hiddenWord + " guess:" + guess +
                        " expected:" + expected + " actual:" + actual);
            }
        }

        {
            //about, roomy =>
            String hiddenWord = "about";
            String guess = "roomy";
            String expected = "--O--";
            String actual = Wordle.getFeedback(hiddenWord, guess);
            if (!expected.equals(actual)) {
                error = true;
                System.out.println("testGetFeedback 6) hidden:" + hiddenWord + " guess:" + guess +
                        " expected:" + expected + " actual:" + actual);
            }
        }

        {
            //afoot, roomy => -oO--
            String hiddenWord = "afoot";
            String guess = "roomy";
            String expected = "-oO--";
            String actual = Wordle.getFeedback(hiddenWord, guess);
            if (!expected.equals(actual)) {
                error = true;
                System.out.println("testGetFeedback 7) hidden:" + hiddenWord + " guess:" + guess +
                        " expected:" + expected + " actual:" + actual);
            }
        }

        {
            //roomy, afoot => --Oo-
            String hiddenWord = "roomy";
            String guess = "afoot";
            String expected = "--Oo-";
            String actual = Wordle.getFeedback(hiddenWord, guess);
            if (!expected.equals(actual)) {
                error = true;
                System.out.println("testGetFeedback 8) hidden:" + hiddenWord + " guess:" + guess +
                        " expected:" + expected + " actual:" + actual);
            }
        }

        if (error) {
            System.out.println("testGetFeedback failed");
        } else {
            System.out.println("testGetFeedback passed");
        }
    }
}
