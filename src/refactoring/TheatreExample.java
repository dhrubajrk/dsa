package refactoring;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheatreExample {
    private static final String COMEDY = "comedy";
    private static final String TRAGEDY = "tragedy";

    public String statement(Invoice invoice, Map<String, Play> plays) {
        StatementData statementData = createStatementData(invoice, plays);
        return renderPlainText(statementData);
    }

    public String getHtmlStatement(Invoice invoice, Map<String, Play> plays) {
        StatementData statementData = createStatementData(invoice, plays);
        return renderPlainText(statementData);
    }

    private String renderPlainText(StatementData statementData) {
        StringBuilder result = new StringBuilder(String.format("Statement for %s%n", statementData.customer));
        for (EnrichedPerformance perf : statementData.performances) {
            // print line for this order
            result.append("\t").append(perf.play.name).append(": ").append(usd(perf.amount))
                    .append(" ").append(perf.audience).append("seats\n");
        }

        result.append("Amount owed is ").append(usd(getTotalAmount(statementData))).append("\n");
        result.append("You earned ").append(getTotalVolumeCredits(statementData)).append(" credits\n");
        return result.toString();
    }

    private StatementData createStatementData(Invoice invoice, Map<String, Play> plays) {
        StatementData statementData = new StatementData();
        statementData.customer = invoice.customer;
        statementData.plays = plays;
        statementData.performances = new ArrayList<>();
        for (Performance perf : invoice.performances) {
            statementData.performances.add(enrichPerformance(statementData.plays, perf));
        }
        statementData.totalAmount = getTotalAmount(statementData);
        statementData.totalVolumeCredits = getTotalVolumeCredits(statementData);
        return statementData;
    }

    private EnrichedPerformance enrichPerformance(Map<String, Play> plays, Performance perf) {
        EnrichedPerformance enrichedPerformance = new EnrichedPerformance();
        enrichedPerformance.play = getPlayFor(plays, perf);
        enrichedPerformance.audience = perf.audience;
        enrichedPerformance.volumeCredit = getVolumeCreditsFor(enrichedPerformance);
        enrichedPerformance.amount = getAmountFor(enrichedPerformance);
        return enrichedPerformance;
    }



    private int getTotalAmount(StatementData statementData) {
        int result = 0;
        for (EnrichedPerformance perf : statementData.performances) {
            result += perf.amount;
        }
        return result;
    }

    private int getTotalVolumeCredits(StatementData statementData) {
        int result = 0;
        for (EnrichedPerformance perf : statementData.performances) {
            result += perf.volumeCredit;
        }
        return result;
    }

    private String usd(double amount) {
        return String.format("$%.2f", amount / 100);
    }

    private int getVolumeCreditsFor(EnrichedPerformance perf) {
        // add volume credits
        int volumeCredits = Math.max(perf.audience - 30, 0);
        // add extra credit for every ten comedy attendees
        if (COMEDY.equals(perf.play.type))
            volumeCredits += (perf.audience / 5);
        return volumeCredits;
    }

    private Play getPlayFor(Map<String, Play> plays, Performance perf) {
        return plays.get(perf.playId);
    }

    private int getAmountFor(EnrichedPerformance perf) {
        int result;
        switch (perf.play.type) {
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
                throw new IllegalArgumentException("Unknown type:" + perf.play.type);
        }
        return result;
    }

    private class StatementData {
        Map<String, Play> plays;
        String customer;
        List<EnrichedPerformance> performances;
        int totalVolumeCredits;
        int totalAmount;
    }

    private class EnrichedPerformance {

        int audience;
        int volumeCredit;
        int amount;
        Play play;
    }

    private class Play {
        String name;
        String type;

        Play(String name, String type) {
            this.name = name;
            this.type = type;
        }
    }

    private class Performance {
        String playId;
        int audience;

        Performance(String playId, int audience) {
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
