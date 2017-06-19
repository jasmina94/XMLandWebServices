package com.ftn.controller;

import com.ftn.constants.Auth;
import com.ftn.service.ZaposleniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

/**
 * Created by Olivera on 17.6.2017..
 */
@RestController
@RequestMapping("api/employees")
public class ZaposleniController {

    private ZaposleniService zaposleniService;

    @Autowired
    public ZaposleniController(ZaposleniService zaposleniService) {
        this.zaposleniService = zaposleniService;
    }

    @Transactional
    @GetMapping
    public ResponseEntity read() {
        return new ResponseEntity<>(zaposleniService.read(), HttpStatus.OK);
    }
}
