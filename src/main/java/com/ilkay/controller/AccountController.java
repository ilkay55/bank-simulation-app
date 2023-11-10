package com.ilkay.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ilkay.service.AccountService;

@Controller //@Controller already has @Component functionality, that`s why we do not need to put an extra @Component annotation here
public class AccountController {

      /*
        write a method to return index.html including account list information
        endpoint:index
     */

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
//we need a regular java method which has always String as Return type
// because we are returning html path  (location of my html or thymeleaf path)

//    @RequestMapping(value="/index", method = RequestMethod.GET)
//    or if we do not need info from user we simply use GetMapping
    @GetMapping("/index")
    public String getIndexPage(Model model){

        model.addAttribute("accountList",accountService.listAllAccount());

return "account/index";

//to show the location of the page, we assume templates directory as our root.
// So that we do not need to indicate it is under resources/templates

    }
}
