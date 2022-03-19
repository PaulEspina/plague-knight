package main.states;

public class GameSetting
{
    public static boolean gameDifficulty = false;
    public int normalZombieAttackDelay = 50;
    public int zombieSpawnTimer = 70;
    public int zombiePerSpawn = 1;
    public float zoom = 1.5f;

    public int bossAttackDelay = 70;
    public int bossAttackCooldownDelay = 100;
    public int bossSpawnTimer = 2500;
    public int bossPerSpawn = 1;

    public int itemChance = 100;

    public float playerMovementSpeed = 1.25f;

    public long boostDuration = 8000;

    public GameSetting()
    {
        if(gameDifficulty){
            gameDifficulty = false;
            normalZombieAttackDelay = 40;
            zombieSpawnTimer = 60;
            zombiePerSpawn = 2;
            boostDuration = 5000;

            bossAttackDelay = 50;
            bossSpawnTimer = 2250;
            bossPerSpawn = 1;
        }
    }
}
