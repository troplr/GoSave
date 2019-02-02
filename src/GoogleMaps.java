import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleMaps {
	
	private static String radius;
	private static String type;
	
	public static String convertAddress(String rawAddress) {
		String[] strArr = rawAddress.split(" ");
		String outputStr = "";
		for (int i = 0; i < (strArr.length - 1); i++) {
			outputStr += strArr[i] + "+";
		}
		outputStr += strArr[strArr.length - 1];
		return outputStr;
	}
	
	public static ArrayList<String> getLocation(String address) throws IOException, JSONException {
		ArrayList<String> location = new ArrayList<String>();
		String jsonTxt = "";
		String queryParams = "address=" + address + "&key=" + GoogleAPISecretFile.key;
		String urlStr = GoogleAPISecretFile.endPoint + GoogleAPISecretFile.staticMapsPath2
				+ queryParams;
		
		URL url = new URL(urlStr);
        URLConnection urlConnection = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String inputLine = in.readLine();
        while (inputLine != null) {
        	jsonTxt += inputLine;
        	inputLine = in.readLine();
        }
        in.close();
        
        JSONObject jsonObj = new JSONObject(jsonTxt);
        JSONArray jsonArr = jsonObj.getJSONArray("results");
        String latitude = Double.toString(jsonArr.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
        String longitude = Double.toString(jsonArr.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
        location.add(latitude);
        location.add(longitude);
        return location;
	}
	
	public static ArrayList<String> getData(ArrayList<String> location, String radius1, String type1) throws IOException, JSONException {
		String jsonTxt = "";
		String latitude = location.get(0);
		String longitude = location.get(1);
		radius = radius1;
		type = type1;
		String queryParams = "location=" + latitude + "," + longitude + "&radius=" + radius
				+ "&type=" + type + "&key="+GoogleAPISecretFile.key;
		String urlStr = GoogleAPISecretFile.endPoint + GoogleAPISecretFile.staticMapsPath1
				+ queryParams;
		URL url = new URL(urlStr);
        URLConnection urlConnection = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String inputLine = in.readLine();
        while (inputLine != null) {
        	jsonTxt += inputLine;
        	inputLine = in.readLine();
        }
        in.close();
		
		ArrayList<String> al = new ArrayList<String>();
        JSONObject jsonObj = new JSONObject(jsonTxt);
        JSONArray jsonArr = jsonObj.getJSONArray("results");
        for (int i = 0; i < jsonArr.length(); i++) {
        	String name = jsonArr.getJSONObject(i).getString("name");
        	al.add(name);
        }
        return al;
	}
	
	public static void main(String[] args) {
		try {
			String processedStr = GoogleMaps.convertAddress("1600 Amphitheatre Parkway, Mountain View, CA");
			ArrayList<String> location = GoogleMaps.getLocation(processedStr);
			ArrayList<String> names = GoogleMaps.getData(location, "1500", "restaurant");
			System.out.println(names);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}
}