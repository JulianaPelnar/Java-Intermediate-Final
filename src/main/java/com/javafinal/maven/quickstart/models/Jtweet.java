package com.javafinal.maven.quickstart.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Jtweet {
	
	// Fields of class
	private int idtweet;
	private String nombreusuario;
	private String texto;
	
	public Jtweet() {
	}
	
	//  GETTERS
	public int getIdtweet() {
		return this.idtweet;
	}
	public String getNombreusuario() {
		return this.nombreusuario;
	}
	public String getTexto() {
		return this.texto;
	}
	
	//  SETTERS
	public void setIdtweet(int idtweet) {
		this.idtweet = idtweet;
	}
	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	//  TOSTRING()
	@Override
	public String toString() {
		return this.nombreusuario + " escribió : \r\n" + this.texto;
	}
	
	
	/**
	 * This method is used to get from the user input a text with minimum required requisites. 
	 * Most return a String with at least 1, but not more than 250, characters. 
	 * The text will be inserted on the database in another method.
	 * 
	 * @param br (Buffered Reader object, it will be the same throughout the program)
	 * @return texto (the String type text with correct input)
	 */
	public String scanTexto(BufferedReader br) {
		
		// Declaration and initialization of variables
		String texto = "null";
		boolean success = false;
		
		System.out.println("\r\nEscriba un texto con 250 caracteres máximo : ");
		
		// this loop will stop only if a text with at least 1 character and at most 250 character
		// is typed by user
		while(!success) {
			
			try {
				
				texto = br.readLine();
				
				if(texto.length() > 250) {
					System.out.println("\r\nEl texto ingresado contiene más de 250 caracteres, elimine algunos para continuar.");
				} else if(texto == "") {
					System.out.println("\r\nEl texto ingresado está vacío, por favor ingrese un texto con contenido.");
				} else {
					success = true;
				}
			} catch (IOException e) {
				System.out.println("\r\nHubo un error al ingresar input.");
				//e.printStackTrace();
			}
		}
		return texto;
	}
	
	
	/**
	 * This method adds to database a jtweet and all temas written in it (words starting with "#"). 
	 * The jtweets'table idtweet has an auto-increment attribute, so it's not needed to add it as value. 
	 * It will be needed to get last idtweet inserted to assign it to corresponding temas, tho.
	 * 
	 * @param nombreusuario (a username typed by the user in a previous instance)
	 * @param connection (Connection object, it will be the same throughout the program)
	 * @param br (Buffered Reader object, it will be the same throughout the program)
	 */
	public void addJTweetAndTema(String nombreusuario, Connection connection, BufferedReader br) {
		
		// Declaration and initialization of variables
		int idtweet;
		Jtweet jtweet = new Jtweet();
		String texto = jtweet.scanTexto(br);
		Tema t = new Tema();
		PreparedStatement ps;
		Statement s;
		ResultSet rs;
		
		try {
			
			// jtweets table only needs to receive "texto" and "nombreusuario", 
			// idtweet is a column with auto-increment, so it's not needed to insert the value. 
			// In a real-life application this might not be convenient.
			ps = connection.prepareStatement("INSERT INTO jtweets(nombreusuario, texto) VALUES (?, ?)");
			ps.setString(1, nombreusuario);
			ps.setString(2, texto);
			ps.execute();
			
			try {
				
				// this Statement is used to get the idtweet of the last inserted jtweet
				s = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				rs = s.executeQuery("SELECT idtweet FROM jtweets ORDER BY idtweet DESC LIMIT 1");
				rs.next();
				idtweet = rs.getInt(1);
				t.addTema(connection, texto, idtweet);
				
			} catch(SQLException e2) {
				System.out.println("Hubo un problema al obtener idtweet.");
				//e2.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.println("Hubo un problema al realizar statement.");
			//e1.printStackTrace();
		}
	}
	
}










