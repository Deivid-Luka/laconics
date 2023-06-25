package com.laconics.schoolService.service;

import com.laconics.schoolService.DTO.authentification.JWTValues;
import com.laconics.schoolService.DTO.Converter;
import com.laconics.schoolService.DTO.save.TicketDisplayDTO;
import com.laconics.schoolService.DTO.save.TicketSaveDTO;
import com.laconics.schoolService.entity.Part;
import com.laconics.schoolService.entity.Status;
import com.laconics.schoolService.entity.Ticket;
import com.laconics.schoolService.entity.User;
import com.laconics.schoolService.exception.CustomExceptions;
import com.laconics.schoolService.repository.PartRepository;
import com.laconics.schoolService.repository.TicketRepository;
import com.laconics.schoolService.repository.UserRepository;
import com.laconics.schoolService.securityConfig.JWTTokenFunctions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final UserRepository userRepository;

    private final TicketRepository ticketRepository;

    private final PartRepository partRepository;

    private final JWTTokenFunctions jwtTokenFunctions;


    public TicketServiceImpl(UserRepository userRepository, TicketRepository ticketRepository, PartRepository partRepository, JWTTokenFunctions jwtTokenFunctions) {
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.partRepository = partRepository;
        this.jwtTokenFunctions = jwtTokenFunctions;
    }


    @Override
    public void save(TicketSaveDTO ticket, String token) throws CustomExceptions.ItemNotFoundException, CustomExceptions.ItemSavingFailedException {
        JWTValues jwtValues = jwtTokenFunctions.tokenValueExtractor(token);
        User user = userRepository.findByUsername(jwtValues.getUsername());
        Part part = partRepository.findById(ticket.getPart()).orElseThrow(() -> new CustomExceptions.ItemNotFoundException("Part not found"));
        Ticket saveTicket = new Ticket();
        saveTicket.setUser(user);
        saveTicket.setPart(part);
        saveTicket.setDescription(ticket.getDescription());
        try {
            ticketRepository.save(saveTicket);
        } catch (Exception e) {
            throw new CustomExceptions.ItemSavingFailedException("Failed to save ticket");
        }
        part.setStock(part.getStock() - 1);
        partRepository.save(part);
    }

    @Override
    public void update(TicketSaveDTO ticket, Long id) throws CustomExceptions.ItemNotFoundException {
        Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ItemNotFoundException("Ticket not found"));

        Part newPart = null;
        Part oldPart = null;

        if (ticket.getPart() != null) {
            newPart = partRepository.findById(ticket.getPart())
                    .orElseThrow(() -> new CustomExceptions.ItemNotFoundException("Part not found"));

            oldPart = partRepository.findById(existingTicket.getPart().getId())
                    .orElseThrow(() -> new CustomExceptions.ItemNotFoundException("The existing part not found"));

            oldPart.setStock(oldPart.getStock() + 1);
            existingTicket.setPart(newPart);
        }

        existingTicket.setDescription(ticket.getDescription());

        try {
            ticketRepository.save(existingTicket);
        } catch (Exception e) {
            throw new CustomExceptions.ItemSavingFailedException("Ticket update failed");
        }

        if (newPart != null) {
            newPart.setStock(newPart.getStock() - 1);
            partRepository.save(newPart);
        }

        if (oldPart != null) {
            partRepository.save(oldPart);
        }
    }

    @Override
    public List<TicketDisplayDTO> getAllTicketsByUser(String data, String type) throws CustomExceptions.ItemNotFoundException{
        String username;
        if (type.equals("token")) {
            JWTValues jwtValues = jwtTokenFunctions.tokenValueExtractor(data);
            username = jwtValues.getUsername();
        } else {
            username = data;
        }
        User user = userRepository.findByUsername(username);
        if (user==null){
            throw new CustomExceptions.ItemNotFoundException("User not found");
        }
        ticketRepository.findAllByUser(user);
        return Converter.convertListTicketToDisplay(ticketRepository.findAllByUser(user));
    }

    @Override
    public void closeTicket(Long ticketId) throws CustomExceptions.ItemNotFoundException, CustomExceptions.ItemSavingFailedException {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new CustomExceptions.ItemNotFoundException("Ticket not found"));
        ticket.setStatus(Status.CLOSED);
        try {
            ticketRepository.save(ticket);
        } catch (Exception e) {
            throw new CustomExceptions.ItemSavingFailedException("Error while saving ticket");
        }
    }

    @Override
    public List<TicketDisplayDTO> getAllTickets() {
        return Converter.convertListTicketToDisplay(ticketRepository.findAll());
    }


}
