package ua.alexch.wertiksss.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.alexch.wertiksss.WertOptions;
import ua.alexch.wertiksss.exception.AccountEntityNotFoundException;
import ua.alexch.wertiksss.exception.IncorrectArgumentDomainException;
import ua.alexch.wertiksss.model.Account;
import ua.alexch.wertiksss.model.User;
import ua.alexch.wertiksss.repository.IAccountRepository;
import ua.alexch.wertiksss.util.SecurityUtils;

/**
 * Service layer for managing {@link Account} objects.
 */
@Service
@Transactional(readOnly = true)
public class AccountService {
    private final IAccountRepository accountRepository;

    @Autowired
    public AccountService(IAccountRepository accountRepo) {
        this.accountRepository = accountRepo;
    }

    public List<Account> findByTermsForUser(String term, String option, User user) {
        if (isBlank(user) || user.getId() == null) {
            throw new IncorrectArgumentDomainException("User is required for searching");// "User cannot be null"
        }

        List<Account> accounts = Collections.emptyList();

        if (isBlank(term.trim()) || isBlank(option.trim())) {
            return accounts;
        }

        Long userId = user.getId();
        if (option.equals(WertOptions.PHONE_NUMBER)) {
            accounts = accountRepository.findAllByPhoneNumber(term, userId);
        }
        if (option.equals(WertOptions.LAST_NAME)) {
            accounts = accountRepository.findAllByLastName(term, userId);
        }
        if (option.equals(WertOptions.PHONE_MODEL)) {
            accounts = accountRepository.findAllByPhoneModel(term, userId);
        }

        return accounts;
    }

    public List<Account> findAllSorted() {
        return accountRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<Account> findAllForUser(User user) {
        if (isBlank(user) || user.getId() == null) {
            throw new IncorrectArgumentDomainException("User is required for searching");// "User cannot be null"
        }

        return accountRepository.findAllForUserSortedByIdDesc(user.getId());
//        return repository.findByAndSort(user, Sort.by(Sort.Direction.DESC, "id"));
    }

    public Account findByIdForCurrentUser(Long id) {
        if (isBlank(id)) {
            throw new IncorrectArgumentDomainException("ID cannot be null");
        }

        Long userId = SecurityUtils.getCurrentUser().getId();

        return accountRepository.findByIdForUser(id, userId)
                .orElseThrow(() -> new AccountEntityNotFoundException("Could not find account with ID=" + id + " for current user"));
    }

    @Transactional
    public Account saveAccountForCurrentUser(Account account) {
        if (isBlank(account)) {
            throw new IncorrectArgumentDomainException("Account cannot be null");
        }

        account.setId(null);
        account.setOwner(SecurityUtils.getCurrentUser());

        return accountRepository.save(account);
    }

    @Transactional
    public Account updateAccountForCurrentUser(Account account) {
        if (isBlank(account) || account.getId() == null) {
            throw new IncorrectArgumentDomainException("Account cannot be null");
        }

        return accountRepository.findById(account.getId())
                .map(existingAccount -> {
                    if (SecurityUtils.isNotTheCurrentUser(existingAccount.getOwner())) {
                        throw new AccessDeniedException("Update denied for non-owner of the account!");
                    }

                    String[] ignoredProperties = new String[] { "id", "owner" };
                    BeanUtils.copyProperties(account, existingAccount, ignoredProperties);

                    return accountRepository.save(existingAccount);
                })
                .orElseThrow(() -> new AccountEntityNotFoundException("Failed to update account because it was not found"));
    }

    @Transactional
    public void deleteAccountByIdForCurrentUser(Long id) {
        if (isBlank(id)) {
            throw new IncorrectArgumentDomainException("ID cannot be null");
        }

        Account accountToDel = accountRepository.findById(id)
                .orElseThrow(() -> new AccountEntityNotFoundException("Failed to delete account because it was not found"));

        if (SecurityUtils.isNotTheCurrentUser(accountToDel.getOwner())) {
            throw new AccessDeniedException("Deletion denied for non-owner of the account!");
        }

        accountRepository.delete(accountToDel);
    }

    private boolean isBlank(Object obj) {
        return (obj == null || "".equals(obj));
    }

}
