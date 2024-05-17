package dev.feramaro.urlshortener.services;

import dev.feramaro.urlshortener.entities.DTOs.UrlDTO;
import dev.feramaro.urlshortener.entities.Url;
import dev.feramaro.urlshortener.repositories.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.TimeZone;

@Service
@Slf4j
public class ShortenerService {

    @Value("${app.url}")
    private String appUrl;

    @Autowired
    private UrlRepository repository;

    public ResponseEntity<UrlDTO> shortURL(UrlDTO urlDTO) {
        LocalDateTime expireDate = LocalDateTime.now().plusDays(365);
        Url url = new Url();
        url.setUrl(urlDTO.url());
        url.setExpDate(expireDate.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli());
        url.setShortCode(generateShortCode());
        url = repository.save(url);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UrlDTO(String.format("%s/%s", appUrl, url.getShortCode())));
    }

    public ResponseEntity<Object> redirectToUrl(String shortCode) {
        Optional<Url> url = repository.findByShortCode(shortCode);
        URI redirect = null;
        if(url.isPresent()) {
            Url urlFound = url.get();
            if(!verifyWasExpired(urlFound.getExpDate())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            try {
                redirect = new URI(urlFound.getUrl());
            } catch (URISyntaxException ex) {
                log.warn("Error on URI syntax", ex);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(redirect);
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).headers(headers).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    private String generateShortCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    private boolean verifyWasExpired(Long timestamp) {
        LocalDateTime extDate =
                LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp),
                        TimeZone.getDefault().toZoneId());
        return !LocalDateTime.now().isAfter(extDate);
    }
}
