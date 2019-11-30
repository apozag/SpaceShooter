package dadm.scaffold.space;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;

public class GameController extends GameObject {

    private static final int TIME_BETWEEN_ASTEROIDS = 500;
    private static final int TIME_BETWEEN_ENEMIES = 1000;
    private long currentMillis;
    private List<Asteroid> asteroidPool = new ArrayList<Asteroid>();
    private List<Enemy> enemyPool = new ArrayList<Enemy>();
    private int asteroidsSpawned;
    public int enemiesSpawned;

    public GameController(GameEngine gameEngine) {
        // We initialize the pool of items now
        for (int i=0; i<10; i++) {
            asteroidPool.add(new Asteroid(this, gameEngine));
        }
        for (int j=0; j<5; j++){
            enemyPool.add(new Enemy(this, gameEngine));
        }
    }

    @Override
    public void startGame() {
        currentMillis = 0;
        asteroidsSpawned = 0;
        enemiesSpawned = 0;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        currentMillis += elapsedMillis;

        long waveTimestamp = asteroidsSpawned *TIME_BETWEEN_ASTEROIDS;
        if (currentMillis > waveTimestamp && !asteroidPool.isEmpty()) {
            // Spawn a new enemy
            Asteroid a = asteroidPool.remove(0);
            a.init(gameEngine);
            gameEngine.addGameObject(a);
            asteroidsSpawned++;
        }

        long waveEnemyTime = enemiesSpawned * TIME_BETWEEN_ENEMIES;
        if (currentMillis > waveEnemyTime && !enemyPool.isEmpty()) {
            Enemy e = enemyPool.remove(0);
            e.init(gameEngine);
            gameEngine.addGameObject(e);
            enemiesSpawned++;
        } else {
            System.out.println(enemyPool.size());
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        // This game object does not draw anything
    }

    public void returnToPool(Asteroid asteroid) {
        asteroidPool.add(asteroid);
    }

    public void returnToEnemyPool(Enemy enemy) {
        enemyPool.add(enemy);
    }
}
