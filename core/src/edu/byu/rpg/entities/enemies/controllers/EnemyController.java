package edu.byu.rpg.entities.enemies.controllers;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.utils.Array;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.enemies.AI.Attacks.AttackAI;
import edu.byu.rpg.entities.enemies.AI.Movement.MovementAI;
import edu.byu.rpg.entities.enemies.bosses.Babi;
import edu.byu.rpg.entities.enemies.bosses.BossType;
import edu.byu.rpg.entities.enemies.standard.MonsterType;
import edu.byu.rpg.entities.enemies.standard.Scarab;
import edu.byu.rpg.physics.World;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Andrew on 2/13/2017.
 */
public class EnemyController {

    private Random monsterGenerator = new Random();
    private ArrayList<MonsterType> monsterTypes = new ArrayList<MonsterType>();
    private AIController aiController;
    private Array<TiledMapTileMapObject> EnemyTiles;

    public EnemyController(AIController aiController, Array<TiledMapTileMapObject> EnemyTiles){
        this.aiController = aiController;
        this.EnemyTiles = EnemyTiles;
    }

    public void addEnemy(MonsterType monsterType){
        monsterTypes.add(monsterType);
    }

    public void spawnRandomMonsters(RpgGame game, World world){
        for (TiledMapTileMapObject enemyTile : EnemyTiles) {
            switch(monsterTypes.get(monsterGenerator.nextInt(monsterTypes.size()))){
                case SCARAB: new Scarab(game, world, (int)enemyTile.getX(), (int)enemyTile.getY(), aiController.getRandomMovementAI(), aiController.getRandomAttackAI());
            }
        }
    }

    //This will spawn presets instead of random movmement and random Attack.
    public void spawnPresetMonster(RpgGame game, World world, TiledMapTileMapObject enemyTile, MonsterType monsterType){
        switch(monsterType){
            case SCARAB: Scarab scarab = new Scarab(game, world, (int)enemyTile.getX(), (int)enemyTile.getY(), aiController.getRandomMovementAI(), aiController.getRandomAttackAI());
        }
    }

    public void spawnSpecificMonster(RpgGame game, World world, TiledMapTileMapObject enemyTile, MonsterType monsterType, MovementAI movementAI, AttackAI attackAI){
        switch(monsterType){
            case SCARAB: Scarab scarab = new Scarab(game, world, (int)enemyTile.getX(), (int)enemyTile.getY(), movementAI, attackAI);
        }
    }

    public void spawnBoss(RpgGame game, World world, TiledMapTileMapObject enemyTile, BossType bossType){
        switch(bossType){
            case BABI: Babi babi = new Babi(game, world, (int)enemyTile.getX(), (int)enemyTile.getY(), aiController);
        }
    }
}
