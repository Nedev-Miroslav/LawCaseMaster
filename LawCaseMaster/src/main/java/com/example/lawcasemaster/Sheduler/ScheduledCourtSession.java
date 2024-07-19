package com.example.lawcasemaster.sheduler;

import com.example.lawcasemaster.service.CourtSessionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScheduledCourtSession {

    private final CourtSessionService courtSessionService;

    public ScheduledCourtSession(CourtSessionService courtSessionService) {
        this.courtSessionService = courtSessionService;
    }

//    @Scheduled(cron = "0 * * * * ?")    // На всяка минута за да си тествам
    @Scheduled(cron = "0 0 0 * * ?")   // Всеки ден в полунощ
    public void deleteOldCourtSessions() {
        courtSessionService.deletePastSessions();
        System.out.println("Deleted past court sessions at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

}