package com.javafinal.maven.quickstart.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tema {
	
	// Fields of class
	private int idtweet;
	private String tema;
	
	public Tema() {
	}
	
	//  GETTERS
	public int getIdtweet() {
		return this.idtweet;
	}
	public String getTema() {
		return this.tema;
	}
	
	//  SETTERS
	public void setIdtweet(int idtweet) {
		this.idtweet = idtweet;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}
	
	/**
	 * This method adds temas directly to database. 
	 * Temas are words starting with "#" stored in a specific table called temas. They
	 * are used to find jtweets with similar content.
	 * 
	 * @param connection (Connection object, it will be the same throughout the program)
	 * @param texto (String that possibly contains temas)
	 * @param idtweet (int that relates a tema to a jtweet in the database, obtained before the method is called)
	 */
	public void addTema(Connection connection, String texto, int idtweet) {
		
		// Declaration of variables
		Pattern p = Pattern.compile("(\\s#[a-zA-ZÀ-ÿ0-9]+)");
		Matcher m = p.matcher(texto);
		PreparedStatement ps;
		
		// this loop will iterate while the pattern (# + any word) is found.
		while(m.find()) {
			
			try {
				
				// prepared statement to insert founded temas in database
				ps = connection.prepareStatement("INSERT INTO temas(idtweet, tema) VALUES (?, ?)");
				// ---------------------------------------------------------------------------------------
				// this print is used for testing only. Should be deleted when the application is finished
				// ---------------------------------------------------------------------------------------
				System.out.println("Match : " + m.group());
				ps.setInt(1, idtweet);
				ps.setString(2, m.group());
				ps.execute();
				
			} catch(SQLException e) {
				System.out.println("Hubo un error al buscar etiquetas.");
				//e.printStackTrace();
			}
		}
	}
}
