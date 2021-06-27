package com.si.siassignment.controllers;
import com.si.siassignment.exceptions.NotAuthorisedException;
import com.si.siassignment.exceptions.WrongPasswordException;
import com.si.siassignment.models.Clipboard;
import com.si.siassignment.models.ClipboardStats;
import com.si.siassignment.models.PasswordPayLoad;
import com.si.siassignment.services.ClipboardService;
import com.si.siassignment.utility.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ClipboardController {

    @Value("${stats.token}")
    private String statsToken;

    @Autowired
    private ClipboardService clipboardService;

    @GetMapping("/")
    public String newClipboard() {
        return "index.html";
    }

    @PostMapping("/")
    @ResponseBody
    public Clipboard createClipboard(@RequestBody Clipboard clipboard) {
        Clipboard savedClipboard = clipboardService.saveClipboard(clipboard);
        ClipboardStats cs = new ClipboardStats(savedClipboard.getId());
        clipboardService.saveClipboardStats(cs);
        return savedClipboard;
    }
    @GetMapping("/b/{hexId}")
    public String editClipboard(@PathVariable("hexId") String hexId) {
        ClipboardStats cs = clipboardService.getClipboardStats(hexId);
        cs.setReadCount(cs.getReadCount() + 1);
        clipboardService.saveClipboardStats(cs);
        return "/edit.html";
    }

    @PostMapping("/b/{hexId}")
    @ResponseBody
    public String getClipboardDetails(@PathVariable("hexId") String hexId, @RequestBody(required = false) PasswordPayLoad passwordPayLoad) throws IOException {
        Clipboard clipboard = clipboardService.findByHexId(hexId);
        if (clipboard == null) {
            Map<String, Boolean> resp = new HashMap<>();
            resp.put("notFound", true);
            return AppUtility.convertToJson(resp);
        }
        if (clipboard.isPrivate()) {
            if (passwordPayLoad == null || passwordPayLoad.getPassword() == null) {
                Map<String, Boolean> resp = new HashMap<>();
                resp.put("askPassword", true);
                return AppUtility.convertToJson(resp);
            }
            if (clipboard.getPassword() != null && !clipboard.getPassword().equals(passwordPayLoad.getPassword())) {
                throw new WrongPasswordException();
            }
        }
        return AppUtility.convertToJson(clipboard);
    }

    @PutMapping("/b/{hexId}")
    @ResponseBody
    public Clipboard editClipboardDetails(@PathVariable("hexId") String hexId, @RequestBody Clipboard newClipboard) throws IOException {
        Clipboard oldClipboard = clipboardService.findByHexId(hexId);
        Clipboard savedClipboard = clipboardService.updateClipboard(oldClipboard, newClipboard);
        ClipboardStats cs = clipboardService.getClipboardStats(hexId);
        cs.setEditCount(cs.getEditCount() + 1);
        clipboardService.saveClipboardStats(cs);
        return savedClipboard;
    }

    @GetMapping("/b/{hexId}/stats")
    @ResponseBody
    public ClipboardStats getClipboardStats(HttpServletRequest request, @PathVariable("hexId") String hexId) {
        String token = request.getHeader("token");
        if (token == null || !token.equals(statsToken)) throw new NotAuthorisedException();
        return clipboardService.getClipboardStats(hexId);
    }
}
