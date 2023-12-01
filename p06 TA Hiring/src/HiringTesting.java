//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Finding the Best Schedule to Hire TAs
// Course: CS 300 Fall 2023
//
// Author: Dylan Zulkosky
// Email: dzulkosky@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: None
// Partner Email: None
// Partner Lecturer's Name: None
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// ___ Write-up states that pair programming is allowed for this assignment.
// ___ We have both read and understand the course Pair Programming Policy.
// ___ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: None
// Online Sources: (identify each by URL and describe how it helped)
//
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is a tester class for the Hiring class that tests all of the methods and makes sure
 * they work properly and use recusrions in each method
 */
public class HiringTesting {

  /**
   * Runs the tests of all the test cases and prints out the result
   * 
   * @param args never used
   */
  public static void main(String[] args) {
    // All test cases being run
    System.out.println("Result: " + greedyHiringBaseTest());
    System.out.println("Result: " + greedyHiringRecursiveTest());
    System.out.println("Result: " + optimalHiringBaseTest());
    System.out.println("Result: " + optimalHiringRecursiveTest());
    System.out.println("Result: " + optimalHiringFuzzTest());
    System.out.println("Result: " + minCoverageHiringBaseTest());
    System.out.println("Result: " + minCoverageHiringRecursiveTest());
    System.out.println("Result: " + minCoverageHiringFuzzTest());
  }

  /**
   * Tester method for greedyHiring to test base tests for ta hiring
   * 
   * @return true if methods run as they should
   */
  public static boolean greedyHiringBaseTest() {
    // Create candidates and add them to a candidate list for base cases
    Candidate ta1 = new Candidate(new boolean[] {true, true, true, false});
    Candidate ta2 = new Candidate(new boolean[] {true, true, false, true});
    Candidate ta3 = new Candidate(new boolean[] {true, false, false, false});
    Candidate ta4 = new Candidate(new boolean[] {false, false, false, false});

    CandidateList candidates = new CandidateList();
    candidates.add(ta1);
    candidates.add(ta2);
    candidates.add(ta3);
    candidates.add(ta4);

    // Set the number of hires you expect
    int hiresLeft = 2; // Define the number of hires expected

    // Call the greedyHiring function from Hiring class
    CandidateList hiredCandidates = Hiring.greedyHiring(candidates, new CandidateList(), hiresLeft);

    // Check for base cases
    if (hiresLeft == 0 || candidates.size() == 0) {
      return new CandidateList(hiredCandidates) != null;
    }

    // Check if the number of hired candidates matches the expected hires
    boolean testPassed = (hiredCandidates.size() == hiresLeft);

    // print the candidates to show the maximum hours were covered
    System.out.println(hiredCandidates);

    return testPassed;
  }

  /**
   * Tests the recursion in greedyHiring with complex input
   * 
   * @return true if the code runs as it should
   */
  public static boolean greedyHiringRecursiveTest() {
    // create random candidates
    CandidateList candidate = HiringTestingUtilities.generateRandomInput(4, 4);
    // candidates to hire
    int hiresLeft = 2;
    CandidateList hiredCandidate = new CandidateList();

    // run the candidates through greedyHiring
    CandidateList actual = Hiring.greedyHiring(candidate, hiredCandidate, hiresLeft);
    ArrayList<CandidateList> expected =
        HiringTestingUtilities.allOptimalSolutions(candidate, hiresLeft);

    // checks to see if the code works properly
    if (HiringTestingUtilities.compareCandidateLists(expected, actual)) {
      return true;
    } else {
      System.out.println("Expected was: " + expected.toString());
      System.out.println("Actual was: " + actual.toString());
      return false;
    }
  }

  /**
   * Tester method for optimalHiring to test base tests for TA hiring
   * 
   * @return true if the method runs as it should
   */
  public static boolean optimalHiringBaseTest() {
    // Create candidates and add them to a candidate list for base cases
    Candidate ta1 = new Candidate(new boolean[] {true, true, true, false});
    Candidate ta2 = new Candidate(new boolean[] {true, true, true, true});
    Candidate ta3 = new Candidate(new boolean[] {true, false, false, false});
    Candidate ta4 = new Candidate(new boolean[] {false, false, false, false});

    CandidateList candidates = new CandidateList();
    candidates.add(ta1);
    candidates.add(ta2);
    candidates.add(ta3);
    candidates.add(ta4);

    // Set the number of hires you expect
    int hiresLeft = 1; // Define the number of hires expected

    // Call the optimalHiring function from Hiring class
    CandidateList hiredCandidates =
        Hiring.optimalHiring(candidates, new CandidateList(), hiresLeft);

    // Check if the number of hired candidates matches the expected hires
    boolean testPassed = (hiredCandidates.size() == hiresLeft);

    // Print the candidates to show the maximum hours were covered
    System.out.println(hiredCandidates);
    System.out.println(hiresLeft);

    return testPassed;
  }

  /**
   * Recursive call tester for the optimalHiring method
   * 
   * @return true if the method works properly
   */
  public static boolean optimalHiringRecursiveTest() {
    // create random candidates
    CandidateList candidate = HiringTestingUtilities.generateRandomInput(4, 4);
    // candidates to hire
    int hiresLeft = 2;
    CandidateList hiredCandidate = new CandidateList();

    // run the candidates through optimalHiring
    CandidateList actual = Hiring.optimalHiring(candidate, hiredCandidate, hiresLeft);
    ArrayList<CandidateList> expected =
        HiringTestingUtilities.allOptimalSolutions(candidate, hiresLeft);

    // checks to see if the code works properly
    if (HiringTestingUtilities.compareCandidateLists(expected, actual)) {
      return true;
    } else {
      System.out.println("Expected was: " + expected.toString());
      System.out.println("Actual was: " + actual.toString());
      return false;
    }
  }

  /**
   * Tester for optimal Hiring that tests a large amount of different inputs
   * 
   * @return true if all of the tests pass
   */
  public static boolean optimalHiringFuzzTest() {
    int totalTests = 125; // Number of tests to perform between 100 and 200

    for (int i = 0; i < totalTests; i++) {
      // Generate random inputs for the test
      CandidateList candidate = HiringTestingUtilities.generateRandomInput(4, 4);
      System.out.println(candidate);
      int hiresLeft = 2; // Set hires left count

      // Generate a random hiring test
      CandidateList hiredCandidate = new CandidateList();
      CandidateList actual = Hiring.optimalHiring(candidate, hiredCandidate, hiresLeft);
      System.out.println(actual);
      ArrayList<CandidateList> expected =
          HiringTestingUtilities.allOptimalSolutions(candidate, hiresLeft);

      // Verify if the result matches the expected output
      if (!HiringTestingUtilities.compareCandidateLists(expected, actual)) {
        System.out.println("Expected: " + expected);
        System.out.println("Actual: " + actual);
        return false; // Return false if any test fails
      }
    }

    // All tests passed
    return true;
  }

  /**
   * Tester for minCoverageHiring base tests
   * 
   * @return true if the code works as it should
   */
  public static boolean minCoverageHiringBaseTest() {
    CandidateList candidates = new CandidateList();
    // Add sample candidates
    Candidate c1 = new Candidate(new boolean[] {true, false, true, false}, 1);
    Candidate c2 = new Candidate(new boolean[] {true, true, true, false}, 1);
    Candidate c3 = new Candidate(new boolean[] {false, false, false, false}, 2);
    candidates.add(c1);
    candidates.add(c2);
    candidates.add(c3);

    CandidateList hiredCandidates = new CandidateList(); // new candidate list for hired candidates

    int minHours = 3;

    CandidateList actual = Hiring.minCoverageHiring(candidates, hiredCandidates, minHours);

    // You need to consider the conditions that make the actual output an empty list
    CandidateList expected = new CandidateList();
    expected.add(c2);

    // base test cases
    if (candidates.size() == 0 || minHours <= 0) {
      expected = new CandidateList(); // Return an empty list for zero candidates or zero minimum
                                      // hours
    }

    // Check if the expected output and actual output match
    return HiringTestingUtilities.compareCandidateLists(expected, actual);
  }

  /**
   * Tester for the recursive call for minCoverageHiring recursion
   * 
   * @return true if all code works as expected
   */
  public static boolean minCoverageHiringRecursiveTest() {
    // Generate a random set of candidates
    CandidateList candidate = HiringTestingUtilities.generateRandomInput(10, 5, 5);

    // Run the candidates through minCoverageHiring method
    CandidateList actual = Hiring.minCoverageHiring(candidate, new CandidateList(), 4);

    // Retrieve expected results
    ArrayList<CandidateList> expected =
        HiringTestingUtilities.allMinCoverageSolutions(candidate, 4);

    // Compare the output of the method with the expected results
    return HiringTestingUtilities.compareCandidateLists(expected, actual);
  }

  /**
   * Tester for minCoverageHiring that tests a lot of different random inputs
   * 
   * @return true if the code works properly
   */
  public static boolean minCoverageHiringFuzzTest() {
    for (int i = 0; i < 125; i++) {
      Random random = new Random();
      int randHours = random.nextInt(5) + 1;// between 1 and 5
      int randCandidates = random.nextInt(10) + 1; // between 1 and 10
      int randMinHours = random.nextInt(randHours) + 1;
      int randPay = random.nextInt(4) + 1; // between 1 and 4

      // create candidates
      CandidateList candidates =
          HiringTestingUtilities.generateRandomInput(randHours, randCandidates, randPay);
      // actual output
      CandidateList actual =
          Hiring.minCoverageHiring(candidates, new CandidateList(), randMinHours);
      // expected output
      ArrayList<CandidateList> expected =
          HiringTestingUtilities.allMinCoverageSolutions(candidates, randMinHours);
      if (!HiringTestingUtilities.compareCandidateLists(expected, actual)) {
        return false;
      }
    }
    // true if all tests pass
    return true;
  }
}
