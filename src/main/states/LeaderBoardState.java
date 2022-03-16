package main.states;

import main.Config;
import main.Game;
import main.crop.Button;
import main.crop.RankingHUD;
import main.crop.Screen;
import main.crop.Speaker;
import main.gfx.AssetManager;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class LeaderBoardState extends State{
    private Game game;
    private RankingHUD screenImage;
    private Button menuButton;
    private Speaker speaker;

    private boolean showRanking = true;



    public LeaderBoardState(Game game){
        this.game = game;

        menuButton = new Button(new Point(500, 465), new Point(90, 55), 440, "menu");
        screenImage = new RankingHUD(new Point(130, 50), new Point(540, 255), "ranking");
        speaker = new Speaker(new Point(Config.SCREEN_WIDTH - 55, Config.SCREEN_HEIGHT - 55), new Point(50, 50), 336, 192, "speaker");

    }

    private boolean menuPressed = false;
    private boolean returnMenu = false;
    private int menuAnimationCounter = 0;
    @Override
    public void tick() {
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();

        game.getInGameMusic().setSound(-80);
        game.getBackgroundMusic().setSound(-80);

        if(menuPressed){
            menuAnimationCounter++;
            if(menuAnimationCounter % Config.BUTTON_DELAY_ANIMATION == 0){
                returnMenu = true;
            }
        }
        if(menuButton.isInside(x, y)){
            menuButton.hoveredImage();
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)){
                menuButton.clickedImage();
                game.getButtonPressSound().setSound(-10);
                game.getButtonPressSound().play();
                game.getButtonPressSound().setFramePosition(0);
                menuPressed = true;
            }
        }
        else{
            menuButton.unhoveredImage();
        }

        if(speaker.isInside(x, y)){
            speaker.hoveredImage();
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)){
                game.getButtonPressSound().setSound(-10);
                game.getButtonPressSound().play();
                game.getButtonPressSound().setFramePosition(0);
                if(game.getBackgroundMusic().isPlaying())
                {
                    game.getBackgroundMusic().stop();
                }
                else
                {
                    game.getBackgroundMusic().play();
                }
            }
        }
        else{
            if(game.getBackgroundMusic().isPlaying())
            {
                speaker.unhoveredImage();
            }
            else
            {
                speaker.clickedImage();
            }
        }
        if(returnMenu){
            setState(new MenuState(game));
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(AssetManager.getInstance().getDefaultBGImage(), 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, null);
        screenImage.draw(g);
        menuButton.draw(g);
        speaker.draw(g);

//        if(showRanking){
//            showRanking = false;
            try {
                scoreReader(g);
            } catch (IOException e) {
                e.printStackTrace();
            }
//        }
    }

    public void scoreReader(Graphics g) throws IOException {

        File file = new File(Config.SCORES_PATH);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        ArrayList<Integer> storage = new ArrayList<>();
        while ((line = br.readLine()) != null)
            storage.add(Integer.valueOf(line));

        Collections.sort(storage, Collections.reverseOrder());

        g.setFont(AssetManager.getInstance().getArcadeClassicSmall());
        for(int i = 0; i < 7; i++){
            g.drawString("Rank " + (i + 1) + " ! " + String.valueOf(storage.get(i)), 200, 100 + (i * 30));
        }
    }
}
