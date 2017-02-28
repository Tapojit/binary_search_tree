
public class test { 

	public static void main(String[] args)
    {
	IntSet301 s = (IntSet301) new BinarySearchTree();

	int[] testValues ={2, 1, 4, 6, 8, 3, 9, 10, 5, 7};//

	/*for (int i:testValues){
		s.add(i);
	}
	System.out.println(s.nextExcluded(0));*/
	for (int i : testValues)
	    {
		System.out.printf("=== ADDING %d ===\n", i);
		s.add(i);
		System.out.println(s);
		for (int j = 0; j <= 11; j++)
		    {
			System.out.printf("nextExcluded(%d) = %d\n", j, s.nextExcluded(j));
		    }
		System.out.println("Size is: "+s.size());
	    }

	for (int i = 0; i <= 11; i++)
	    {
		System.out.println(i + ": " + s.contains(i));
	    }
	System.out.println(s);

	int[] removeValues = {1, 10, 5, 2, 4, 8, 7, 6, 9, 3};
	for (int i : removeValues)
	    {
		System.out.printf("=== REMOVING %d ===\n", i);
		s.remove(i);
		System.out.println("Size is: "+s.size());
		System.out.println(s);
	    }

	System.out.println("=== ADDING BACK ===");
	for (int i : testValues)
	    {
		s.add(i);
	    }
	System.out.println(s);
	//System.out.println(s.find(1)+";" +s.find(1).get(0).next.get(0)+" "+s.find(1).get(0).next.size());
    }

}
