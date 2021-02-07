package com.revature.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.dao.EmployeeDao;
import com.revature.dao.EmployeeDaoImpl;
import com.revature.models.Customer;


public class EmployeeService {
	private static Logger log = Logger.getLogger(EmployeeService.class);
	EmployeeDao employeeDao;
	
	public EmployeeService() {
		employeeDao = new EmployeeDaoImpl();
	}
	
	public void viewAllCustomersAccount() {
		
		List<Customer> list =  employeeDao.viewAllCustomersAccount();
		log.info("----------------------------------------------------------------------------------------------------------------------------");
		log.info("CustomerID\tFirstName\tLastName\tAccountNumber\tAccountType\tBalance\t\tOpenDate");
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getAccount().getAccountType() == "checking") {
				log.info(list.get(i).getCustomerID() + "\t\t" + list.get(i).getFirstName()+ "\t\t" + list.get(i).getLastName()+ "\t\t" + "checking" + "\t" +list.get(i).getAccount().getAccountNumberChecking() + "\t" +list.get(i).getAccount().getBalanceChecking()+ "\t\t" +list.get(i).getAccount().getDateChecking());				
			}else {
				log.info(list.get(i).getCustomerID() + "\t\t" + list.get(i).getFirstName()+ "\t\t" + list.get(i).getLastName()+ "\t\t" + "saving" + "\t\t"+list.get(i).getAccount().getAccountNumberSaving() + "\t\t" + +list.get(i).getAccount().getBalanceSaving()+ "\t\t" +list.get(i).getAccount().getDateSaving());
			}
		}
		
		log.info("----------------------------------------------------------------------------------------------------------------------------");
	}

}
