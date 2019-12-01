package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

public class Enemy extends Sprite {

    private final GameController gameController;

    private double speed;
    private double speedX;
    private double speedY;
    private double TargetX;
    private double TargetY;


    public Enemy(GameController gameController, GameEngine gameEngine) {
        super(gameEngine, R.drawable.alien);
        this.speed = 150d * pixelFactor/1000d;
        this.gameController = gameController;
    }

    public void init(GameEngine gameEngine) {
        // They initialize in a [-30, 30] degrees angle
        double angle = gameEngine.random.nextDouble()*Math.PI/6d-Math.PI/12d;
        speedX = -speed * Math.cos(angle);
        speedY = speed * Math.sin(angle);
        // Enemies initialize in the central 50% of the screen horizontally
        positionX =  gameEngine.width;
        // They initialize outside of the screen horizontally
        positionY = gameEngine.random.nextInt(gameEngine.height/2)+gameEngine.height/4;
    }

    public void removeObject(GameEngine gameEngine) {
        // Return to the pool
        gameEngine.removeGameObject(this);
        gameController.returnToEnemyPool(this);
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        removeObject(gameEngine);
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        TargetX = gameEngine.getSpaceShipPlayer().getPosX();
        TargetY = gameEngine.getSpaceShipPlayer().getPosY();

        double distanceX = TargetX - positionX;
        double distanceY = TargetY - positionY;
        double normalizedX = distanceX / (distanceX + distanceY);
        double normalizedY = distanceY / (distanceX + distanceY);

        speedX = -speed * normalizedX;
        speedY = -speed * normalizedY;

        positionX += speedX * elapsedMillis;
        positionY += speedY * elapsedMillis;

        // Check if the sprite goes out of the screen and return it to the pool if so
        if (positionX < 0) {
            // Return to the pool
            gameEngine.removeGameObject(this);
            gameController.returnToEnemyPool(this);
        }
    }
}
