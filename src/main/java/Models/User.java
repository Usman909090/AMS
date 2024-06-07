package Models;

import java.sql.SQLException;

import Repositories.UserRepository;

public class User {
    private int id;
    private String name;
    private String cnic;
    private String role;
    private String email;
    private String password;

    // Constructor, getters, and setters

    public User(int id, String name, String cnic, String role, String email, String password) {
        this.id = id;
        this.name = name;
        this.cnic = cnic;
        this.role = role;
        this.email = email;
        this.password = password;
    }
    
    public User(String name, String cnic, String role, String email, String password) {
        this.id = getLatestUserID();
        this.name = name;
        this.cnic = cnic;
        this.role = role;
        this.email = email;
        this.password = password;
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
        return role;
    }

    public void setRole(String role) {
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
    
    private int getLatestUserID() {
    	int id = -1;
    	try {
    		id = UserRepository.getLatestUserID();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return id;
    }
}