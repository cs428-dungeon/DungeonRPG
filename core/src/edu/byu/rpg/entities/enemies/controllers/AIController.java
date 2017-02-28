package edu.byu.rpg.entities.enemies.controllers;

import edu.byu.rpg.entities.enemies.AI.Attacks.*;
import edu.byu.rpg.entities.enemies.AI.Movement.MovementAI;
import edu.byu.rpg.entities.enemies.AI.Movement.MovementType;
import edu.byu.rpg.entities.enemies.AI.Movement.RandomMovementAI;
import edu.byu.rpg.entities.enemies.AI.Movement.WallBounceMovementAI;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Andrew on 2/10/2017.
 */
public class AIController {
    private Random randomGenerator = new Random();
    private ArrayList<MovementType> movementAIs = new ArrayList<MovementType>();
    private ArrayList<AttackType> attackAIs = new ArrayList<AttackType>();

    public AIController(){
    }

    public void addMovementAI(MovementType type){
        movementAIs.add(type);
    }

    public void addAttackAI(AttackType type){
        attackAIs.add(type);
    }

    public MovementAI getMovementAI(MovementType type){
        switch(type){
            case RANDOM: return new RandomMovementAI();
            case BOUNCE: return new WallBounceMovementAI();
        }
        return null;
    }

    public AttackAI getAttackAI(AttackType type){

        switch(type){
            case ONE_BULLET: return new BulletAttackAI();
            case THREE_BULLET: return new ThreeBulletAttackAI();
            case HOMING_BULLET: return new HomingBulletAttackAI();
            case FIRE_TRAIL: return new FireTrailAI();
        }
        return null;
    }

    public MovementAI getRandomMovementAI(){
        return getMovementAI(movementAIs.get(randomGenerator.nextInt(movementAIs.size())));
    }

    public AttackAI getRandomAttackAI(){
        return getAttackAI(attackAIs.get(randomGenerator.nextInt(attackAIs.size())));
    }

    public ArrayList<MovementType> getMovementTypes(){
        return movementAIs;
    }

    public ArrayList<AttackType> getAttackTypes(){
        return attackAIs;
    }
}
