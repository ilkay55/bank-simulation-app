package com.ilkay.controller;

import com.ilkay.dto.AccountDTO;
import com.ilkay.enums.AccountType;
import com.ilkay.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;


//@Controller already has @Component functionality, that`s why we do not need to put an extra @Component annotation here
@Controller
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
        model.addAttribute("account", new AccountDTO());
        //we need to provide accountType enum info for filling the dropdown options
        model.addAttribute("accountTypes", AccountType.values());

        return "account/create-account";
    }
    //create a method to capture information from ui
    //print them on the console.
    //trigger createNewAccount method, create the account based on the user input.
    //once user created return back to the index page.

    @PostMapping("/create")
    public String createAccount(@Valid @ModelAttribute("account") AccountDTO accountDTO, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("accountTypes", AccountType.values());
            return "account/create-account";
        }
        System.out.println(accountDTO);
        accountService.createNewAccount
                (accountDTO.getBalance(),new Date(), accountDTO.getAccountType(), accountDTO.getUserId());
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String getDeleteAccount(@PathVariable("id") Long id){
        //print the id on the console
        //System.out.println(id);

        accountService.deleteAccount(id);

        return "redirect:/index";
    }

    @GetMapping("/activate/{id}")
    public String getActivateAccount(@PathVariable("id") Long id){
        //print the id on the console
        //System.out.println(id);

        accountService.activateAccount(id);

        return "redirect:/index";
    }
}
