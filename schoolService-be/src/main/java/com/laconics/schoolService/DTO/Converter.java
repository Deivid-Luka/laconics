package com.laconics.schoolService.DTO;

import com.laconics.schoolService.DTO.save.TicketDisplayDTO;
import com.laconics.schoolService.entity.Ticket;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    public static TicketDisplayDTO convertTicketToDisplay(Ticket ticket){
        TicketDisplayDTO ticketDisplayDTO = new TicketDisplayDTO();

        ticketDisplayDTO.setUser(ticket.getUser().getUsername());
        ticketDisplayDTO.setPart(ticket.getPart().getName());
        ticketDisplayDTO.setDescription(ticket.getDescription());
        ticketDisplayDTO.setStatus(ticket.getStatus());
        return ticketDisplayDTO;
    }

    public static List<TicketDisplayDTO> convertListTicketToDisplay(List<Ticket>tickets){
        List<TicketDisplayDTO> ticketDisplayDTOS = new ArrayList<>();
        for (Ticket t:tickets
             ) {
            ticketDisplayDTOS.add(convertTicketToDisplay(t));
        }
        return ticketDisplayDTOS;
    }
}
