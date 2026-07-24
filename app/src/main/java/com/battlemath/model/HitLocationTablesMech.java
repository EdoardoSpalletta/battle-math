package com.battlemath.model;

import java.util.HashMap;
import java.util.Map;

public class HitLocationTablesMech {

    public enum HitLocation {
        CENTER_TORSO,
        LEFT_TORSO,
        RIGHT_TORSO,
        LEFT_ARM,
        RIGHT_ARM,
        LEFT_LEG,
        RIGHT_LEG,
        HEAD
    }

    // Mappa per associare le somme dei dadi alle righe della hit location table CENTER
    private static final Map<Integer, HitLocation> mapFront = new HashMap<>();
    static {
        mapFront.put(2, HitLocation.CENTER_TORSO);
        mapFront.put(3, HitLocation.RIGHT_ARM);
        mapFront.put(4, HitLocation.RIGHT_ARM);
        mapFront.put(5, HitLocation.RIGHT_LEG);
        mapFront.put(6, HitLocation.RIGHT_TORSO);
        mapFront.put(7, HitLocation.CENTER_TORSO);
        mapFront.put(8, HitLocation.LEFT_TORSO);
        mapFront.put(9, HitLocation.LEFT_LEG);
        mapFront.put(10, HitLocation.LEFT_ARM);
        mapFront.put(11, HitLocation.LEFT_ARM);
        mapFront.put(12, HitLocation.HEAD);
    }
    public static HitLocation getPositionFront(int roll) {
       return  mapFront.get(roll);
    }


    // Mappa per associare le somme dei dadi alle righe della hit location table RIGHT
    private static final Map<Integer, HitLocation> mapRight = new HashMap<>();
    static {
        mapRight.put(2, HitLocation.RIGHT_TORSO);
        mapRight.put(3, HitLocation.RIGHT_LEG);
        mapRight.put(4, HitLocation.RIGHT_ARM);
        mapRight.put(5, HitLocation.RIGHT_ARM);
        mapRight.put(6, HitLocation.RIGHT_LEG);
        mapRight.put(7, HitLocation.RIGHT_TORSO);
        mapRight.put(8, HitLocation.CENTER_TORSO);
        mapRight.put(9, HitLocation.LEFT_TORSO);
        mapRight.put(10, HitLocation.LEFT_ARM);
        mapRight.put(11, HitLocation.LEFT_LEG);
        mapRight.put(12, HitLocation.HEAD);
    }
    public static HitLocation getPositionRight(int roll) {
        return  mapRight.get(roll);
    }


    // Mappa per associare le somme dei dadi alle righe della hit location table LEFT
    private static final Map<Integer, HitLocation> mapLeft = new HashMap<>();
    static {
        mapLeft.put(2, HitLocation.LEFT_TORSO);
        mapLeft.put(3, HitLocation.LEFT_LEG);
        mapLeft.put(4, HitLocation.LEFT_ARM);
        mapLeft.put(5, HitLocation.LEFT_ARM);
        mapLeft.put(6, HitLocation.LEFT_LEG);
        mapLeft.put(7, HitLocation.LEFT_TORSO);
        mapLeft.put(8, HitLocation.CENTER_TORSO);
        mapLeft.put(9, HitLocation.RIGHT_TORSO);
        mapLeft.put(10, HitLocation.RIGHT_ARM);
        mapLeft.put(11, HitLocation.RIGHT_LEG);
        mapLeft.put(12, HitLocation.HEAD);
    }
    public static HitLocation getPositionLeft(int roll) {
        return  mapLeft.get(roll);
    }
}
