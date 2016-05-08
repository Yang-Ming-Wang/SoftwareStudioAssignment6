package main.java;

import processing.core.PApplet;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character {
	
	private MainApplet parent;
	private String name;
	private int value;
	private int colour;
	public int x,y;
	public boolean isSelect;
	public Character(MainApplet parent,String name,int value,int colour,int x,int y){

		this.parent = parent;
		this.name = name;
		this.value = value;
		this.colour = colour;
		this.x = x;
		this.y = y;
		this.isSelect = false;
	}

	public void display(){
		parent.fill(colour);
		parent.noStroke();
		parent.arc(x, y, 30, 30, 0f, 2*3.14f);
	}
	
}
