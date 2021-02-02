package com.revature.main;

import com.revature.ui.MainMenu;
import com.revature.ui.Menu;

public class Application {

	public static void main(String[] args) {
		
		// Create mainMenu object to call display menu for our system
		Menu mainMenu = new MainMenu();
		mainMenu.display();
		System.out.println("\n---------------THANKS FOR USING XING LIU'S SYSTEM, SEE YOU NEXT TIME--------------");
		
	}

}
