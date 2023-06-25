package com.laconics.schoolService.controller;

import com.laconics.schoolService.DTO.save.TicketDisplayDTO;
import com.laconics.schoolService.DTO.save.TicketSaveDTO;
import com.laconics.schoolService.DTO.specialCase.SingleData;
import com.laconics.schoolService.entity.Part;
import com.laconics.schoolService.entity.User;
import com.laconics.schoolService.exception.CustomExceptions;
import com.laconics.schoolService.service.PartService;
import com.laconics.schoolService.service.TicketService;
import com.laconics.schoolService.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final PartService partService;
    private final UserService userService;

    private final TicketService ticketService;

    public AdminController(PartService partService, UserService userService, TicketService ticketService) {
        this.partService = partService;
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @PostMapping("/user")
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        try {
            userService.save(user);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (CustomExceptions.ItemExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/part")
    public ResponseEntity<String> savePart(@RequestBody Part part) {
        try {
            partService.save(part);
            return ResponseEntity.ok("Part saved successfully");
        } catch (CustomExceptions.ItemExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PutMapping("/part")
    public ResponseEntity<String> updatePart(@RequestBody Part part) {
        try {
            partService.update(part);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (CustomExceptions.ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/part/{id}")
    public ResponseEntity<String> deletePart(@PathVariable("id") Long partId) {
        try {
            partService.deleteById(partId);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (CustomExceptions.ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/part/{id}")
    public ResponseEntity<?> getPartById(@PathVariable("id") Long partId) {
        try {
            Part part = partService.getById(partId);
            return ResponseEntity.ok(part);
        } catch (CustomExceptions.ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/part")
    public ResponseEntity<?> getAllParts() {
        try {
            List<Part> parts = partService.getAllParts();
            return ResponseEntity.ok(parts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/ticket/{ticketId}")
    public ResponseEntity<String> updateTicket(@RequestBody TicketSaveDTO ticketSaveDTO, @PathVariable Long ticketId) {
        try {
            ticketService.update(ticketSaveDTO, ticketId);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (CustomExceptions.ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (CustomExceptions.ItemSavingFailedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/close/ticket/{ticketId}")
    public ResponseEntity<String> closeTicket(@PathVariable Long ticketId){
        try {
            ticketService.closeTicket(ticketId);
            return ResponseEntity.ok("Ticket Closed");
        }catch (CustomExceptions.ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/ticket")
    public ResponseEntity<List<TicketDisplayDTO>> getAllTickets(){
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/user/ticket")
    public ResponseEntity<?> getAllTicketsForUser(@RequestBody SingleData singleData){
        try {
            return ResponseEntity.ok(ticketService.getAllTicketsByUser(singleData.getData(),"username"));
        }catch (CustomExceptions.ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
