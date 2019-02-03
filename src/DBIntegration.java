import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

public class DBIntegration {
	
	private static HashSet<String> entries = new HashSet<String>();
	//private static HashSet<String> newEntries = new HashSet<String>();
	
	public static ArrayList<ArrayList<String>> getData() throws IOException, JSONException {
		
		ArrayList<ArrayList<String>> requests = new ArrayList<ArrayList<String>>();
		
		String jsonTxt = "";
		String urlStr = "https://savingpro-6e70c.firebaseio.com/data.json";
		
		URL url = new URL(urlStr);
        URLConnection urlConnection = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String inputLine = in.readLine();
        while (inputLine != null) {
        	jsonTxt += inputLine;
        	inputLine = in.readLine();
        }
        in.close();
        
        //System.out.println(jsonTxt);
        
        JSONObject jsonObj = new JSONObject(jsonTxt);
        Iterator<String> keys = jsonObj.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            //if (!entries.contains(key)) {
            	entries.add(key);
            //}
        }
        //System.out.println(entries);
        //System.out.println(newEntries);
        
        for (String s : entries) {
        	//System.out.println(s);
        	//System.out.println("triggered");
        	JSONObject obj = jsonObj.getJSONObject(s);
        	//System.out.println(obj);
        	String addr = obj.getString("address");
        	String dish = obj.getString("dish");
        	String price = obj.getString("price");
        	//System.out.println("Address: " + addr);
        	//System.out.println("Cuisine: " + dish);
        	//System.out.println("Price level: " + price);
        	
        	ArrayList<String> request = new ArrayList<String>();
        	//System.out.println(request);
        	//request.add(GoogleMaps.convertAddress(addr));
        	request.add(addr);
        	request.add(dish);
        	request.add(price);
        	requests.add(request);
        	//entries.add(s);
        }
        //newEntries.clear();
        //System.out.println(entries);
        //System.out.println(newEntries);
		return requests;
	}
	
	public static void main(String[] args) {
		try {
			//DBIntegration db = new DBIntegration();
			ArrayList<ArrayList<String>> data = DBIntegration.getData();
			Leaderboard.addUser("Calvin");
			Leaderboard.addUser("Benny");
			Leaderboard.addUser("Harrison");
			Leaderboard.addUser("Ivy");
			Leaderboard.updatePts("Benny", ((int) (Math.random() * 2000 -1000)));
			Leaderboard.updatePts("Harrison", ((int) (Math.random() * 2000 -1000)));
			Leaderboard.updatePts("Ivy", ((int) (Math.random() * 2000 -1000)));
			//System.out.println(data);
			for (int i = 0; i < data.size(); i++) {
				System.out.println("Address:                    " + data.get(i).get(0));
				System.out.println("Food:                       " + data.get(i).get(1));
				System.out.println("Price level:                " + data.get(i).get(2));
				String userAddr = data.get(i).get(0);
				ArrayList<String> result = GoogleMaps.findBest(userAddr, data.get(i).get(1), Integer.parseInt(data.get(i).get(2)));
				System.out.println("Restaurant receommendation: " + result.get(0));
				double avg = GoogleMaps.calcAvg(userAddr, data.get(i).get(1));
				int pts = GoogleMaps.countPts(avg, Double.parseDouble(result.get(1)));
				System.out.println("Points earned:              " + pts);
				Leaderboard.updatePts("Calvin", pts);
				System.out.println();
			}
			Leaderboard.writeJSON("leaderboard.json");
			//Runtime.getRuntime().exec("/bin/bash -c your_command");
			
			//System.out.println("first cycle finished");
			
			//data = db.getData();
			//DBIntegration.getData();
			//if (!data.isEmpty()) {
				/*for (int i = 0; i < data.size(); i++) {
					String userAddr = data.get(i).get(0);
					ArrayList<String> result = GoogleMaps.findBest(userAddr, data.get(i).get(1), Integer.parseInt(data.get(i).get(2)));
					double avg = GoogleMaps.calcAvg(userAddr, data.get(i).get(1));
					int pts = GoogleMaps.countPts(avg, Double.parseDouble(result.get(1)));
					Leaderboard.updatePts("Calvin", pts);
				}*/
			//}
			//Leaderboard.writeJSON("leaderboard.json");
			//Integer test = 1000;
			//String command = "/bin/bash -c curl -X PUT -d '{ \"Calvin\" : \"" + test.toString() + "\"}' 'https://leaderboard-541b0.firebaseio.com/data.json'";
			
			//Runtime.getRuntime().exec(command);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}
}