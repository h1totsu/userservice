package com.dev.controllers;

import com.dev.activedir.ActiveDirectoryLdapService;
import com.dev.activedir.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.naming.NamingException;

@Controller
public class BaseController
{
  @Autowired
  ActiveDirectoryLdapService directoryLdapService;

  @RequestMapping("/")
  public String index(Model model) {
    model.addAttribute("userForm", new UserInfo());

    return "index";
  }

  @RequestMapping(value = "/user", method = RequestMethod.POST)
  public String createUser(@ModelAttribute("userForm") UserInfo userInfo, Model model) {
    try {
      directoryLdapService.addUserToDomain(userInfo);
      model.addAttribute("error", "User was created");
    } catch (NamingException e) {
      model.addAttribute("error", "User not created");
      throw new RuntimeException(e);
    }
    return "index";
  }
}
