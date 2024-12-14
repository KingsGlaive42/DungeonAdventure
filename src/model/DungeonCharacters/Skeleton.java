package model.DungeonCharacters;

import model.AnimationSystem.Animation;
import model.AnimationSystem.Sprite;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a Skeleton monster in the game.
 * This class extends the Monster abstract class and includes animations and sprite management
 * for various actions such as idle, attack, death, and hit states.
 */
public class Skeleton extends Monster implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** File paths for Skeleton spritesheets. */
    private static final String SKELETON_SS_IDLE = "src/resources/assets/Monsters/skeleton_sprite_idle.png";
    private static final String SKELETON_SS_ATTACK = "src/resources/assets/Monsters/skeleton_sprite_attack.png";
    private static final String SKELETON_SS_DEATH = "src/resources/assets/Monsters/skeleton_sprite_death.png";
    private static final String SKELETON_SS_HIT = "src/resources/assets/Monsters/skeleton_sprite_hit.png";

    /** Number of frames for each animation state. */
    private static final int IDLE_SPRITES = 5;
    private static final int ATTACK_SPRITES = 9;
    private static final int DEATH_SPRITES = 12;
    private static final int HIT_SPRITES = 6;

    /** Sprite loaders for each animation state. */
    private Sprite idleSpriteLoader;
    private Sprite attackSpriteLoader;
    private Sprite deathSpriteLoader;
    private Sprite hitSpriteLoader;

    /** Animations for each state. */
    private Animation idleAnimation;
    private Animation attackAnimation;
    private Animation deathAnimation;
    private Animation hitAnimation;

    /**
     * Tracks the last update time for animations.
     */
    private long lastUpdate;

    /**
     * Frame time for animation updates (milliseconds per frame).
     */
    private final long FT = 1000 / 60;

    /**
     * Constructs a Skeleton monster with the specified attributes.
     *
     * @param theHitPoints    The hit points of the Skeleton.
     * @param theMinDamage    The minimum damage the Skeleton can deal.
     * @param theMaxDamage    The maximum damage the Skeleton can deal.
     * @param theAttackSpeed  The attack speed of the Skeleton.
     * @param theChanceToHit  The chance to hit for the Skeleton.
     * @param theHealChance   The chance for the Skeleton to heal.
     * @param theMinHeal      The minimum amount of healing.
     * @param theMaxHeal      The maximum amount of healing.
     */
    public Skeleton(int theHitPoints, int theMinDamage,
                    int theMaxDamage, int theAttackSpeed, double theChanceToHit, double theHealChance,
                    int theMinHeal, int theMaxHeal) {
        super("Skeleton",theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit,
                theHealChance, theMinHeal, theMaxHeal);
        loadSprites();
        initializeAnimations();
        lastUpdate = System.currentTimeMillis();
    }

    /**
     * Loads the sprite sheets for the Skeleton's animations.
     */
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

    /**
     * Initializes the animations for the Skeleton's various states.
     */
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

    /**
     * Returns the current sprite based on the Skeleton's state.
     *
     * @return The current frame of the idle animation.
     */
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
     * Clones the current Skeleton instance.
     *
     * @return A new Skeleton instance with the same attributes as the current one.
     */
    @Override
    public Monster cloneMonster() {
        return new Skeleton(
                this.getHitPoints(),
                this.getMinDamage(),
                this.getMaxDamage(),
                this.getAttackSpeed(),
                this.getChanceToHit(),
                this.getMyHealChance(),
                this.getMyMinHeal(),
                this.getMyMaxHeal()
        );
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
