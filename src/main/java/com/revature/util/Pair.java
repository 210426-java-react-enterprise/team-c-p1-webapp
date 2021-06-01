package com.revature.util;

/**
 * Simple data structure for a pair of related objects.
 *
 * @param <T> The type for the first element
 * @param <E> The type for the second element
 * @author Sean Taba
 */
public class Pair<T, E> {

    private T first;
    private E second;

    public Pair() {

    }

    public Pair(T first, E second) {
        this.first = first;
        this.second = second;
    }


    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public E getSecond() {
        return second;
    }

    public void setSecond(E second) {
        this.second = second;
    }
}
