import java.io.*;
import java.util.*;


public class Testing {

	public static void main(String[] args) throws FileNotFoundException {
		
		File f = new File("linuxwords.txt") ;
		Scanner in = new Scanner(f) ;
		Trie trie = new Trie() ;
		
		while(in.hasNextLine())
			trie.insert(in.nextLine()) ;
			
		System.out.println(trie.size);
		
		Scanner user = new Scanner(System.in) ;
		
		while(user.hasNextLine())
		{
			String query = user.nextLine() ;
			for(String s : trie.sufixes(query))
				System.err.println(s);
			
		}

	}
}