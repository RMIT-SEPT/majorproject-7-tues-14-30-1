package tests;

import model.Business;
import model.Employee;
import model.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void easyCheapestCost() {
        //Testing the function that gets the cheapest employee's rate for a given business
        Business testBusiness= new Business(1, "Haircuts", "04 1234 5678", "person@customer.com");
        Employee emp1 = new Employee(1, testBusiness.getBusiness_id(), "firstname", "lastname", "firstname@haircuts.com", "0466879385", "test");
        emp1.setCost(12.50);
        Employee emp2 = new Employee(2, testBusiness.getBusiness_id(), "firstname", "lastname", "firstname@haircuts.com", "0466879385", "test");
        emp2.setCost(24.80);
        Employee emp3 = new Employee(2, testBusiness.getBusiness_id(), "firstname", "lastname", "firstname@haircuts.com", "0466879385", "test");
        emp3.setCost(18.65);
        testBusiness.addEmployee(emp1);testBusiness.addEmployee(emp2);testBusiness.addEmployee(emp3);
        double cheapestCost = testBusiness.getCheapestCost();
        assert(cheapestCost == 12.50);
    }

    @Test
    void CheapestCostNoEmployees() {
        //Testing the function that gets the cheapest employee's rate for a given business
        Business testBusiness= new Business(1, "Haircuts", "04 1234 5678", "person@customer.com");
        double cheapestCost = testBusiness.getCheapestCost();
        assert(cheapestCost == -1);
    }

    @Test
    void CheapestCostTied() {
        //Testing the function that gets the cheapest employee's rate for a given business
        Business testBusiness= new Business(1, "Haircuts", "04 1234 5678", "person@customer.com");
        Employee emp1 = new Employee(1, testBusiness.getBusiness_id(), "firstname", "lastname", "firstname@haircuts.com", "0466879385", "test");
        emp1.setCost(12.50);
        Employee emp2 = new Employee(2, testBusiness.getBusiness_id(), "firstname", "lastname", "firstname@haircuts.com", "0466879385", "test");
        emp2.setCost(12.50);
        testBusiness.addEmployee(emp1);testBusiness.addEmployee(emp2);

        double cheapestCost = testBusiness.getCheapestCost();
        assert(cheapestCost == 12.50);
    }
    @Test
    void nextSessionEasy() {
        Business testBusiness= new Business(1, "Haircuts", "04 1234 5678", "person@customer.com");
        Employee emp1 = new Employee(1, testBusiness.getBusiness_id(), "firstname", "lastname", "firstname@haircuts.com", "0466879385", "test");
        Session ses1 = new Session(1);

        int a[] = {0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0};
        emp1.addSessions(ses1);
        String nextSession = testBusiness.nextSession();
        assert("next session is tomorrow" == nextSession);
    }
}