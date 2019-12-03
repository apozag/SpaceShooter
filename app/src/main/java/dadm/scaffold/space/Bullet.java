package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

public class Bullet extends Sprite {

    private double speedFactor;

    private double speedX;
    private double speedY;

    private SpaceShipPlayer parent;
    private GameEngine gameEngine;

    public Bullet(GameEngine gameEngine){
        super(gameEngine, R.drawable.laser);

        speedFactor = gameEngine.pixelFactor * -300d / 1000d;
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionX -= speedX * elapsedMillis;
        positionY -= speedY * elapsedMillis;
        if (positionX > gameEngine.width) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBullet(this);
        }
    }



    public void init(SpaceShipPlayer parentPlayer, GameEngine gameEngine, double initPositionX, double initPositionY, float angle) {
        gameEngine.bulletSound.play();
        positionX = initPositionX + width/2;
        positionY = initPositionY +  height/2;
        speedX = speedFactor * Math.cos(angle);
        speedY = speedFactor * Math.sin(angle);
        parent = parentPlayer;
        rotation = angle;
    }

    private void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        // And return it to the pool
        parent.releaseBullet(this);
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Asteroid) {
            // Remove both from the game (and return them to their pools)
            removeObject(gameEngine);
            Asteroid a = (Asteroid) otherObject;
            a.removeObject(gameEngine);
            // Add some score
        }else if (otherObject instanceof Enemy) {
            // Remove both from the game (and return them to their pools)
            removeObject(gameEngine);
            Enemy e = (Enemy) otherObject;
            e.removeObject(gameEngine);
            // Add some score
        }
    }
}
