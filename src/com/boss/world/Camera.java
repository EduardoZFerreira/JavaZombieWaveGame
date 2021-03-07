package com.boss.world;

public class Camera {
	public static int x, y;
	
	public static int clamp(int currentValue, int minValue, int maxValue) {
		if(currentValue < minValue) {
			currentValue = minValue;
		} 
		
		if(currentValue > maxValue) {
			currentValue = maxValue;
		}
		return currentValue;
	}
}
