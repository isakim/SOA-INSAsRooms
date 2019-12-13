package fr.insa.heatersMS.resources;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.insa.heatersMS.model.Heater;

@RestController
public class HeaterResource {
	
	// Database of heaters' states
	HashMap<String, Heater> heaters = new HashMap<>();
	
	public HeaterResource () {
		RestTemplate restTemplate = new RestTemplate();

		String[] roomsID = restTemplate.getForObject("http://localhost:8091/all-id", String[].class);
		
		// Fill the simulated DB with a list that contains the heaters of all rooms
		for (String roomID : roomsID)
			heaters.putIfAbsent(roomID, (new Heater(roomID, false)));
	}	
	
	@GetMapping(value="/INSA/heaters")
	public HashMap<String, Heater> getAllLights() {
		return heaters;
	}
	
	@GetMapping(value="/INSA/{building}/{room}/heater")
	public boolean getHeaterStatus(@PathVariable String building, @PathVariable String room) {
		return heaters.get(building + "/" + room).getStatus();
	}
	
	@PostMapping(path="/INSA/{building}/{room}/light/{state}")
	public void setHeaterStatus(@PathVariable String building, @PathVariable String room, @PathVariable String state) {
		Heater heater = heaters.get(building + "/" + room);
		switch (state) {
			case "true":
			case "on":
				heater.setStatus(true);
				break;

			case "false":
			case "off":
				heater.setStatus(false);
				break;
		}
	}	
	
}
