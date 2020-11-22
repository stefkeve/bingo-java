package com.workshop.bingo.services;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.workshop.bingo.core.BingoConsts;
import com.workshop.bingo.core.RNG;
import com.workshop.bingo.database.DBUtils;
import com.workshop.bingo.database.entity.Draw;
import com.workshop.bingo.database.repository.DrawRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.workshop.bingo.services.dto.DrawDTO;
import com.workshop.bingo.services.dto.NextDrawDTO;

@Service
public class DrawService {
    private static Logger logger = LoggerFactory.getLogger(DrawService.class);

    @Autowired
    DrawRepository drawRepository;
    
    @Autowired
    RNG randomNumberGenerator;

    @Autowired
    DBUtils dbUtils;

    public void settleAndSpawnDraw(Draw draw) throws Exception {
        logger.info("Spawning new draw...");
        Draw spawnedDraw = new Draw();
        spawnedDraw.setDrawDt(createDrawTime(draw.getDrawDt()));
        spawnedDraw.setStatus(Draw.DRAW_STATUS_OPEN);
        drawRepository.save(spawnedDraw);

        logger.info("Settle draw " + draw.getId());
        List<Integer> numbers = randomNumberGenerator.generate(
            BingoConsts.DRAW_FROM,
            BingoConsts.DRAW_TO,
            BingoConsts.DRAW_NUMBER,
            false
        );
        draw.setStatus(Draw.DRAW_STATUS_SETTLED);
        draw.setDraw(numbers.stream().map(String::valueOf).collect(Collectors.joining(",")));
        logger.info("Random drawn numbers: " + draw.getDraw());
        drawRepository.save(draw);
    }

    public Draw getNextDraw() {
        List<Draw> draws = drawRepository.findNextDraw();
        return draws.size() > 0 ? draws.get(0) : null;
    }

    public Draw getLastOpenDraw() {
        return drawRepository.findTop1ByStatusOrderByIdDesc(Draw.DRAW_STATUS_OPEN);
    }

    public void reschedule(Draw draw) {
        logger.info("Rescheduling draw...");
        draw.setDrawDt(createDrawTime(dbUtils.getCurrentDate()));
        drawRepository.save(draw);
    }

    public void spawnDraw() {
        logger.info("Spawning new draw...");
        Draw spawnedDraw = new Draw();
        System.out.println("Current date " + dbUtils.getCurrentDate());
        spawnedDraw.setDrawDt(createDrawTime(dbUtils.getCurrentDate()));
        spawnedDraw.setStatus(Draw.DRAW_STATUS_OPEN);
        drawRepository.save(spawnedDraw);
    }

    private Date createDrawTime(Date startTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.SECOND, (int) BingoConsts.DRAW_INTERVAL);
        return calendar.getTime();
    }

    // i.e these two methods can be in separated interface and expose it only to presentation layer (api controller)
    public NextDrawDTO getTimeleft() {
        Draw nextDraw = getNextDraw();

        NextDrawDTO nextDrawDTO = new NextDrawDTO();
        nextDrawDTO.setDrawId(nextDraw.getId());
        nextDrawDTO.setDraw(nextDraw.getDraw());
        nextDrawDTO.setStatus(nextDraw.getStatus());
        nextDrawDTO.setTimeleft((int) Math.floor(nextDraw.getTimeleft()));

        return nextDrawDTO;
    }

    public DrawDTO getDrawById(Integer id) throws Exception {
        Draw draw = drawRepository.findById(id)
                                  .orElseThrow(
                                    () -> new Exception("Missing draw for id " + id));

        DrawDTO response = new DrawDTO();
        response.setDrawId(draw.getId());
        response.setDraw(draw.getDraw());
        response.setStatus(draw.getStatus());

        return response;
    }
}