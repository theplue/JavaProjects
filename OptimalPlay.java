
import java.util.List;
import java.util.ArrayList;
/**
 *  Utility class to determine the best play for an initial Video Poker hand 
 * using evaluation of expected values of all possible discard strategies. 
 * Note that there must be no public constructor.
 * @author robertslavik
 */
public final class OptimalPlay extends Object {
    /**
     * the highest expecting payout for a hand
     */
    private static double bestPay = 0;
    /**
     * handOfCards is the hand, put into a list
     */
    private static final List<Card> handOfCards = new ArrayList();
    /**
     * the list of cards to keep
     */
    private static final List<Card> keepers = new ArrayList();
    private static double pay0 =0;
    /**
     * private constructor
     */
    private OptimalPlay() {

    }
    /**
     * Determines the optimal keep/discard strategy for a newly dealt video 
     * poker hand and specified payout table.
     * 
     * @param inHand - the hand to examine
     * @param pt- the payout table for evaluation
     * @return the cards of the original hand that, if kept, would result 
     *          in the greatest expected value
     */
    public static java.util.List<Card> optimalKeepers(Hand inHand, PayoutTable pt) {
        keepers.clear();
        bestPay = 0;
        System.out.println("best pay  = " + bestPay);    
    // copy the original hand
        Hand copyHand = inHand;
        copyHand.sort();
        //set best pay to the original hand payout, keeping all 5 cards
        bestPay = pt.getPayout(Qualifier.evaluate(inHand));
        //keep0(copyHand, pt);
        System.out.println("keep 0 complete" + keepers.toString() + " " + bestPay);
        keep1(copyHand, pt);
        System.out.println("keep 1 complete" + keepers.toString()+ " " + bestPay);
        keep2(copyHand, pt);
        System.out.println("keep 2 complete" + keepers.toString()+ " " + bestPay);
        keep3(copyHand, pt);
        System.out.println("keep 3 complete" + keepers.toString()+ " " + bestPay);
        keep4(copyHand, pt);
        System.out.println("keep 4 complete" + keepers.toString()+ " " + bestPay);
        if (keepers.isEmpty() &&  pay0 < bestPay)  {
            for (int i = 0; i < 5; i++) {
                keepers.add(copyHand.getCard(i));
            }
        }
        return keepers;
    }
    /**
     * check discarding all the cards in the hand
     * @param hand - the hand to examine
     * @param pt - payout table for evaluation
     */
    private static void keep0(Hand hand, PayoutTable pt){
         //the expected payout of each hand built, to be compared against original hand
        pay0 = PartialHandEvaluator.expectedValue(hand, handOfCards, pt);
        if(pay0 > bestPay){
            bestPay =pay0;
            keepers.clear();
        }
    }
    /**
     * evaluate all possible hands if discarding 4 cards from hand.
     * @param hand- the hand of cards to evaluate
     * @param pt - the payout table for evaluation
     */
    private static void keep1(Hand hand, PayoutTable pt) {
        //the expected payout of each hand built, to be compared against original hand
        double pay1 = 0;
        for (int i = 0; i < 5; i++) {
            handOfCards.add(hand.getCard(i));
            pay1 = PartialHandEvaluator.expectedValue(hand, handOfCards, pt);
            if (pay1 > bestPay) {
                bestPay = pay1;
                keepers.clear();
                keepers.addAll(handOfCards);
            }
            handOfCards.clear();
        }
    }
    /**
     * evaluate all possible hands if discarding 3 cards from hand.
     * @param hand - the hand to evaluate
     * @param pt  - the payout table for evaluation
     */
    private static void keep2(Hand hand, PayoutTable pt) {
         //the expected payout of each hand built, to be compared against original hand
        double pay2 = 0;
        for (int i = 0; i < 4; i++) {
            handOfCards.add(hand.getCard(i));
            for (int j = i + 1; j < 5; j++) {
                handOfCards.add(hand.getCard(j));
                pay2 = PartialHandEvaluator.expectedValue(hand, handOfCards, pt);
                if (pay2 > bestPay) {
                    bestPay = pay2;
                    keepers.clear();
                    keepers.addAll(handOfCards);
                }
                handOfCards.remove(hand.getCard(j));
            }
            handOfCards.remove(hand.getCard(i));
        }
    }
    /**
     * evaluate all possible hands if discarding 2 cards
     * @param hand - to be examined
     * @param pt - the payout table for evaluation
     */
    private static void keep3(Hand hand, PayoutTable pt) {
         //the expected payout of each hand built, to be compared against original hand
        double pay3 = 0;
        for (int i = 0; i < 3; i++) {
            handOfCards.add(hand.getCard(i));
            for (int j = i + 1; j < 4; j++) {
                handOfCards.add(hand.getCard(j));
                for (int k = j + 1; k < 5; k++) {
                    handOfCards.add(hand.getCard(k));
                    pay3= PartialHandEvaluator.expectedValue(hand, handOfCards, pt);
                    if (pay3 > bestPay) {
                        bestPay = pay3;
                        keepers.clear();
                        keepers.addAll(handOfCards);
                    }
                    handOfCards.remove(hand.getCard(k));
                }
                handOfCards.remove(hand.getCard(j));
            }
            handOfCards.remove(hand.getCard(i));
        }
    }
    /**
     * evaluate all possible hands if discarding 1 card
     * @param hand- to be examined
     * @param pt - the payout table for evaluation
     */
    private static void keep4(Hand hand, PayoutTable pt) {
         //the expected payout of each hand built, to be compared against original hand
        double pay4 = 0;
        for (int i = 0; i < 2; i++) {
            handOfCards.add(hand.getCard(i));
            for (int j = i + 1; j < 3; j++) {
                handOfCards.add(hand.getCard(j));
                for (int k = j + 1; k < 4; k++) {
                    handOfCards.add(hand.getCard(k));
                    for (int n = k + 1; n < 5; n++) {
                        handOfCards.add(hand.getCard(n));
                        pay4= PartialHandEvaluator.expectedValue(hand, handOfCards, pt);
                        if (pay4 > bestPay) {
                            bestPay=pay4;
                            System.out.println("best pay = " + bestPay +" pay 4 = "+ pay4);
                            System.out.println("keepers before clear() "+ keepers.toString());
                            
                            keepers.clear();
                            keepers.addAll(handOfCards);
                            System.out.println("keepers afer addall "+ keepers.toString());
                        }
                        handOfCards.remove(hand.getCard(n));
                    }
                    handOfCards.remove(hand.getCard(k));
                }
                handOfCards.remove(hand.getCard(j));
            }
            handOfCards.remove(hand.getCard(i));
        }
    }
    public static void main( String args[] ){
        List<Card> listkeepers = new ArrayList();
        Hand test = new Hand(67932253);
        /*Card a = new Card(Rank.ACE, Suit.HEARTS);
        Card b = new Card(Rank.DEUCE, Suit.SPADES);
        Card c = new Card(Rank.TREY, Suit.CLUBS);
        Card d = new Card(Rank.SEVEN, Suit.DIAMONDS);
        Card e = new Card(Rank.FOUR, Suit.CLUBS);
        test.setCard(0, a);
        test.setCard(1, b);
        test.setCard(2, c);
        test.setCard(3, d);
        test.setCard(4, e);*/
        System.out.println(test.toString());
        PayoutTable pt = PayoutTable.payoutTable85();
        listkeepers = OptimalPlay.optimalKeepers(test, pt);
        System.out.println(listkeepers.toString());
        System.out.println(PartialHandEvaluator.expectedValue(test, listkeepers, pt));
        /*
        List<Card> test2 = new ArrayList();
        test2.add(a);
        test2.add(b);
        test2.add(c);
        //test2.add(d);
        test2.add(e);
        System.out.println(PartialHandEvaluator.expectedValue(test, test2, pt));
        */
    }
}
