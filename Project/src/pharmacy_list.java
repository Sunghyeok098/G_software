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


public class pharmacy_list extends JFrame {

	JScrollPane scrollPane;
	JLabel label;
	String list = "<html>";
	
	public pharmacy_list(String str) throws IOException, ParseException {

		setTitle("List of pharmacy");
		
		//NewWindowContainer = new JPanel();
		parsing(str);
		
		JLabel label = new JLabel(list);
		add(label);
		//NewWindowContainer.add(label);
		
		scrollPane = new JScrollPane(label);
		setContentPane(scrollPane);
		setSize(530,425);
		setResizable(false);
		setVisible(true);

	}
	
	public void parsing(String str) throws IOException, ParseException {
		

		String apiUrl = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyListInfoInqire";
		String serviceKey = "wFWF%2F8cgnoQxyDRtiGulEucscAnKsSEh1GNaoyg3l5X01b252HiFZhvQRP8XVwbzQnvthshquiyKJXZWmqbo3g%3D%3D";
		
		String sido = str.split(" ")[0]; 
		String city = str.split(" ")[1]; 
		
		String date = "";
		System.out.println(sido + " " +  city);
		Calendar calendar = Calendar.getInstance(); 
		String []days = {"월","화","수","목","금","토","일"}; //

		switch(days[calendar.get(Calendar.DAY_OF_WEEK)-2]) {

		case "월":
			date = "1";
			break;
		case "화":
			date = "2";
			break;
		case "수":
			date = "3";
			break;
		case "목":
			date = "4";
			break;
		case "금":
			date = "5";
			break;
		case "토":
			date = "6";
			break;
		case "일":
			date = "7";
			break;
		}

		String type = "json"; 

		StringBuilder urlBuilder = new StringBuilder(apiUrl);
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + serviceKey);
		urlBuilder.append("&" + URLEncoder.encode("Q0","UTF-8") + "=" + URLEncoder.encode(sido, "UTF-8")); 
		urlBuilder.append("&" + URLEncoder.encode("Q1","UTF-8") + "=" + URLEncoder.encode(city, "UTF-8")); 
		urlBuilder.append("&" + URLEncoder.encode("QT","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")); 
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("30", "UTF-8")); 
		urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); 

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
		}
		StringBuilder sb = new StringBuilder();
		String line;


		while ((line = rd.readLine()) != null) {
			sb.append(line);
		} 

		rd.close();
		conn.disconnect();
		String result = sb.toString();
		System.out.println(result);

		JSONParser parser = new JSONParser(); 
		JSONObject obj = (JSONObject) parser.parse(result); 
		
		JSONObject parse_response = (JSONObject) obj.get("response"); 
		
		JSONObject parse_body = (JSONObject) parse_response.get("body"); 
		
		JSONObject parse_items = (JSONObject) parse_body.get("items");

		
		JSONArray parse_item = (JSONArray) parse_items.get("item");
		JSONObject pharmarcy; 
		

		String start = "dutyTime";
		start = start + date + "s";

		String end = "dutyTime";
		end = end + date + "c";

	
		for(int i = 0 ; i < parse_item.size(); i++) {
			
			
			pharmarcy = (JSONObject) parse_item.get(i);
			Object dutyAddr = pharmarcy.get("dutyAddr"); //약국 주소를 가져온다.
			Object dutyName = pharmarcy.get("dutyName"); //약국 이름을 가져온다.
			Object dutyTel1 = pharmarcy.get("dutyTel1"); //약국 전화번호를 가져온다.
			Object startTime = pharmarcy.get(start); //약국 오픈시간을 가져온다.
			Object endTime = pharmarcy.get(end); //약국 닫는시간을 가져온다

			
			list = list + "Pharmarcy Name : " + dutyName + "<br>" 
					+ "Pharmarcy Address : " + dutyAddr + "<br>"
					+ "Pharmarcy Tell Number : " + dutyTel1 + "<br>"
					+ "Open : " + startTime +" ~ " + endTime
					+ "<br>=================================================================<br>";
			
		}
		
		list = list + "</html";
		
		
	}

	

}
