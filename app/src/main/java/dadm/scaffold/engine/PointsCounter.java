package dadm.scaffold.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import dadm.scaffold.space.SpaceShipPlayer;

public class PointsCounter extends GameObject {

    private final float textWidth;
    private final float textHeight;

    private Paint paint;
    private long totalMillis;
    private int draws;
    private SpaceShipPlayer player;

    private String pointsCounter = "";

    public PointsCounter(GameEngine gameEngine, SpaceShipPlayer player){
        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        textHeight = (float) (50 * gameEngine.pixelFactor);
        textWidth = (float) (100 * gameEngine.pixelFactor);
        paint.setTextSize(textHeight / 2);
        this.player = player;
    }
    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        canvas.drawText("points: " + player.getPoints(), textWidth / 2, (int) (canvas.getHeight() / 3), paint);
        draws++;
    }
}
