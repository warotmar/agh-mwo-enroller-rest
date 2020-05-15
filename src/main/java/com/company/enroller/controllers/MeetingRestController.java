package com.company.enroller.controllers;


import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController

@RequestMapping("/meetings")

public class MeetingRestController {


	@Autowired

	MeetingService meetingService;


	@RequestMapping(value = "", method = RequestMethod.GET)

	public ResponseEntity<?> getMeetings() {

		Collection<Meeting> meetings = meetingService.getAll();

		return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);

	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)

	public ResponseEntity<?> getMeeting(@PathVariable("id") Long id) {

		Meeting meeting = meetingService.findById(id);

		if (meeting == null) {

			return new ResponseEntity(HttpStatus.NOT_FOUND);

		}


		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);

	}


	@RequestMapping(value = "", method = RequestMethod.POST)

	public ResponseEntity<?> addMeeting(@RequestBody Meeting meeting) {

		Meeting foundMeeting = MeetingService.findById(meeting.getId());

		if (foundMeeting != null) {

			return new ResponseEntity(

					"Unable to add. A meeting with id " + meeting.getId() + " already exist.",

					HttpStatus.CONFLICT);

		}

		meetingService.add(meeting);

		return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/{id}/participants", method = RequestMethod.PUT)
	public ResponseEntity<?> addParticipant(@PathVariable("id") long id, @RequestBody Participant participant){
		Meeting meeting = meetingService.findById(id);
		if (meeting == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		meeting.addParticipant(participant);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);

	}


	@RequestMapping(value = "/{id}/participants", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipants(@PathVariable("id") long id){
		Meeting meeting = meetingService.findById(id);
		if (meeting == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Participant>>(meeting.getParticipants(), HttpStatus.OK);

	}


}