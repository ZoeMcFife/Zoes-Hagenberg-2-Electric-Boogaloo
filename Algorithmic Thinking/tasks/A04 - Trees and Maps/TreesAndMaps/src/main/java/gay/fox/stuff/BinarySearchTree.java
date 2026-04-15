package gay.fox.stuff;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>>
{
    private TreeNode<T> root;

    // ------------------------------------------------------------------ insert

    public void insert(T value)
    {
        root = insert(root, value);
    }

    private TreeNode<T> insert(TreeNode<T> node, T value)
    {
        if (node == null)
        {
            return new TreeNode<>(value);
        }

        int cmp = value.compareTo(node.value);

        if (cmp < 0)
        {
            node.left = insert(node.left, value);
        }
        else if (cmp > 0)
        {
            node.right = insert(node.right, value);
        }

        // equal values are not inserted again (BST property: no duplicates)
        return node;
    }

    // ----------------------------------------------------------------- contains

    public boolean contains(T value)
    {
        return contains(root, value);
    }

    private boolean contains(TreeNode<T> node, T value)
    {
        if (node == null)
        {
            return false;
        }

        int cmp = value.compareTo(node.value);
        if (cmp < 0) return contains(node.left,  value);
        if (cmp > 0) return contains(node.right, value);

        return true; // cmp == 0: found
    }

    // ---------------------------------------------------------------- traversals

    /** Inorder (Left → Root → Right): yields values in ascending sorted order. */
    public List<T> inorder()
    {
        List<T> result = new ArrayList<>();
        inorder(root, result);

        return result;
    }

    private void inorder(TreeNode<T> node, List<T> result)
    {
        if (node == null) return;

        inorder(node.left, result);
        result.add(node.value);
        inorder(node.right, result);
    }

    /** Preorder (Root → Left → Right): useful for serializing / copying the tree. */
    public List<T> preorder()
    {
        List<T> result = new ArrayList<>();
        preorder(root, result);

        return result;
    }

    private void preorder(TreeNode<T> node, List<T> result)
    {
        if (node == null) return;

        result.add(node.value);
        preorder(node.left,  result);
        preorder(node.right, result);
    }

    /** Postorder (Left → Right → Root): useful for deleting or evaluating subtrees. */
    public List<T> postorder()
    {
        List<T> result = new ArrayList<>();
        postorder(root, result);

        return result;
    }

    private void postorder(TreeNode<T> node, List<T> result)
    {
        if (node == null) return;

        postorder(node.left,  result);
        postorder(node.right, result);
        result.add(node.value);
    }

    // --------------------------------------------------------------- BFS / level-order

    /**
     * Breadth-First Search (level-order traversal).
     * Uses java.util.ArrayDeque as a queue: the same FIFO concept as the
     * hand-built Queue from UE6, now provided by the standard library.
     */
    public List<T> bfs()
    {
        List<T> result = new ArrayList<>();

        if (root == null) return result;

        ArrayDeque<TreeNode<T>> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty())
        {
            TreeNode<T> current = queue.poll();
            result.add(current.value);
            if (current.left  != null) queue.offer(current.left);
            if (current.right != null) queue.offer(current.right);
        }

        return result;
    }

    // --------------------------------------------------------------- helpers

    public boolean isEmpty()
    {
        return root == null;
    }
}
