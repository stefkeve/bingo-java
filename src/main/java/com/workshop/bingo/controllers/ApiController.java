package com.workshop.bingo.controllers;

import java.util.List;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import com.workshop.bingo.controllers.requests.Placebet;
import com.workshop.bingo.controllers.requests.RNGRequest;
import com.workshop.bingo.core.RNG;
import com.workshop.bingo.database.entity.Client;
import com.workshop.bingo.services.BettingService;
import com.workshop.bingo.services.ClientService;
import com.workshop.bingo.services.DrawService;
import com.workshop.bingo.services.dto.BetDTO;
import com.workshop.bingo.services.dto.DrawDTO;
import com.workshop.bingo.services.dto.PlacebetDTO;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private BettingService bettingService;

    @Autowired
    private DrawService drawService;

    @Autowired
    private RNG randomNumberGenerator;

    @PostMapping("/placebet")
    public PlacebetDTO placeBet(@Valid @RequestBody Placebet request,
        @Parameter(hidden = true) @AuthenticationPrincipal OAuth2User principal) throws Exception {
        // this part can be much better
        Client client = clientService.findClientByEmail(principal.getAttribute("email"));
        // todo error handling
        return bettingService.placeBet(client, request.getTicketId());
    }

    @GetMapping("/bets")
    public List<BetDTO> bets(
        @Parameter(hidden = true) @AuthenticationPrincipal OAuth2User principal) throws Exception {
        // this part can be much better
        Client client = clientService.findClientByEmail(principal.getAttribute("email"));
        // todo error handling
        return bettingService.getListOfBetsByClient(client);
    }

    @GetMapping(value="/bets/{id}")
    public BetDTO betById(@PathVariable Integer id
        , @Parameter(hidden = true) @AuthenticationPrincipal OAuth2User principal) throws Exception {
        // this part can be much better
        Client client = clientService.findClientByEmail(principal.getAttribute("email"));
        // todo error handling
        return bettingService.getBetById(id, client);
    }

    @GetMapping(value="/draw/{id}")
    public DrawDTO getDraw(@PathVariable Integer id) throws Exception {
        // todo error handling
        return drawService.getDrawById(id);
    }

    @GetMapping(value="/timeleft")
    public DrawDTO getDraw() throws Exception {
        return drawService.getTimeleft();
    }

    @PostMapping(value="/random")
    public List<Integer> generateRandom(@Valid @RequestBody RNGRequest request) throws Exception {
        return randomNumberGenerator.generate(
            request.getFrom(),
            request.getTo(),
            request.getNumber(),
            request.getRepeat()
        );
    }
}