

import org.json.JSONException;
import org.json.JSONObject;

public class NetworkPlayer {
	    double screenSize=-1; 
		int s_port=-1,priority=-1,id=-1;
		String name="",ip="";
		boolean goodClient;
		//Socket socket=new Socket();
		ConnectionPackage conn=new ConnectionPackage();
		//Priority is -1 if the user does not exist
		
	
		/**
		 * toString() returns the User as a string which can be parsed into a JSONObject
		 */
		
		public String toString()
		{
			JSONObject str_user=new JSONObject();
			try{
			str_user.put("ID",id);
			str_user.put("NAME", name);
			str_user.put("IP", ip);
			str_user.put("PORT", s_port);
			str_user.put("PRIORITY", priority);
			str_user.put("SCREEN_SIZE",screenSize);
			}catch(JSONException je){return "";}
			return str_user.toString();
		}

}
