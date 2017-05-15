package stack;

public class DoublyLinkedList<E> implements List<E> {
	private int size = 0;
	private Node<E> head = null;
	private Node<E> tail = null;
	
	@Override
	public void add(E element) {
		final Node<E> newNode = new Node<E>( element );
		
		if( head == null ) {
			head = tail = newNode;
		} else {
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		}
		
		size++;
	}

	@Override
	public void add(int index, E element) {
		
		if( size < index || index < 0 ) {
			throw new IndexOutOfBoundsException( "Index:" + index + ", size:" + size );
		}
		
		if( size == index ) {  // tail 삽입 
			add( element );
			return;
		} 
		
		if( index == 0 ) { // head 삽입 
			
			final Node<E> newNode = new Node<E>( element );
			newNode.next = head;
			head.prev = newNode;
			head = newNode;

		} else { // 중간 삽입
			Node<E> x = head;
			for( int i = 0; i < index; i++ ) {
				x = x.next;
			}
		
			final Node<E> newNode = new Node<E>( element );
			newNode.prev = x.prev; 
			newNode.next = x;
			newNode.prev.next = newNode;
			newNode.next.prev = newNode;
		}
		
		size++;		
	}

	@Override
	public E get(int index) {
		if( size <= index ) {
			throw new IndexOutOfBoundsException( "Index:" + index + ", size:" + size );
		}
		
		Node<E> x = head;
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
		
		E data = null;
		if( 0 == index ) {  // head 삭제
			
			data = head.data;
			head = head.next;
			if( head != null ) {
				head.prev = null;
			}
		
		} else if( size-1 == index ) { // tail 삭제
			data = tail.data;
			tail.prev.next = null;
			tail = tail.prev;
		} else { // 중간 삭제
			
			Node<E> x = head;
			for( int i = 0; i < index; i++ ) {
				x = x.next;
			}

			data = x.data;
			x.next.prev = x.prev;
			x.prev.next = x.next;
		}
		
		size--;
		return data;		
	}

	@Override
	public void removeAll() {
		Node<E> x = head;
		
		while( x != null ) {
			Node<E> next = x.next;
			x.next =null;
			x.prev = null;
			x = next;
		}
		
		head = null;
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
		
		Node<E> x = head;
		int index = 0;
		while( x != null ) {
			arr[ index++ ] = x.data;
			x = x.next;
		}		
		return arr;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private Node<E> x = head;
			
			@Override
			public boolean hasNext() {
				return x != null;
			}

			@Override
			public E next() {
				E data = x.data;
				x = x.next;
				return data;
			}
		};
	}
	
	private static class Node<E>{
		private Node<E> next;
		private Node<E> prev;
		private E data;

		private Node( E element ) {
			this.data = element;
			this.next = null;
			this.prev = null;
		}
	}	
}
