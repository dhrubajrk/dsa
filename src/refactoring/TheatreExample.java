package refactoring;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheatreExample {
    public String statement(Invoice invoice, Map<String, Play> plays) throws Exception {
        int totalAmount = 0;
        int volumeCredits = 0;
        String result = String.format("Statement for %s%n", invoice.customer);

        for (Performance perf : invoice.performances) {
            Play play = plays.get(perf.playId);
            int thisAmount = amountFor(perf, play);

            // add volume credits
            volumeCredits += Math.max(perf.audience - 30, 0);
            // add extra credit for every ten comedy attendees
            if ("comedy".equals(play.type))
                volumeCredits += Math.floor(perf.audience * 1.0 / 5);

            // print line for this order
            result += "\t" + play.name + ": " + String
                    .format("$%.2f", thisAmount * 1.0 / 100) + " " + perf.audience + "seats\n";
            totalAmount += thisAmount;
        }
        result += "Amount owed is " + String.format("$%.2f", totalAmount * 1.0 / 100) + "\n";
        result += "You earned " + volumeCredits + " credits\n";
        return result;
    }

    private int amountFor(final Performance perf,
                          final Play play) throws Exception {
        int result;
        switch (play.type) {
            case "tragedy":
                result = 40000;
                if (perf.audience > 30) {
                    result += 1000 * (perf.audience - 30);
                }
                break;
            case "comedy":
                result = 30000;
                if (perf.audience > 20) {
                    result += 10000 + 500 * (perf.audience - 20);
                }
                result += 300 * perf.audience;
                break;
            default:
                throw new Exception("Unknown type:" + play.type);
        }
        return result;
    }


    private class Play {
        String name;
        String type;

        public Play(String name, String type) {
            this.name = name;
            this.type = type;
        }
    }

    private class Performance {
        String playId;
        int audience;

        public Performance(String playId, int audience) {
            this.playId = playId;
            this.audience = audience;
        }
    }

    private class Invoice {
        String customer;
        List<Performance> performances;

        public Invoice(String customer, List<Performance> performances) {
            this.customer = customer;
            this.performances = performances;
        }
    }

    @Test
    public void testTheatre() throws Exception {
        Map<String, Play> plays = new HashMap<>();
        plays.put("hamlet", new Play("Hamlet", "tragedy"));
        plays.put("as-like", new Play("As You Like It", "comedy"));
        plays.put("othello", new Play("Othello", "tragedy"));

        List<Performance> performances = Arrays.asList(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)
        );
        Invoice invoice = new Invoice("BigCo", performances);

        String statement = statement(invoice, plays);
        System.out.println(statement);

        String expectedString = "Statement for BigCo\n" +
                "\tHamlet: $650.00 55seats\n" +
                "\tAs You Like It: $580.00 35seats\n" +
                "\tOthello: $500.00 40seats\n" +
                "Amount owed is $1730.00\n" +
                "You earned 47 credits\n";
        Assert.assertEquals(expectedString, statement);
    }
}
