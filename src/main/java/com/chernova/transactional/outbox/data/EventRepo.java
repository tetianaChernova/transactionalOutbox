package com.chernova.transactional.outbox.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepo
		extends JpaRepository<Event, Long> {
}

