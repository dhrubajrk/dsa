package problems;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LC919 {
    public int minMeetingRooms(List<Interval> intervals) {
        // Write your code here
        Map<Integer, Integer> cumulativeSum = new TreeMap<>();
        for (Interval interval : intervals) {
            cumulativeSum.putIfAbsent(interval.start, 0);
            cumulativeSum.put(interval.start, cumulativeSum.get(interval.start)+1);

            cumulativeSum.putIfAbsent(interval.end+1, 0);
            cumulativeSum.put(interval.end+1, cumulativeSum.get(interval.end+1)-1);
        }

        int result = 0;
        int prevSum = 0;
        for (Map.Entry<Integer, Integer> tuple: cumulativeSum.entrySet()) {
            prevSum += tuple.getValue();
            result = Math.max(result, prevSum);
        }
        return result;
    }


    class Interval {
        int start, end;
        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
