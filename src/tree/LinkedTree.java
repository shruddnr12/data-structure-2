package tree;

import list.List;
import stack.Stack;
import stack.StackException;
import tree.LinkedTree.TreeNode;

public class LinkedTree<E> {
	private TreeNode<E> root;
	
	public LinkedTree( E data ) {
		root = new TreeNode<E>( data );
	}

	public LinkedTree() {
		root = new TreeNode<E>();
	}

	public TreeNode<E> getRoot() {
		return root;
	}
	
	public TreeNode<E> insertLeft( TreeNode<E> parent, E data) {
		final TreeNode<E> node = new TreeNode<E>( data );
		parent.left = node;
		return node;
	}

	public TreeNode<E> insertRight( TreeNode<E> parent, E data) {
		final TreeNode<E> node = new TreeNode<E>( data );
		parent.right = node;
		return node;
	}

	public void traversalPostorder( List<E> list ){
		traversalPostorder( root, list );
	}
	
	public void traversalPostorder( TreeNode<E> node, List<E> list ){
		if( node.left != null ) {
			traversalPostorder( node.left, list );
		}
		
		if( node.right != null ) {
			traversalPostorder( node.right, list );
		}
		
		list.add( node.data );
	}
	public static double evaluteExpression( TreeNode<String> treeNode ){
		double result = 0.;
		double lValue = 0.;
		double rValue = 0.;
		
		if( treeNode.left != null ) {
			lValue = evaluteExpression( treeNode.left );
		}

		if( treeNode.right != null ) {
			rValue = evaluteExpression( treeNode.right );
		}
		
		switch ( treeNode.data ){
			case "+" : 
				result = lValue + rValue;
				break;
			case "-" : 
				result = lValue - rValue;
				break;
			case "*" : 
				result = lValue * rValue;
				break;
			case "/" : 
				result = lValue / rValue;
				break;
			default	 :  
				result = Double.parseDouble( treeNode.data );
		}
		
		return result;
	}
	
	public static LinkedTree<String> toExpressionTree( 
			List<String> tokens ) 
		throws StackException {
		LinkedTree<String> tree = new LinkedTree<String>();

		int index = 0;
		Stack<TreeNode<String>> stack = new Stack<TreeNode<String>>();
		
		while( true ) {
			String token = tokens.get( index );
			TreeNode<String> treeNode = new TreeNode<String>( token );
			
			if( token.matches("-?\\d+(\\.\\d+)?") ) {
				stack.push( treeNode );
			} else {
				treeNode.right = stack.pop();
				treeNode.left = stack.pop();
				stack.push( treeNode );
			}
			
			if( ++index == tokens.size() ) {
				tree.root = stack.pop();
				break;
			}
		}
		
		return tree;
	}
	public static class TreeNode<E>{
		private E data;
		private TreeNode<E> left;
		private TreeNode<E> right;

	
		
		public TreeNode( E data ) {
			this.data = data;
			this.left = null;
			this.right = null;
		}

		public E getData() {
			return data;
		}

		public void setData(E data) {
			this.data = data;
		}

		public TreeNode() {
		}
		
		public TreeNode<E> getLeft() {
			return left;
		}

		public void setLeft(TreeNode<E> left) {
			this.left = left;
		}

		public TreeNode<E> getRight() {
			return right;
		}

		public void setRight(TreeNode<E> right) {
			this.right = right;
		}
		
	}
}