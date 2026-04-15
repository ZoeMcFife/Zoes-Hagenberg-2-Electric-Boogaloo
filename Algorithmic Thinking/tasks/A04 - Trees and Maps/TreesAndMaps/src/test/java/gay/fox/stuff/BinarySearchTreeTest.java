package gay.fox.stuff;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BinarySearchTreeTest
{
    private BinarySearchTree<Integer> createTree()
    {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        bst.insert(6);
        bst.insert(4);
        bst.insert(2);
        bst.insert(5);
        bst.insert(10);
        bst.insert(11);
        bst.insert(9);
        bst.insert(8);

        return bst;
    }

    @Test
    @DisplayName("BST remove node with no children")
    void removeNoChildren()
    {
        BinarySearchTree<Integer> bst = createTree();

        bst.remove(8);

        assertEquals(List.of(6,4,10,2,5,9,11), bst.bfs());
        assertFalse(bst.contains(8));
    }

    @Test
    @DisplayName("BST remove node with one child")
    void removeOneChild()
    {

        BinarySearchTree<Integer> bst = createTree();

        bst.remove(9);

        assertEquals(List.of(6,4,10,2,5,8,11), bst.bfs());
        assertFalse(bst.contains(9));
    }

    @Test
    @DisplayName("BST remove node with two Children")
    void removeTwoChildren()
    {
        BinarySearchTree<Integer> bst = createTree();

        bst.remove(6);

        assertFalse(bst.contains(6));
        assertEquals(List.of(8, 4, 10, 2, 5, 9, 11), bst.bfs());
    }
}
