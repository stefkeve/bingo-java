package com.workshop.bingo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.workshop.bingo.core.BingoConsts;
import com.workshop.bingo.database.entity.Balance;
import com.workshop.bingo.database.entity.Client;
import com.workshop.bingo.database.repository.BalanceRepository;
import com.workshop.bingo.database.repository.ClientRepository;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    BalanceRepository balanceRepository;

    public Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Transactional
    public void registerClient(String name, String email, String authProvider) {
        Client client = new Client();
        client.setName(name);
        client.setEmail(email);
        client.setAuthProvider(authProvider);
        clientRepository.save(client);

        Balance balance = new Balance();
        balance.setClient(client);
        balance.setBalance(BingoConsts.DEFAULT_BALANCE_AMOUNT);
        balanceRepository.save(balance);
    }
}