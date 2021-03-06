import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.text.*;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class find_pharmacy extends JFrame {
	
	
	JScrollPane scrollPane;
	ImageIcon icon;
	
	
	public find_pharmacy() {
		
		setTitle("Finding Pharmacy Service");
		
		icon = new ImageIcon("pharmacy_icon3.png");
		JButton button = new JButton("search");
		JTextField search1 = new JTextField(10);
		JTextField search2 = new JTextField(10);
		JLabel text = new JLabel("Search Pharmacy");
		
		text.setFont(new Font("배달의민족 도현", Font.BOLD, 35));
		
		JPanel background = new JPanel() {
			
			public void paintComponent(Graphics g) {
				
				g.drawImage(icon.getImage(), 0, 0, null);
				text.setBounds(110, 100, 400, 80);
				search1.setBounds(110, 200, 180, 30);
				button.setBounds(320, 200, 80, 30);
				add(text);
				add(search1);
				
				add(button);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
	
		
		scrollPane = new JScrollPane(background);
		setContentPane(scrollPane);
	
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = search1.getText();
				System.out.println(str);
				try {
					new pharmacy_list(str);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	

			}
		});
	}
	
	
	
	public static void main(String[] args) throws IOException, ParseException {
		
		
		find_pharmacy client = new find_pharmacy();
		//client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.setSize(525,420);
		client.setResizable(false);
		client.setVisible(true);

		
	}
	
}