import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleMaps {
	
	public static String convertAddress(String rawAddress) {
		String[] strArr = rawAddress.split(" ");
		String outputStr = "";
		for (int i = 0; i < (strArr.length - 1); i++) {
			outputStr += strArr[i] + "+";
		}
		outputStr += strArr[strArr.length - 1];
		return outputStr;
	}
	
	public static ArrayList<String> getCoordinates(String address) throws IOException, JSONException {
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
	
	public static ArrayList<Restaurant> searchNearby(ArrayList<String> location, String keyword) throws IOException, JSONException {
		String jsonTxt = "";
		String latitude = location.get(0);
		String longitude = location.get(1);
		String queryParams = "location=" + latitude + "," + longitude +
				"&radius=10000&type=restaurant&keyword=" + keyword +
				"&key="+GoogleAPISecretFile.key;
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
		
		ArrayList<Restaurant> al = new ArrayList<Restaurant>();
        JSONObject jsonObj = new JSONObject(jsonTxt);
        JSONArray jsonArr = jsonObj.getJSONArray("results");
        for (int i = 0; i < jsonArr.length(); i++) {
        	String name = jsonArr.getJSONObject(i).getString("name");
        	Restaurant r = new Restaurant(name, 1.0, 1);
        	al.add(r);
        }
        return al;
	}
	
	public static double getDistance(String src, String dest) throws IOException, JSONException {
		String jsonTxt = "";
		String processedSrc = GoogleMaps.convertAddress(src);
		String processedDest = GoogleMaps.convertAddress(dest);
		String queryParams = "units=imperial&origins=" + processedSrc + "&destinations="
				+ processedDest + "&key="+GoogleAPISecretFile.key;
		String urlStr = GoogleAPISecretFile.endPoint + GoogleAPISecretFile.staticMapsPath3
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
        JSONArray jsonArr = jsonObj.getJSONArray("rows");
        Double distance = jsonArr.getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("distance").getDouble("value");
        return distance;
	}
	
	public static void main(String[] args) {
		try {
			String processedStr = GoogleMaps.convertAddress("1600 Amphitheatre Parkway, Mountain View, CA");
			ArrayList<String> location = GoogleMaps.getCoordinates(processedStr);
			ArrayList<Restaurant> restaurants = GoogleMaps.searchNearby(location, "Chinese");
			for (Restaurant r : restaurants) {
				System.out.println(r.getName());
			}
			//System.out.println(GoogleMaps.getDistance("NYC", "DC"));
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}
}