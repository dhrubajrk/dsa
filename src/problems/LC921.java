package problems;

/**
 * Given a binary tree, count the number of unique value subtrees.
 * A unique value subtree means that all nodes of the subtree have the same value.
 */
public class LC921 {
    public int countUnivalSubtrees(TreeNode root) {
        // write your code here
        return countUniqueSubtrees(root).numUniqueSubtrees;
    }

    private Result countUniqueSubtrees(final TreeNode root) {
        if (root == null) {
            return new Result(0, true);
        }

        Result leftResult = countUniqueSubtrees(root.left);
        Result rightResult = countUniqueSubtrees(root.right);

        boolean isUnique = isUniqueWithChild(leftResult.isSubtreeUnique, root, root.left) &&
                isUniqueWithChild(rightResult.isSubtreeUnique, root, root.right);
        int count = leftResult.numUniqueSubtrees + rightResult.numUniqueSubtrees;

        return new Result(isUnique ? count + 1 : count, isUnique);
    }

    private boolean isUniqueWithChild(boolean isChildUnique, TreeNode root, TreeNode child) {
        if (child == null)
            return true;
        return isChildUnique && child.val == root.val;
    }

    private static class Result {
        boolean isSubtreeUnique;
        int numUniqueSubtrees;

        Result(int numUniqueSubtrees, boolean isSubtreeUnique) {
            this.isSubtreeUnique = isSubtreeUnique;
            this.numUniqueSubtrees = numUniqueSubtrees;
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
            this.left = this.right = null;
        }
    }
}
