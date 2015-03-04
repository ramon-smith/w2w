package Tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import Entitys.BuildSite;
import Entitys.Customer;


public class CreateCustsAndSites {
	
	

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("w2w.odb");
		EntityManager cust = emf.createEntityManager();
		
		Customer a = new Customer("Ramon");
		a.addBuildSite(new BuildSite(-36.50254,  174.442351));

		cust.getTransaction().begin();;
		cust.persist(a);
		cust.getTransaction().commit();
		
		cust.close();
		emf.close();
	}

}
