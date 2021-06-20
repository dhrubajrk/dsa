package problems;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * https://leetcode.com/problems/course-schedule-ii/submissions/
 */
public class TopologicalSortAndAcyclic {

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = new List[numCourses];
        Stack<Integer> sortOrder = new Stack<>();
        boolean[] visited = new boolean[numCourses];
        boolean[] recursionTrace = new boolean[numCourses];

        for(int i=0;i<prerequisites.length; ++i) {
            if(graph[prerequisites[i][1]] == null)
                graph[prerequisites[i][1]] = new ArrayList<>();
            graph[prerequisites[i][1]].add(prerequisites[i][0]);
        }

        for(int i = 0; i< numCourses; ++i) {
            if(!visited[i] && !dfs(i, graph, sortOrder, visited, recursionTrace))
                return new int[0];
        }

        int result[] = new int[numCourses];
        int k =0;
        while(!sortOrder.isEmpty()){
            result[k]= sortOrder.pop();
            k++;
        }
        return result;
    }

    private boolean dfs(int node,
                        List<Integer>[] graph,
                        Stack<Integer> sortOrder,
                        boolean[] visited,
                        boolean[] recursionTrace) {
        recursionTrace[node] = true;
        visited[node] = true;

        List<Integer> dest=graph[node];
        if(dest != null) {
            for(int destNode: dest) {
                if(recursionTrace[destNode] || (!visited[destNode] && !dfs(destNode, graph, sortOrder, visited, recursionTrace)))
                    return false;

            }
        }

        recursionTrace[node] = false;
        sortOrder.push(node);
        return true;
    }
}
