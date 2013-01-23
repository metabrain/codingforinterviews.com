import java.util.*;

public class Trie {
	int size = 0 ;
	
	class Node {	
		char c ;
		boolean isWord = false ;
		List<Node> childs ;
		
		Node(char c) {
			this.c = c ;
			this.childs = new ArrayList<Node>() ; 
		}
		
		Node(String word) {
			this(word.charAt(0)) ;
			
			if(word.length()>1) { //has more to create recursively
				String remain = word.substring(1) ;
				this.childs.add(new Node(remain)) ;
			}
			else //end of word
				isWord = true ;
		}
		
		boolean insert(String word) {
			if(word.equals("")) {
				if(!isWord) {
					isWord=true; //found end of word, mark as so
					return true ;
				}
				else { //was word already
					return false ;
				}
			}
			
			Node next = null ;
			for(Node child : childs) {
				if(child.c==word.charAt(0)) {
					next = child ; break ;
				}
			}
			
			if(next==null) { //didnt find, recursively create word
				childs.add(new Node(word)) ;
				return true ;
			}
			else //found, substring to child
				return next.insert(word.substring(1)) ;
		}
		
		Node find(String word) {
			if(word.equals(""))
				return this ;
			
			//find correct next child for word
			Node next = null ;
			for(Node child : childs) {
				if(child.c==word.charAt(0)) {
					next = child ; break ;
				}
			}
			
			if(next!=null) //found next
				return next.find(word.substring(1)) ;
			else //didnt found next, word doesnt exist
				return null ;
		}
		
		List<String> sufixes() {
			ArrayList<String> list = new ArrayList<String>() ;
			
			for(Node child : childs){
				List<String> sufs = child.sufixes() ;
				for(String s : sufs)
					list.add(child.c+s) ;

				if(child.isWord)
					list.add(""+child.c);
			}
			
			return list ;
		}
	}
	
	Node root ;
	
	Trie() {
		root = new Node(".") ;
	}
	
	boolean insert(String word) {
		boolean res = root.insert(word) ;
		if(res)
			size++;
		return res ;		
	}
	
	boolean contain(String word) {
		return sufixes(word).size()>0 ;
	}
	
	//assumes word exists
	List<String> sufixes(String word) {
		Node startOfWord = root.find(word) ;
		List<String> res = new ArrayList<String>(1) ;

		if(startOfWord==null) //word didnt exist, return nothing
			return res ;
			 
		List<String> sufs = startOfWord.sufixes() ;		
		for(String suf : sufs) 
			res.add(word+suf) ;
		res.add(word) ; //sufixes plus current word
		return res ;
	}
}
