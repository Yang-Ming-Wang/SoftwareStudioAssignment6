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
	public int originX,originY;
	public int x,y;
	public boolean isSelect;
	public boolean onCircle;
	public Character(MainApplet parent,String name,int value,int colour,int x,int y){

		this.parent = parent;
		this.name = name;
		this.value = value;
		this.colour = colour;
		this.originX = x;
		this.originY = y;
		this.x = x;
		this.y = y;
		this.isSelect = false;
		this.onCircle = false;
	}
	public boolean resetPos(){
		if(x != originX || y != originY){
			x = originX;
			y = originY;
			return true;
		}
		return false;
	}
	public boolean isHovered(){
		if (parent.mouseX <= x+30 && parent.mouseX >= x-30
			&& parent.mouseY <= y+30 && parent.mouseY >= y-30)
			return true;
		else
			return false;
	}
	public void display(){
		parent.fill(colour);
		parent.noStroke();
		if (isHovered()) {
			parent.arc(x, y, 50, 50, 0f, 2*3.14f);
			parent.fill(0,0,0);
			parent.textSize(24);
			parent.text(name, x, y-50, 120, 40);
		} else
			parent.arc(x, y, 30, 30, 0f, 2*3.14f);
	}
	
}