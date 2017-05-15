package list;

public class CircularLinkedList<E> implements List<E> {
	private int size = 0;
	private Node<E> tail = null;
	private Node<E> pos = null;
	
	@Override
	public void add(E element) {
		final Node<E> newNode = new Node<E>( element );
		
		if( tail == null ) {
			tail = newNode.next = newNode;
		} else {
			newNode.next = tail.next;  // head
			tail.next = newNode;
			tail = tail.next;
		}
		size++;
	}

	@Override
	public void add(int index, E element) {
		if( size < index || index < 0 ) {
			throw new IndexOutOfBoundsException( "Index:" + index + ", size:" + size );
		}
		
		if( size == index ){  // tail 삽입 
			add( element );
			return;
		} 

		if( index == 0 ) {   // head 삽입 
		
			final Node<E> newNode = new Node<E>( element );
			newNode.next = tail.next;
			tail.next = newNode;

		} else { // 중간 삽입
			Node<E> x = tail.next;
			for( int i = 0; i < index - 1; i++ ) {
				x = x.next;
			}
		
			final Node<E> newNode = new Node<E>( element, x.next );
			x.next = newNode;
		}
		
		size++;		
	}

	@Override
	public E get(int index) {
		if( size <= index ) {
			throw new IndexOutOfBoundsException( "Index:" + index + ", size:" + size );
		}
		
		Node<E> x = tail.next; // head
		for( int i = 0; i < index; i++ ) {
			x = x.next;
		}
		return x.data;
	}

	@Override
	public E remove(int index) {
		if( size <= index ) {
			throw new IndexOutOfBoundsException( "Index:" + index + ", size:" + size );
		}

		size--;
		
		if( 0 == index ) {  // head 삭제
			E data = tail.next.data;
			tail.next = tail.next.next;
			return data;
		} 
		
		Node<E> x = tail.next;
			
		for( int i = 0; i < index - 1; i++ ) {
			x = x.next;
		}
			
		E data = x.next.data;
			
		if( x.next == tail ) {  // tail 삭제
			x.next = tail.next;
			tail = x;
			return data;
		}
		
		// 중간 삭제
		x.next = x.next.next;
		return data;
	}

	@Override
	public void removeAll() {
		Node<E> x = tail.next; //head로 이동
		
		while( x != tail ) {
			Node<E> next = x.next;
			
			x.next = null;
			x = next;
		}
		
		tail.next = null;
		tail = null;
		size = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Object[] toArray() {
		Object[] arr = new Object[ size ];
		if( tail == null ) {
			return arr;
		}
		
		int index = 0;
		Node<E> x = tail.next;
		while( true ) {
			arr[ index++ ] = x.data;
			x = x.next;
			if( x == tail.next ) { // 다시 head로 이동됨
				break;
			}
		}
		
		return arr;
	}

	public E next() {
		if( tail.next == null ) {
			return null;
		}
		
		if( pos == null ) {
			pos = tail.next;
		}
		
		E data = pos.data;
		pos = pos.next;
		
		return data;
	}
	
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private int index = 0;
			private Node<E> x = tail.next;
			
			@Override
			public boolean hasNext() {
				return index < size;
			}

			@Override
			public E next() {
				E data = x.data;
				x = x.next;
				index++;
				return data;
			}
		};
	}
	
	private static class Node<E>{
		private Node<E> next;
		private E data;

		private Node( E element ) {
			this.data = element;
			this.next = null;
		}
		
		private Node( E element, Node<E> next ) {
			this.data = element;
			this.next = next;
		}
	}	
}
