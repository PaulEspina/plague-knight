package main.states;

import main.Config;
import main.Game;
import main.entity.Crate;
import main.entity.Item;
import main.Vector2f;
import main.entity.enemy.Zombie;
import main.input.MouseManager;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;

public class GameState extends State {
    private Game game;
    private Crate crate;
    private String imgPath = "/assets/sprites/world/crate/cratev1.png";
    private Item item;
    private String itemPath = "/assets/items/boosts/boosts.png";

    private double max = 5;
    private double counter = 0;
  
    private Zombie zombie;
    private final MouseManager mouseManager;

    public GameState(Game game) {
        this.game = game;

    public GameState(Game game)
    {
        this.game = game;

        mouseManager = game.getMouseManager();

        zombie = new Zombie(new Vector2f((float) Config.SCREEN_WIDTH / 2, (float) Config.SCREEN_HEIGHT / 2),
                            new Vector2f(Config.ZOMBIE_SPRITE_WIDTH, Config.ZOMBIE_SPRITE_HEIGHT));
        
        crate = new Crate(new Point(300, 100), new Point(100, 100));
        crate.loadTexture(new Point(0, 0), new Point(78, 67), imgPath);
        item = new Item(new Point(300,100), new Point(100,100));
    }


    private double maxFrame = 20;
    private double deltaCounter = 0;
    @Override
    public void tick()
    {
        Vector2f mouse = new Vector2f(mouseManager.getMouseX(), mouseManager.getMouseY());
        zombie.follow(mouse, 2);

        deltaCounter += game.getDeltaPlease();
        if(deltaCounter >= maxFrame){
            zombie.animate();
            deltaCounter = 0;
        }
        zombie.update();
      
        if(crate.isInside(mouse.getX(),mouse.getY())){
//            System.out.println(x + y);
            if (game.getMouseManager().getMouseButtonState(MouseEvent.BUTTON1)) {
//              Animate button
                crate.setImage(crate.getClickedBox());
                delay(2);
            }
        }
    }


    @Override
    public void render(Graphics g)
    {
        zombie.draw(g);
  
        g.drawImage(crate.getImage(),
                    (int) crate.getPos().getX(),
                    (int) crate.getPos().getY(),
                    (int) crate.getSize().getX(),
                    (int) crate.getSize().getY(),
                    null);

        g.drawImage(item.getImage(),
                    (int) item.getPos().getX(),
                    (int) item.getPos().getY(),
                    (int) item.getSize().getX(),
                    (int) item.getSize().getY(),
                    null);
    }

    public void delay(float delta){
        counter += delta;
        System.out.println(delta);
        if(counter >= max){
            item.loadTexture(new Point(7,7), new Point(40,40), itemPath);
            crate.randomDrop();
        }
    }
}