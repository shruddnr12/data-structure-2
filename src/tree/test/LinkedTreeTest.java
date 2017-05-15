package tree.test;
import java.util.Arrays;

import list.DoublyLinkedList;
import list.List;
import tree.LinkedTree;

public class LinkedTreeTest {

	public static void main(String[] args) {
		LinkedTree<String> tree = 
				new LinkedTree<String>( "A" );
		
		LinkedTree.TreeNode<String> root = tree.getRoot();
		
		LinkedTree.TreeNode<String> nodeB = 
				tree.insertLeft( root, "B" );
		LinkedTree.TreeNode<String> nodeC =
				tree.insertRight( root, "C" );
		
		LinkedTree.TreeNode<String> nodeD = 
				tree.insertLeft( nodeB, "D" );
		LinkedTree.TreeNode<String> nodeE =
				tree.insertRight( nodeB, "E" );
		
		LinkedTree.TreeNode<String> nodeF =
				tree.insertLeft( nodeC, "F" );
		LinkedTree.TreeNode<String> nodeG =
				tree.insertRight( nodeC, "G" );
		
		tree.insertLeft( nodeD, "H" );
		tree.insertRight( nodeD, "I" );
		tree.insertLeft( nodeE, "J" );
		tree.insertRight( nodeF, "K" );
		tree.insertLeft( nodeG, "L" );
		tree.insertRight( nodeG, "M" );
		
		List<String> list = new DoublyLinkedList<String>();
		tree.traversalPostorder( list );
		System.out.println( Arrays.toString( list.toArray() ) );
	}

}