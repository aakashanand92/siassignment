package com.si.siassignment.services;

import com.si.siassignment.models.Clipboard;
import com.si.siassignment.repository.ClipboardRepository;
import com.si.siassignment.utility.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

@Service
public class ClipboardService {
    @Autowired
    private ClipboardRepository clipboardRepo;

    public Clipboard createClipboard(Clipboard clipboard) {
        clipboard.setTinyURL(generateRandomURL());
        if (clipboard.getExpiresAt() == null) clipboard.setExpiresAt(getMonthLaterDate());
        return clipboardRepo.save(clipboard);
    }

    public Clipboard findByHexId(String hexId) {
        return clipboardRepo.findByTinyURL(AppUtility.getClipboardBaseURL() + hexId);
    }

    private String generateRandomURL() {
        String url = AppUtility.getClipboardBaseURL() +getRandomHexString();
        return url;
    }

    private String getRandomHexString() {
        try {
            Long count = Long.valueOf(clipboardRepo.getCount() + 1);
            MessageDigest md = MessageDigest.getInstance("SHA1");
            String toDigest = String.valueOf(count + Instant.now().toEpochMilli());
            byte[] messageDigest = md.digest(toDigest.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext.substring(0,8);
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    private Date getMonthLaterDate() {
        Calendar c= Calendar.getInstance();
        c.add(Calendar.DATE, 30);
        return c.getTime();
    }

}
