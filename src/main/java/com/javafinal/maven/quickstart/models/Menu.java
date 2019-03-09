package com.javafinal.maven.quickstart.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Menu{
	
	/**
	 * Teticas
	 *
	 * Each time a "yes/no" decision is made, this method
	 * will return true (yes) or false (no)
	 * 
	 * it is needed to assign this method to a boolean, eg. 
	 * boolean x = BinaryDecision(br);
	 * 
	 * @param br (Buffered Reader object, it will be the same throughout the program)
	 * @return x (a boolean)
	 */
	public boolean BinaryDecision(BufferedReader br) {
		// The menu
		System.out.println("\r\nIngrese el número indicado en la opción : ");
		System.out.println("1 ) Sí.");
		System.out.println("2 ) No.");
		
		// Declaration and initialization of variables to be used
		
		String input = "null";
		int option = 0;
		boolean x = false;
		
		// this loop will not finish until the required options are typed
		while(option != 1 && option != 2) {
			
			try {
				input = br.readLine();
				
				// The String is supposed to become an int, that's why regex is used
				if(input.matches("[0-9]*")) {
					option = Integer.parseInt(input);
					
					switch(option) {
					case 1:
						x = true;
						break;
					case 2:
						x = false;
						break;
					default:
						// In case user types wrong number
						System.out.println("\r\nIngrese un número válido.");
					}
					
				} else {
					// In case user types wrong character
					System.out.println("\r\nIngrese un número válido, no letras.");
				}
				
			} catch(IOException e) {
				System.out.println("\r\nHubo un problema al procesar input, intente nuevamente.");
				//e.printStackTrace();
			}
		}
		return x;
	}

	
	/**
	 * Method to search username through database. 
	 * 
	 * It will return an Usuario object if found, and null if not.
	 * If found, should ask for password until input is correct (return Usuario) or user give up (return null).
	 * 
	 * @param nombreusuario (a username typed by the user in a previous instance)
	 * @param connection (Connection object, it will be the same throughout the program)
	 * @param br (BufferedReader object, it will be the same throughout the program)
	 * @return Usuario object if found, null if not.
	 */
	public Usuario SearchUsers(String nombreusuario, Connection connection, BufferedReader br) {
		
		// Declaration and initialization of variables to be used
		boolean option = true;
		String contraseña = "null";
		Usuario usuario; // It will be initialized with a parameterized constructor
		Statement st; // Requires try-catch
		ResultSet rs; // Requires try-catch
		
		try {
			
			// Used to get all users data from the database
			st = connection.createStatement();
			rs = st.executeQuery("SELECT * FROM usuarios");
			
			while(rs.next()) {
				
				rs.getString(1);
				
				if(rs.getString(1).equals(nombreusuario)) {
					
					// if username is found in database, data will be assigned to a new Usuario object
					usuario = new Usuario(nombreusuario, rs.getString("nombre"), rs.getString("apellido"), 
							rs.getString("fecha"), rs.getString("email"), rs.getString("contraseña"));
					
					try {
						
						System.out.println("\r\nIngrese su contraseña : ");
						contraseña = br.readLine();
						
						// Checks password
						if(usuario.getContraseña().equals(contraseña)) {
							
							return usuario;
							
						} else {
							
							System.out.println("\r\nLa contraseña ingresada no es correcta. Desea volver a intentar?");
							
							// This loop will iterate until the user types the right password or decides to finish the program instead
							while(option) {
								
								option = BinaryDecision(br);
								
								try {
									
									contraseña = br.readLine();
									
									if(usuario.getContraseña().equals(contraseña)) {
										
										return usuario;
										
									} else {
										
										System.out.println("\r\nLa contraseña ingresada no es correcta. Desea volver a intentar?");
									}
								
								} catch(IOException e1) {
									System.out.println("\r\nHubo un problema al procesar la contraseña ingresada, intente nuevamente.");
									//e1.printStackTrace();
								}
							}
						}
					} catch(IOException e) {
						System.out.println("\r\nHubo un problema al procesar la contraseña ingresada, intente nuevamente.");
						//e.printStackTrace();
					} 
				}
			}
		} catch(SQLException e) {
			System.out.println("\r\nHubo un problema al conectar con la base de datos.");
			//e.printStackTrace();
		}
		return null;		
	}
	
	
	/**
	 * Method to find or search user. 
	 * 
	 * If username is not found in database, a new user can be created.
	 * If user does not choose to sign in or create a user, the program will finish.
	 * 
	 * @param br (BufferedReader object, it will be the same throughout the program)
	 * @param connection (Connection object, it will be the same throughout the program)
	 * @return Usuario object (from database or created) or null (it will finish program)
	 */
	public Usuario FindOrSetUser(BufferedReader br, Connection connection) {
		
		System.out.println("---------- JAVA INTERMEDIATE FINAL ----------\r\n");
		System.out.println("Ingrese su nombre de usuario : ");
		
		// Declaration and initialization of variables
		boolean success = false;
		boolean option = false;
		String nombreusuario = "null";
		Usuario usu = new Usuario();
		
		while(!success) {
			
			try {
				
				nombreusuario = br.readLine();
				usu = SearchUsers(nombreusuario, connection, br);
				
				if(usu == null) {
					System.out.println("\r\nEl nombre de usuario no existe. Desea registrarse?");
					option = BinaryDecision(br);
					
					if(option) {
						
						// to avoid nullpointerexception
						usu = new Usuario();
						usu = usu.addUsuario(nombreusuario, connection);
						return usu;
						
					} else {
						
						// If user has an account but typed wrong
						System.out.println("\r\nDesea volver a ingresar el nombre de usuario?");
						option = BinaryDecision(br);
						if(!option) {
							return null;
						}
					}
				} else {
					return usu;
				}
			} catch (IOException e) {
				System.out.println("\r\nHubo un problema al procesar el nombre ingresado, intente nuevamente.");
				//e.printStackTrace();
			}
		}
		return null;
	}
	
	
	/**
	 * This method simply prints the menu of user's options. 
	 * It's used to reduce amount of code in the main menu.
	 */
	public void PrintMenu() {
		System.out.println("---------- MENÚ ----------\r\n");
		System.out.println(" 1 ) Escribir nuevo JTweet.");
		System.out.println(" 2 ) Buscar JTweets por usuario.");
		System.out.println(" 3 ) Buscar JTweets por texto.");
		System.out.println(" 4 ) Buscar JTweets por tema.");
		System.out.println(" 5 ) Ver JTweets en tiempo real.");
		System.out.println(" 6 ) Salir de la aplicación.\r\n");
	}
	
	
	/**
	 * This method comprehends the totality of code not related to connection of database. 
	 * It uses the already made connection and buffered reader instance, and organize 
	 * everything the user can do with the application: create or use an account, 
	 * search through tweets using different criteria and write new tweet.
	 * 
	 * @param connection (Connection object, it will be the same throughout the program)
	 * @param br (BufferedReader object, it will be the same throughout the program)
	 */
	public void CompleteMenu(Connection connection, BufferedReader br) {
		
		// Declaration and initialization of Usuario object to log in or to create new account
		Usuario usuario = new Usuario();
		usuario = FindOrSetUser(br, connection);
		
		if(usuario != null) {
			
			// Declaration and initialization of needed variables and objects
			// that will be needed for the application to work
			String aux = "null";
			String nombreusuario = "null";
			int option = 0;
			Jtweet jtweet = new Jtweet();
			JtweetArray jtweets = new JtweetArray(connection);
			TemaArray temas = new TemaArray(connection);
			
			// Loop to choose options as long as user wants
			// It will stop at input 6, which is the option to exit application
			while(option != 6) {
				
				PrintMenu();
				
				try {
					
					aux = br.readLine();
					
					// The String is supposed to become an int, that's why regex is used
					if(aux.matches("[0-9]*")) {
						
						option = Integer.parseInt(aux);
						
						switch(option) {
						
						case 1 :
							nombreusuario = usuario.getNombreusuario();
							jtweet.addJTweetAndTema(nombreusuario, connection, br);
							jtweets = new JtweetArray(connection);
							break;
						case 2 :
							jtweets.sortByUserName(br);
							break;
						case 3 :
							jtweets.sortByText(br);
							break;
						case 4 :
							jtweets.sortByTema(br, temas);
							break;
						case 5 :
							//System.out.println("En breve.");
							RealTimeThread thread = new RealTimeThread(connection);
							thread.start();
							break;
						case 6 :
							continue;
						default :
							System.out.println("Por favor, ingrese un número válido.");
							
						}
					} else {
						System.out.println("Por favor, ingrese un número del 1 al 6.");
					}
					
				} catch(IOException e) {
					System.out.println("Hubo un problema al procesar la información ingresada, intente nuevamente.");
					//e.printStackTrace();
				}
			}
		} else {
			System.out.println("Usted eligió no ingresar como usuario registrado o registrarse.");
			System.out.println("La aplicación se cerrará.");
		}
	}
	
	
}











