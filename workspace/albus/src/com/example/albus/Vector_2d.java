package com.example.albus;

public class Vector_2d {
	public double x, y;
	
	public Vector_2d(double x0, double y0) {
		x = x0; y = y0;
	}
	
	public Vector_2d() {
		x = 0; y = 0;
	}

	public void print() {
		System.out.println(x + " " + y);
	}
	
	public Vector_2d add(Vector_2d b) {
		return new Vector_2d(x + b.x, y + b.y);
	}
	
	public Vector_2d subtract(Vector_2d b) {
		return new Vector_2d(x - b.x, y - b.y);
	}
	
	public Vector_2d multiply(double l) {
		return new Vector_2d(x * l, y * l);
	}
	
	public double norm() {
		return Math.sqrt(dot(this));
	}
	
	public double dot(Vector_2d b) {
		return x*b.x + y*b.y;
	}
	
	public double cross(Vector_2d b) {
		return x*b.y - y*b.x;
	}

	public Vector_2d unit() {
		return multiply(1.0/norm());
	}
}
