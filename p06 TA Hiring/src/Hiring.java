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
// Online Sources: I learned and used the ':' function in my project with help from Neso Academy on
////////////////// youtube (https://www.youtube.com/watch?v=6SweFjpcYOI&t=283s) and it helped me
////////////////// with the recursion in this project. I also looked through the zybooks and read
////////////////// about the advanced for-each loop to help me with the project.
//
///////////////////////////////////////////////////////////////////////////////

/**
 * This class uses different methods to find the best ways to hire tas based on hours available, the
 * cost, and the minimum tas for a certain amoiunt of time
 */
public class Hiring {

  /**
   * Given a set of `candidates` that we can hire, a list of candidates we've already hired, and a
   * maximum number of tas to hire, return the set of hires made using a greedy strategy that always
   * chooses the candidate that increases hours covered the most. In this function, we will ignore
   * pay rates.
   * 
   * @param candidates - the set of available candidates to hire from (excluding those already
   *                   hired)
   * @param hired      - the list of those currently hired
   * @param hiresLeft  - the maximum number of candidates to hire
   * @return a list of hired candidates
   */
  public static CandidateList greedyHiring(CandidateList candidates, CandidateList hired,
      int hiresLeft) {
    if (hiresLeft == 0 || candidates.size() == 0) {
      if (hiresLeft == 0) {
        return hired; // Return hired after recursive method is done and no hires left
      } else {
        return new CandidateList(); // Return new list if no candidates available
      }
    }

    // recursive call for greedyHiring to find best ta
    Candidate bestTa = findBestCandidate(candidates, hired);
    if (bestTa != null) {
      hired.add(bestTa);
      return greedyHiring(candidates, hired, hiresLeft - 1);
    }

    // returns the people who were hired
    return hired;
  }

  /**
   * Getter method for candidate with best availability
   * 
   * @param candidates - people to be hired
   * @param hired      - people that are hired
   * @return the best candidate with availability
   */
  private static Candidate findBestCandidate(CandidateList candidates, CandidateList hired) {
    // initialize variables
    Candidate bestTa = null;
    int maxAvailability = -1;

    for (Candidate candidate : candidates) {
      if (!hired.contains(candidate)) {
        int availabilityCount = countTrueValues(candidate.getAvailability());
        if (availabilityCount > maxAvailability) {
          maxAvailability = availabilityCount;
          bestTa = candidate;
        }
      }
    }

    // returns best ta
    return bestTa;
  }

  /**
   * gets the availability for the ta
   * 
   * @param availability - the true or false input for when they can work
   * @return the availability the ta has
   */
  private static int countTrueValues(boolean[] availability) {
    int count = 0;
    for (boolean value : availability) {
      if (value) {
        count++;
      }
    }
    // returns ta availabilty
    return count;
  }

  /**
   * Given a set of `candidates` that we can hire, a list of candidates we've already hired, and a
   * maximum number of tas to hire, return the set of hires that maximizes number of scheduled
   * hours. In this function, we will ignore pay rates.
   * 
   * @param candidates - the set of available candidates to hire from (excluding those already
   *                   hired)
   * @param hired      - the list of those currently hired
   * @param hiresLeft  - the maximum number of candidates to hire
   * @return a list of hired candidates
   */
  public static CandidateList optimalHiring(CandidateList candidates, CandidateList hired,
      int hiresLeft) {
    if (hiresLeft <= 0 || candidates.isEmpty()) {
      return new CandidateList(); // Return an empty list when no hires are left or there are no
                                  // candidates
    }

    int maxHours = calculateTotalHours(hired);
    CandidateList bestHired = new CandidateList(hired);

    for (Candidate candidate : candidates) {
      if (hired.contains(candidate)) {
        continue; // Skip candidates already hired
      }

      CandidateList updatedHired = new CandidateList(hired);
      updatedHired.add(candidate);

      // creates an updated list of candidates
      CandidateList updatedCandidates = new CandidateList(candidates);
      updatedCandidates.remove(candidate);

      // recursive call for optimalHiring
      CandidateList hires = optimalHiring(updatedCandidates, updatedHired, hiresLeft);

      // calculate total hours of hires
      int coveredHours = hires.numCoveredHours(); // Calculate total covered hours by the hired
                                                  // candidates

      if (coveredHours > maxHours && hires.size() == hiresLeft) {
        maxHours = coveredHours;
        bestHired = new CandidateList(hires);
      }
    }

    return bestHired;
  }

  /**
   * Getter method for amount of hours tas can work
   * 
   * @param hiredCandidates - the hired tas
   * @return the total hours the tas can work
   */
  private static int calculateTotalHours(CandidateList hiredCandidates) {
    int totalHours = 0;
    for (Candidate candidate : hiredCandidates) {
      totalHours += countTrueValues(candidate.getAvailability());
    }
    return totalHours;
  }

  /**
   * Knapsack dual problem: find the minimum-budget set of hires to achieve a threshold number of
   * hours. That is, given a set of candidates, a set of already-hired candidates, and a minimum
   * number of hours we want covered, what is the cheapest set of candidates we can hire that cover
   * at least that minimum number of hours specified.
   * 
   * @param candidates - the set of available candidates to hire from (excluding those already
   *                   hired)
   * @param hired      - the set of candidates already hired
   * @param minHours   - the minimum number of hours we want to cover total
   * @return a list of hired candidates or null if no set of candidates achieves the requested
   *         number of hours
   */
  public static CandidateList minCoverageHiring(CandidateList candidates, CandidateList hired, int minHours) {
    if (minHours <= 0) {
        return new CandidateList();
        // Base case: Minimum hours requirement is already met
    }

    if (candidates.size() == 0) {
        return new CandidateList();
        // Base case: There are no candidates available
    }

    CandidateList bestHired = null;
    int minCost = Integer.MAX_VALUE;

    for (Candidate candidate : candidates) {
        if (!hired.contains(candidate)) {
            CandidateList newHired = new CandidateList(hired);
            newHired.add(candidate);

            int totalHours = newHired.numCoveredHours(); // Calculate the total hours covered by hired candidates

            if (totalHours >= minHours) {
                int totalCost = calculateCost(newHired);
                if (totalCost < minCost) {
                    bestHired = newHired;
                    minCost = totalCost;
                }
            } else {
                CandidateList hires = minCoverageHiring(candidates, newHired, minHours);
                if (hires != null) {
                    int totalCost = calculateCost(hires);
                    if (totalCost < minCost) {
                        bestHired = hires;
                        minCost = totalCost;
                    }
                }
            }
        }
    }

    return bestHired;
}

  /**
   * Getter method to calcualte the cost to pay the tas
   * 
   * @param hiredCandidates - the tas that were hired
   * @return the cost to pay the tas
   */
  private static int calculateCost(CandidateList hiredCandidates) {
    int totalCost = 0;
    for (Candidate candidate : hiredCandidates) {
      totalCost += candidate.getPayRate();
    }
    return totalCost;
  }
}
