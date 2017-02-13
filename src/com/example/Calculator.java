package com.example;

import javax.swing.*;
import java.util.logging.Logger;

class Calculator
{
	 public static void main(String[] args)
	 {
		//Look & Feel
		 Logger log = Logger.getLogger(Calculator.class.getName());
	    try {
	            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        } catch (Exception unused) {
				log.warning("bad");
	        }
	    

		 CalculatorFrame frame = new CalculatorFrame();
		 frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	     frame.setTitle("com.example.Calculator");
	     frame.setResizable(true);
	     frame.setSize(325, 300);
	     frame.setLocation(100,100);
	     frame.setVisible(true);
	 }
	 
}


