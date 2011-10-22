

import java.net.UnknownHostException;

import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

class PlayerCreator{
	public Player createPLayer(String type){
		if(type == "wr"){
			return new Widereciever();
		}
		if(type == "qb"){
			return new Quarterback();
		}
		else
		{
		System.out.println("Incorrect position provided " + type );	
		return null;
		}
	}
}
class Player{
	String pos;
	public void printname(DBCollection players, String teamName, String Highest,String Lowest){
		BasicDBObject query = new BasicDBObject();
		query.put("team", teamName);
		query.put("position", pos);
		List<DBObject> list = players.find(query).toArray();
		TreeMap<Integer, String> tm = new TreeMap<Integer, String>(); 
		for (DBObject o : list) {
			String nameLL = o.get("name").toString();
			StringTokenizer st = new StringTokenizer(nameLL, ",");
			String Name = st.nextToken();
			st.nextToken(" ");
			int jerseyNo = Integer.parseInt(st.nextToken());
			try{
			tm.put(jerseyNo, Name);
			}catch(Exception e){
				System.out.println("Cannot insert into map " + Name + jerseyNo);
			}
	    }
		if(Highest == "true")
		{
		System.out.println(tm.get(tm.lastKey()));
		}
		if(Lowest == "true")
		{
		System.out.println(tm.get(tm.firstKey()));
		}
		
	}
}

class Widereciever extends Player{
	    public Widereciever(){
	    	this.pos = "wr";
	    }
  
}

class Quarterback extends Player{
    public Quarterback(){
    	this.pos = "qb";
    }

}

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Mongo m = null;
		
		try {
			 m = new Mongo("dbh63.mongolab.com", 27637);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		DB trendrr = m.getDB("trendrr");
		trendrr.authenticate("", "".toCharArray());
		DBCollection players = trendrr.getCollection("players");
		
		
	
		
		PlayerCreator pc = new PlayerCreator();
		Player Jets = pc.createPLayer("wr");   
		Jets.printname(players,"jets","true","false");
	}

	
}

