package com.revature.ui;

import org.apache.log4j.Logger;

public class CustomerLoginView implements Menu{

	private static Logger log = Logger.getLogger(CustomerLoginView.class);
	
	@Override
	public void display() {
		log.info("customerloginview");
	}

}
