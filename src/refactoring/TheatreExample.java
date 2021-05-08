package refactoring;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheatreExample {
    Map<String, Play> plays;
    Invoice invoice;
    private final static  String COMEDY = "comedy";
    private final static  String TRAGEDY = "tragedy";

    public String statement(Invoice invoice, Map<String, Play> plays) throws Exception {
        this.plays = plays;
        this.invoice = invoice;
        int totalAmount = 0;

        StringBuilder result = new StringBuilder(String.format("Statement for %s%n", invoice.customer));

        for (Performance perf : invoice.performances) {
            // print line for this order
            result.append("\t").append(getPlayFor(perf).name).append(": ").append(usd(getAmountFor(perf)))
                    .append(" ").append(perf.audience).append("seats\n");
            totalAmount += getAmountFor(perf);
        }

        result.append("Amount owed is ").append(usd(totalAmount)).append("\n");
        result.append("You earned ").append(getTotalVolumeCredits()).append(" credits\n");
        return result.toString();
    }

    private int getTotalVolumeCredits() {
        int volumeCredits = 0;
        for (Performance perf : invoice.performances) {
            volumeCredits += getVolumeCreditsFor(perf);
        }
        return volumeCredits;
    }

    private String usd(double amount) {
        return String.format("$%.2f", amount / 100);
    }

    private double getVolumeCreditsFor(Performance perf) {
        // add volume credits
        double volumeCredits = 0;
        volumeCredits += Math.max(perf.audience - 30, 0);
        // add extra credit for every ten comedy attendees
        if (COMEDY.equals(getPlayFor(perf).type))
            volumeCredits += Math.floor(perf.audience * 1.0 / 5);
        return volumeCredits;
    }

    private Play getPlayFor(Performance perf) {
        return plays.get(perf.playId);
    }

    private int getAmountFor(final Performance perf) throws Exception {
        int result;
        switch (getPlayFor(perf).type) {
            case TRAGEDY:
                result = 40000;
                if (perf.audience > 30) {
                    result += 1000 * (perf.audience - 30);
                }
                break;
            case COMEDY:
                result = 30000;
                if (perf.audience > 20) {
                    result += 10000 + 500 * (perf.audience - 20);
                }
                result += 300 * perf.audience;
                break;
            default:
                throw new Exception("Unknown type:" + getPlayFor(perf).type);
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
        plays.put("hamlet", new Play("Hamlet", TRAGEDY));
        plays.put("as-like", new Play("As You Like It", COMEDY));
        plays.put("othello", new Play("Othello", TRAGEDY));

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
