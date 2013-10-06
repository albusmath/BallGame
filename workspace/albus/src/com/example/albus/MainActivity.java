package com.example.albus;

import java.util.*;

import android.graphics.*;
import android.hardware.*;
import android.os.*;
import android.annotation.*;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity implements SensorEventListener {
	private Ball ball;
	private Sensor mAccelerometer;
	private SensorManager mSensorManager;
	private Vector<Vector_2d> wallA, wallB;
	private RelativeLayout mainLayout;
	
	protected void insertWall(Vector_2d la, Vector_2d lb) {
		wallA.add(la);
		wallB.add(lb);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		
		mAccelerometer = (Sensor) mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
		
		ball = new Ball(this);
		
		mainLayout.addView(ball);
		
		wallA = new Vector<Vector_2d>();
		wallB = new Vector<Vector_2d>();

		int W = getWindowManager().getDefaultDisplay().getWidth();
		int H = getWindowManager().getDefaultDisplay().getHeight();

		double r = ball.getR();
		
		insertWall(new Vector_2d(r, r), new Vector_2d(W-r, r));
		insertWall(new Vector_2d(W-r, r), new Vector_2d(W-r, H-r));
		insertWall(new Vector_2d(W-r, H-r), new Vector_2d(r, H-r));
		insertWall(new Vector_2d(r, H-r), new Vector_2d(r, r));

		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@Override
	protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }
	
	@Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
			return ;
		}
		ball.acceleration = new Vector_3d(-event.values[0], event.values[1], event.values[2]).multiply(2.0);
		findViewById(android.R.id.content).invalidate();
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
	}
	
	class Ball extends View {
		private double r, x, y;
		public Vector_3d acceleration, velocity;
		private Paint painter;
		
		private long lastTime = 0;
		
		public Ball(Context context) {
			super(context);
			r = 50;
			x = 100;
			y = 100;
			acceleration = new Vector_3d();
			velocity = new Vector_3d();
			painter = new Paint();
			painter.setAntiAlias(true);
			painter.setColor(Color.GREEN);
		}
		
		public double getR() {
			return r;
		}

		@SuppressLint("Override")
		public double getX() {
			return x;
		}
		
		@SuppressLint("Override")
		public double getY() {
			return y;
		}

		@SuppressLint("Override")
		public void setX(double d) {
			x = d;
		}
		
		@SuppressLint("Override")	
		public void setY(double y0) {
			y = y0;
		}
		
		@SuppressLint("Override")
		public void setR(double r0) {
			r = r0;
		}
		
		boolean isIntersected(	Vector_2d A, Vector_2d B,
					Vector_2d X, Vector_2d Y) {
			return true ;
		}
		
		void reflect(Vector_2d pos, double dt) {
			boolean hasNext = true ;
			while (hasNext) {
				hasNext = false ;
				for (int i = 0;i < wallA.size();i++) {
					Vector_2d next = new Vector_2d(velocity.x, velocity.y).multiply(dt).add(pos);
					if (Geometry.intersect(pos, next, wallA.get(i), wallB.get(i))) {
						Vector_2d t = Geometry.reflect(new Vector_2d(velocity.x, velocity.y), 
												wallA.get(i).subtract(wallB.get(i)));
						velocity.x = t.x;
						velocity.y = t.y;
						hasNext = true ;
					}
				}
			}
		}
		
		@SuppressLint("DrawAllocation")
		protected void onDraw(Canvas canvas) {
			long currTime = System.nanoTime();
			double dt = 0;
			if (lastTime > 0) {
				 dt = (currTime - lastTime) / 1000000000.0;
				velocity = velocity.add(acceleration.multiply(dt));

				reflect(new Vector_2d(x, y), dt);
				x += velocity.x * dt;
				y += velocity.y * dt;

				canvas.drawCircle((float)x, (float)y, (float)r, painter);
			}
			lastTime = currTime;
			invalidate();
			System.out.println("albus> " + x + " " + y + " " + velocity.x + " " + velocity.y + " "+ dt);
		}
	}
}
