package com.laconics.schoolService.controller;

import com.laconics.schoolService.DTO.save.TicketDisplayDTO;
import com.laconics.schoolService.DTO.save.TicketSaveDTO;
import com.laconics.schoolService.exception.CustomExceptions;
import com.laconics.schoolService.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final TicketService ticketService;

    public UserController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/ticket")
    public ResponseEntity<String> saveTicket(@RequestBody TicketSaveDTO ticketSaveDTO, @RequestHeader("Authorization") String token) {
        try {
            ticketService.save(ticketSaveDTO, token.substring(7));
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (CustomExceptions.ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (CustomExceptions.ItemSavingFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ticket")
    public ResponseEntity<List<TicketDisplayDTO>> getAllTickets(@RequestHeader("Authorization")String token){
        return ResponseEntity.ok(ticketService.getAllTicketsByUser(token.substring(7),"token"));
    }

}
