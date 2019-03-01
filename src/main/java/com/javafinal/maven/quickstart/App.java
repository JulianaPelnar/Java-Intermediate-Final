package com.javafinal.maven.quickstart;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.javafinal.maven.quickstart.models.Menu;

public class App 
{
    public static void main( String[] args )
    {
        
    	InputStreamReader inp = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(inp);
		
		try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e) {
        	System.out.println( "MySQL JDBC Driver no encontrado." );
        	e.printStackTrace();
        	return;
        }
        
        Connection connection = null;
        
        try {
        	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jtweet?serverTimezone=UTC", "root", "1234");
        } catch(SQLException e) {
        	System.out.println( "La conexión fallo, observe el mensaje en la consola." );
        	e.printStackTrace();
        	return;
        }
        
        if(connection != null) {
        	
        	Menu menu = new Menu();
        	menu.CompleteMenu(connection, br);
        	
        } else {
        	System.out.println("Falló la conexión con la base de datos.");
        }
        
        try {
        	connection.close();
        } catch(SQLException e) {
        	System.out.println("No se pudo cerrar la conexión con la base de datos.");
        	e.printStackTrace();
        }
        
        try {
			inp.close();
		} catch (IOException e) {
			System.out.println("No se pudo cerrar InputStreamReader.");
		}
        try {
			br.close();
		} catch (IOException e) {
			System.out.println("No se pudo cerrar BufferedReader.");
		}
        System.out.println("Ha salido exitosamente de la aplicación.");
        System.exit(0);
    
    }
}












