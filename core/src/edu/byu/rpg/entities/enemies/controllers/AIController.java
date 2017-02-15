package edu.byu.rpg.entities.enemies.controllers;

import edu.byu.rpg.entities.enemies.AI.AttackAI;
import edu.byu.rpg.entities.enemies.AI.MovementAI;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Andrew on 2/10/2017.
 */
public class AIController {
    private Random randomGenerator = new Random();
    private ArrayList<MovementAI> movementAIs = new ArrayList<MovementAI>();
    private ArrayList<AttackAI> attackAIs = new ArrayList<AttackAI>();

    public AIController(){
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
        return movementAIs.get(randomGenerator.nextInt(movementAIs.size()));
    }

    public AttackAI getRandomAttackAI(){
        return attackAIs.get(randomGenerator.nextInt(attackAIs.size()));
    }

    public ArrayList<MovementAI> getMovementAIs(){
        return movementAIs;
    }

    public ArrayList<AttackAI> getAttackAIs(){
        return attackAIs;
    }
}
