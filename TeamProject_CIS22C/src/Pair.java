/**
 * At this point, temporarily admit that a Pair is just a combination of two Object
 */

public class Pair<E, F>
{
   public E first;
   public F second;
   
   public Pair(E x, F y) { first = x; second = y; }
   
   @SuppressWarnings("unchecked")
   public boolean equals(Object rhs)
   {
      Pair<E, F> other;
      other = (Pair<E, F>) rhs;
      return first.equals(other.first);
   }
   
   //  we may take a look at this hashCode later
   public int hashCode()
   {
      return first.hashCode();
   }
}
