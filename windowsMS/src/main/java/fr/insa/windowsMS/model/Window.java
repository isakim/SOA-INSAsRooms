package fr.insa.windowsMS.model;

public class Window {
	private String roomID;		// All the windows of a room are controlled
	private boolean status;		// Status is on (true) or off (false)
	
	public Window(String roomID, boolean status) {
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
