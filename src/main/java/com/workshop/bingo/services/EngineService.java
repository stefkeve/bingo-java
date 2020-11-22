package com.workshop.bingo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.workshop.bingo.core.BingoConsts;
import com.workshop.bingo.database.entity.Draw;

@Service
public class EngineService {
    private final static Logger logger = LoggerFactory.getLogger(EngineService.class);
    
    @Autowired
    DrawService drawService;

    @Autowired
    BettingService bettingService;

    @Scheduled(fixedDelay = (BingoConsts.DRAW_INTERVAL - BingoConsts.DRAW_THRESHOLD) * 1000)
    @Transactional
    public void run() {
        logger.info("Starting scheduled draw job...");

        try {
            Draw draw = drawService.getNextDraw();

            if (draw != null) {
                Float timeleft = draw.getTimeleft();

                if(timeleft >= 0) {
                    logger.info(String.format("Next draw id: %s, timeleft : %s", draw.getId(), timeleft));

                    // just reschedule and wait next run
                    if(timeleft > BingoConsts.DRAW_INTERVAL + 10) {
                        drawService.reschedule(draw);
                        return;
                    }
                    
                    // wait to draw threshold
                    if(timeleft > BingoConsts.DRAW_THRESHOLD) {
                        long sleep = timeleft.longValue() - BingoConsts.DRAW_THRESHOLD;
                        logger.info("Sleeping  " + sleep + " seconds");
                        Thread.sleep(sleep * 1000);
                    }

                    logger.info("Starting settlement of draw and bets...");
                    // settle draw and bets and spawn next draw
                    drawService.settleAndSpawnDraw(draw);
                    bettingService.settleBets(draw);
                    
                    // sleep to countdown 0
                    Thread.sleep(BingoConsts.DRAW_THRESHOLD * 1000);
                } else {
                    // in the past, reschedule it
                    logger.info("There is draw, but it is in the past, rescheduling it...");
                    drawService.reschedule(draw);
                    return;
                }
            } else {
                Draw lastDraw = drawService.getLastOpenDraw();
                // missing draw, spawn it!
                if(lastDraw != null) {
                    logger.info("There is unsettled draw in the past, lets reschedule it...");
                    drawService.reschedule(lastDraw);
                } else {
                    logger.info("There is no draw, lets spawn new one...");
                    drawService.spawnDraw();
                }
            }
        } catch(Exception ex) {
            logger.error("Draw schedule job failed", ex);
        }
    }
}
