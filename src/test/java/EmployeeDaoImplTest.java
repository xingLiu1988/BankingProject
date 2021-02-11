import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.revature.dao.EmployeeDao;
import com.revature.dao.EmployeeDaoImpl;
import com.revature.models.Customer;
import com.revature.services.CustomerService;
import com.revature.services.EmployeeService;
import com.revature.ui.CreateCustomerView;
import com.revature.ui.CustomerLoginView;
import com.revature.ui.CustomerPanel;
import com.revature.ui.EmployeeLoginView;
import com.revature.ui.Menu;

class EmployeeDaoImplTest {

	//test if employee can view all customers
	@Test
	public void testViewAllCustomersAccount() {
		System.out.println("nihao");
		EmployeeDao e = new EmployeeDaoImpl();
		List<Customer> list = new ArrayList<>();
		list = e.viewAllCustomersAccount();
		System.out.println(list);
	}
	
	//test employee login ui
	@Test
	public void testEmployeeLoginView() {
		Menu e = new EmployeeLoginView();
		e.display();
	}
	
	//test customer ui display
	@Test
	public void testCustomerUI() {
		Menu e = new CustomerPanel();
		e.display();
	}

	@Test
	public void testCustomerLoginUI() {
		CustomerLoginView cus = new CustomerLoginView();
		cus.display();
	}
	
	@Test
	public void testUserChoide() {
		EmployeeService e = new EmployeeService();
		e.viewSingleCustomerAccount();
	}
	
	@Test
	public void testDeleteCustomerByAccountNumber() {
		EmployeeDaoImpl e = new EmployeeDaoImpl();
		int result = e.deleteCustomerByAccountNumber(94411802);
		System.out.println(result);
	}
	
	@Test
	public void testValidateUsernameAndPassword() {
		CustomerLoginView c = new CustomerLoginView();
		c.display();
	}
	
	@Test
	public void testViewSingleCustomer() {
		EmployeeService e = new EmployeeService();
		e.viewSingleCustomerAccount();
	}
	
	@Test
	public void testGetBalance() {
		CustomerService c = new CustomerService();
		Customer customer = c.getBalance(1);
		System.out.println(customer);
	}
	
	@Test
	public void testCreateCustomer() {
		Menu c = new CreateCustomerView();
		c.display();
		
	}
}
