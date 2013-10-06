import com.example.albus.*;

public class Test {
	public static void  main(String [] args) {
		Vector_2d la = new Vector_2d(50.092762417121314, 62.94684207995578);
		Vector_2d lb = new Vector_2d(55.25335780516065, 45.27000310508588);

		Vector_2d lx = new Vector_2d(50, 50);
		Vector_2d ly = new Vector_2d(430, 50);
		
		System.out.println(Geometry.intersect(lx, ly, la, lb));
	}
}