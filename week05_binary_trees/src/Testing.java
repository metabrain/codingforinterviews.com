import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Testing {

	public static void main(String[] args) throws Exception {
		Tree<Integer> tree = new Tree<Integer>(); 
		
		int N_TESTS = 10000 ;
		
		List<Integer> used_numbers = new ArrayList<Integer>(N_TESTS+2) ;

		for(int i = 0 ; i < N_TESTS ; i++) {
			int num = (int)Math.round(Math.random()*10000000) ;
			tree.add(num) ;
			used_numbers.add(num) ;
		}
		
		File f = new File("test.txt") ;		
		printToFile(tree, f) ;
		
		Scanner in = new Scanner(f) ;
		
		int last = Integer.MIN_VALUE ;
		int count = 0 ;
		while(in.hasNextInt()) {
			if(in.nextInt()<last) {
				System.out.println("inOrder incorrect before removals!") ;
				return ;
			}			
			count++;
		}		
		System.out.printf("Added %d entries.\n",count);
		if(count!=N_TESTS) {
			System.out.println("inOrder has less/more elements before removals!") ;
			return ;
		}			
		System.out.println("Consistency? "+tree.checkConsistency("before removal"));
		
		//################# REMOVAL ##############

		int N_REMOVED = (int)((double)N_TESTS/1.1) ;
		
		for(int i = 0 ; i < N_REMOVED ; i++) {
			int to_remove = (int)((double)used_numbers.size()*Math.random()) ;
			boolean removal_result = tree.remove(used_numbers.get(to_remove),null) ;
			
//			if(removal_result)
//			System.err.println("failed removal...");
			
			used_numbers.remove(to_remove);
		}
		System.out.printf("Removed %d entries.\n",N_REMOVED);
		System.out.println("Consistency? "+tree.checkConsistency("after removal"));
		
		f = new File("test2.txt") ;		
		printToFile(tree, f) ;
		
		in = new Scanner(f) ;
		
		last = Integer.MIN_VALUE ;
		count = 0 ;
		while(in.hasNextInt()) {
			if(in.nextInt()<last) {
				System.out.println("inOrder incorrect after removals!") ;
				return ;
			}			
			count++;
		}		
		
		if(count!=N_TESTS-N_REMOVED) {
			System.out.println("inOrder has less/more elements after removals!") ;
			System.out.printf("count=%d n_tests-n_removed=%d\n",count,N_TESTS-N_REMOVED);
			return ;
		}	
		
		System.out.println("Correct!");
	}
	
	public static void printToFile(Tree tree, File f) throws Exception {
		f.delete();
		f.createNewFile();
		PrintStream out = new PrintStream(f) ;
		tree.printInOrder(out) ;		
		out.flush();
	}
}
