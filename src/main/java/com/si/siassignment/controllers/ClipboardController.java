package com.si.siassignment.controllers;

import com.si.siassignment.exceptions.NotAuthorisedException;
import com.si.siassignment.models.Clipboard;
import com.si.siassignment.services.ClipboardService;
import com.si.siassignment.utility.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ClipboardController {

    @Autowired
    private ClipboardService clipboardService;

    @GetMapping("/")
    public String newClipboard() {
        return "index.html";
    }

    @PostMapping("/")
    @ResponseBody
    public Clipboard createClipboard(@RequestBody Clipboard clipboard) {
        Clipboard savedClipboard = clipboardService.createClipboard(clipboard);
        return savedClipboard;
    }
    @GetMapping("/b/{hexId}")
    public String editClipboard() {
        return "/edit.html";
    }

    @PostMapping("/b/{hexId}")
    @ResponseBody
    public String getClipboardDetails(@PathVariable("hexId") String hexId, HttpServletRequest request) {
        Clipboard clipboard = clipboardService.findByHexId(hexId);
        String password = request.getParameter("password");
        if (clipboard == null) return "{notFound: true}";
        if (clipboard.isPrivate()) {
            if (password == null) return "{askPassword : true}";
            if (clipboard.getPassword() != null && !clipboard.getPassword().equals(password)) return "{wrongPassword: true}";
        }
        return AppUtility.convertToJson(clipboard);
    }

    @GetMapping("/ping")
    @ResponseBody
    public String ping(@CookieValue("token") String token) {
        System.out.println(token);
        if (!token.equals("mytoken")) throw new NotAuthorisedException();
        return "Service is up";
    }
}
