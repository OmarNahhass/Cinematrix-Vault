// -----------------------------------------------------
// Assignment 2
// Written by: Omar Nahhas: 40253505
// COMP249
// Due Date: 26/03/2024
// -----------------------------------------------------
package com.filmbuff.moviedb.helper;

/**
 * Represents a custom implementation of a list.
 */
public class CustomListImpl {
    private String[] elements;
    private int size;

    /**
     * Constructs a new CustomListImpl with the specified initial elements.
     *
     * @param entries the initial elements of the list
     */
    public CustomListImpl(String... entries) {
        elements = entries;
        size = entries.length;
    }

    /**
     * Adds an element to the end of the list.
     *
     * @param element the element to add
     */
    public void add(String element) {
        ensureCapacity(size + 1);
        elements[size++] = element;
    }

    /**
     * Retrieves the element at the specified index.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     */
    public String get(int index) {
        return elements[index];
    }

    /**
     * Checks if the list contains the specified element.
     *
     * @param element the element to check for
     * @return true if the list contains the element, false otherwise
     */
    public boolean contains(String element) {
        for (String e : elements) {
            if (e.equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the list does not contain the specified element.
     *
     * @param element the element to check for
     * @return true if the list does not contain the element, false otherwise
     */
    public boolean notContains(String element) {
        return !contains(element);
    }

    private void ensureCapacity(int capacity) {
        if (capacity > elements.length) {
            int newCapacity = Math.max(elements.length * 2, capacity);
            String[] newArray = new String[newCapacity];
            System.arraycopy(elements, 0, newArray, 0, size);
            elements = newArray;
        }
    }
}
