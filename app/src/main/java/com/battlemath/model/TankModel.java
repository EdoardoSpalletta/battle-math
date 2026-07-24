package com.battlemath.model;

public class TankModel {

    private int front;
    private int rear;
    private int right;
    private int left;
    private int turret;
    private int criticalCount;
    private int criticalCountTu;
    private int motiveSysDmg;

    public int getFront() {
        return front;
    }
    public void setFront(int front) {
        this.front = front;
    }
    public int getRear() {
        return rear;
    }
    public void setRear(int rear) {
        this.rear = rear;
    }
    public int getLeft() {
        return left;
    }
    public void setLeft(int left) {
        this.left = left;
    }
    public int getMotiveSysDmg() {
        return motiveSysDmg;
    }
    public void setMotiveSysDmg(int motiveSysDmg) {
        this.motiveSysDmg = motiveSysDmg;
    }
    public int getCriticalCountTu() {
        return criticalCountTu;
    }
    public void setCriticalCountTu(int criticalCountTu) {
        this.criticalCountTu = criticalCountTu;
    }
    public int getCriticalCount() {
        return criticalCount;
    }
    public void setCriticalCount(int criticalCount) {
        this.criticalCount = criticalCount;
    }
    public int getTurret() {
        return turret;
    }
    public void setTurret(int turret) {
        this.turret = turret;
    }
    public int getRight() {
        return right;
    }
    public void setRight(int right) {
        this.right = right;
    }

    public void multiplyDamageByShotSize(int shotSize) {
        this.front *= shotSize;
        this.left *= shotSize;
        this.right *= shotSize;
        this.rear *= shotSize;
        this.turret *= shotSize;
    }

    public void setDamage(HitLocationTablesTank.HitLocation hitLocation, int damage) {
        switch (hitLocation) {
            case FRONT:
                setFront(getFront()+damage);
                break;
            case LEFT:
                setLeft(getLeft()+damage);
                break;
            case RIGHT:
                setRight(getRight()+damage);
                break;
            case REAR:
                setRear(getRear()+damage);
                break;
            case TURRET:
                setTurret(getTurret()+damage);
                break;
        }
    }

    public int getDamageTotal() {
        return (getFront()+getLeft()+getRight()+getRear()+getTurret());
    }
}
