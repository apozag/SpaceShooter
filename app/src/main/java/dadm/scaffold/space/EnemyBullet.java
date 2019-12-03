package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

public class EnemyBullet extends Sprite {

    private double speedFactor;

    private double speedX;
    private double speedY;


    public EnemyBullet(GameEngine gameEngine){
        super(gameEngine, R.drawable.laser_enemy);

        speedFactor = gameEngine.pixelFactor *300d / 1000d;
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionX -= speedX * elapsedMillis;
        positionY -= speedY * elapsedMillis;
        if (positionX < 0) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
        }
    }



    public void init(Enemy parentPlayer, double initPositionX, double initPositionY, float angle) {
        positionX = initPositionX - width*4;
        positionY = initPositionY + height/2;
        speedX = speedFactor * Math.cos(angle);
        speedY = speedFactor * Math.sin(angle);
        rotation = angle;
    }

    public void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        // And return it to the pool
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
