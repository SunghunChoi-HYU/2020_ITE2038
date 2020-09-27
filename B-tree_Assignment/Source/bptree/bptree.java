package bptree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class bptree<K extends Comparable<? super K>, V> {

	public int node_size;
	public Node root;

	public bptree(int node_size) {
		this.node_size = node_size;
		root = new Leaf_Node();
	}
	
	public V search(K key) {
		return root.find_V(key);
	}

	public HashMap<K,V> searchRange(K key1, K key2) {
		return root.get_Range(key1, key2);
	}

	public void insert(K key, V value) {
		root.insert_v(key, value);
	}

	public void delete(K key) {
		root.delete_v(key);
	}

	public void FileSave(String fileName, bptree BplusTree) {
		if(root == null)
			return;
		try {
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			pw.println(node_size);
			Leaf_Node LeafNode = (bptree.Leaf_Node) getFirstLeafNode(root);
			while(LeafNode != null) {
				for(int i = 0; i < LeafNode.values.size(); i++) {
					V value = LeafNode.values.get(i);
					K key = LeafNode.keys.get(i);
					pw.println(key + "," + value);
				}
				LeafNode = LeafNode.next;
			}
			pw.close();
			bw.close();
			fw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Node getFirstLeafNode(Node node) {
		if(node instanceof bptree.Inter_Node) {
			return getFirstLeafNode(((bptree.Inter_Node) node).getChild(0));
		}
		else
			return node;
	}
	
	public bptree FileLoad(String fileName, bptree BplusTree) {
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			Scanner sc = new Scanner(br);
			String degree = sc.nextLine();
		 	int degree2 = Integer.parseInt(degree);
			BplusTree = new bptree<Integer, Integer>(degree2);
			
		 	while(sc.hasNextLine()) {
		 		String keyNvalue = sc.nextLine();
		 		String[] keyNvaluesplit = keyNvalue.split(",");
		 		int key = Integer.parseInt(keyNvaluesplit[0]);
		 		int value = Integer.parseInt(keyNvaluesplit[1]);
				BplusTree.insert(key, value);
		 	}
		 	sc.close();
		 	br.close();
		 	fr.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return BplusTree;
	}

	abstract class Node {
		List<K> keys;

		int _max_key() {
			return keys.size();
		}

		abstract V find_V(K key);
		abstract void delete_v(K key);
		abstract void insert_v(K key, V value);
		
		abstract K first_leaf_key();
		abstract HashMap<K,V> get_Range(K key1,K key2);
		
		abstract void merge(Node s);
		abstract Node split();
		abstract boolean flag_over();
		abstract boolean flag_under();
	}

	private class Inter_Node extends Node {
		List<Node> children;

		Inter_Node() {
			this.keys = new ArrayList<K>();
			this.children = new ArrayList<Node>();
		}

		@Override
		V find_V(K key) {
			for(int i = 0; i < keys.size()-1; i++) {
				System.out.print(keys.get(i) + ",");
			}
			System.out.println(keys.get(keys.size()-1));
			return getChild(key).find_V(key);
		}

		@Override
		void delete_v(K key) {
			Node child = getChild(key);
			child.delete_v(key);
			if (child.flag_under()) {
				Node cLeft = left_s(key);
				Node cRight = right_s(key);
				Node my_left, my_right;
				if(cLeft != null) {
					my_left = cLeft;
				}
				else {
					my_left = child;
				}
				
				if(cRight != null) {
					my_right = cRight;
				}
				else {
					my_right = child;
				}
				int index = Collections.binarySearch(keys, key);
				int loc = index >=0 ? index : -index -1;
				if(loc < keys.size()) {
					this.keys.remove(loc);
					this.children.remove(loc+1);
					my_left.merge(my_right);
					if(my_right.keys.size() != 0)
						delete_c(my_right.first_leaf_key());
					if (my_left.flag_over()) {
						Node s = my_left.split();
						insert_c(s.first_leaf_key(), s);
					}
					if (root._max_key() == 0)
						root = my_left;
				}
			}
			
		}

		@Override
		void insert_v(K key, V value) {
			Node child = getChild(key);
			child.insert_v(key, value);
			if(child instanceof bptree.Leaf_Node) {
				if (child.flag_over()) {
					Node s = child.split();
					insert_c(s.first_leaf_key(), s);
				}
			}
			else {
				if(child.flag_over()) {
					Node s = child.split();
					insert_c(child.keys.get(child.keys.size()-1), s);
					child.keys.remove(child.keys.size()-1);
				}
			}
			if (root.flag_over()) {
				Node s = split();
				Inter_Node newRoot = new Inter_Node();
				newRoot.keys.add(keys.get(keys.size()-1));
				newRoot.children.add(this);
				newRoot.children.add(s);
				keys.remove(keys.size()-1);
				root = newRoot;
			}
		}

		@Override
		K first_leaf_key() {
			return children.get(0).first_leaf_key();
		}

		@Override
		HashMap<K,V> get_Range(K key1, K key2) {
			return getChild(key1).get_Range(key1, key2);
		}

		@Override
		void merge(Node s) {
			@SuppressWarnings("unchecked")
			Inter_Node node = (Inter_Node) s;
			keys.addAll(node.keys);
			children.addAll(node.children);
			node.keys.clear();
			node.children.clear();
		}

		@Override
		Node split() {
			int start = _max_key() / 2 + 1, end = _max_key();
			Inter_Node s = new Inter_Node();
			s.keys.addAll(keys.subList(start, end));
			s.children.addAll(children.subList(start, end + 1));

			keys.subList(start, end).clear();
			children.subList(start, end + 1).clear();

			return s;
		}

		@Override
		boolean flag_over() {
			return keys.size() > node_size -1;
		}

		@Override
		boolean flag_under() {
			return children.size() < (node_size + 1) / 2;
		}

		Node getChild(K key) {
			int loc = Collections.binarySearch(keys, key);
			int idx = loc >= 0 ? loc + 1 : -loc -1;
			return children.get(idx);
		}

		void delete_c(K key) {
			int loc = Collections.binarySearch(keys, key);
			if (loc >= 0) {
				keys.remove(loc);
				children.remove(loc + 1);
			}
		}

		void insert_c(K key, Node child) {
			int loc = Collections.binarySearch(keys, key);
			int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
			if (loc >= 0) {
				children.set(childIndex, child);
			} else {
				keys.add(childIndex, key);
				children.add(childIndex + 1, child);
			}
		}

		Node left_s(K key) {
			int loc = Collections.binarySearch(keys, key);
			int idx = loc >= 0 ? loc + 1 : -loc - 1; 
			if (idx > 0) {
					return children.get(idx - 1);
			}
			return null;
		}

		Node right_s(K key) { 
			int loc = Collections.binarySearch(keys, key);
			int idx = loc >= 0 ? loc + 1 : -loc - 1;
			if (idx < _max_key()) {
				return children.get(idx + 1);
			}
			return null;
		}
	}

	private class Leaf_Node extends Node {
		List<V> values;
		Leaf_Node next;

		Leaf_Node() {
			keys = new ArrayList<K>();
			values = new ArrayList<V>();
		}

		@Override
		V find_V(K key) {
			int loc = Collections.binarySearch(keys, key);
			return loc >= 0 ? values.get(loc) : null;
		}

		@Override
		void delete_v(K key) {
			int loc = Collections.binarySearch(keys, key);
			if (loc >= 0) {
				keys.remove(loc);
				values.remove(loc);
			}
		}

		@Override
		void insert_v(K key, V value) {
			int loc = Collections.binarySearch(keys, key);
			int valueIndex = loc >= 0 ? loc : -loc - 1;
			if (loc >= 0) {
				values.set(valueIndex, value);
			} else {
				keys.add(valueIndex, key);
				values.add(valueIndex, value);
			}
			if (root.flag_over()) {
				Node s = split();
				Inter_Node newRoot = new Inter_Node();
				newRoot.keys.add(s.first_leaf_key());
				newRoot.children.add(this);
				newRoot.children.add(s);
				root = newRoot;
			}
		}

		@Override
		K first_leaf_key() {
			return keys.get(0);
		}

		@Override
		HashMap<K,V> get_Range(K key1, K key2) {
			HashMap<K,V> result = new HashMap<>();
			Leaf_Node node = this;
			while (node != null) {
				Iterator<K> Iter_k = node.keys.iterator();
				Iterator<V> Iter_V = node.values.iterator();
				while (Iter_k.hasNext()) {
					K key = Iter_k.next();
					V value = Iter_V.next();
					int res1 = key.compareTo(key1);
					int res2 = key.compareTo(key2);
					if ((res1 > 0 || res1 >= 0)	&& res2 <= 0) {
						//result.put(key,value);
						System.out.println(key + "," + value);
					}
					else if (res2 >= 0) {
						return result;
					}
				}
				node = node.next;
			}
			return result;
		}

		@Override
		void merge(Node s) {
			Leaf_Node node = (Leaf_Node)s;
			keys.addAll(node.keys);
			values.addAll(node.values);
			next = node.next;
		}

		@Override
		Node split() {
			Leaf_Node s = new Leaf_Node();
			int start = (_max_key() + 1) / 2, end = _max_key();
			s.keys.addAll(keys.subList(start, end));
			s.values.addAll(values.subList(start, end));
			keys.subList(start, end).clear();
			values.subList(start, end).clear();
			s.next = next;
			next = s;
			return s;
		}

		@Override
		boolean flag_over() {
			return keys.size() > node_size - 1;
		}

		@Override
		boolean flag_under() {
			return values.size() < node_size / 2;
		}
	}
}
