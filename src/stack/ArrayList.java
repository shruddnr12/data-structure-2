package stack;

public class ArrayList<E> implements List<E> {
	private static final int INIT_CAPACITY = 3;
	
	private int size = 0;
	private E[] data = null;
	
	public ArrayList() {
		size = 0;
		resize();
	}

	@SuppressWarnings("unchecked")
	private void resize() {
		int capacity = ( data == null ) ? INIT_CAPACITY : data.length * 2;
		E[] temp = (E[])new Object[  capacity  ];
		
		for( int i = 0; i < size; i++ ) {
			temp[ i ] = data[ i ];
		}
		
		data = temp;
	}
	
	@Override
	public void add(E element) {
		if( data.length <= size ) {
			resize();
		}
		data[ size ] = element;
		size++;
	}

	@Override
	public void add(int index, E element) {
		if( size < index || index < 0 ) {
			throw new IndexOutOfBoundsException( "Index:" + index + ", size:" + size );
		}
		
		if( data.length <= size ) {
			resize();
		}
		
		for( int i = size; i > index; i-- ) {
			data[ i ] =  data[ i - 1 ];
		}
		
		data[ index ] = element;
		size++;
	}

	@Override
	public E get(int index) {
		if( index == size ) {
			throw new IndexOutOfBoundsException( "Index:" + index + ", size:" + size );
		}
		return data[ index ];
	}

	@Override
	public E remove(int index) {
		if( size <= index || index < 0 ) {
			throw new IndexOutOfBoundsException( "Index:" + index + ", size:" + size );
		}

		E element = data[ index ];

		for( int i = index; i < size-1; i++ ) {
			data[ i ] = data[ i + 1 ]; 
		}

		size--;

		return element;
	}

	@Override
	public void removeAll() {
		for( int i = 0; i < size; i++ ) {
			data[ i ] = null;
		}
		size = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Object[] toArray() {
		Object[] arr = new Object[ size ];
		for( int i = 0; i < size; i++ ) {
			arr[ i ] = data[ i ];
		}
		
		return arr;
	}
	
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>(){
			private int index = 0;
			
			@Override
			public boolean hasNext() {
				return index < size;
			}

			@Override
			public E next() {
				return data[ index++ ];
			}
		};
	}

}
