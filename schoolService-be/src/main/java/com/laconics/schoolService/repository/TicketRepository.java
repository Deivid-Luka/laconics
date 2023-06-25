package com.laconics.schoolService.repository;

import com.laconics.schoolService.entity.Ticket;
import com.laconics.schoolService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByUser(User user);
}
