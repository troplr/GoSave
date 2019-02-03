import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.json.simple.JSONObject;


public class Leaderboard {

	private static HashMap<String, Integer> hm = new HashMap<String, Integer>();
	
	public static void updatePts(String userName, int pts) {
		hm.put(userName, (hm.get(userName) + pts));
	}
	
	public static void addUser(String userName) {
		hm.put(userName, 1000);
	}
	
	public static Map<Integer, String> rankUsers() {
		Map<Integer, String> tm = new TreeMap<Integer, String>(Collections.reverseOrder());
		for (String user : hm.keySet()) {
			tm.put(hm.get(user), user);
		}
		return tm;
	}
	
	public static void writeJSON(String filename) throws IOException {
		Map<Integer, String> map = Leaderboard.rankUsers();
		JSONObject jsonObject = new JSONObject();
		for (Integer pts : map.keySet()) {
			jsonObject.put(map.get(pts), pts.toString());
		}
	    Files.write(Paths.get(filename), jsonObject.toJSONString().getBytes());
	}
	
	public static void main(String[] args) {
		try {
			Leaderboard.addUser("Calvin");
			Leaderboard.addUser("Benny");
			Leaderboard.updatePts("Calvin", 35);
			Leaderboard.updatePts("Benny", -35);
			Leaderboard.writeJSON("leaderboard.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}