package fr.insa.heatersMS.model;

public class Heater {
	private String roomID;		// All the heaters of a room are controlled
	private boolean status;		// Status is on (true) or off (false)
	
	public Heater(String roomID, boolean status) {
		this.roomID = roomID;
		this.status = status;
	}
	
	public String getId() {
		return this.roomID;
	}
	
	public void setId(String roomID) {
		this.roomID = roomID;
	}
	
	public boolean getStatus() {
		return this.status;
	}
	
	public boolean setStatus(boolean status) {
		return this.status = status;
	}	
}
