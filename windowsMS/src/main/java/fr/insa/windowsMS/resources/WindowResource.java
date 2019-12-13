package fr.insa.windowsMS.resources;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.insa.windowsMS.model.Window;

@RestController
public class WindowResource {

	// Database of windows' states
	HashMap<String, Window> windows = new HashMap<>();

	public WindowResource () {
		RestTemplate restTemplate = new RestTemplate();

		String[] roomsID = restTemplate.getForObject("http://localhost:8091/all-id", String[].class);
		
		// Fill the simulated DB with a list that contains the windows of all rooms
		for (String roomID : roomsID)
			windows.putIfAbsent(roomID, (new Window(roomID, false)));
	}

	@GetMapping(value="/INSA/windows")
	public HashMap<String, Window> getAllLights() {
		return windows;
	}
	
	@GetMapping(value="/INSA/{building}/{room}/window")
	public boolean getWindowStatus(@PathVariable String building, @PathVariable String room) {
		return windows.get(building + "/" + room).getStatus();
	}
	
	@PostMapping(path="/INSA/{building}/{room}/window/{state}")
	public void setWindowStatus(@PathVariable String building, @PathVariable String room, @PathVariable String state) {
		Window window = windows.get(building + "/" + room);
		switch (state) {
			case "true":
			case "on":
				window.setStatus(true);
				break;

			case "false":
			case "off":
				window.setStatus(false);
				break;
		}
	}	
}
