package com.laconics.schoolService.service;

import com.laconics.schoolService.DTO.save.TicketDisplayDTO;
import com.laconics.schoolService.DTO.save.TicketSaveDTO;
import com.laconics.schoolService.exception.CustomExceptions;

import java.util.List;

public interface TicketService {
    void save(TicketSaveDTO ticket, String token) throws CustomExceptions.ItemNotFoundException, CustomExceptions.ItemSavingFailedException;
    void update(TicketSaveDTO ticketSaveDTO,Long id) throws CustomExceptions.ItemNotFoundException;
    List<TicketDisplayDTO> getAllTicketsByUser(String data,String type) throws CustomExceptions.ItemNotFoundException;
    void closeTicket(Long ticketId) throws CustomExceptions.ItemNotFoundException, CustomExceptions.ItemSavingFailedException;
    List<TicketDisplayDTO> getAllTickets();
}
