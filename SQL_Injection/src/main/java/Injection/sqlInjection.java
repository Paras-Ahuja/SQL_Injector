package Injection;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
public class sqlInjection extends JFrame {
	public HttpResponse<String> req(String url) throws IOException, InterruptedException
	{
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url)).build();
		HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
//		System.out.println(response.statusCode());
		return response;
	}
	 private JTextField usernameField;
	    private JTextField passwordField;
	    private JTextField urlField;
	    private JTextArea resultArea;
	    private JButton loginButton;
	    private Connection connection;
	    public sqlInjection() {
	    	super("SQL Injection App");

	        // Create GUI components
	        JLabel usernameLabel = new JLabel("Username:");
	        JLabel passwordLabel = new JLabel("Password:");
	        JLabel URL = new JLabel("URL:");
	        usernameField = new JTextField(20);
	        passwordField = new JTextField(20);
	        urlField = new JTextField(20);
	        resultArea = new JTextArea(10, 20);
	        loginButton = new JButton("Login");
	        
	        // Set layout
	        setLayout(new BorderLayout());
	        JPanel formPanel = new JPanel(new GridLayout(3, 2));
	        formPanel.add(usernameLabel);
	        formPanel.add(usernameField);
	        formPanel.add(passwordLabel);
	        formPanel.add(passwordField);
	        formPanel.add(URL);
	        formPanel.add(urlField);
	        add(formPanel, BorderLayout.NORTH);
	        add(new JScrollPane(resultArea), BorderLayout.CENTER);
	        add(loginButton, BorderLayout.SOUTH);

	        // Register login button action listener
	        loginButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                String username = usernameField.getText();
	                String password = passwordField.getText();
	                String url = urlField.getText();
	                try {
	                    // Establish database connection (assuming MySQL)
	                	HttpResponse<String> res;
	                	
	                	
	                String[] payload = username.split(",");
	                int check=0;
	                  String[] para = url.split("=");
	                  if(para.length>0) {
	                	  for(int i=0;i<payload.length;i++)
	                	  {
	                		  String tempUrl = para[0]+"="+payload[i];
//	                		  if(para.length>1) {
//	                			  tempUrl=tempUrl+"="+para[1];
//	                		  }
	                		  if(para.length>2) {
	                			  for(int j=1;j<para.length;j=j+2)
		                		  {
		                			  tempUrl=tempUrl+"&"+para[j].split("&")[1]+"="+para[j+1];
		                		  }  
	                		  }
	                		  
	                		  System.out.println(tempUrl);
	                		  HttpResponse<String> code = req(tempUrl);
	                		  if(code.statusCode()==200) {
	                			  resultArea.setText("hacked");
	                			  check=1;
	                		  }
	                		  
	                	  }
	                	  if(check==0)
	                	  {
	                		  resultArea.setText("Safe Buddy");
	                	  }
	                  }else {
	                	  resultArea.setText("No go Amigo!!");
	                  }
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                    resultArea.setText("An error occurred: " + ex.getMessage());
	                }
	            }
	        });

	        // Set window properties
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        pack();
	        setLocationRelativeTo(null);
	        setVisible(true);
	    }
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                new sqlInjection
	                ();
	            }
	        });
	    }
}
