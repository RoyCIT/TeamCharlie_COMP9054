package com.cardreader.demo.Data;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cardreader.demo.Model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
