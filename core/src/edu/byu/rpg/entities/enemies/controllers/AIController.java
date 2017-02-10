package edu.byu.rpg.entities.enemies.controllers;

import edu.byu.rpg.entities.enemies.AI.AttackAI;
import edu.byu.rpg.entities.enemies.AI.MovementAI;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Andrew on 2/10/2017.
 */
public class AIController {
    private Random randomGenerator;
    private ArrayList<MovementAI> movementAIs;
    private ArrayList<AttackAI> attackAIs;

    public AIController(){
        movementAIs = new ArrayList<MovementAI>();
        attackAIs = new ArrayList<AttackAI>();
        randomGenerator = new Random();
    }

    public void addMovementAI(MovementAI movementAI){
        movementAIs.add(movementAI);
    }

    public void addAttackAI(AttackAI attackAI){
        attackAIs.add(attackAI);
    }

    public MovementAI getMovementAI(String movementAI){
        for(MovementAI AI : movementAIs){
            if(AI.getClass().getSimpleName() == movementAI){
                return AI;
            }
        }
        return null;
    }

    public AttackAI getAttackAI(String attackAI){
        for(AttackAI AI : attackAIs){
            if(AI.getClass().getSimpleName() == attackAI){
                return AI;
            }
        }
        return null;
    }

    public MovementAI getRandomMovementAI(){
        int index = randomGenerator.nextInt(movementAIs.size());
        return movementAIs.get(index);
    }

    public AttackAI getRandomAttackAI(){
        int index = randomGenerator.nextInt(movementAIs.size());
        return attackAIs.get(index);
    }

    public ArrayList<MovementAI> getMovementAIs(){
        return movementAIs;
    }

    public ArrayList<AttackAI> getAttackAIs(){
        return attackAIs;
    }
}
