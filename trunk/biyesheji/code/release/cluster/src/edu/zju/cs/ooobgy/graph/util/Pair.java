package edu.zju.cs.ooobgy.graph.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;


/**
 * 定义一对元素,该容器实现了<code>Collection</code>,并实现了序列化</br>
 * 在图的相关定义中常需要用到该容器
 * @author frogcherry 周晓龙
 * @created 2010-11-29
 */
@SuppressWarnings("serial")
public final class Pair<T> implements Collection<T>, Serializable
{
    private T first;
    private T second;

    /**
     * 使用一对值来初始化该容器，该容器只接受一对非null的值，其他情况都会抛出异常
     * @param firstValue
     * @param secondValue
     */
    public Pair(T firstValue, T secondValue) 
    {
    	if(firstValue == null || secondValue == null) 
    		throw new IllegalArgumentException("Pair cannot contain null values");
        this.first = firstValue;
        this.second = secondValue;
    }
    
    /**
     * Returns the first element.
     */
    public T getFirst() 
    {
        return first;
    }
    
    /**
     * Returns the second element.
     */
    public T getSecond() 
    {
        return second;
    }
    
    /**
     * 返回另外一个点
     * @param one
     * @return
     */
    public T getAnother(T one){
    	T first_o = getFirst();
    	T second_o = getSecond();
    	return one.equals(first_o)?first_o:second_o;
    }
    
    @Override
    public boolean equals( Object o ) {
        if (o == this)
            return true;

        if (o instanceof Pair) {
            Pair<?> otherPair = (Pair<?>) o;
            Object otherFirst = otherPair.getFirst();
            Object otherSecond = otherPair.getSecond();
            return 
            	(this.first  == otherFirst  || 
            			(this.first != null  && this.first.equals(otherFirst)))   
            			&&
                (this.second == otherSecond || 
                		(this.second != null && this.second.equals(otherSecond)));
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() 
    {
    	int hashCode = 1;
	    hashCode = 31*hashCode + (first==null ? 0 : first.hashCode());
	    hashCode = 31*hashCode + (second==null ? 0 : second.hashCode());
    	return hashCode;
    }
    
    @Override
    public String toString()
    {
        return "<" + first.toString() + ", " + second.toString() + ">";
    }

    @Deprecated
    public boolean add(T o) {
        throw new UnsupportedOperationException("Pairs cannot be mutated");
    }

    @Deprecated
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Pairs cannot be mutated");
    }

    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException("Pairs cannot be mutated");
    }

    public boolean contains(Object o) {
        return (first == o || first.equals(o) || second == o || second.equals(o));
    }

    public boolean containsAll(Collection<?> c) {
        if (c.size() > 2)
            return false;
        Iterator<?> iter = c.iterator();
        Object c_first = iter.next();
        Object c_second = iter.next();
        return this.contains(c_first) && this.contains(c_second);
    }

    public boolean isEmpty() {
        return false;
    }

    public Iterator<T> iterator() {
        return new PairIterator();
    }

    @Deprecated
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Pairs cannot be mutated");
    }

    @Deprecated
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Pairs cannot be mutated");
    }

    @Deprecated
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Pairs cannot be mutated");
    }

    public int size() {
        return 2;
    }

    public Object[] toArray() {
        Object[] to_return = new Object[2];
        to_return[0] = first;
        to_return[1] = second;
        return to_return;
    }

    @SuppressWarnings("unchecked")
    public <S> S[] toArray(S[] a) {
        S[] to_return = a;
        Class<?> type = a.getClass().getComponentType();
        if (a.length < 2)
            to_return = (S[])java.lang.reflect.Array.newInstance(type, 2);
        to_return[0] = (S)first;
        to_return[1] = (S)second;
        
        if (to_return.length > 2)
            to_return[2] = null;
        return to_return;
    }
    
    private class PairIterator implements Iterator<T>
    {
        int position;
        
        private PairIterator()
        {
            position = 0;
        }

        public boolean hasNext()
        {
            return position < 2;
        }

        public T next()
        {
            position++;
            if (position == 1)
                return first;
            else if (position == 2)
                return second;
            else
                return null;
        }

        @Deprecated
        public void remove()
        {
            throw new UnsupportedOperationException("Pairs cannot be mutated");
        }
    }
}


