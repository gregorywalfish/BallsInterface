package packagea4;

import acm.program.GraphicsProgram;

import acm.util.RandomGenerator;
import acm.program.*;
import acm.graphics.*;
import acm.gui.DoubleField;
import acm.gui.IntField;
import acm.gui.TableLayout;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This is my main class from assignment 2
 * 
 * Behaviour is governed by the class below
 * 
 * @author gregorywalfish
 *
 */

public class bSim extends GraphicsProgram implements ChangeListener, ItemListener {
	//parameters used for the program
		private static final int WIDTH = 1425; // n.b. screen coordinates
		private static final int HEIGHT = 600;
		private static final int OFFSET = 200;
		private static final double SCALE = HEIGHT/100; // pixels per meter
	
		bSim link; //for the tracer
		bTree myTree; //creating the btree
		RandomGenerator rgen = new RandomGenerator(); //random generator
		
		//declaring variables
		UserInterface numballs_box;
		UserInterface minsize_box;
		UserInterface maxsize_box;
		UserInterface lossmin_box;
		UserInterface lossmax_box;
		UserInterface minvel_box;
		UserInterface maxvel_box;
		UserInterface thmin_box;
		UserInterface thmax_box;
		
		//the drop downs
		JComboBox<String> File;
		JComboBox<String> Edit;
		JComboBox<String> Help;
		JComboBox<String> bSimC;
		
		//Trace Button
		JToggleButton Trace;
		
		//this is the method that adds the ground
		public void addHorizon() {
			GRect groundplane = new GRect (0, HEIGHT,WIDTH,3);  //building the ground
			groundplane.setFilled(true);
			groundplane.setColor(Color.black);
			add(groundplane);
		}
		/**
		 * creation of all the parameter boxes for the User Interface
		 * setting the seed for random generator 
		 * 
		 */
	
		public void init() {
			rgen.setSeed((long) 424242);
			//resize window
			this.resize(WIDTH,HEIGHT+OFFSET);
			
			//add horizon method
			addHorizon();
			
			//create parameters for the UI
			JLabel parameters = new JLabel("General Simulation Parameters");
			numballs_box = new UserInterface("NUMBALLS:", 1, 255, 60);
			minsize_box = new UserInterface("MIN SIZE:", 1.0, 25.0, 1.0);
			maxsize_box = new UserInterface("MAX SIZE:", 1.0, 25.0, 7.0);
			lossmin_box = new UserInterface("LOSS MIN:", 0.0, 1.0, 0.2);
			lossmax_box = new UserInterface("LOSS MAX:", 0.0, 1.0, 0.6);
			minvel_box = new UserInterface("MIN VEL:", 1.0,200.0,40.0);
			maxvel_box = new UserInterface("MAX VEL:", 1.0, 200.0, 50.0);
			thmin_box  = new UserInterface("TH MIN:", 1.0, 180.0, 80.0);
			thmax_box = new UserInterface("TH MAX:", 1.0, 180.0, 100.0);
			
			//adding the parameters to the UI 
			JPanel east = new JPanel(new TableLayout(10, 1));
			east.add(parameters);
			east.add(numballs_box);
			east.add(minsize_box);
			east.add(maxsize_box);
			east.add(lossmin_box);
			east.add(lossmax_box);
			east.add(minvel_box);
			east.add(maxvel_box);
			east.add(thmin_box);
			east.add(thmax_box);

			//placement on the screen
			add(east,EAST);
			
			bSimC = new JComboBox<String>();
			bSimC.addItem("bSim");
			bSimC.addItem("Run");
			bSimC.addItem("Stack");
			bSimC.addItem("Clear");
			bSimC.addItem("Stop");
			bSimC.addItem("Quit");

			File = new JComboBox<String>();
			File.addItem("File");

			Edit = new JComboBox<String>();
			Edit.addItem("Edit");

			Help = new JComboBox<String>();
			Help.addItem("Help");

			JPanel top = new JPanel(new TableLayout(1, 4));
			top.add(bSimC);
			top.add(File);
			top.add(Edit);
			top.add(Help);
			add(top,NORTH);

			Trace = new JToggleButton("Trace"); //jtoggle type button
			add(Trace,SOUTH); //adding it to the bottom 
			
			Trace.addChangeListener(this);
			bSimC.addItemListener(this);
			
			//create btree
			bTree myTree = new bTree(); 
		     
			
		}
		/**
		 * The following is all the different methods that the program can do
		 * 
		 */
		public void doStack() { //stack sorting method 
			
			//while(myTree.isRunning()); //block until termination
			myTree.inorder(); //lay out balls in order
			
		}
		
		public void doClear() { //this is the clear all method
			this.removeAll();
			addHorizon();
			myTree = new bTree();
			
		}

		public void doExit() {	//this is the exit method 
			System.exit(0);
			
		}
		public void doSim() {
			
			for (int i=0; i<numballs_box.IValue.getValue(); i++) {
				
				double bSize=rgen.nextDouble(minsize_box.DValue.getValue(),maxsize_box.DValue.getValue());
				Color bColor =rgen.nextColor();
				double bLoss=rgen.nextDouble(lossmin_box.DValue.getValue(),lossmax_box.DValue.getValue());
				double Vo=rgen.nextDouble(minvel_box.DValue.getValue(), maxvel_box.DValue.getValue());
				double theta= rgen.nextDouble(thmin_box.DValue.getValue(),thmax_box.DValue.getValue());
				double Xi =(WIDTH/2)/SCALE;
				double Yi =bSize;
				
				aBall Ball = new aBall(Xi, Yi, Vo, theta, bSize, bColor, bLoss, link);
				add(Ball.getBall());
				Ball.start();
				
				
				//the program was starting with always 1 ball so i caught the error and set doClear which fixed it.
					try {
						myTree.addNode(Ball);
					}
					catch(Exception e) {
						System.out.println(">>>Error caught on first run");
						doClear();
						
						
					}
				}

			GLabel prompt = new GLabel("Simulation done", 900, 550); //prompt user
			prompt.setColor(Color.red); //set color to red
			add(prompt); //add prompt to screen

			
		}
		
		/**
		 * listens for when the trace button is clicked
		 * 
		 */
		@Override
		//changes states 
		public void stateChanged(ChangeEvent e) {

			if (e.getSource()== Trace) {
				System.out.println("trace source");
				if (Trace.isSelected()){
					link = this;
				}
				else {
					link=null;
				}
			}
			
		}
		
		/**
		 * 
		 * listens for which method was chosen
		 */
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			
			if(e.getSource() == bSimC) {
				System.out.println("bSim combox was selected");
				if(bSimC.getSelectedIndex()== 1 && e.getStateChange()==1)	{
					System.out.println("Starting simulation"); 
					doSim();
										
				}

				if(bSimC.getSelectedIndex() == 2 && e.getStateChange() == 1) {
					System.out.println("Stack is selected");
					doStack();
				}
				if(bSimC.getSelectedIndex()== 3 && e.getStateChange() == 1) {
					System.out.println("Clear is selected");
					doClear();
				}
				if(bSimC.getSelectedIndex() == 4 && e.getStateChange() == 1) {
					System.out.println("Stop is selected");
					myTree.stop();

				}
				if(bSimC.getSelectedIndex() == 5 && e.getStateChange() == 1) {
					System.out.println("Quit is selected");
					doExit();
					
				}
			}	
		}

		
	}

		




