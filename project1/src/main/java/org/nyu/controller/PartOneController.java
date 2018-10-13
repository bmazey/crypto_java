package org.nyu.controller;

import org.nyu.dto.Ciphertext;
import org.nyu.dto.Plaintext;
import org.nyu.service.Decrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

public class PartOneController {

    @Autowired
    Decrypt decrypt;

    private final String STRATEGY = "1";

    @RequestMapping(value = "/api/partone/ciphertext", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> postCiphertext(@Valid @RequestBody Ciphertext ciphertext) {
        String cipherContent = decrypt.convertIntArrayToCsvString(ciphertext.getCiphertext());

        // a little sloppy but we can clean later ...
        Decrypt myDecrypt = new Decrypt(cipherContent, STRATEGY);
        Plaintext myPlaintext = new Plaintext();
        myPlaintext.setPlaintext(myDecrypt.toString());
        return ResponseEntity.ok(myPlaintext);
    }
}
