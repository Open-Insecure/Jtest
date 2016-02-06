package com.br.dong.swing.jimagecomponent.gui;

import com.br.dong.httpclientTest.porn.new_91porn_2016.componet.JImageComponent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


public class SampleApplication {
	
	/**
	 * Main program entry point.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
	/**
	 * Creates and displays the sample Swing application.
	 */
	public static void createAndShowGUI() {
		JFrame frame = new JFrame("Image in JImageComponent");
		frame.setPreferredSize(new Dimension(320, 320));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane pane = new JScrollPane();
		pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JImageComponent image = new JImageComponent();
		
		try {
			image.loadImage(Object.class.getResource("/com/br/dong/swing/jimagecomponent/JavaTM_Logo.png"));
		}
		catch (IOException exception) {
			exception.printStackTrace();
		}
		
		pane.setViewportView(image);
		
		frame.getContentPane().add(pane, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}
	
}
