import at.fhooe.stack.LinkedList;
import at.fhooe.stack.Queue;
import at.fhooe.stack.Stack;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollectionTests {
    @Nested
    @DisplayName("Stack")
    class StackTests {
        @Test
        @DisplayName("New stack is empty with size 0")
        void newStack_isEmptyWithSize0() {
            // Given
            Stack stack = new Stack(5);
            // When, Then
            assertTrue(stack.isEmpty());
            assertEquals(0, stack.size());
        }

        @Test
        @DisplayName("Push increases size and peek returns the top element")
        void push_increasesSizeAndPeekReturnsTopElement() {
            // Given
            Stack stack = new Stack(5);
            // When
            stack.push(10);
            stack.push(20);
            stack.push(30);
            // Then
            assertEquals(3, stack.size());
            assertEquals(30, stack.peek());
        }

        @Test
        @DisplayName("Pop returns the top element and decreases size by 1")
        void pop_returnsTopElementAndDecreasesSize() {
            // Given
            Stack stack = new Stack(5);
            stack.push(10);
            stack.push(20);
            // When
            int value = stack.pop();
            // Then
            assertEquals(20, value);
            assertEquals(1, stack.size());
        }

        @Test
        @DisplayName("Pop then peek reflects new top")
        void pop_thenPeek_reflectsNewTop() {
            // Given
            Stack stack = new Stack(5);
            stack.push(10);
            stack.push(20);
            // When
            stack.pop();
            // Then
            assertEquals(10, stack.peek());
        }

        @Test
        @DisplayName("toString lists elements from bottom to top intepreted by commas")
        void toString_listsElementsFromBottomToTop() {
            // Given
            Stack stack = new Stack(5);
            stack.push(10);
            stack.push(20);
            // When, Then
            assertEquals("[10, 20]", stack.toString());
        }

        @Test
        @DisplayName("push beyond capacity throws RuntimeException")
        void push_beyondCapacity_throwsRuntimeException() {
            // Given
            Stack stack = new Stack(1);
            stack.push(10);
            // When, Then
            assertThrows(RuntimeException.class, () -> stack.push(20));
        }

        @Test
        @DisplayName("push after overflow does not change the stack")
        void push_afterOverflow_stackUnchanged() {
            // Given
            Stack stack = new Stack(1);
            stack.push(10);
            // When
            assertThrows(RuntimeException.class, () -> stack.push(20));
            // Then
            assertEquals(10, stack.peek());
        }

        @Test
        @DisplayName("pop on empty stack throws RuntimeException")
        void pop_onEmptyStack_throwsRuntimeException() {
            // Given
            Stack stack = new Stack(5);
            // Given, Then
            assertThrows(RuntimeException.class, stack::pop);
        }

        @Test
        @DisplayName("peek on empty stack throw RuntimeException")
        void peek_onEmptyStack_throwsRuntimeException() {
            // Given
            Stack stack = new Stack(5);
            // Given, Then
            assertThrows(RuntimeException.class, stack::peek);
        }
    }

    @Nested
    @DisplayName("Queue")
    class QueueTests {
        @Test
        @DisplayName("New queue is empty with size 0")
        void newQueue_isEmptyWithSize0() {
            // Given
            Queue queue = new Queue();
            // When, Then
            assertTrue(queue.isEmpty());
            assertEquals(0, queue.size());
        }

        @Test
        @DisplayName("Offer increments size and peek returns head")
        void offer_incrementsSizeAndPeekReturnsHead() {
            // Given
            Queue queue = new Queue();
            // When
            queue.offer(1);
            queue.offer(2);
            queue.offer(3);
            // Then
            assertEquals(3, queue.size());
            assertEquals(1, queue.peek());
        }

        @Test
        @DisplayName("Poll removes elements in first in, first out order")
        void poll_removesElementsInFifoOrder() {
            // Given
            Queue queue = new Queue();
            queue.offer(10);
            queue.offer(20);
            // When
            int value = queue.poll();
            // Then
            assertEquals(10, value);
            assertEquals(1, queue.size());
        }
    }

    @Nested
    @DisplayName("LinkedList")
    class LinkedListTests {
        @Test
        @DisplayName("New list has size 0")
        void newList_hasSize0() {
            // Given
            LinkedList list = new LinkedList();
            // When, Then
            assertEquals(0, list.size());
        }

        @Test
        @DisplayName("addLast appends elements in order")
        void addLast_appendsElementsInOrder() {
            // Given
            LinkedList list = new LinkedList();
            // Given
            list.addLast(10);
            list.addLast(20);
            list.addLast(30);
            // Then
            assertEquals(3, list.size());
            assertEquals("[10 -> 20 -> 30]", list.toString());
        }
    }
}
