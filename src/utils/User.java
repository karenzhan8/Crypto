package utils;

import java.io.*;
import java.util.*;

/**
 * Class used to verify user in the login 
 */
public class User {
	private static User instance;
	
	private User () {
		//singleton design pattern
	}
	
	/**
	 * Class that creates a global point of access to User using singleton design pattern  
	 */
	public static User getInstance() {
		if (instance == null) {
			instance = new User();
		}
		return instance;
	}
	
	/**
	 * verifies user info
	 * @param user
	 * @param pass
	 * @return		true if match and false if not a match or FileNotFoundException is thrown
	 */
	public boolean verify (String user, String pass) {
		try {
			//read input from file
			Scanner input = new Scanner(new File("login.txt"));
			String[] curr = {"", ""}; 
			
			while (input.hasNextLine()) {
				curr = input.nextLine().split(" ");
				
				//if user and password matches, return true 
				if (curr[0].equals(user) && curr[1].equals(pass)) {
					return true;
				}
			}
			return false;	
		} 
		catch (FileNotFoundException e) {
			return false;
		}
	}
}
