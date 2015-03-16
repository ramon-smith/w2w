package Tests;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import Entitys.WorkSite;
import Entitys.Customer;

public class GetCustomers {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("w2w.odb");
		EntityManager cust = emf.createEntityManager();
		
		TypedQuery<Customer> q2 = cust.createQuery("SELECT c FROM Customer c", Customer.class);
		List<Customer> clist = q2.getResultList();

		
		for (Customer c : clist){
			System.out.println(c);
		}
	}

}
