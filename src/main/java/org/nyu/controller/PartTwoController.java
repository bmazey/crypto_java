package org.nyu.controller;


import org.nyu.dto.Ciphertext;
import org.nyu.dto.Plaintext;
import org.nyu.service.Decrypt;
import org.nyu.service.Decryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PartTwoController {

    @Autowired
    Decryptor decryptor;

    @Autowired
    Decrypt decrypt;

    private final String STRATEGY = "2";

    @RequestMapping(value = "/api/parttwo/ciphertext", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> postCiphertext(@Valid @RequestBody Ciphertext ciphertext) {

        String cipherContent = decryptor.convertIntArrayToCsvString(ciphertext.getCiphertext());

        // a little sloppy but we can clean later ...
        String plaintext = decrypt.decrypt(cipherContent, STRATEGY);
        Plaintext myPlaintext = new Plaintext();
        myPlaintext.setPlaintext(plaintext);
        System.out.println("plaintext: " + plaintext);
        return ResponseEntity.ok(myPlaintext);
    }


}
