package main.states;

public class GameSetting
{
    public static boolean gameDifficulty = false;
    public int normalZombieAttackDelay = 50;
    public int zombieSpawnTimer = 70;
    public int zombiePerSpawn = 1;
    public float zoom = 1.5f;

    public int itemChance = 100;

    public float playerMovementSpeed = 1.25f;

    public long boostDuration = 8000;

    public GameSetting()
    {
        if(gameDifficulty){
            normalZombieAttackDelay = 40;
            zombieSpawnTimer = 60;
            zombiePerSpawn = 2;
            boostDuration = 5000;
        }
    }
}
