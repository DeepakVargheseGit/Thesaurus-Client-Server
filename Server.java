
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JFrame;

/*
 * Author - Deepak Varghese , Student ID - 1001572764, Net ID - dxv2764
 * 
 * The Server class includes the window frame,main method,the server constructor,
 * socket creation and connection,opening and closing file and finding synonyms
 * 
 * References are as follows
 * 1) https://github.com/martiniturbide/os2-cse5306-projects/blob/master/os2-cse5306-projects/lab1/src/cse/uta/edu/os2/server/ServerProg.java
 * 2) https://www.youtube.com/watch?v=fSQOhFYgE3s
 * 3) https://www.youtube.com/watch?v=3RNYUKxAgmw
 * 4) http://javatutorialhq.com/java/util/scanner-class-tutorial/findinline-string-pattern-method-example/ 
 */

public class Server extends JFrame
  implements WindowListener{//, MouseListener, KeyListener{
/*
 * The member variable definition are as follows - 
 * input_area_server        = The area where the server gets the word to be found from client. 
 * synonym_area_server      = The area where the synonyms are displayed when found.
 * Server_In_stream         = Used as the input stream server gets from client.
 * Server_Out_stream        = Used as the output stream server sends to client.
 * server_socket            = Used to create server socket connection.
 * socket                   = Used to accept socket connection.
 * file_scanner             = Used to open the file and scan for the word to be found.
 */
	
	 private static TextArea input_area_server = null;
	 public static TextArea synonym_area_server = null;
	 public static DataInputStream Server_In_stream;
	 public static DataOutputStream Server_Out_stream;
	 public static Scanner file_scanner;
	 static Socket socket;
	 static ServerSocket server_socket;
	 
 /*
  * Creates the GUI for the server		 
  */	 
	 Server(String name)
	 {
		 super(name);
		 this.addWindowListener(this);
		
		 this.setSize(500,500);
		 this.setResizable(true);
		 this.setLayout(new FlowLayout());
		 
		 input_area_server = new TextArea();
		 input_area_server.setEditable(false);
		 this.add(input_area_server, "North");
		 
		 synonym_area_server = new TextArea();
		 synonym_area_server.setEditable(false);
		 this.add(synonym_area_server, "South");		
		 this.setVisible(true);
		 
	 }
	
	 public static void start() //Used to start and establish the server socket connection
	 {
		try { 
		 server_socket = new ServerSocket(2928);
		 String received_from_client = " ";
		 System.out.println("here");
			while(received_from_client != "ënd")
			{
		 socket = server_socket.accept();
		 Server_In_stream  = new DataInputStream (socket.getInputStream() );
		 Server_Out_stream = new DataOutputStream(socket.getOutputStream());
		 System.out.println("socket created");
		 
				received_from_client = Server_In_stream.readUTF();
				System.out.println("Client message: "+received_from_client+"\n");
				input_area_server.append("Request From Client: "+received_from_client+"\n");//Displays the input word received from the client
				String result = openfile(received_from_client);
				Server_Out_stream.writeUTF(result);
				synonym_area_server.append("Response To Client: "+result+"\n"); //Displays the result on the synonym_area_server
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static String openfile(String word) {  //Used to open the file and scan it to check the word
/*
 * 	line_from_file = store the line when read from file 
 *  result_return  = shows the result in the server side synonym area.
 *  	
 */
		String line_from_file = "",flag="",result_return="";
		try {
			file_scanner = new Scanner(new File("Dictionary.txt"));
//			a = x.nextLine();
//			while(!a.contains(word))
//			{
//				a = x.nextLine();
//			}
			while((file_scanner.hasNextLine())) { //Reads until end of file is not reached.
				line_from_file=file_scanner.nextLine();
				StringTokenizer str_token_search= new StringTokenizer(line_from_file, ","); // str_token_search is used to tokenize the words read from the line when word is found.
				while(str_token_search.hasMoreTokens()) {
					if(str_token_search.nextToken().equalsIgnoreCase(word)) { //Checks for multiple tokens and ignores the case of input word
						flag=line_from_file;
					}
				}

			}
			if(flag=="") {
				result_return = "Error.. No synonym found";
			}
			else {
				StringTokenizer str_token_synonym= new StringTokenizer(flag, ","); // str_token_synonym is used to to show the synonyms found for the word
				while(str_token_synonym.hasMoreTokens()) {
					String temp=str_token_synonym.nextToken();
					if((temp.equalsIgnoreCase(word))==false) { // Used to display synonyms other than the input word
						result_return+=temp+",";
					}
				}
			}
			
		}
		catch (Exception e) {
		  System.out.println("Could not find file"+e.getMessage()); // Shows error if file not found.
		  e.printStackTrace();
		}
		return result_return;
	}
	

	
	public static void closefile() { //Used to close the file 
		file_scanner.close();
	}
	public static void main (String[] args) { //Used to invoke the server class and  set the socket connection
		Server server = new Server("Server ");
		Server.start();

	}

	
	public void windowActivated(WindowEvent e) {
		
		
	}

	
	public void windowClosed(WindowEvent e) //Used to close the socket
	{		
		try 
		{
			socket.close();
			server_socket.close();
		} catch (IOException e1) 
		{
			//e1.printStackTrace();
		}		
	}

	
	public void windowClosing(WindowEvent e)
	{		
		
	}

	
	public void windowDeactivated(WindowEvent e) 
	{		
		
	}

	
	public void windowDeiconified(WindowEvent e)
	{		
		
	}

	
	public void windowIconified(WindowEvent e)
	{		
		
	}

	
	public void windowOpened(WindowEvent e) 
	{			
		
	}
}
