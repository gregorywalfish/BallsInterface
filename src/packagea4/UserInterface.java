package packagea4;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import acm.gui.TableLayout;

import acm.gui.*;

/**
 * 
 * Userinterface class that sets up the JPanel
 * @author gregorywalfish
 *
 */

public class UserInterface extends JPanel {
	
	//declaring JLabels
	JLabel paramName;
	JLabel minValue;
	JLabel maxValue;
	JLabel currentVal;
	JSlider slider;
	
	//declaring the fields
	IntField IValue;
	DoubleField DValue;
	
	//declaring the integers
	int intMin;
	int intMax;
	int intCurrent;
	
	//declaring the doubles
	double doubleMin;
	double doubleMax;
	double doubleCurrent;
	

	//this is the constructor for building each parameter in the UI for INTEGER
	public UserInterface (String name, int min, int max, int current) {

		
		this.setLayout(new TableLayout(1, 5));
	
		paramName  = new JLabel(name);
		minValue = new JLabel(min +"");
		maxValue = new JLabel(max +"");
		IValue = new IntField(current);
		IValue.setForeground(Color.blue);
		
		
		this.add(paramName,"width = 100");
		this.add(minValue,"width = 25");
		this.add(maxValue,"width = 100");
		this.add(IValue,"width = 80");
		
		
		
		
	}
	//this is the constructor for building each parameter in the UI for DOUBLE
	public UserInterface (String name, double min, double max, double current) {

		
		this.setLayout(new TableLayout(1, 5));

		paramName  = new JLabel(name);
		minValue = new JLabel(min +"");
		maxValue = new JLabel(max +"");
		DValue = new DoubleField(current);
		DValue.setForeground(Color.blue);
		
		
		this.add(paramName,"width = 100");
		this.add(minValue,"width = 25");
		this.add(maxValue,"width = 100");
		this.add(DValue,"width = 80");

		
	}
	
}
