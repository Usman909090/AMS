package Models;

import java.sql.SQLException;

import Repositories.UserRepository;
import Utility.DBConnection;

public class User {
    private int id;
    private String name;
    private String cnic;
    private UserRole role;
    private String email;
    private String password;
    private double balance;

    // Constructor, getters, and setters

    public User(int id, String name, String cnic, String email, String password, UserRole role) {
        this.id = id;
        this.name = name;
        this.cnic = cnic;
        this.email = email;
        this.password = password;
        this.balance = (double) 0.0;
        this.role = role;
    }
    
    
 
    public User(String name, String cnic, String email, String password, UserRole role) throws SQLException {
        this.id = DBConnection.getLatestUserID();
        this.name = name;
        this.cnic = cnic;
        this.email = email;
        this.password = password;
        this.balance = (double) 0.0;
        this.role = role;
    }
    
   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getRole() {
        return role.getRoleName();
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public double getBalance() {
    	return this.balance;
    }
    
    public void setBalance(double bal) {
    	this.balance = bal;
    }
}