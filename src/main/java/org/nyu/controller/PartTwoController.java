package org.nyu.controller;


import org.nyu.service.Decryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PartTwoController {

    @Autowired
    Decryptor decryptor;


}
