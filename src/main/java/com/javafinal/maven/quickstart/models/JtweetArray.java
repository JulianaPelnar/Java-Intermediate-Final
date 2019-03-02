package com.javafinal.maven.quickstart.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JtweetArray {
	private ArrayList<Jtweet> jtweets = new ArrayList<Jtweet>();
	
	/**
	 * This method will return all Jtweets founded on database whenever a new JtweetsArray object is created. 
	 * The result will be an array of jtweets, so they should be treated as an ArrayList.
	 * 
	 * @param connection (Connection object, it will be the same throughout the program)
	 */
	JtweetArray(Connection connection) {
		
		try {
		
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM jtweets");
			
			while(rs.next()) {
			
				Jtweet j = new Jtweet();
				j.setIdtweet(rs.getInt("idtweet"));
				j.setNombreusuario(rs.getString("nombreusuario"));
				j.setTexto(rs.getString("texto"));
				this.jtweets.add(j);
			
			}
		} catch (SQLException e) {
			System.out.println("Hubo un problema al intentar acceder a la base de datos.");
			System.out.println("No se pudo crear lista con Jtweets.");
		}
		
	}
	
	//  GETTERS
	public ArrayList<Jtweet> getJtweetArray() {
		return this.jtweets;
	}
	
	//  SETTERS
	public void setJtweetArray(ArrayList<Jtweet> ja) {
		this.jtweets = ja;
	}
	
	
	//  SORTED BY
	
	/**
	 * This method will print a list of jtweets written by a specified user. But will not create 
	 * an array of jtweets, only print them.
	 * 
	 * @param br (BufferedReader object, it will be the same throughout the program)
	 */
	public void sortByUserName(BufferedReader br) {
		
		// Declaration of variables
		boolean success = false;
		String nombreusuario;
		
		System.out.println("Ingrese el nombre de usuario cuyos jtweets quiere ver : ");
		
		while(!success) {
			try {
				nombreusuario = br.readLine();
				
				for(Jtweet j : this.getJtweetArray()) {
					
					// this will print jtweets by user name
					if(j.getNombreusuario().equals(nombreusuario)) {
						System.out.println(j.toString());
					}
				}
				success = true;

			} catch (IOException e) {
				System.out.println("Hubo un problema al leer el nombre ingresado, intente nuevamente.");
			}
		}
	}
	
	/**
	 * This method will print a list of jtweets which text comprehend word/s written by the user. 
	 * But will not create an array of jtweets, only print them.
	 * 
	 * @param br (BufferedReader object, it will be the same throughout the program)
	 */
	public void sortByText(BufferedReader br) {
		
		// Declaration of variables
		boolean success = false;
		String text;
		Pattern p;
		Matcher m = null;
		
		System.out.println("Ingrese las palabras que quiere buscar : ");
		
		while(!success) {
			
			try {
				
				text = br.readLine();
				p = Pattern.compile(text);
				
				for(Jtweet j : this.getJtweetArray()) {
					
					m = p.matcher(j.getTexto());
					
					// this will print jtweets if matches are found
					if(m.matches()) {
						System.out.println(j.toString());
					}
				}
				success = true;
				
			} catch (IOException e) {
				System.out.println("Hubo un problema al leer las palabras ingresadas, intente nuevamente.");
			}
		}
		
			
	}
	
	/**
	 * This method will print a list of jtweets with same tema, written by user. 
	 * But will not create an array of jtweets, only print them.
	 * 
	 * @param br
	 * @param temas
	 */
	public void sortByTema(BufferedReader br, TemaArray temas) {
		
		// Declaration of variables
		boolean success = false;
		String tema;
		
		System.out.println("Ingrese el tema que quiere buscar : ");
		
		while(!success) {
		
			try {
			
				tema = br.readLine();
				
				// searchs through temas
				for(Tema t : temas.getTemaArray()) {
				
					if(t.getTema().equalsIgnoreCase(tema)) {
					
						// if founded, will search idtweet to print jtweet with same tema.
						for(Jtweet j : this.getJtweetArray()) {
						
							if(t.getIdtweet() == j.getIdtweet()) {
								System.out.println(j.toString());
							}
							break;
						}
					}
				}
			} catch(IOException e) {
				System.out.println("Hubo un problema al leer el tema ingresado, intente nuevamente.");
			}
		}
	}
	
}
