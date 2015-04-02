import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

/**
 * This is a utility class that returns the expected pay-out for a hand, given
 * the cards the user wants to keep, the original hand, and the pay-out table.
 * It will test all possible hands, and return the expected pay-out
 *
 * @author robert slavik
 * @version 1.1
 */
public class PartialHandEvaluator extends Object {

    /**
     * the deck to draw cards from for the hand
     */
    private static final Deck deck = new Deck();
    /**
     * the expected payoutTotal
     */
    private static double payoutTotal;
    /**
     * the total number of hands checked
     */
    private static double totalHands;
    /**
     * to return a formatted double with 4 decimals
     */
    private static final DecimalFormat fmt = new DecimalFormat("##0.####");

    private PartialHandEvaluator() {

    }

    /**
     * Computes the expected value for a given partial hand and payout table.
     * The partial hand is specified as a hand and a list containing those cards
     * from that hand form the partial hand. The cards in the hand that are not
     * part of the partial hand are never considered in final hands that
     * contribute to the expected value.
     *
     * @param inHand - Hand from which the partial hand is formed
     * @param keepers - The cards from the partial hand
     * @param pt - the payout Table for evaluation
     * @return the expected value of the partial hand
     */
    public static double expectedValue(Hand inHand, List<Card> keepers, PayoutTable pt) {
        payoutTotal = 0;
        totalHands = 0;
        Hand myHand = inHand;
        myHand.sort();
        List<Card> keep = keepers;
        int numKeepers = keepers.size();
        if (numKeepers == 5) {
            return pt.getPayout(Qualifier.evaluate(inHand));
        }
        if (numKeepers == 4) {
            evaluate4(myHand, keep, pt);
        }
        if (numKeepers == 3) {
            evaluate3(myHand, keep, pt);
        }
        if (numKeepers == 2) {
            evaluate2(myHand, keep, pt);
        }
        if (numKeepers == 1) {
            evaluate1(myHand, keep, pt);
        }
        if (numKeepers == 0) {
            evaluate0(myHand, keep, pt);
        }
        //payoutTotal = payoutTotal / totalHands;
        //return Double.valueOf(fmt.format(payoutTotal));
        return payoutTotal / totalHands;

    }

    /**
     * This method calculates the expected value if user keeping 4 cards
     *
     * @param inhand- cards in users hand
     * @param keepers- cards that user is keeping
     * @param pt- the payout table
     * @return the total expected payout of the hand
     */
    private static double evaluate4(Hand inhand, List<Card> keepers, PayoutTable pt) {
        Hand myHand = inhand;
        List<Card> keep = keepers;
        for (int j = 0; j < deck.size(); j++) {
            Hand handy = new Hand();
            Card c = deck.getCard(j);
            if (!inHand(myHand, c)) {
                keep.add(c);
                for (int t = 0; t < 5; t++) {
                    handy.setCard(t, keep.get(t));
                }
                Quality q = Qualifier.evaluate(handy);
                payoutTotal += pt.getPayout(q);
                totalHands++;
                keep.remove(c);
            }
        }
        return payoutTotal;
    }

    /**
     * this method calculates the expected value if the user keeps 3 cards
     *
     * @param inHand- in the users original hand
     * @param keepers- the cards the user is keeping
     * @param pt- the payout table
     * @return the total expected payout
     */
    private static double evaluate3(Hand inHand, List<Card> keepers, PayoutTable pt) {
        List<Card> keep = keepers;
        for (int i = 0; i < deck.size(); i++) {
            Card c = deck.getCard(i);
            if (!inHand(inHand, c)) {
                keep.add(c);
                for (int j = 0; j < deck.size(); j++) {
                    Hand handy = new Hand();
                    Card c2 = deck.getCard(j);
                    if (!inHand(inHand, c2) && c != c2) {
                        keep.add(c2);
                        for (int t = 0; t < 5; t++) {
                            handy.setCard(t, keep.get(t));
                        }
                        Quality q = Qualifier.evaluate(handy);
                        payoutTotal += pt.getPayout(q);
                        totalHands++;
                        keep.remove(c2);
                    }
                }
                keep.remove(c);
            }

        }
        return payoutTotal;
    }

    /**
     * this method calculates the expected payout if the user keeps 2 cards
     *
     * @param inHand- in the users original hand
     * @param keepers- the cards the user is keeping
     * @param pt- the payout table
     * @return the total expected payout
     */
    private static double evaluate2(Hand inHand, List<Card> keepers, PayoutTable pt) {
        List<Card> keep = keepers;
        for (int i = 0; i < deck.size(); i++) {
            Card c1 = deck.getCard(i);
            if (!inHand(inHand, c1)) {
                keep.add(c1);
                for (int k = 0; k < deck.size(); k++) {
                    Card c2 = deck.getCard(k);
                    if (!inHand(inHand, c2) && c1 != c2) {
                        keep.add(c2);
                        for (int j = 0; j < deck.size(); j++) {
                            Hand handy = new Hand();
                            Card c3 = deck.getCard(j);
                            if (!inHand(inHand, c3) && c1 != c3 && c2 != c3) {
                                keep.add(c3);
                                for (int t = 0; t < 5; t++) {
                                    handy.setCard(t, keep.get(t));
                                }
                                Quality q = Qualifier.evaluate(handy);
                                payoutTotal += pt.getPayout(q);
                                totalHands++;
                                keep.remove(c3);
                            }
                        }
                        keep.remove(c2);
                    }
                }
                keep.remove(c1);
            }

        }
        return payoutTotal;
    }

    /**
     * This method calculates the total payout if the user keeps 1 card
     *
     * @param inHand- in the users original hand
     * @param keepers- the cards the user is keeping
     * @param pt- the payout table
     * @return the total expected payout
     */
    private static double evaluate1(Hand inHand, List<Card> keepers, PayoutTable pt) {
        List<Card> keep = keepers;
        for (int n = 0; n < deck.size(); n++) {
            Card c1 = deck.getCard(n);
            if (!inHand(inHand, c1)) {
                keep.add(c1);
                for (int i = 0; i < deck.size(); i++) {
                    Card c2 = deck.getCard(i);
                    if (!inHand(inHand, c2) && c1 != c2) {
                        keep.add(c2);
                        for (int k = 0; k < deck.size(); k++) {
                            Card c3 = deck.getCard(k);
                            if (!inHand(inHand, c3) && c1 != c3 && c2 != c3) {
                                keep.add(c3);
                                for (int j = 0; j < deck.size(); j++) {
                                    Hand handy = new Hand();
                                    Card c4 = deck.getCard(j);
                                    if (!inHand(inHand, c4) && c1 != c4 && c2 != c4 && c3 != c4) {
                                        keep.add(c4);
                                        for (int t = 0; t < 5; t++) {
                                            handy.setCard(t, keep.get(t));
                                        }
                                        Quality q = Qualifier.evaluate(handy);
                                        payoutTotal += pt.getPayout(q);
                                        totalHands++;
                                        keep.remove(c4);
                                    }
                                }
                                keep.remove(c3);
                            }
                        }
                        keep.remove(c2);
                    }
                }

                keep.remove(c1);
            }
        }
        return payoutTotal;
    }

    /**
     * This method calculates the total payout if the user doesn't keep any
     * cards
     *
     * @param inHand- in the users original hand
     * @param keepers- the cards the user is keeping
     * @param pt- the payout table
     * @return the total expected payout
     */
    private static double evaluate0(Hand inHand, List<Card> keepers, PayoutTable pt) {
        List<Card> keep = keepers;
        for (int a = 0; a < deck.size(); a++) {
            Card c0 = deck.getCard(a);
            if (!inHand(inHand, c0)) {
                keep.add(c0);
                for (int n = 0; n < deck.size(); n++) {
                    Card c1 = deck.getCard(n);
                    if (!inHand(inHand, c1) && c0 != c1) {
                        keep.add(c1);
                        for (int i = 0; i < deck.size(); i++) {
                            Card c2 = deck.getCard(i);
                            if (!inHand(inHand, c2) && c0 != c2 && c1 != c2) {
                                keep.add(c2);
                                for (int k = 0; k < deck.size(); k++) {
                                    Card c3 = deck.getCard(k);
                                    if (!inHand(inHand, c3) && c0 != c3 && c1 != c3 && c2 != c3) {
                                        keep.add(c3);
                                        for (int j = 0; j < deck.size(); j++) {
                                            Hand handy = new Hand();
                                            Card c4 = deck.getCard(j);
                                            if (!inHand(inHand, c4) && c0 != c4 && c1 != c4 && c2 != c4 && c3 != c4) {
                                                keep.add(c4);
                                                for (int t = 0; t < 5; t++) {
                                                    handy.setCard(t, keep.get(t));
                                                }
                                                Quality q = Qualifier.evaluate(handy);
                                                payoutTotal += pt.getPayout(q);
                                                totalHands++;
                                                keep.remove(c4);
                                            }
                                        }
                                        keep.remove(c3);
                                    }
                                }
                                keep.remove(c2);
                            }
                        }

                        keep.remove(c1);
                    }
                }
                keep.remove(c0);
            }
        }
        return payoutTotal;
    }

    /**
     * A utility to check a card to see if it is currently in the hand of cards
     *
     * @param hand the current hand of cards
     * @param card the card to be checked if in hand
     * @return true if card is already in hand, false otherwise
     */
    private static boolean inHand(Hand inHand, Card card) {
        for (int i = 0; i < 5; i++) {
            Card c = inHand.getCard(i);
            if (c.equals(card)) {
                return true;
            }
        }
        return false;
    }
}
