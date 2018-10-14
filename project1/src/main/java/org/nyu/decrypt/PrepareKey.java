package org.nyu.decrypt;

import org.nyu.dto.Key;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PrepareKey {

    private Key[] keyList;

    public PrepareKey() {
        this.keyList = new Key[27];
        ObjectMapper mapper =  new ObjectMapper();

    }
}
