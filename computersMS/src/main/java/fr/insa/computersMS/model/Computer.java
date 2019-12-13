package fr.insa.computersMS.model;

public class Computer {
	private String roomID;		// all the computers of a room are controlled
	private boolean status;		// status is on (all computers on) or off (all computers off)
	
	public Computer(String roomID, boolean status) {
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
