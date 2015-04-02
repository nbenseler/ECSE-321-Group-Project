package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MapNameFrame extends JFrame {
	JPanel jp = new JPanel();
	JLabel jl = new JLabel();
	public static JButton inputButton = new JButton("Enter");
	JTextField editTextArea = new JTextField(30);
/*	JTextField editTextArea2 = new JTextField(30);
	JTextField editTextArea3 = new JTextField(30);*/
	
	private String name = "No Name";
	
	public MapNameFrame(String title)
	{
		setLayout(new BorderLayout());
		setTitle("Enter a Map Name");
		setVisible(true);
		setSize(400,100);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		jp.add(editTextArea);
/*		jp.add(editTextArea2);
		jp.add(editTextArea3);*/
		editTextArea.setBackground(Color.WHITE);
		editTextArea.setForeground(Color.BLUE);
/*		editTextArea2.setBackground(Color.WHITE);
		editTextArea2.setForeground(Color.BLUE);
		editTextArea3.setBackground(Color.WHITE);
		editTextArea3.setForeground(Color.BLUE);*/
		Container c = getContentPane();
	    c.add(editTextArea, BorderLayout.SOUTH);
/*	    c.add(editTextArea2, BorderLayout.north);
	    c.add(editTextArea3, BorderLayout.AFTER_LINE_ENDS);*/
	    c.add(inputButton, BorderLayout.WEST);
	    MapNameFrame.inputButton.addActionListener(new ActionListener()
	    {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				name = editTextArea.getText();
				editTextArea.setText("");
				name = name.replaceAll(",", "_");
				if (!name.equals(""))
				{
					Screen.setMapNameViewable(false);
					Screen.setMapNameText(name);
				}
			}
	    	
	    });
		
		
	}
	

}
