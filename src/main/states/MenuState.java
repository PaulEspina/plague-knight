package main.states;

import main.Config;
import main.Game;
import main.crop.Button;
import main.crop.Screen;
import main.crop.Speaker;
import main.gfx.AssetManager;
import main.gfx.Sound;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.Vector;

public class MenuState extends State
{
    private final Game game;
//     Create Buttons
    private final Button storyButton;
    private final Button survivalButton;
    private final Button exitButton;
    private final Button startButton;
    private final Button cancelButton;

//    For Blinking Lights
    private final Screen defaultBG;
    private final Screen dottedBG;
    private final Speaker speaker;

    private double flickerAnimation = 0;
    private double brokenSurvivalAnimation = 0;
    private double brokenStoryAnimation = 0;
    private double exitGameAnimation = 0;
    private final Random rand = new Random();
    private int random;

    //    Mouse Click
    private boolean survivalIsPressed = false;
    private boolean storyIsPressed = false;
    private boolean exitIsPressed = false;

    //    For brokendelay
    private boolean survivalNext = false;
    private boolean storyNext = false;
    private boolean exitNext = false;

    private final Vector<Sound> soundEffects;

    public MenuState(Game game)
    {
        this.game = game;
//        Coordinate in Frame
        survivalButton = new Button(new Point(364, 465), new Point(85, 50), 0, "survival");
        storyButton = new Button(new Point(480, 465), new Point(85, 50), 88, "story");
        exitButton = new Button(new Point(606, 465), new Point(85, 50), 264, "exit");
        startButton = new Button(new Point(435, 385), new Point(85, 50), 176, "start");
        cancelButton = new Button(new Point(548, 385), new Point(85, 50), 352, "cancel");

        dottedBG = new Screen(new Point(169, 69), new Point(463, 222), Config.DOTTED_BACKGROUND_ASSET_PATH, "dot");
        defaultBG = new Screen(new Point(169, 69), new Point(463, 222), Config.MENU_BACKGROUND_ASSET_PATH, "default");

        speaker = new Speaker(new Point(Config.SCREEN_WIDTH - 55, Config.SCREEN_HEIGHT - 55), new Point(50, 50), 336, 192, "speaker");

        game.getMenuBGM().loop();
        game.getSurvivalBGM().stop();

        soundEffects = game.getSoundEffects();
    }

    @Override
    public void tick() {
        int x = game.getMouseManager().getMouseX();
        int y = game.getMouseManager().getMouseY();

        speaker.unhoveredImage();

        startButton.disabledImage();
        cancelButton.disabledImage();

        flickerAnimation++;
        if(flickerAnimation % Config.FLICKER_ANIMATION_DELAY == 0){
            random = rand.nextInt(2);
            flickerAnimation = 0;
        }

//      NEXT STATE
        if(survivalIsPressed){
            brokenSurvivalAnimation++;
            if(brokenSurvivalAnimation % Config.BROKEN_SURVIVAL_ANIMATION_DELAY == 0){
//                game.getButtonPressSound().stop();
                survivalIsPressed = false;
                survivalNext = true;
                brokenSurvivalAnimation = Config.BROKEN_SURVIVAL_ANIMATION_DELAY;
            }
        }

        if(storyIsPressed){
            brokenStoryAnimation++;
            if(brokenStoryAnimation % Config.BROKEN_STORY_ANIMATION_DELAY == 0){
                storyIsPressed = false;
                storyNext = true;
                brokenStoryAnimation = Config.BROKEN_STORY_ANIMATION_DELAY;
            }
        }

        if(exitIsPressed){
            exitGameAnimation++;
            if(exitGameAnimation % Config.BUTTON_DELAY_ANIMATION == 0){
                exitIsPressed = false;
                exitNext = true;
                exitGameAnimation = Config.BUTTON_DELAY_ANIMATION;
            }
        }
        if(survivalButton.isInside(x, y)){
            survivalButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                Sound buttonPressed = new Sound(AssetManager.getInstance().getButtonPressFX());
                buttonPressed.play();
                soundEffects.add(buttonPressed);
                survivalButton.clickedImage();
                survivalIsPressed = true;
            }
        }
        else{
            survivalButton.unhoveredImage();
        }

        if(storyButton.isInside(x, y)){
            storyButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                Sound buttonPressed = new Sound(AssetManager.getInstance().getButtonPressFX());
                buttonPressed.play();
                soundEffects.add(buttonPressed);
                storyButton.clickedImage();
                storyIsPressed = true;
            }
        }
        else{
            storyButton.unhoveredImage();
        }
//        System.out.println(game.getDeltaPlease());
        if(exitButton.isInside(x, y)){
            exitButton.hoveredImage();
            //If button clicked
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
                Sound buttonPressed = new Sound(AssetManager.getInstance().getButtonPressFX());
                buttonPressed.play();
                soundEffects.add(buttonPressed);
                exitButton.clickedImage();
                exitIsPressed = true;
            }
        }
        else{
            exitButton.unhoveredImage();
        }

        if(speaker.isInside(x, y)){
            speaker.hoveredImage();
            if(game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)){
                Sound buttonPressed = new Sound(AssetManager.getInstance().getButtonPressFX());
                buttonPressed.play();
                soundEffects.add(buttonPressed);
                if(game.getMenuBGM().isPlaying())
                {
                    game.getMenuBGM().pause();
                }
                else
                {
                    game.getMenuBGM().play();
                }
            }
        }
        else{
            if(game.getMenuBGM().isPlaying())
            {
                speaker.unhoveredImage();
            }
            else
            {
                speaker.clickedImage();
            }
        }
    }

    @Override
    public void render(Graphics g)
    {
        g.drawImage(AssetManager.getInstance().getDefaultBGImage(), 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, null);
        survivalButton.draw(g);
        storyButton.draw(g);
        exitButton.draw(g);
        startButton.draw(g);
        cancelButton.draw(g);
        speaker.draw(g);

//      Animation in main menu
        if(random % 2 == 0){
            dottedBG.draw(g);
        }
        else{
            defaultBG.draw(g);
        }

//        Next state
        if(survivalNext){
            survivalNext = false;
            setState(new SurvivalMenuState(game));
        }

        if(storyNext){
            storyNext = false;
            setState(new StoryMenuState(game));
        }

        if(exitNext){
            exitNext = false;
            setState(new ExitState(game));
        }
    }
}
