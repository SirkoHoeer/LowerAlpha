package model;

import java.util.*;


/**
 * JStack describes a regular Stack with some more options for visualization.
 * @author Maximus S.
 * @param <T>
 */

public class JStack<T> {
	
	private List<T> list;
	
	/**
	 * @author Maximus S. 
	 * @param Index of the Item in stack.
	 * @return Returns the value at the Index.
	 */
	public T getItemOfList(int index) {
		return this.list.get(index);
	}
	
	/** Sets the item at the index.
	 * @author Maximus S. 
	 * @param index 
	 * @param t
	 */
	public void setItemOfList(int index, T t) {
		this.list.set(index, t);
	}
	
	/**
	 * @author Maximus S. 
	 * @return Returns the size of stack.
	 */
	public int getSize() {
		return this.list.size();
	}
	
	/**
	 * @author Maximus S.
	 * @param t - Pushes an item of T into the stack.
	 */
	public void push(T t) {
		this.list.add(t);
	}
	
	public T pop() {
		T t = this.list.get(this.list.size() - 1);
                list.remove(this.list.size() - 1);
                return t;
                
	}
	
	public JStack() {
		this.list = new ArrayList<T>();		
	}
	
	public JStack<T> clone() {
		return this;
	}
	
	public JStack<T> shallowclone() {
		return this;
	}
	
	public JStack<T> deepclone() {
		JStack<T> t = new JStack<T>();
		t.list = copyList();
		return t;
	}
	
	private List<T> copyList() {
		List<T> l = new ArrayList<T>(this.list);		
		return l;
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < this.list.size(); i++) {
			s += this.list.get(i).toString() + " ";			
		}
		return s;
	}	
	
	
}

