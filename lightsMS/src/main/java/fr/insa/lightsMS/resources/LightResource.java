package fr.insa.lightsMS.resources;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.insa.lightsMS.TimeManagement;
import fr.insa.lightsMS.model.Light;

@RestController
public class LightResource {
	// Database of lights' states
	HashMap<String, Light> lights = new HashMap<>();
	
	public LightResource () {
		RestTemplate restTemplate = new RestTemplate();

		String[] roomsID = restTemplate.getForObject("http://localhost:8091/all-id", String[].class);
		
		// Fill the simulated DB with a list that contains the light of all rooms
		for (String roomID : roomsID)
			lights.putIfAbsent(roomID, (new Light(roomID, false)));
	}

	@GetMapping(value="/INSA/lights")
	public HashMap<String, Light> getAllLights() {
		return lights;
	}
	
	@GetMapping(value="/INSA/{building}/{room}/light")
	public boolean getLightStatus(@PathVariable String building, @PathVariable String room) {
		return lights.get(building + "/" + room).getStatus();
	}

	@PostMapping(path="/INSA/{building}/{room}/light/{state}")
	public void setLightStatus(@PathVariable String building, @PathVariable String room, @PathVariable String state) {
		Light light = lights.get(building + "/" + room);
		switch (state) {
			case "true":
			case "on":
				light.setStatus(true);
				break;

			case "false":
			case "off":
				light.setStatus(false);
				break;
		}
	}	
			
	// Timestamp format: xx:xx:xx
	@PostMapping(path="/INSA/{building}/{room}/light/{timestamp}")
	public void setLightStatusOnSchedule(@PathVariable String building, @PathVariable String room, @PathVariable String state, @PathVariable String timestamp) {
		Light light = lights.get(building + "/" + room);
		
		// Get the current time		
		TimeManagement time = new TimeManagement();
		int timenow = time.getTime();  // Returns the hour 
		
		// Get the time user sets as input
		// Gets the first two digits of the timestamp string (hour) and converts to int
		Pattern pattern = Pattern.compile("^[\\d]{1,2}]");
		Matcher matcher = pattern.matcher(timestamp);
		if (matcher.find())	{
			int timestamp_hour = Integer.parseInt(matcher.group(1));		    
			// If time now is greater or equal input => turn off lights
			if (timenow >= timestamp_hour) {
				light.setStatus(false);
			} else {
				light.setStatus(true);
			}
		}
	}
}
