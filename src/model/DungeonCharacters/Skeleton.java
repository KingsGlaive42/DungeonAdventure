package model.DungeonCharacters;

import model.AnimationSystem.Animation;
import model.AnimationSystem.Sprite;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;

public class Skeleton extends Monster implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final String SKELETON_SS_IDLE = "src/resources/assets/Monsters/skeleton_sprite_idle.png";
    private static final String SKELETON_SS_ATTACK = "src/resources/assets/Monsters/skeleton_sprite_attack.png";
    private static final String SKELETON_SS_DEATH = "src/resources/assets/Monsters/skeleton_sprite_death.png";
    private static final String SKELETON_SS_HIT = "src/resources/assets/Monsters/skeleton_sprite_hit.png";

    private static final int IDLE_SPRITES = 5;
    private static final int ATTACK_SPRITES = 9;
    private static final int DEATH_SPRITES = 12;
    private static final int HIT_SPRITES = 6;

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

    public Skeleton(int theHitPoints, int theMinDamage,
                    int theMaxDamage, int theAttackSpeed, double theChanceToHit, double theHealChance,
                    int theMinHeal, int theMaxHeal) {
        super("Skeleton",theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit,
                theHealChance, theMinHeal, theMaxHeal);
        loadSprites();
        initializeAnimations();
        lastUpdate = System.currentTimeMillis();
    }

    private void loadSprites() {
        idleSpriteLoader = new Sprite();
        idleSpriteLoader.loadSprite(SKELETON_SS_IDLE);

        attackSpriteLoader = new Sprite();
        attackSpriteLoader.loadSprite(SKELETON_SS_ATTACK);

        deathSpriteLoader = new Sprite();
        deathSpriteLoader.loadSprite(SKELETON_SS_DEATH);

        hitSpriteLoader = new Sprite();
        hitSpriteLoader.loadSprite(SKELETON_SS_HIT);
    }

    private BufferedImage getIdleSprite(int theFrameIndex) {
        if (theFrameIndex < 0 || theFrameIndex >= IDLE_SPRITES) {
            throw new IllegalArgumentException("Invalid frame for sprite");
        }
        return idleSpriteLoader.getSprite(theFrameIndex,0, 128, 128);
    }

    private void initializeAnimations() {
        BufferedImage[] idleFrames = new BufferedImage[IDLE_SPRITES];
        for (int i = 0; i < IDLE_SPRITES; i++) {
            idleFrames[i] = idleSpriteLoader.getSprite(i, 0, 128, 128);
        }
        idleAnimation = new Animation(idleFrames, IDLE_SPRITES);
        idleAnimation.start();

        BufferedImage[] attackFrames = new BufferedImage[ATTACK_SPRITES];
        for (int i = 0; i < ATTACK_SPRITES; i++) {
            attackFrames[i] = attackSpriteLoader.getSprite(i, 0, 128, 128);
        }
        attackAnimation = new Animation(attackFrames, 7);
        attackAnimation.start();

        BufferedImage[] deathFrames = new BufferedImage[DEATH_SPRITES];
        for (int i = 0; i < DEATH_SPRITES; i++) {
            deathFrames[i] = deathSpriteLoader.getSprite(i, 0, 128, 128);
        }
        deathAnimation = new Animation(deathFrames, 6);
        deathAnimation.start();

        BufferedImage[] hitFrames = new BufferedImage[HIT_SPRITES];
        for (int i = 0; i < HIT_SPRITES; i++) {
            hitFrames[i] = hitSpriteLoader.getSprite(i, 0, 128, 128);
        }
        hitAnimation = new Animation(hitFrames, HIT_SPRITES);
        hitAnimation.start();
    }

    @Override
    public BufferedImage getSprite() {
        // return animation based on state
        long currT = System.currentTimeMillis();
        long eT = currT - lastUpdate;

        if (eT >= FT) {
            idleAnimation.update();
            lastUpdate = currT;
        }
        return idleAnimation.getSprite();
    }

    /**
     * Custom deserialization method to restore transient fields.
     *
     * @param in The ObjectInputStream used to read the object.
     * @throws IOException If an I/O error occurs.
     * @throws ClassNotFoundException If the class cannot be found.
     */
    @Serial
    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        //System.out.println("Deserialized Room object.");

        loadSprites();
        //System.out.println("Reloaded sprite sheets.");

        initializeAnimations();
        //System.out.println("Initialized animations.");
    }
}
