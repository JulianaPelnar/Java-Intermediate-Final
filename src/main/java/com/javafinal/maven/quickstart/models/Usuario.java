package com.javafinal.maven.quickstart.models;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Usuario {
	
	private String nombreusuario;
	private String nombre;
	private String apellido;
	private String fecha;
	private String mail;
	private String contraseña;
	
	public Usuario() {
	}
	
	/**
	 * Constructor overloading, used to verify input
	 * 
	 * @param nombreusuario
	 * @param nombre
	 * @param apellido
	 * @param fecha
	 * @param mail
	 * @param contraseña
	 */
	public Usuario(String nombreusuario, String nombre, String apellido, String fecha, String mail, String contraseña) {
		this.nombreusuario = nombreusuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fecha = fecha;
		this.mail = mail;
		this.contraseña = contraseña;
	}
	
	//  GETTERS
	public String getNombreusuario() {
		return this.nombreusuario;
	}
	public String getNombre() {
		return this.nombre;
	}
	public String getApellido() {
		return this.apellido;
	}
	public String getFecha() {
		return this.fecha;
	}
	public String getMail() {
		return this.mail;
	}
	public String getContraseña() {
		return this.contraseña;
	}
	
	//  SETTERS
	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	
	
	// Add methods
	/**
	 * This method verifies and return the name of the user. Does not have connection to database 
	 * because all user data will be inserted together on another method.
	 * 
	 * @param br (BufferedReader object, it will be the same throughout the program)
	 * @param p (Pattern object, it is created in another method to not create a new one over and over)
	 * @param m (Matcher object, it is created in another method to not create a new one over and over)
	 * @return nombre (String with user'name)
	 */
	public String addNombre(BufferedReader br, Pattern p, Matcher m) {
		
		// Declaration and/or initialization of variables
		boolean success = false;
		String nombre = "null";
		p = Pattern.compile("[a-zA-ZÀ-ÿ]*");
		
		// this loop iterates until valid input
		while(!success) {
			
			try {
			
				nombre = br.readLine();
				
				try {
				
					m = p.matcher(nombre);
					
					if(m.matches()) {
					
						success = true;
						this.setNombre(nombre);
						return nombre;
						
					} else {
						System.out.println("El nombre ingresado es inválido, intente nuevamente : ");
					}
				} catch(Exception e) {
					System.out.println("Hubo un problema al procesar input, intente nuevamente : ");
					//e.printStackTrace();
				}
			} catch(IOException e) {
				System.out.println("Hubo un problema al leer input, intente nuevamente : ");
				//e.printStackTrace();
			}
		}
		return nombre;
	}
	
	/**
	 * This method verifies and return the surname of the user. Does not have connection to database 
	 * because all user data will be inserted together on another method.
	 * 
	 * @param br (BufferedReader object, it will be the same throughout the program)
	 * @param p (Pattern object, it is created in another method to not create a new one over and over)
	 * @param m (Matcher object, it is created in another method to not create a new one over and over)
	 * @return apellido (String with user'surname)
	 */
	public String addApellido(BufferedReader br, Pattern p, Matcher m) {
		
		// Declaration and/or initialization of variables
		boolean success = false;
		String apellido = "null";
		p = Pattern.compile("[a-zA-ZÀ-ÿ]*");
		
		// this loop iterates until valid input
		while(!success) {
			
			try {
			
				apellido = br.readLine();
				
				try {
					
					m = p.matcher(apellido);
					
					if(m.matches()) {
					
						success = true;
						this.setApellido(apellido);
						return apellido;
						
					} else {
						System.out.println("El apellido ingresado es inválido, intente nuevamente : ");
					}
					
				} catch(Exception e) {
					System.out.println("Hubo un problema al procesar input, intente nuevamente : ");
					//e.printStackTrace();
				}
			} catch(IOException e) {
				System.out.println("Hubo un problema al leer input, intente nuevamente : ");
				//e.printStackTrace();
			}
		}
		return apellido;
	}

	/**
	 * This method verifies and return the date of birth of the user. Does not have connection to database 
	 * because all user data will be inserted together on another method.
	 * 
	 * @param br (BufferedReader object, it will be the same throughout the program)
	 * @param p (Pattern object, it is created in another method to not create a new one over and over)
	 * @param m (Matcher object, it is created in another method to not create a new one over and over)
	 * @return fecha (String with user'date of birth)
	 */
	public String addFecha(BufferedReader br, Pattern p, Matcher m) {
		
		// Declaration and/or initialization of variables
		boolean success = false;
		String fecha = "null";
		p = Pattern.compile("^(?:(?:(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec))(\\/|-|\\.)31)\\1|(?:(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))(\\/|-|\\.)(?:29|30)\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:(?:0?2|(?:Feb))(\\/|-|\\.)(?:29)\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))(\\/|-|\\.)(?:0?[1-9]|1\\d|2[0-8])\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");
		
		// this loop iterates until valid input
		while(!success) {
			try {
				fecha = br.readLine();
				try {
					m = p.matcher(fecha);
					if(m.matches()) {
						success = true;
						this.setFecha(fecha);
						return fecha;
					} else {
						System.out.println("La fecha de nacimiento ingresada es inválida. Intente de nuevo.");
					}
				} catch(Exception e) {
					System.out.println("Hubo un problema al procesar input, intente nuevamente : ");
					//e.printStackTrace();
				}
			} catch(IOException e) {
				System.out.println("Hubo un problema al leer input, intente nuevamente : ");
				//e.printStackTrace();
			}
		}
		return fecha;
	}
	
	/**
	 * This method verifies and return the email of the user. Does not have connection to database 
	 * because all user data will be inserted together on another method.
	 * 
	 * @param br (BufferedReader object, it will be the same throughout the program)
	 * @param p (Pattern object, it is created in another method to not create a new one over and over)
	 * @param m (Matcher object, it is created in another method to not create a new one over and over)
	 * @return mail (String with user'email)
	 */
	public String addMail(BufferedReader br, Pattern p, Matcher m) {
		
		// Declaration and/or initialization of variables
		boolean success = false;
		String mail = "null";
		p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		
		// this loop iterates until valid input
		while(!success) {
			try {
				mail = br.readLine();
				try {
					m = p.matcher(mail);
					if(m.matches()) {
						success = true;
						this.setMail(mail);
						return mail;
					} else {
						System.out.println("El mail ingresado es inválido. Intente de nuevo.");
					}
				} catch(Exception e) {
					System.out.println("Hubo un problema al procesar input, intente nuevamente : ");
					//e.printStackTrace();
				}
			} catch(IOException e) {
				System.out.println("Hubo un problema al leer input, intente nuevamente : ");
				//e.printStackTrace();
			}
		}
		return mail;
	}
	
	/**
	 * This method verifies and return the password of the user. Does not have connection to database 
	 * because all user data will be inserted together on another method.
	 * 
	 * @param br (BufferedReader object, it will be the same throughout the program)
	 * @param p (Pattern object, it is created in another method to not create a new one over and over)
	 * @param m (Matcher object, it is created in another method to not create a new one over and over)
	 * @return contraseña (String with user'password)
	 */
	public String addContraseña(BufferedReader br, Pattern p, Matcher m) {
		
		// Declaration and/or initialization of variables
		boolean success = false;
		String contraseña = "null";
		p = Pattern.compile("\\S+");
		
		// this loop iterates until valid input
		while(!success) {
			
			try {
				
				contraseña = br.readLine();
				
				try {
					
					m = p.matcher(contraseña);
					
					if(m.matches()) {
						success = true;
						this.setContraseña(contraseña);
						return contraseña;
						
					} else {
						System.out.println("La contraseña ingresada es inválida. Intente de nuevo.");
					}
				} catch(Exception e) {
					System.out.println("Hubo un problema al procesar input, intente nuevamente : ");
					//e.printStackTrace();
				}
			} catch(IOException e) {
				System.out.println("Hubo un problema al leer input, intente nuevamente : ");
				//e.printStackTrace();
			}
		}
		return contraseña;
	}
	
	/**
	 * This method is used to create a new Usuario object and, at the same time, 
	 * insert object's data into the database. It is needed a return value with the new user 
	 * to be able to run the rest of the code.
	 * 
	 * @param nombreusuario (a username typed by the user in a previous instance)
	 * @param connection (Connection object, it will be the same throughout the program)
	 * @return usuario (Usuario object created with user input)
	 */
	public Usuario addUsuario(String nombreusuario, Connection connection) {
		
		// Declaration/initialization of variables
		String nombre, apellido, fecha, mail, contraseña;
		Usuario usuario = new Usuario();
		InputStreamReader inp = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(inp);
		Pattern p = null;
		Matcher m = null;
		PreparedStatement ps;
		
		// Asks for user input
		System.out.println("\r\nIngrese su nombre : ");
		nombre = usuario.addNombre(br, p, m);
		System.out.println("\r\nIngrese su apellido : ");
		apellido = usuario.addApellido(br, p, m);
		System.out.println("\r\nIngrese su fecha de nacimiento en formato mm/dd/aaaa : ");
		fecha = usuario.addFecha(br, p, m);
		System.out.println("\r\nIngrese su mail : ");
		mail = usuario.addMail(br, p, m);
		System.out.println("\r\nIngrese su contraseña : ");
		contraseña = usuario.addContraseña(br, p, m);
		String[] input = {nombre, apellido, fecha, mail, contraseña};
		
		try {
			
			// inserts input in database
			ps = connection.prepareStatement("INSERT INTO usuarios(nombreusuario, nombre, apellido, "
					+ "fecha, email, contraseña) VALUES (?, ?, ?, ?, ?, ?)");
			for(int i = 1; i <= 6; i++) {
				ps.setString(i, input[i - 1]);
			}
			ps.execute();
			
		} catch (SQLException e) {
			System.out.println("Hubo un problema con la conexión a la base de datos.");
			//e.printStackTrace();
		}
		return usuario;
	}
}
