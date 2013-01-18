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
		
		int N_TESTS = 100000 ;
		
		List<Integer> used_numbers = new ArrayList<Integer>(N_TESTS+2) ;

		for(int i = 0 ; i < N_TESTS ; i++) {
			int num = (int)Math.round(Math.random()*10000000) ;
			tree.add(num) ;
			used_numbers.add(num) ;
		}
		
		File f = new File("test.txt") ;
		
		PrintStream out = new PrintStream(f) ;
		
		tree.printInOrder(out) ;
		
		out.flush();
		
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
		
		
//		f.delete();
//		f.createNewFile();

		int N_REMOVED = (int)((double)N_TESTS/1.5) ;
		
		for(int i = 0 ; i < N_REMOVED ; i++) {
			int to_remove = (int)((double)used_numbers.size()*Math.random()) ;
			if(used_numbers.get(to_remove)==null)
				System.err.println("yep null") ;
			tree.remove(used_numbers.get(to_remove)) ;
			count--;
		}
		System.out.printf("Removed %d entries.\n",N_REMOVED);

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
}
