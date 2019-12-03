package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

public class Star extends Sprite {

    private final GameController gameController;
    private double speed;

    public Star(GameController gameController, GameEngine gameEngine) {
        super(gameEngine, R.drawable.star);
        int r = gameEngine.random.nextInt(2) + 2;
        this.speed = 50d * pixelFactor/1000d * r;
        this.gameController = gameController;
        this.pixelFactor *= gameEngine.random.nextInt(1) + 0.1;
    }

    public void init(GameEngine gameEngine) {

        positionX =  gameEngine.width;
        positionY = gameEngine.random.nextInt(gameEngine.height);
    }

    public void removeObject(GameEngine gameEngine) {
        // Return to the pool
        gameEngine.removeGameObject(this);
        gameController.returnToStarPool(this);
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

        positionX -= speed * elapsedMillis;


        // Check if the sprite goes out of the screen and return it to the pool if so
        if (positionX < 0) {
            // Return to the pool
            gameEngine.removeGameObject(this);
            gameController.returnToStarPool(this);
        }
    }
}

