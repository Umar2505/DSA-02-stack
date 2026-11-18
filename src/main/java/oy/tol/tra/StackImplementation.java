package oy.tol.tra;

public class StackImplementation<E> implements StackInterface<E> {

   private Object [] itemArray;
   private int capacity;
   private int currentIndex = -1;
   private static final int DEFAULT_STACK_SIZE = 10;

   public StackImplementation() throws StackAllocationException {
      this(DEFAULT_STACK_SIZE);
   }

   public StackImplementation(int capacity) throws StackAllocationException {
      if (capacity < 2) {
         throw new StackAllocationException("Stack capacity must be at least two");
      }
      try {
         itemArray = new Object[capacity];
         this.capacity = capacity;
      } catch (OutOfMemoryError error) {
         throw new StackAllocationException("Could not allocate stack");
      }
   }

   @Override
   public int capacity() {
      return capacity;
   }

   @Override
   public void push(E element) throws StackAllocationException, NullPointerException {
      if (element == null) {
         throw new NullPointerException("Element must not be null");
      }
      if (size() == capacity) {
         grow();
      }
      itemArray[++currentIndex] = element;
   }

   private void grow() throws StackAllocationException {
      try {
         Object [] largerArray = new Object[capacity * 2];
         for (int index = 0; index <= currentIndex; index++) {
            largerArray[index] = itemArray[index];
         }
         itemArray = largerArray;
         capacity = largerArray.length;
      } catch (OutOfMemoryError error) {
         throw new StackAllocationException("Could not grow stack");
      }
   }

   @SuppressWarnings("unchecked")
   @Override
   public E pop() throws StackIsEmptyException {
      if (isEmpty()) {
         throw new StackIsEmptyException("Stack is empty");
      }
      E element = (E)itemArray[currentIndex];
      itemArray[currentIndex--] = null;
      return element;
   }

   @SuppressWarnings("unchecked")
   @Override
   public E peek() throws StackIsEmptyException {
      if (isEmpty()) {
         throw new StackIsEmptyException("Stack is empty");
      }
      return (E)itemArray[currentIndex];
   }

   @Override
   public int size() {
      return currentIndex + 1;
   }

   @Override
   public void clear() {
      currentIndex = -1;
   }

   @Override
   public boolean isEmpty() {
      return currentIndex == -1;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder("[");
      for (int index = 0; index <= currentIndex; index++) {
         builder.append(itemArray[index]);
         if (index < currentIndex) {
            builder.append(", ");
         }
      }
      builder.append("]");
      return builder.toString();
   }
}
