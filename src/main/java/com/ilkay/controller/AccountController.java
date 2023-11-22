package com.ilkay.controller;

import com.ilkay.enums.AccountType;
import com.ilkay.model.Account;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.ilkay.service.AccountService;

import java.util.Date;
import java.util.UUID;

@Controller
//@Controller already has @Component functionality, that`s why we do not need to put an extra @Component annotation here
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
    @GetMapping( value = {"/index", "/"})
    public String getIndexPage(Model model) {

        model.addAttribute("accountList", accountService.listAllAccount());

        return "account/index";

//to show the location of the page, we assume templates directory as our root.
// So that we do not need to indicate it is under resources/templates

    }

    @GetMapping("/create-form")
    public String getCreateForm(Model model){
        //we need to provide empty account object
        model.addAttribute("account", Account.builder().build());
        //we need to provide accountType enum info for filling the dropdown options
        model.addAttribute("accountTypes", AccountType.values());

        return "account/create-account";
    }
    //create a method to capture information from ui
    //print them on the console.
    //trigger createNewAccount method, create the account based on the user input.
    //once user created return back to the index page.

    @PostMapping("/create")
    public String createAccount(@ModelAttribute("account") Account account){
        System.out.println(account);
        accountService.createNewAccount(account.getBalance(),new Date(),account.getAccountType(),account.getUserId());
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String getDeleteAccount(@PathVariable("id")UUID id){
        //print the id on the console
        //System.out.println(id);

        accountService.deleteAccount(id);

        return "redirect:/index";
    }

    @GetMapping("/activate/{id}")
    public String getActivateAccount(@PathVariable("id")UUID id){
        //print the id on the console
        //System.out.println(id);

        accountService.activateAccount(id);

        return "redirect:/index";
    }
}
