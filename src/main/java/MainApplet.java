package main.java;

import processing.core.PApplet;
import java.lang.StringBuilder;
import processing.data.JSONArray;
import processing.data.JSONObject;
import java.util.ArrayList;

/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.  
*/
@SuppressWarnings("serial")
public class MainApplet extends PApplet{
	private String path = "main/resources/";
	private String file = "starwars-episode-1-interactions.json";
	private JSONObject data;
	private JSONArray nodes, links;
	private char episode = '1';
	private ArrayList<Character> characters = new ArrayList<Character>();
	private final static int width = 1200, height = 650;
	
	public void setup() {

		size(width, height);
		smooth();
		loadData();
		
	}

	public void draw() {
		for(Character ch: characters)
			ch.display();
	}

	private void loadData(){
		StringBuilder temp = new StringBuilder(file);
		//revise episode here in order to get other JSONfile
		temp.setCharAt(17, episode);
		
		data = loadJSONObject(path + temp);
		nodes = data.getJSONArray("nodes");
		links = data.getJSONArray("links");
		
		int i;
		String char_name;
		int char_value;
		int char_colour;
		int char_x;
		int char_y;
		
		for (i = 0;i < nodes.size();i++){
			char_name = nodes.getJSONObject(i).getString("name");
			char_value = nodes.getJSONObject(i).getInt("value");
			char_colour = unhex(nodes.getJSONObject(i).getString("colour").substring(1, 9));
			char_x = 50+(i/10)*50;
			char_y = (i%10)*50+50;
			characters.add(new Character(this,char_name,char_value,char_colour,char_x,char_y));
		}
		
		
	}

}
