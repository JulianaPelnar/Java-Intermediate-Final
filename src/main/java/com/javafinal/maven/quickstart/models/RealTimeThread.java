package com.javafinal.maven.quickstart.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**RealTimeThread extends Thread <br>
 * This class provides a thread to search for new jtweets periodically. 
 * The new jtweets will be printed and then added to the list of already founded jtweets.
 * 
 * @author Joule
 *
 */
public class RealTimeThread extends Thread{
	
	// Class variables
	ArrayList<Jtweet> jtweetSearch;
	Connection connection;
	public boolean activo = true;
	
	// Constructor
	public RealTimeThread(Connection connection) {
		
		this.jtweetSearch = new ArrayList<Jtweet>();
		searchCurrentJtweets(connection);
		this.connection = connection;
		
	}
	
	/** Run method <br>
	 * It will get new jtweets by comparing the class list of jtweets with database jtweets. 
	 * Once a new jtweet is found, it will be printed and added to the class list of jtweets.
	 */
	@Override
	public void run() {
		while(activo) {
			
			try {
				
				Statement stm = connection.createStatement();
				ResultSet rs = stm.executeQuery("SELECT * FROM jtweets;");
				boolean exist = false;
				
				while(rs.next()) {
					
					Jtweet j1 = new Jtweet();
					j1.setIdtweet(rs.getInt("idtweet"));
					j1.setNombreusuario(rs.getString("nombreusuario"));
					j1.setTexto(rs.getString("texto"));
					exist = false;
					
					for(Jtweet j2 : this.jtweetSearch) {
						if(j1.getIdtweet() == j2.getIdtweet()) {
							exist = true;
							break;
						}
					}
					
					if(!exist) {
						
						System.out.println(j1.toString());
						jtweetSearch.add(j1);
						
					}
				}
			Thread.sleep(10000);
			} catch(SQLException e) {
				System.out.println("Hubo un problema al ejecutar query.");
				e.printStackTrace();
			} catch(InterruptedException e) {
				System.out.println("Hubo un problema con thread.");
				e.printStackTrace();
			}
			
		}
	}
	
	/**searchCurrentJtweets <br>
	 * This method will search jtweets in the database and add them to the jtweets array of the thread when thread is initialized. 
	 * That way, every new jtweet will be found by comparation of ids. 
	 * 
	 * @param connection
	 */
	void searchCurrentJtweets(Connection connection) {
		
		try {
			
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM jtweets;");
			
			while(rs.next()) {
				
				Jtweet j = new Jtweet();
				j.setIdtweet(rs.getInt("idtweet"));
				j.setNombreusuario(rs.getString("nombreusuario"));
				j.setTexto(rs.getString("texto"));
				this.jtweetSearch.add(j);
				
			}
			
		} catch(SQLException e) {
			System.out.println("Hubo un problema al conectar con la base de datos.");
		}
	}
	
}
