package dadm.scaffold.engine;

import android.content.Context;
import android.media.MediaPlayer;

public class Audio {
    private MediaPlayer player;
    private Context context;
    private int id;

    public Audio(Context soundContext, int sound){
        this.context = soundContext;
        this.id = sound;
    }
    //Crea un nuevo reproductor en caso de que no exista y lo reproduce
    public void play(){
        if (player == null){
            player = MediaPlayer.create(context, id);
        }
        player.start();
    }

    //Detiene la reproducción y libera los recursos
    public void stop (){
        if (player != null){
            player.release();
            player = null;
        }
    }


}
