package com.example.otelDemo.repository;

import com.example.otelDemo.entry.GuestbookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestbookEntryRepository extends JpaRepository<GuestbookEntry, Long> {}

