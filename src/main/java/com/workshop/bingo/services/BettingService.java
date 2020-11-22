package com.workshop.bingo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.workshop.bingo.core.BingoConsts;
import com.workshop.bingo.core.RNG;
import com.workshop.bingo.database.entity.Bet;
import com.workshop.bingo.database.entity.Client;
import com.workshop.bingo.database.entity.Draw;
import com.workshop.bingo.database.entity.TicketType;
import com.workshop.bingo.database.repository.BetRepository;
import com.workshop.bingo.database.repository.TicketTypeRepository;
import com.workshop.bingo.services.dto.BetDTO;
import com.workshop.bingo.services.dto.DrawDTO;
import com.workshop.bingo.services.dto.PlacebetDTO;
import com.workshop.bingo.services.dto.TransactionDTO;

@Service
public class BettingService {
    
    @Autowired
    BetRepository betRepository;

    @Autowired
    TicketTypeRepository ticketTypeRepository;
    
    @Autowired
    TransactionService transactionService;

    @Autowired
    DrawService drawService;

    @Autowired
    RNG randomNumberGenerator;

    // todo error handling, throw some custom exception
    @Transactional
    public PlacebetDTO placeBet(Client client, Integer ticketId) throws Exception {
        TicketType ticketType = ticketTypeRepository.findById(ticketId)
                                                    .orElseThrow(
                                                        () -> new Exception("Missing ticket with id " + ticketId)
                                                    );
        

        // get next draw
        Draw nextDraw = drawService.getNextDraw();
        // get price value for requested ticket
        Float stake = ticketType.getPrice();
        // create transaction
        TransactionDTO transaction = transactionService.withdraw(client, stake);
        // create bet entity and store it
        Bet bet = new Bet();
        bet.setStake(stake);
        bet.setWin(0f);
        bet.setStatus(Bet.STATUS_OPEN);
        bet.setClient(client);
        bet.setTicketType(ticketType);
        bet.setDraw(nextDraw);
        betRepository.save(bet);

        // for this mapping some library can be used
        PlacebetDTO response = new PlacebetDTO();
        response.setBetId(bet.getId());
        response.setDrawId(nextDraw.getId());
        response.setStake(bet.getStake());
        response.setWin(bet.getWin());
        response.setStatus(bet.getStatus());
        response.setPlacedDate(new Date());
        response.setBalance(transaction.getBalance());

        return response;
    }

    public List<BetDTO> getListOfBetsByClient(Client client) {
        List<BetDTO> listOfBets = new ArrayList<BetDTO>();

        betRepository.findAllByClient(client).forEach(b -> {
            BetDTO betDTO = new BetDTO();
            betDTO.setBetId(b.getId());
            betDTO.setStatus(b.getStatus());
            betDTO.setPlacedDate(b.getPlaceDt());
            betDTO.setSettleDate(b.getSettleDt());
            betDTO.setStake(b.getStake());
            betDTO.setWin(b.getWin());
            betDTO.setTicketId(b.getTicketType().getId());
            // create draw dto
            if(b.getDraw() != null) {
                DrawDTO draw = new DrawDTO();
                draw.setDrawId(b.getDraw().getId());
                draw.setDraw(b.getDraw().getDraw());
                draw.setStatus(b.getDraw().getStatus());
                betDTO.setDraw(draw);
            }
            // populate list
            listOfBets.add(betDTO);
        });

        return listOfBets;
    }

    public BetDTO getBetById(Integer id, Client client) {
        Bet bet = betRepository.findByIdAndClient(id, client);

        // this creation can be static create method in BetDTO
        BetDTO betDTO = new BetDTO();
        betDTO.setBetId(bet.getId());
        betDTO.setStatus(bet.getStatus());
        betDTO.setPlacedDate(bet.getPlaceDt());
        betDTO.setSettleDate(bet.getSettleDt());
        betDTO.setStake(bet.getStake());
        betDTO.setWin(bet.getWin());
        betDTO.setTicketId(bet.getTicketType().getId());

        if(bet.getDraw() != null) {
            DrawDTO draw = new DrawDTO();
            draw.setDrawId(bet.getDraw().getId());
            draw.setDraw(bet.getDraw().getDraw());
            draw.setStatus(bet.getDraw().getStatus());
            betDTO.setDraw(draw);
        }

        return betDTO;
    }

    public void settleBets(Draw draw) throws Exception {
        List<Integer> drawnNumbers = Arrays.stream(draw.getDraw().split(","))
                                           .map(Integer::parseInt)
                                           .collect(Collectors.toList());
        for(Bet bet : draw.getBets()) {
            List<Integer> selections = randomNumberGenerator.generate(
                BingoConsts.DRAW_FROM,
                BingoConsts.DRAW_TO, 
                bet.getTicketType().getNumberOfSelections(), 
                false
            );
        
            List<Integer> intersection = drawnNumbers.stream()
                                                    .filter(selections::contains)
                                                    .collect(Collectors.toList());
            
            if(intersection.size() >= BingoConsts.MIN_NUM_MATCH) {
                bet.setStatus(Bet.STATUS_WON);
                Float winningAmount = bet.getStake() * bet.getTicketType().getOdds();
                bet.setWin(winningAmount);
                TransactionDTO transaction = transactionService.withdraw(bet.getClient(), winningAmount);
            } else {
                bet.setStatus(Bet.STATUS_LOSE);
            }
            
            bet.setSelectionData(selections.stream()
                                           .map(String::valueOf)
                                           .collect(Collectors.joining(","))
            );

            betRepository.save(bet);
        }
    }
}