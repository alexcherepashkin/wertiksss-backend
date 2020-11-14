package ua.alexch.demowert.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ua.alexch.demowert.dto.MessageResponse;
import ua.alexch.demowert.model.Account;
import ua.alexch.demowert.model.User;
import ua.alexch.demowert.service.AccountService;

/**
 * REST controller for managing {@link Account}s of the current {@link User}.
 * 
 * @author Alexey Cherepashkin.
 */
@RestController
@RequestMapping(path = "/main/accounts")
@CrossOrigin
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService service) {
        this.accountService = service;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Account>> findAllByTermsForUser(@AuthenticationPrincipal User currentUser,
            @RequestParam(name = "term", required = false) String term,
            @RequestParam(name = "option", required = false) String option) {

        List<Account> accounts = accountService.findByTermsForUser(term, option, currentUser);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping
    public ResponseEntity<List<Account>> findAllForUser(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(accountService.findAllForUser(currentUser));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Account>> findAllForAdmin() {
        return ResponseEntity.ok(accountService.findAllSorted());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> findByIdForUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(accountService.findByIdForCurrentUser(id));
    }

    @PostMapping
    public ResponseEntity<Account> saveAccountForUser(@Valid @RequestBody Account account) {
        Account savedAccount = accountService.saveAccountForCurrentUser(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

//    @PreAuthorize("#account.id == #id")
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccountForUser(@PathVariable("id") Long id,
            @Valid @RequestBody Account account) {

        if (!id.equals(account.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "False account ID!");
        }
        return ResponseEntity.ok(accountService.updateAccountForCurrentUser(account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteAccountForUser(@PathVariable("id") Long id) {
        accountService.deleteAccountByIdForCurrentUser(id);
        return ResponseEntity.ok(new MessageResponse("Account was successfully deleted"));
    }

}
