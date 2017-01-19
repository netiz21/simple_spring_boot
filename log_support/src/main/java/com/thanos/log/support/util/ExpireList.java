package com.thanos.log.support.util;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

/**
 * @author peiheng.zph created on 17/1/19 上午11:44
 * @version 1.0
 */
public class ExpireList<E> implements List<E> {

  private static final int DEFAULT_EXPIRE_TIME_COUNT = 5;

  private static final TimeUnit DEFAULT_EXPIRE_TIME_UNIT = TimeUnit.SECONDS;

  private final Seconds maxTimeCountInSecond;

  private volatile LinkedList<E> items;

  private volatile LinkedList<DateTime> timePoints;

  public ExpireList() {
    this(DEFAULT_EXPIRE_TIME_COUNT, DEFAULT_EXPIRE_TIME_UNIT);
  }

  public ExpireList(int timeCount, TimeUnit timeUnit) {
    if (timeCount < 0) {
      throw new IllegalArgumentException("Time count can't be negative!");
    }
    if (timeUnit == null) {
      throw new IllegalArgumentException("Time unit can't be null!");
    }

    this.maxTimeCountInSecond = Seconds.seconds((int) timeUnit.toSeconds(timeCount));
    this.items = new LinkedList<E>();
    this.timePoints = new LinkedList<DateTime>();
  }

  @Override
  public synchronized boolean add(E e) {
    DateTime dateTime = DateTime.now();
    items.add(e);
    timePoints.add(dateTime);
    removeExpiredItem(dateTime);
    return true;
  }

  private synchronized void removeExpiredItem(DateTime current) {
    while (timePoints.size() > 1 && Seconds.secondsBetween(timePoints.get(0), current).
        isGreaterThan(maxTimeCountInSecond)) {
      items.remove();
      timePoints.remove();
    }
  }

  @Override
  public boolean remove(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    items.clear();
    timePoints.clear();
  }

  @Override
  public E get(int index) {
    return items.get(index);
  }

  @Override
  public E set(int index, E element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(int index, E element) {
    throw new UnsupportedOperationException();
  }

  @Override
  public E remove(int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public synchronized int size() {
    return items.size();
  }

  /**
   * =============== Default Operation ==================
   **/

  @Override
  public boolean isEmpty() {
    return items.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return items.contains(o);
  }

  @Override
  public Iterator<E> iterator() {
    return items.iterator();
  }

  @Override
  public Object[] toArray() {
    return items.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return items.toArray(a);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(int index, Collection<? extends E> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int indexOf(Object o) {
    return items.indexOf(o);
  }

  @Override
  public int lastIndexOf(Object o) {
    return items.lastIndexOf(o);
  }

  @Override
  public ListIterator<E> listIterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ListIterator<E> listIterator(int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<E> subList(int fromIndex, int toIndex) {
    throw new UnsupportedOperationException();
  }
}
