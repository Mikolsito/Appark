package com.example.appark.Activities.src;

import java.time.LocalDateTime;

public class Loc_User {
    private Integer idLoc;
    private Integer idUser;
    private LocalDateTime[] duration;

    public Loc_User(Integer idLoc, Integer idUser, LocalDateTime startTime){
        this.idLoc = idLoc;
        this.idUser = idUser;
        duration[0] = startTime;
    }

    public Integer getIdLoc() {
        return idLoc;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public LocalDateTime getStartTime() {
        return duration[0];
    }

    public LocalDateTime getEndTime() {
        return duration[1];
    }

    public void setEndTime(LocalDateTime endTime){
        duration[1] = endTime;
    }


}
