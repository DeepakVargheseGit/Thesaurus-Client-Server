import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.TextArea;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JFrame;

/*
 * Author - Deepak Varghese , Student ID - 1001572764, Net ID - dxv2764
 * 
 * The Client class includes the window frame,main method,the client constructor,
 * socket creation and connection. 
 * 
 * References are as follows
 * 1) https://github.com/martiniturbide/os2-cse5306-projects/blob/master/os2-cse5306-projects/lab1/src/cse/uta/edu/os2/server/ServerProg.java
 * 2) https://www.youtube.com/watch?v=fSQOhFYgE3s
 * 3) https://www.youtube.com/watch?v=3RNYUKxAgmw
 * 4) http://javatutorialhq.com/java/util/scanner-class-tutorial/findinline-string-pattern-method-example/ 
 */

public class Client extends JFrame
 implements WindowListener{//, MouseListener, KeyListener{
/*
 * The member variable definition are as follows - 
 * word_area_client         = The area where the client inputs the word to be found. 
 * synonym_area_client      = The area where the synonyms are displayed when found.
 * search                   = The button used to input the word.
 * Client_In_stream         = Used as the input stream client gets from server.
 * Client_Out_stream        = Used as the output stream client sends to .
 * socket                   = Used to create socket connection.
 */
	 private TextArea word_area_client = null;
	 private static TextArea synonym_area_client = null;
	 private Button search ;
	 public static DataInputStream Client_In_stream;
	 public static DataOutputStream Client_Out_stream;
	 static Socket socket;
	 
	 
	 Client(String name){
		 
 /*
  * Creates the GUI for the client		 
  */
		 super(name);
		 this.addWindowListener(this);
		 this.setSize(500,500);
		 this.setResizable(true);
		 this.setLayout(new BorderLayout());
		 
		 word_area_client = new TextArea();
		 word_area_client.setEditable(true);
		 this.add(word_area_client, "North");
		 
		 synonym_area_client = new TextArea();
		 synonym_area_client.setEditable(false);
		 this.add(synonym_area_client, "South");
		 
		 
		 
		 search = new Button("Search");
 /*
  * addActionListener is used to perform actions when search is clicked
  */
		 search.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String input =	word_area_client.getText(); // input stores the data written in word_area_client
					System.out.println("ïnput from text area : "+input);
					try {
						if(input.equals("")) { // if client enters no data then the message is shown in synonym_area_client
							synonym_area_client.setText("Enter word before searching !!");
							
						}
						else {
							startSending(input); //else it goes to the user defined method 	startSending to send the data to server				
							}
						//Out_stream.writeUTF(input);
					} catch (Exception e) {
						e.printStackTrace();
					}
					}
				});
		 this.add(search);
		 this.setVisible(true);
		 
	 }
	 
	 public static void startSending(String inputWord)
	 {
		try { 
			
		 socket = new Socket("127.0.0.1", 2928);
		 Client_In_stream = new DataInputStream(socket.getInputStream());
		 Client_Out_stream = new DataOutputStream(socket.getOutputStream());
		 
		 String received_from_server = "";
//		 while(received != "ënd")
//			{
			 Client_Out_stream.writeUTF(inputWord); 
			 //System.out.println("message send from client");
			 received_from_server = Client_In_stream.readUTF();
				System.out.println("Received from server : "+received_from_server);
				synonym_area_client.setText(received_from_server); //If synonyms are found it is shown in the synonym_area_client
			//}
		}
		catch(Exception e) {
			synonym_area_client.setText("No server connection is open."); //Shows error if server connection is not open
		}
		}
	public static void main (String[] args) {
		
		Client client = new Client("Client "); //Invokes the Client class 
		//Client.startSending();
	}
	

	
	public void windowActivated(WindowEvent e) {

		
	}

	
	public void windowClosed(WindowEvent e) { //Used to close the socket
		 try {
			socket.close();
		} catch (IOException e1) {
			//e1.printStackTrace();
		}
		
	}

	
	public void windowClosing(WindowEvent e) {

		
	}

	
	public void windowDeactivated(WindowEvent e) {

		
	}

	
	public void windowDeiconified(WindowEvent e) {

		
	}

	
	public void windowIconified(WindowEvent e) {

		
	}

	
	public void windowOpened(WindowEvent e) {

		
	}

}
