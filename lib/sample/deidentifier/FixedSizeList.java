package sample.deidentifier;

import java.util.ArrayList;

public class FixedSizeList<E> extends ArrayList<E>{
	int limit;
	public FixedSizeList(int limit) {
        this.limit = limit;
    }
	@Override
	public boolean add(E item) {
		if(super.size()>=limit)
			this.remove(0);
		return super.add(item);
	}
	
	@Override
	public synchronized void add(int index, E item) {
		if(index<limit)
			super.add(index, item);
	}
}
