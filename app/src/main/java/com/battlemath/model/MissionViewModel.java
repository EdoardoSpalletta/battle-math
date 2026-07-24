package com.battlemath.model;

import androidx.lifecycle.ViewModel;

public class MissionViewModel extends ViewModel {
    public int roundCount = 1;
    public int defPoints = 0;
    public int atkPoints = 0;
    public String defenderName = "";
    public String attackerName = "";

    public void clearAll() {
        this.roundCount =1;
        this.defPoints=0;
        this.atkPoints=0;
        this.defenderName="";
        this.attackerName="";
    }

}

