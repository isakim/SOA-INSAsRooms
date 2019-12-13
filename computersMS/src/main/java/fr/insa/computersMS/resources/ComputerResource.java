package fr.insa.computersMS.resources;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.insa.computersMS.model.Computer;

@RestController
public class ComputerResource {

	// Database of computers' states
	HashMap<String, Computer> computers = new HashMap<>();

	public ComputerResource () {
		RestTemplate restTemplate = new RestTemplate();

		String[] roomsID = restTemplate.getForObject("http://localhost:8091/all-id", String[].class);
		
		// Fill the simulated DB with a list that contains the computers of all rooms
		for (String roomID : roomsID)
			computers.putIfAbsent(roomID, (new Computer(roomID, false)));
	}

	@GetMapping(value="/INSA/computers")
	public HashMap<String, Computer> getAllComputers() {
		return computers;
	}
	
	@GetMapping(value="/INSA/{building}/{room}/computer")
	public boolean getComputerStatus(@PathVariable String building, @PathVariable String room) {
		return computers.get(building + "/" + room).getStatus();
	}
	
	@PostMapping(path="/INSA/{building}/{room}/computer/{state}")
	public void setComputerStatus(@PathVariable String building, @PathVariable String room, @PathVariable String state) {
		Computer computer = computers.get(building + "/" + room);
		switch (state) {
			case "true":
			case "on":
				computer.setStatus(true);
				break;

			case "false":
			case "off":
				computer.setStatus(false);
				break;
		}
	}
	
}
