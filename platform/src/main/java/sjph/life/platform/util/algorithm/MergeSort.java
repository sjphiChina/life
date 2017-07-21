/**
 * 
 */
package sjph.life.platform.util.algorithm;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A template for merging sorted lists.
 * 
 * @author shaohuiguo
 * @param <E> the class type for comparing
 *
 */
public class MergeSort<E> {

    /**
     * The comparator, or null if priority queue uses elements'
     * natural ordering.
     */
    private final Comparator<? super E> comparator;

    /**
     * @param comparator one customized Comparator
     */
    public MergeSort(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    /**
     * @param lists the array of list
     * @return a sorted list
     */
    public Collection<E> mergeKLists(Collection<E>[] lists) {
        if (lists.length == 0) {
            return null;
        }
        Collection<E> node = helper(lists, 0, lists.length - 1);
        return node;
    }

    private Collection<E> helper(Collection<E>[] lists, int start, int end) {
        if (start == end) {
            return lists[start];
        }
        int mid = start + (end - start) / 2;
        Collection<E> left = helper(lists, start, mid);
        Collection<E> right = helper(lists, mid + 1, end);
        return merge(left, right);
    }

    private Collection<E> merge(Collection<E> left, Collection<E> right) {
        Collection<E> result = new LinkedList<>();
        Iterator<E> i1 = left.iterator();
        Iterator<E> i2 = right.iterator();
        while (i1.hasNext() && i2.hasNext()) {
            E n1 = i1.next();
            E n2 = i2.next();
            if (comparator.compare(n1, n2) >= 0) {
                result.add(n2);
                result.add(n1);
            }
            else {
                result.add(n1);
                result.add(n2);
            }
        }
        while (i1.hasNext()) {
            result.add(i1.next());
        }
        while (i2.hasNext()) {
            result.add(i2.next());
        }
        return result;
    }
}
