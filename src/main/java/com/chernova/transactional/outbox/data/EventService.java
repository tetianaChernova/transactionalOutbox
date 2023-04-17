package com.chernova.transactional.outbox.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepo eventRepo;

	public void createEvent(Event event) {
		eventRepo.save(event);
	}
}