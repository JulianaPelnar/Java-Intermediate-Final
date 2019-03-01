package com.javafinal.maven.quickstart.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TemaArray {
	
	//  Fields of class
	private ArrayList<Tema> temas = new ArrayList<Tema>();
	
	//  Constructor
	/**
	 * This method will return all Temas founded on database whenever a new TemaArray object is created. 
	 * The result will be an array of temas, so they should be treated as an ArrayList.
	 * 
	 * @param connection (Connection object, it will be the same throughout the program)
	 */
	public TemaArray (Connection connection) {
		
		try {
		
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM temas");
			
			while(rs.next()) {
			
				Tema t = new Tema();
				t.setTema(rs.getString("temas"));
				t.setIdtweet(rs.getInt("idtweet"));
				this.temas.add(t);
			
			}
		} catch(SQLException e) {
			System.out.println("Hubo un problema al crear la lista de temas.");
		}
	}
	
	//  GETTERS
	public ArrayList<Tema> getTemaArray() {
		return this.temas;
	}
	
	
	
}
