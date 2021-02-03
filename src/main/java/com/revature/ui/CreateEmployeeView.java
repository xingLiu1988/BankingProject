package com.revature.ui;

import org.apache.log4j.Logger;

import com.revature.util.Sc;

public class CreateEmployeeView implements Menu {

	private static Logger log = Logger.getLogger(CreateEmployeeView.class);
	
	@Override
	public void display() {

		log.info("\n--------------------WELCOME TO CREATE CUSTOMER ACCOUNT--------------------");

		log.info("Please Enter Your First Name");
		String firstName = Sc.sc.nextLine();

		log.info("Please Enter Your Last Name");
		String lastName = Sc.sc.nextLine();

	}

}
