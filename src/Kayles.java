import java.util.Arrays;

public class Kayles
{
    public static void main(String[] args)
    {
	int n = Integer.parseInt(args[0]);

	int[] grundy = new int[n + 1];
	grundy[0] = 0;
	grundy[1] = 1;
	grundy[2] = 2;

	for (int i = 3; i <= n; i++)
	    {
		IntSet301 s = (IntSet301) new BinarySearchTree();
		for (int left = 0; left <= i - 1 && left <= i - 1 - left; left++)
		    {
			int right = i - 1 - left;
			s.add(grundy[left] ^ grundy[right]);
		    }

		for (int left = 0; left <= i - 1 && left <= i - 2 - left; left++)
		    {
			int right = i - 2 - left;
			s.add(grundy[left] ^ grundy[right]);
		    }
		grundy[i] = s.nextExcluded(0);
	    }
	System.out.println(Arrays.toString(grundy));
    }
}