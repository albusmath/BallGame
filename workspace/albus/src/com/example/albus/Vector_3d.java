package com.example.albus;

public class Vector_3d {
	public double x, y, z;
	
	public Vector_3d(double x0, double y0, double z0) {
		x = x0; y = y0; z = z0;
	}
	
	public Vector_3d() {
		x = 0; y = 0; z = 0;
	}
	
	public Vector_3d add(Vector_3d b) {
		return new Vector_3d(x + b.x, y + b.y, z + b.z);
	}
	
	public Vector_3d subtract(Vector_3d b) {
		return new Vector_3d(x - b.x, y - b.y, z - b.z);
	}
	
	public Vector_3d multiply(double l) {
		return new Vector_3d(x * l, y * l, z * l);
	}
	
	public double norm() {
		return Math.sqrt(dot(this));
	}
	
	public double dot(Vector_3d b) {
		return x*b.x + y*b.y + z*b.z;
	}
	
	public Vector_3d cross(Vector_3d b) {
		return new Vector_3d(y*b.z - z*b.y, -(x*b.z - z*b.x), x*b.y - y*b.z);
	}

	public Vector_3d unit() {
		return multiply(1.0/norm());
	}
}
