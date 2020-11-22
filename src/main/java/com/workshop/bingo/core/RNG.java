package com.workshop.bingo.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RNG {
    public List<Integer> generate(Integer from, Integer to, Integer number, boolean repeat) throws Exception {
        if(to < from) {
            throw new Exception("Malformed request parameters, <to> is lower than <from>");
        }

        // escape deadloop
        if(!repeat && number > to - from + 1) {
            throw new Exception("Malformed request parameters");
        };

        Random random = new Random();
        
        Collection<Integer> generated = repeat ? new ArrayList<Integer>() : new HashSet<Integer>();

        while(generated.size() < number) {
            generated.add(random.nextInt(to) + from);
        }

        return new ArrayList<Integer>(generated);
    }
}