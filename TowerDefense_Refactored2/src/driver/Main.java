package driver;

import views.Frame;

public class Main {
	
	public static java.util.Scanner sc = new java.util.Scanner(System.in); // scanner is opened to be used for getting user inputs
	
	public static void main (String[]args)	
	{
		int width;				//this will be the number of columns
		int height;
		
		System.out.println("How many columns? ");
		String stringHeight = sc.nextLine();
		if (stringHeight == null)
		{
			System.out.println("Cannot put blank number of columns. set to default of 5");
			height = 5;			
		}
		else
		{
		height = Integer.parseInt(stringHeight);		// string is parsed for an integer - if no double exists will through an exception - this is okay because data is always saved
		}

		
		System.out.println("How many rows?");
		String stringWidth = sc.nextLine();
		if (stringWidth == null)
		{
			System.out.println("Cannot put blank number of rows. set to default of 5");
			width = 15;
		}
		else
		{
			width = Integer.parseInt(stringWidth);		// string is parsed for a double - if no double exists will through an exception - this is okay because data is always saved
		}
		
		new Frame(height, width);         		//creates new frame class
	}

}
