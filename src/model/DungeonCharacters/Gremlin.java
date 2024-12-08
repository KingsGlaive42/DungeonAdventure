package model.DungeonCharacters;

import model.AnimationSystem.Animation;
import model.AnimationSystem.Sprite;

import java.awt.image.BufferedImage;

public class Gremlin extends Monster {
    private static final String GREMLIN_SS_IDLE = "src/resources/assets/Monsters/gremlin_sprite_idle.png";
    private static final String GREMLIN_SS_ATTACK = "src/resources/assets/Monsters/gremlin_sprite_attack.png";
    private static final String GREMLIN_SS_DEATH = "src/resources/assets/Monsters/gremlin_sprite_death.png";
    private static final String GREMLIN_SS_HIT = "src/resources/assets/Monsters/gremlin_sprite_hit.png";

    private static final int IDLE_ATTACK_SPRITES = 8;
    private static final int DEATH_SPRITES = 16;
    private static final int HIT_SPRITES = 3;

    private Sprite idleSpriteLoader;
    private Sprite attackSpriteLoader;
    private Sprite deathSpriteLoader;
    private Sprite hitSpriteLoader;
    private Animation idleAnimation;
    private Animation attackAnimation;
    private Animation deathAnimation;
    private Animation hitAnimation;

    private long lastUpdate;
    private final long FT = 1000 / 60;

    public Gremlin(int theHitPoints, int theMinDamage,
                   int theMaxDamage, int theAttackSpeed, double theChanceToHit, double theHealChance,
                   int theMinHeal, int theMaxHeal) {
        super("Gremlin", theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit,
                theHealChance, theMinHeal, theMaxHeal);
        loadSprites();
        initializeAnimations();
        lastUpdate = System.currentTimeMillis();
    }

    private void loadSprites() {
        idleSpriteLoader = new Sprite();
        idleSpriteLoader.loadSprite(GREMLIN_SS_IDLE);

        attackSpriteLoader = new Sprite();
        attackSpriteLoader.loadSprite(GREMLIN_SS_ATTACK);

        deathSpriteLoader = new Sprite();
        deathSpriteLoader.loadSprite(GREMLIN_SS_DEATH);

        hitSpriteLoader = new Sprite();
        hitSpriteLoader.loadSprite(GREMLIN_SS_HIT);
    }

    private void initializeAnimations() {
        BufferedImage[] idleFrames = new BufferedImage[IDLE_ATTACK_SPRITES];
        for (int i = 0; i < IDLE_ATTACK_SPRITES; i++) {
            idleFrames[i] = idleSpriteLoader.getSprite(i, 0, 64, 64);
        }
        idleAnimation = new Animation(idleFrames, 6);
        idleAnimation.start();

        BufferedImage[] attackFrames = new BufferedImage[IDLE_ATTACK_SPRITES];
        for (int i = 0; i < IDLE_ATTACK_SPRITES; i++) {
            attackFrames[i] = attackSpriteLoader.getSprite(i, 0, 64, 64);
        }
        attackAnimation = new Animation(attackFrames, 6);
        attackAnimation.start();

        BufferedImage[] deathFrames = new BufferedImage[DEATH_SPRITES];
        for (int i = 0; i < DEATH_SPRITES; i++) {
            deathFrames[i] = deathSpriteLoader.getSprite(i, 0, 64, 64);
        }
        deathAnimation = new Animation(deathFrames, 6);
        deathAnimation.start();

        BufferedImage[] hitFrames = new BufferedImage[HIT_SPRITES];
        for (int i = 0; i < HIT_SPRITES; i++) {
            hitFrames[i] = hitSpriteLoader.getSprite(i, 0, 64, 64);
        }
        hitAnimation = new Animation(hitFrames, 8);
        hitAnimation.start();
    }

    @Override
    public BufferedImage getSprite() {
        long currT = System.currentTimeMillis();
        long eT = currT - lastUpdate;

        if (eT >= FT) {
            idleAnimation.update();
            lastUpdate = currT;
        }
        return idleAnimation.getSprite();
    }
}
