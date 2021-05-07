package problems;

import java.util.Stack;

public class LCLongestValidParenthesis {
    public static int longestValidParentheses(String s) {
        Stack<Pair> stack = new Stack<>();
        int result = 0;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '(') {
                stack.push(new Pair('(', i));
            } else {
                if(!stack.isEmpty() && stack.peek().c == '(') {
                    stack.pop();
                    if(stack.isEmpty())
                        result = Math.max(result, i+1);
                    else
                        result = Math.max(result, i-stack.peek().idx);
                } else {
                    stack.push(new Pair(')', i));
                }
            }
        }
        return result;
    }

    static class Pair {
        public char c;
        public int idx;
        public Pair(final char c, final int idx) {
            this.c = c;
            this.idx = idx;
        }
    }

    public static void main(String[] args) {
        System.out.println(longestValidParentheses(")()())"));
        System.out.println(longestValidParentheses("(()"));
        System.out.println(longestValidParentheses(""));
        System.out.println(longestValidParentheses("()()()()()()"));
        System.out.println(longestValidParentheses("()()())()()()"));
        System.out.println(longestValidParentheses("()()()(()()"));
    }
}
