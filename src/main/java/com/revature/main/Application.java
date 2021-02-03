package com.revature.main;

import org.apache.log4j.Logger;

import com.revature.ui.MainMenu;
import com.revature.ui.Menu;

public class Application {
	
	private static Logger log = Logger.getLogger(Application.class);
	
	public static void main(String[] args) {
		
		// Create mainMenu object to call display menu for our system
		Menu mainMenu = new MainMenu();
		mainMenu.display();
		log.info("\n---------------THANKS FOR USING XING LIU'S SYSTEM, SEE YOU NEXT TIME--------------");
		
	}

}
