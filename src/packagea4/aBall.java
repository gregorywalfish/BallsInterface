package packagea4;


import acm.program.*;
import acm.graphics.*;
import java.awt.*;
import acm.program.GraphicsProgram;

import javax.swing.AbstractButton;
import javax.swing.JToggleButton;

public class aBall extends Thread{
	
	/** These notes are in reference to Frank Ferrie's Assignment Instructrions
	* The constructor specifies the parameters for simulation. They are *
	* @param Xi double The initial X position of the center of the ball
	* @param Yi double The initial Y position of the center of the ball
	* @param Vo double The initial velocity of the ball at launch
	* @param theta double Launch angle (with the horizontal plane)
	* @param bSize double The radius of the ball in simulation units
	* @param bColor Color The initial color of the ball
	* @param bLoss double Fraction [0,1] of the energy lost on each bounce
	* 
	* @author gregorywalfish 
	* 
	* */
	
	//initialize variables 
	private static final int HEIGHT = 600;
	private static final double SCALE = HEIGHT/100; // pixels per meter
	private static final double g = 9.8; // MKS gravitational constant 9.8 m/s^2
	private static final double k= 0.0001; // coulombs
	private static final double Pi = 3.141592654; // To convert degrees to radian
	private static final double TICK = 0.1; // Clock tick duration (sec)
	private static final double ETHR = 0.01; // If either Vx or Vy < ETHR STOP 
	private static final int WIDTH = 1200; // n.b. screen coordinates
	public static double time;
	
	double Xi;
	double Yi;
	double Vo;
	double theta;
	double bSize;
	double bLoss;
	Color bColor;
	GOval myBall;
	boolean isRunning;
	private AbstractButton Trace;
	private bSim link;
	GOval trace;
	
	//making the resulting gOval acccessable outside of aBall
	public GOval getBall() {
		return myBall; 
	}
	//stacks ball on top of each other
	public double getbSize() {
		return bSize;
	}
	
	

	//initialize constructor and getting simulation parameters 
	public aBall(double Xi, double Yi, double Vo, double theta, double bSize, Color bColor, double bLoss,bSim link) {
		this.Xi = Xi; 
		this.Yi = Yi;
		this.Vo = Vo;
		this.theta = theta;
		this.bSize = bSize;
		this.bColor = bColor;
		this.bLoss = bLoss;
		this.link=link;
		
		
		
		//create the filled ball
		myBall = new GOval((2*bSize*SCALE),(2*bSize*SCALE)); 
		myBall.setFilled(true); 
		myBall.setFillColor(bColor);
	}
	
		
	//moves ball to a set location (x,y)
		public void moveTo(double x, double y) {
			myBall.setLocation(x, y);
		}

		
		
	/**
	* The run method implements the simulation loop from Assignment 1. * Once the start method is called on the aBall instance, the
	* code in the run method is executed concurrently with the main
	* program.
	* @param void
	* @return void
	* Lots of this code was recycled from my assignment 1 "Bounce.java" 
	*/
	public void run() {
		isRunning=true;
		//computation of initial velocities in x and y direction, and terminal velocity, and setting time to 0
		double Vox=Vo*Math.cos(theta*Pi/180);  
		double Voy=Vo*Math.sin(theta*Pi/180); 
		double Vt = g / (4*Pi*bSize*bSize*k); 
		double time = 0;
		double X=Xi;
		double Y=Yi;
		double Xlast;
		double Ylast;
		double KEx=ETHR;
		double KEy=ETHR;
		double Elast;
		
		double KEt=0;
		double Vy;
		double Vx;
		double Xo=Xi;
		
		Elast = 0.5*Vo*Vo;
		
		boolean HasEnoughEnergy=true;
		
			//simulation loop
			while (HasEnoughEnergy) {
				time += TICK;
				Xlast=X;
				Ylast=Y;
				
				X= Vox*Vt/g*(1-Math.exp(-g*time/Vt)); 
				Y = bSize + ((Vt/g*(Voy+Vt)*(1-Math.exp(-g*time/Vt))-Vt*time));
				
				
				Vx = (X-Xlast)/TICK;
				Vy= (Y-Ylast)/TICK;
				
				
				//check if ball hits the ground plane
				if (Vy<0 && Y<=Yi) {
					
					
					
					KEx = 0.5*Vx*Vx*(1-bLoss);
					KEy = 0.5*Vy*Vy*(1-bLoss);
					
					KEt= KEx+KEy;
					
					
					
					Vox = Math.sqrt(2*KEx);
					Voy = Math.sqrt(2*KEy);
					
					Y=Yi; 
					time=0; 
					Xo+=X;
					X=0; 
					
					Xlast=X;
				    Ylast=Y;
					
				  //setting Vox to negative so that the ball can bounce in both directions
					if(Vx<0) {
						Vox=-Vox;
					}  
					//Check if ball has enough energy to keep bouncing and make sure total is less than last
					if(KEx+KEy < ETHR || KEx+KEy>=Elast) 
						HasEnoughEnergy=false;			 
						
						else
							Elast=KEx+KEy;
					
				}
				
				//set ball to current position
				double ScrX;
				double ScrY;
				ScrX = (int) ((Xo+X-bSize)*SCALE); //changed this section
				ScrY = (int)(HEIGHT-(Y+bSize)*SCALE);
				myBall.setLocation(ScrX,ScrY);
				
				
				
				//if a link is provided plot with trace
				if (link!=null) {
					trace(Xo+X,Y);
				}
				
				try {
					// pause for 20 milliseconds
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		isRunning=false;
			
				
	}
	//the trace method 
	private void trace (double x, double y) {
		double ScrXt = x*SCALE;
		double ScrYt=HEIGHT-y*SCALE;
		GOval pt = new GOval (ScrXt, ScrYt, 1,1);
		pt.setColor(this.bColor);
		pt.setFilled(true);
		link.add(pt);
	}
				
		
}	
		
		
		
		
	