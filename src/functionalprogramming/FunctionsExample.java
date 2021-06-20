package functionalprogramming;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FunctionsExample {

    private static final Predicate<Integer> divisibleBy2 = x -> x % 2 == 0;
    private static final Function<Double, Double> discount = x -> x * 0.95;

    @Test
    public void testEverything() {
        List<Integer> arr = Arrays.asList(1,2,3,4,5,6);
        HashSet<Object> expected = new HashSet<>(Arrays.asList(2, 4, 6));
        Assert.assertEquals(expected, arr.stream().filter(divisibleBy2).collect(Collectors.toSet()));

        Double apply = discount.apply(100.0);
//        Assert.assertEquals(95.0, apply);

    }

    public static void main(String[] args) {

    }
}
