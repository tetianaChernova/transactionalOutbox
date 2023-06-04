package com.chernova.transactional.outbox.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "outboxevent")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_ids")
	@SequenceGenerator(name = "event_ids", sequenceName = "seq_event", allocationSize = 50)
	private Long id;

	@Column(length = 1200)
	private String data;
}
