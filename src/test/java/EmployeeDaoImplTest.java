import org.junit.jupiter.api.Test;

import com.revature.dao.EmployeeDao;
import com.revature.dao.EmployeeDaoImpl;
import com.revature.ui.EmployeeLoginView;
import com.revature.ui.Menu;

class EmployeeDaoImplTest {

	//test if employee can view all customers
	@Test
	public void testViewAllCustomersAccount() {
		System.out.println("nihao");
		EmployeeDao e = new EmployeeDaoImpl();
		e.viewAllCustomersAccount();
	}
	
	//test employee login ui
	@Test
	public void testEmployeeLoginView() {
		Menu e = new EmployeeLoginView();
		e.display();
	}
	
}
