package com.si.siassignment.services;

import com.si.siassignment.exceptions.ExpirationMaxDateExceededException;
import com.si.siassignment.exceptions.PasswordRequiredForPrivateBoardException;
import com.si.siassignment.exceptions.TextContentLengthExceededException;
import com.si.siassignment.exceptions.TitleLengthExceedLimitException;
import com.si.siassignment.models.Clipboard;
import com.si.siassignment.models.ClipboardStats;
import com.si.siassignment.repository.ClipboardRepository;
import com.si.siassignment.repository.ClipboardStatsRepository;
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
    @Autowired
    private ClipboardStatsRepository statsRepo;

    public Clipboard saveClipboard(Clipboard clipboard) {
        if (clipboard.getTextContent().length() > Clipboard.TEXT_LIMIT)
            throw new TextContentLengthExceededException();
        if (clipboard.getTitle().length() > Clipboard.TITLE_LIMIT)
            throw new TitleLengthExceedLimitException();
        if (clipboard.isPrivate() && (clipboard.getPassword() == null || clipboard.getPassword().trim().isEmpty()))
            throw new PasswordRequiredForPrivateBoardException();
        if (clipboard.getExpiresAt() != null && clipboard.getExpiresAt().after(getMaxExpiryDate()))
            throw  new ExpirationMaxDateExceededException();
        if (clipboard.getTinyURL() == null) clipboard.setTinyURL(generateRandomURL());
        if (clipboard.getExpiresAt() == null) clipboard.setExpiresAt(getMonthLaterDate());
        return clipboardRepo.save(clipboard);
    }

    public Clipboard updateClipboard(Clipboard oldClipboard, Clipboard newClipboard) {
        oldClipboard.setExpiresAt(newClipboard.getExpiresAt());
        oldClipboard.setPassword(newClipboard.getPassword());
        oldClipboard.setTextContent(newClipboard.getTextContent());
        oldClipboard.setTitle(newClipboard.getTitle());
        oldClipboard.setExposure(newClipboard.getExposure());
        return saveClipboard(oldClipboard);
    }

    public ClipboardStats getClipboardStats(String hexId) {
        return statsRepo.findStatsByTinyURL(AppUtility.getClipboardBaseURL() + hexId);
    }

    public ClipboardStats getClipboardStatsFromURL(String url) {
        return statsRepo.findStatsByTinyURL(url);
    }

    public ClipboardStats saveClipboardStats(ClipboardStats cs) {
        return statsRepo.save(cs);
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

    private Date getMaxExpiryDate() {
        Calendar c= Calendar.getInstance();
        c.add(Calendar.DATE, 90);
        return c.getTime();
    }

}
