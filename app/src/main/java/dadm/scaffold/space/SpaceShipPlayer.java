package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;

public class  SpaceShipPlayer extends Sprite {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 20;
    private static final long TIME_BETWEEN_BULLETS = 250;
    List<Bullet> bullets = new ArrayList<Bullet>();
    private long timeSinceLastFire;

    private double posX;
    private double posY;
    private int maxX;
    private int maxY;
    private double speedFactor;

    private int lifes;

    private int points;


    public SpaceShipPlayer(GameEngine gameEngine, int shipId){

        super(gameEngine, shipId);
        speedFactor = pixelFactor * 100d / 1000d; // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width/2 - width;
        maxY = gameEngine.height - height;

        lifes = 3;

        initBulletPool(gameEngine);
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine));
        }
    }

    private Bullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    void releaseBullet(Bullet bullet) {
        bullets.add(bullet);
    }


    @Override
    public void startGame() {
        positionX = maxX / 2;
        positionY = maxY / 2;
        this.posX = positionX;
        this.posY = positionY;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        // Get the info from the inputController
        updatePosition(elapsedMillis, gameEngine.theInputController);
        checkFiring(elapsedMillis, gameEngine);
    }

    private void updatePosition(long elapsedMillis, InputController inputController) {
        positionX += speedFactor * inputController.horizontalFactor * elapsedMillis;
        if (positionX < 0) {
            positionX = 0;
        }
        if (positionX > maxX) {
            positionX = maxX;
        }
        positionY += speedFactor * inputController.verticalFactor * elapsedMillis;
        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }
        this.posX = positionX;
        this.posY = positionY;
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (gameEngine.theInputController.tripleShoot && timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            Bullet[] bullet = {getBullet(), getBullet(), getBullet(), getBullet(), getBullet(), getBullet()};
            for(Bullet b : bullet){
                if(b != null) {
                    b.init(this, gameEngine, positionX + width / 2, positionY, (float) (gameEngine.random.nextDouble() * Math.PI / 6d - Math.PI / 12d));
                    gameEngine.addGameObject(b);
                }
            }
            gameEngine.theInputController.tripleShoot = false;
            timeSinceLastFire = 0;
        }
        else if (gameEngine.theInputController.isFiring && timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            Bullet bullet = getBullet();
            if (bullet == null) {
                return;
            }
            bullet.init(this, gameEngine, positionX + width/2, positionY, 0);
            gameEngine.addGameObject(bullet);
            timeSinceLastFire = 0;
        }
        else {
            timeSinceLastFire += elapsedMillis;
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Asteroid) {
            //gameEngine.stopGame();
            gameEngine.crashSound.play();
            Asteroid a = (Asteroid) otherObject;
            a.removeObject(gameEngine);
            lifes--;
        } else if (otherObject instanceof Enemy) {
            //gameEngine.stopGame();
            gameEngine.crashSound.play();
            Enemy e = (Enemy) otherObject;
            e.removeObject(gameEngine);
            lifes--;
        }
        else if (otherObject instanceof EnemyBullet) {
            //gameEngine.stopGame();
            gameEngine.crashSound.play();
            EnemyBullet e = (EnemyBullet) otherObject;
            e.removeObject(gameEngine);
            lifes--;
        }
        if(lifes < 0){
            gameEngine.fragment.gameOver();
        }
    }

    public boolean die(){
        if(lifes > 0){
            lifes--;
            return true;
        }
        else{
            return false;
        }
    }

    public int getLifes(){
        return lifes;
    }

    public int getPoints(){
        return points;
    }
    public void addPoints(int amount, GameEngine gameEngine){
        points += amount;
        if(points > 500){
            gameEngine.fragment.gameOver();
        }
    }
}
