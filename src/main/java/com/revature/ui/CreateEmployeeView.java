package com.revature.ui;

import com.revature.util.Sc;

public class CreateEmployeeView implements Menu {

	@Override
	public void display() {

		System.out.println("\n--------------------WELCOME TO CREATE CUSTOMER ACCOUNT--------------------");

		System.out.println("Please Enter Your First Name");
		String firstName = Sc.sc.nextLine();

		System.out.println("Please Enter Your Last Name");
		String lastName = Sc.sc.nextLine();

	}

}
