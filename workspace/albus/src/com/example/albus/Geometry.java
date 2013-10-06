package com.example.albus;

public class Geometry {
	private static Vector_2d rot90(Vector_2d a) {
		return new Vector_2d(-a.y, a.x);
	}

	public static int dcmp(double a, double b) {
		double eps = 1e-1;
		if (a > b + eps) return +1;
		if (a < b - eps) return -1;
		return 0;
	}

	public static int dcmp(double a) {
		return dcmp(a, 0.0);
	}
	
	// a, b, c in a line
	public static boolean dots_inline(Vector_2d a, Vector_2d b, Vector_2d c) {
		return dcmp(a.subtract(c).cross(b.subtract(c))) == 0;
	}
	
	// x inclusive in line la, lb
	public static boolean dots_online_in(Vector_2d x, Vector_2d la, Vector_2d lb) {
		return dots_inline(x, la, lb) && 
			dcmp(la.x, x.x) * dcmp(lb.x, x.x) < 0 &&
			dcmp(la.y, x.y) * dcmp(lb.y, x.y) < 0;
	}

	// x exclusive in line la, lb
	public static boolean dots_online_ex(Vector_2d x, Vector_2d la, Vector_2d lb) {
		return dots_inline(x, la, lb) &&
			dcmp(la.x, x.x) * dcmp(lb.x, x.x) <= 0 &&
			dcmp(la.y, x.y) * dcmp(lb.y, x.y) <= 0;
	}

	// point x, y in same side of line la, lb
	public static boolean same_side(Vector_2d x, Vector_2d y,
					Vector_2d la,Vector_2d lb) {
		return	dcmp(la.subtract(lb).cross(x.subtract(lb))) * 
			dcmp(la.subtract(lb).cross(y.subtract(lb))) > 0;
	}

	// point x, y in the opposite side of line la, lb
	public static boolean opposite_side(	Vector_2d x, Vector_2d y,
						Vector_2d la,Vector_2d lb) {
	//	System.out.println(dcmp(la.subtract(lb).cross(y.subtract(lb))));
		return	dcmp(la.subtract(lb).cross(x.subtract(lb))) * 
			dcmp(la.subtract(lb).cross(y.subtract(lb))) <= 0;
	}

	// line lx, ly interset line la, lb
	public static boolean intersect(Vector_2d lx, Vector_2d ly,
					Vector_2d la, Vector_2d lb) {
		return	dcmp(Math.max(lx.x, ly.x), Math.min(la.x, lb.x)) >= 0 &&
			dcmp(Math.max(la.x, lb.x), Math.min(lx.x, ly.x)) >= 0 &&
			dcmp(Math.max(lx.y, ly.y), Math.min(la.y, lb.y)) >= 0 &&
			dcmp(Math.max(la.y, lb.y), Math.min(lx.y, ly.y)) >= 0 &&
			opposite_side(lx, ly, la, lb) && 
			opposite_side(la, lb, lx, ly);
	}

	// get intersect point of line lx, ly and line la, lb
	public static Vector_2d intersection(	Vector_2d lx, Vector_2d ly,
						Vector_2d la, Vector_2d lb) {
		Vector_2d dx = rot90(ly.subtract(lx));
		Vector_2d da = rot90(lb.subtract(la));

		Vector_2d M1 = new Vector_2d(dx.x, da.x);
		Vector_2d M2 = new Vector_2d(dx.y, da.y);
		Vector_2d MA = new Vector_2d(lx.dot(dx), la.dot(da));

		double D  = M1.cross(M2);
		double Dx = M2.cross(MA);
		double Dy = M1.cross(MA);

		return new Vector_2d(Dx/D, Dy/D);
	}
	
	//x reflect about vector a
	public static Vector_2d reflect(Vector_2d x, Vector_2d a) {
		Vector_2d e = a.unit();
		Vector_2d dy = e.multiply(e.dot(x));
		Vector_2d dx = x.subtract(dy);
		return dy.subtract(dx);
	}
}
