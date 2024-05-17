package dev.feramaro.urlshortener.controllers;

import dev.feramaro.urlshortener.entities.DTOs.UrlDTO;
import dev.feramaro.urlshortener.services.ShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class ShortenerController {

    @Autowired
    private ShortenerService shortenerService;

    @GetMapping("{shortCode}")
    public ResponseEntity<Object> redirectToUrl(@PathVariable String shortCode) {
        return shortenerService.redirectToUrl(shortCode);
    }

    @PostMapping("shorten-url")
    public ResponseEntity<UrlDTO> shortURL(@RequestBody UrlDTO urlDTO) {
        return shortenerService.shortURL(urlDTO);
    }
}
