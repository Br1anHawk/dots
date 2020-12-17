package spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring.Repositories.AccountRepository;
import spring.controllers.mappingNames.MappingURLs;
import spring.domain.Account;
import spring.domain.Role;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/" + MappingURLs.REGISTRATION)
    public String registration() {
        return MappingURLs.REGISTRATION;
    }

    @PostMapping("/" + MappingURLs.REGISTRATION)
    public String addAccount(
            Account account,
            Map<String, Object> model) {

        Account accountFromDB = accountRepository.findByNickname(account.getNickname());

        if (accountFromDB != null) {
            model.put("errorMessage", "Account exists!");
            return MappingURLs.REGISTRATION;
        }

        account.setActive(true);
        account.setRoles(Collections.singleton(Role.USER));
        accountRepository.save(account);

        return "redirect:/" + MappingURLs.LOGIN;
    }
}
