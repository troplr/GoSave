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
		String urlStr = GoogleAPISecretFile.endPoint + GoogleAPISecretFile.geocodePath
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
	
	public static ArrayList<String> findBest(String userAddr, String keyword, int dollarSign) throws IOException, JSONException {
		String processedAddr = GoogleMaps.convertAddress(userAddr);
		ArrayList<String> coordinates = GoogleMaps.getCoordinates(processedAddr);
		String latitude = coordinates.get(0);
		String longitude = coordinates.get(1);
		int price;
		if (dollarSign == 1) {
			price = 1;
		} else if (dollarSign == 2) {
			price = 2;
		} else if (dollarSign == 3) {
			price = 3;
		} else {
			price = 4;
		}
		String queryParams = "location=" + latitude + "," + longitude +
				"&radius=10000&type=restaurant&keyword=" + keyword + "&minprice=" + price +
				"&maxPrice=" + price + "&rankby=prominence&key="+GoogleAPISecretFile.key;
		String urlStr = GoogleAPISecretFile.endPoint + GoogleAPISecretFile.nearbySearchPath
				+ queryParams;
		URL url = new URL(urlStr);
        URLConnection urlConnection = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String jsonTxt = "";
		String inputLine = in.readLine();
        while (inputLine != null) {
        	jsonTxt += inputLine;
        	inputLine = in.readLine();
        }
        in.close();
		
        JSONObject jsonObj = new JSONObject(jsonTxt);
        JSONArray jsonArr = jsonObj.getJSONArray("results");
        
        ArrayList<String> al = new ArrayList<String>();
        for (int i = 0; i < jsonArr.length(); i++) {
        	try {
        		String name = jsonArr.getJSONObject(i).getString("name");
        		double priceLevel = jsonArr.getJSONObject(i).getDouble("price_level");
        		double rating = jsonArr.getJSONObject(i).getDouble("rating");
        		double ratio = rating / priceLevel;
        		//System.out.println(ratio);
        		al.add(name);
        		al.add(Double.toString(ratio));
        		break;
        	} catch (JSONException e) {
        		
        	}
        }
        return al;
	}
	
	public static double calcAvg(String userAddr, String keyword) throws IOException, JSONException {
		String processedAddr = GoogleMaps.convertAddress(userAddr);
		ArrayList<String> coordinates = GoogleMaps.getCoordinates(processedAddr);
		String latitude = coordinates.get(0);
		String longitude = coordinates.get(1);
		String queryParams = "location=" + latitude + "," + longitude +
				"&radius=10000&type=restaurant&keyword=" + keyword +
				"&rankby=prominence&key="+GoogleAPISecretFile.key;
		String urlStr = GoogleAPISecretFile.endPoint + GoogleAPISecretFile.nearbySearchPath
				+ queryParams;
		URL url = new URL(urlStr);
        URLConnection urlConnection = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String jsonTxt = "";
		String inputLine = in.readLine();
        while (inputLine != null) {
        	jsonTxt += inputLine;
        	inputLine = in.readLine();
        }
        in.close();
		
        double tot = 0;
        int count = 0;
        
        JSONObject jsonObj = new JSONObject(jsonTxt);
        JSONArray jsonArr = jsonObj.getJSONArray("results");
        for (int i = 0; i < jsonArr.length(); i++) {
        	try {
        		double priceLevel = jsonArr.getJSONObject(i).getDouble("price_level");
        		double rating = jsonArr.getJSONObject(i).getDouble("rating");
        		double ratio = rating / priceLevel;
        		//System.out.println(ratio);
            	tot += ratio;
            	count++;
        	} catch (JSONException e) {
        		
        	}
        }
        
        return tot / (double) count;
	}
	
	public static int countPts(double avg, double ratio) {
		return (int) Math.round(((ratio - avg) * 100 / avg));
	}
	
	/*public static double getDistance(String src, String dest) throws IOException, JSONException {
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
	}*/
}