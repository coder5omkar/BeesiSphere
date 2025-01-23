package net.javaguides.todo.controller;

import lombok.AllArgsConstructor;
import net.javaguides.todo.dto.ContryDto;
import net.javaguides.todo.service.ContryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/contries")
@AllArgsConstructor
public class ContryController {

    private final ContryService contryService;

    // Add Contry REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ContryDto> addContry(@RequestBody ContryDto contryDto) {
        ContryDto savedContry = contryService.addContry(contryDto);
        return new ResponseEntity<>(savedContry, HttpStatus.CREATED);
    }

    // Get Contry by ID REST API
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("{id}")
    public ResponseEntity<ContryDto> getContry(@PathVariable("id") Long contryId) {
        ContryDto contryDto = contryService.getContry(contryId);
        return new ResponseEntity<>(contryDto, HttpStatus.OK);
    }

    // Get All Contries REST API
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<ContryDto>> getAllContries() {
        List<ContryDto> contries = contryService.getAllContrys();
        return ResponseEntity.ok(contries);
    }

    // Get Contries by Member ID REST API
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ContryDto>> getContryByMemberId(@PathVariable("memberId") Long memberId) {
        List<ContryDto> contries = contryService.getContryByMemberId(memberId);
        return ResponseEntity.ok(contries);
    }

    // Update Contry REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<ContryDto> updateContry(@RequestBody ContryDto contryDto, @PathVariable("id") Long contryId) {
        ContryDto updatedContry = contryService.updateContry(contryDto, contryId);
        return ResponseEntity.ok(updatedContry);
    }

    // Delete Contry REST API
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteContry(@PathVariable("id") Long contryId) {
        contryService.deleteContry(contryId);
        return ResponseEntity.ok("Contry deleted successfully.");
    }
}
