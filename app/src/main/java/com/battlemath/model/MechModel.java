package com.battlemath.model;

public class MechModel {
    private int centreTorso;
    private int leftTorso;
    private int rightTorso;
    private int leftArm;
    private int rightArm;
    private int leftLeg;
    private int rightLeg;
    private int head;
    private int criticalCount;

    public int getCentreTorso() {
        return centreTorso;
    }

    public void setCentreTorso(int centreTorso) {
        this.centreTorso = centreTorso;
    }

    public int getLeftTorso() {
        return leftTorso;
    }

    public void setLeftTorso(int leftTorso) {
        this.leftTorso = leftTorso;
    }

    public int getRightTorso() {
        return rightTorso;
    }

    public void setRightTorso(int rightTorso) {
        this.rightTorso = rightTorso;
    }

    public int getCriticalCount() {
        return criticalCount;
    }

    public void setCriticalCount(int criticalCount) {
        this.criticalCount = criticalCount;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public int getRightLeg() {
        return rightLeg;
    }

    public void setRightLeg(int rightLeg) {
        this.rightLeg = rightLeg;
    }

    public int getLeftLeg() {
        return leftLeg;
    }

    public void setLeftLeg(int leftLeg) {
        this.leftLeg = leftLeg;
    }

    public int getRightArm() {
        return rightArm;
    }

    public void setRightArm(int rightArm) {
        this.rightArm = rightArm;
    }

    public int getLeftArm() {
        return leftArm;
    }

    public void setLeftArm(int leftArm) {
        this.leftArm = leftArm;
    }

    public void multiplyDamageByShotSize(int shotSize) {
        this.head *= shotSize;
        this.leftArm *= shotSize;
        this.rightArm *= shotSize;
        this.leftLeg *= shotSize;
        this.rightLeg *= shotSize;
        this.leftTorso *= shotSize;
        this.rightTorso *= shotSize;
        this.centreTorso *= shotSize;
    }

    public int getDamageTotal() {
        return (getHead()+getLeftArm()+getLeftLeg()+getLeftTorso()+getCentreTorso()+getRightArm()+getRightLeg()+getRightTorso());
    }

    public void setDamage(HitLocationTablesMech.HitLocation hitLocation, int damage) {
        switch (hitLocation) {
            case CENTER_TORSO:
                setCentreTorso(getCentreTorso()+damage);
                break;
            case LEFT_ARM:
                setLeftArm(getLeftArm()+damage);
                break;
            case LEFT_LEG:
                setLeftLeg(getLeftLeg()+damage);
                break;
            case LEFT_TORSO:
                setLeftTorso(getLeftTorso()+damage);
                break;
            case RIGHT_ARM:
                setRightArm(getRightArm()+damage);
                break;
            case RIGHT_LEG:
                setRightLeg(getRightLeg()+damage);
                break;
            case RIGHT_TORSO:
                setRightTorso(getRightTorso()+damage);
                break;
            case HEAD:
                setHead(getHead()+damage);
                break;
        }
    }

}
