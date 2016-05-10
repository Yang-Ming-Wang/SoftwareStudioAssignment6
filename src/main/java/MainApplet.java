package main.java;

import processing.core.PApplet;
import processing.core.PFont;

import java.lang.StringBuilder;
import processing.data.JSONArray;
import processing.data.JSONObject;
import java.util.ArrayList;


import controlP5.ControlFont;
import controlP5.ControlP5;

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
	private ArrayList<Network> network = new ArrayList<Network>();
	private int noOfselect = 0;
	private int noOnCircle = 0;
	private int circleX = 700,circleY = 320,radius = 250;
	private ControlP5 cp5;
	private final static int width = 1200, height = 650;

	public void setup() {

		size(width, height);

		cp5 = new ControlP5(this);
		cp5.addButton("ClearBtn")
		.setLabel("Clear").setPosition(960, 100).setSize(200, 50);

		cp5.addButton("AddAll")
		.setLabel("AddAll").setPosition(960, 30).setSize(200, 50);

		PFont pfont = createFont("Arial",20,true); // use true/false for smooth/no-smooth
		ControlFont cfont = new ControlFont(pfont,40);


		cp5.getController("ClearBtn").getCaptionLabel().setFont(cfont);
		cp5.getController("ClearBtn").setColorBackground(color(35,180,75));


		cp5.getController("AddAll").getCaptionLabel().setFont(cfont);
		cp5.getController("AddAll").setColorBackground(color(35,80,175));

		smooth();
		loadData();

	}
	public void AddAll(){
		for(Character ch: characters){
			ch.onCircle = true;
		}
		noOnCircle = characters.size();
		reArrangecircle();
		
	}
	public void ClearBtn(){
		boolean allclean = true;
		for(Character ch: characters){
			ch.onCircle = false;
			if(ch.resetPos())
				allclean = false;
		}
		noOnCircle = 0;

		if (allclean && episode < '7'){
			episode = (char)(episode + 1);
			characters.clear();
			network.clear();
			loadData();
		}
	}
	public void draw() {
		background(255,255,255);
		for(Character ch: characters)
			ch.display();
		for(Network net: network){
			Character source = characters.get(net.source);
			Character target = characters.get(net.target);
			if(source.onCircle && target.onCircle){
				stroke(0);
				strokeWeight(net.value);
				line(source.x,source.y,target.x,target.y);
				//arc(source.x, source.y, target.x, target.y, 0, 90);
			}
		}
		noFill();
		stroke(0,0,0);
		arc(circleX, circleY, radius*2, radius*2, 0f, 2*3.14f);

		textSize(36);
		fill(185,120,90);
		text("Star Wars " + episode,350,0,200,200);
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
			char_x = 50+(i/10)*70;
			char_y = (i%10)*60+50;
			characters.add(new Character(this,char_name,char_value,char_colour,char_x,char_y));
		}

		int source;
		int target;
		int value;
		for (i = 0;i < links.size();i++){
			source = links.getJSONObject(i).getInt("source");
			target = links.getJSONObject(i).getInt("target");
			value = links.getJSONObject(i).getInt("value");
			network.add(new Network(this,source,target,value));
		}
	}
	public void mousePressed(){
		for(Character ch: characters){
			if(noOfselect == 0 && ch.isHovered()){
				noOfselect = noOfselect + 1;
				ch.isSelect = true;
			}
		}
	}
	public void mouseDragged() {
		for(Character ch: characters){
			if (ch.isSelect){
				ch.x = mouseX;
				ch.y = mouseY;
			}
		}
	}
	public void mouseReleased() {
		noOfselect = 0;
		for(Character ch: characters){
			if (ch.isSelect){
				ch.isSelect = false;
				if (ch.onCircle){
					ch.resetPos();
					ch.onCircle = false;
					noOnCircle = noOnCircle - 1;
				} else if(dist(ch.x,ch.y,circleX,circleY) <= radius){
					noOnCircle = noOnCircle + 1;
					ch.onCircle = true;
					reArrangecircle();
				}
			}
		}
	}
	public void reArrangecircle(){
		int i = 0;
		for(Character ch: characters){
			if (ch.onCircle){
				ch.x = (int)(circleX + radius*Math.cos(2*i*Math.PI/noOnCircle));
				ch.y = (int)(circleY + radius*Math.sin(2*i*Math.PI/noOnCircle));
				i = i + 1;
			}
		}
	}

}
