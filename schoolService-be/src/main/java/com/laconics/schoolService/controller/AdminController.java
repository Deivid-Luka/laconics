package com.laconics.schoolService.controller;

import com.laconics.schoolService.entity.Part;
import com.laconics.schoolService.entity.User;
import com.laconics.schoolService.exception.CustomExceptions;
import com.laconics.schoolService.service.PartService;
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

    public AdminController(PartService partService, UserService userService) {
        this.partService = partService;
        this.userService = userService;
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

}
