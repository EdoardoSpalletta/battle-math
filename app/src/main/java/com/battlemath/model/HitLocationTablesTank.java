package com.battlemath.model;

import java.util.HashMap;
import java.util.Map;

public class HitLocationTablesTank {

    public enum HitLocation {
        FRONT,
        LEFT,
        RIGHT,
        REAR,
        TURRET
    }

    // Mappa per associare le somme dei dadi alle righe della hit location table FRONT
    private static final Map<Integer, HitLocation> mapFront = new HashMap<>();
    static {
        mapFront.put(2, HitLocation.FRONT);
        mapFront.put(3, HitLocation.FRONT);
        mapFront.put(4, HitLocation.FRONT);
        mapFront.put(5, HitLocation.RIGHT);
        mapFront.put(6, HitLocation.FRONT);
        mapFront.put(7, HitLocation.FRONT);
        mapFront.put(8, HitLocation.FRONT);
        mapFront.put(9, HitLocation.LEFT);
        mapFront.put(10, HitLocation.TURRET);
        mapFront.put(11, HitLocation.TURRET);
        mapFront.put(12, HitLocation.TURRET);
    }
    public static HitLocation getPositionFront(int roll) {
       return  mapFront.get(roll);
    }

    // Mappa per associare le somme dei dadi alle righe della hit location table REAR
    private static final Map<Integer, HitLocation> mapRear = new HashMap<>();
    static {
        mapRear.put(2, HitLocation.REAR);
        mapRear.put(3, HitLocation.REAR);
        mapRear.put(4, HitLocation.REAR);
        mapRear.put(5, HitLocation.LEFT);
        mapRear.put(6, HitLocation.REAR);
        mapRear.put(7, HitLocation.REAR);
        mapRear.put(8, HitLocation.REAR);
        mapRear.put(9, HitLocation.RIGHT);
        mapRear.put(10, HitLocation.TURRET);
        mapRear.put(11, HitLocation.TURRET);
        mapRear.put(12, HitLocation.TURRET);
    }
    public static HitLocation getPositionRear(int roll) {
        return  mapRear.get(roll);
    }

    // Mappa per associare le somme dei dadi alle righe della hit location table RIGHT
    private static final Map<Integer, HitLocation> mapRight = new HashMap<>();
    static {
        mapRight.put(2, HitLocation.RIGHT);
        mapRight.put(3, HitLocation.RIGHT);
        mapRight.put(4, HitLocation.RIGHT);
        mapRight.put(5, HitLocation.FRONT);
        mapRight.put(6, HitLocation.RIGHT);
        mapRight.put(7, HitLocation.RIGHT);
        mapRight.put(8, HitLocation.RIGHT);
        mapRight.put(9, HitLocation.REAR);
        mapRight.put(10, HitLocation.TURRET);
        mapRight.put(11, HitLocation.TURRET);
        mapRight.put(12, HitLocation.TURRET);
    }
    public static HitLocation getPositionRight(int roll) {
        return  mapRight.get(roll);
    }

    // Mappa per associare le somme dei dadi alle righe della hit location table LEFT
    private static final Map<Integer, HitLocation> mapLeft = new HashMap<>();
    static {
        mapLeft.put(2, HitLocation.LEFT);
        mapLeft.put(3, HitLocation.LEFT);
        mapLeft.put(4, HitLocation.LEFT);
        mapLeft.put(5, HitLocation.FRONT);
        mapLeft.put(6, HitLocation.LEFT);
        mapLeft.put(7, HitLocation.LEFT);
        mapLeft.put(8, HitLocation.LEFT);
        mapLeft.put(9, HitLocation.REAR);
        mapLeft.put(10, HitLocation.TURRET);
        mapLeft.put(11, HitLocation.TURRET);
        mapLeft.put(12, HitLocation.TURRET);
    }
    public static HitLocation getPositionLeft(int roll) {
        return  mapLeft.get(roll);
    }
}