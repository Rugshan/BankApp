package coe528.project;

import java.io.FileWriter;
import java.io.IOException;

public  class User {

    protected String username;

    public User(String username, String password, String role) {
        this.username = username;
        
        String filename = "credentials/" + username + ".txt";

        try {
            FileWriter writer = new FileWriter(filename, false);
            writer.write(password + "\n" + role);
            writer.close();
        } 
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
            
    }
    
    public String getUsername(){
        return username;
    }
        
}
