package model.DungeonCharacters;

import controller.InputListener;
import controller.SoundManager;
import model.AnimationSystem.Animation;
import model.AnimationSystem.Sprite;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a Gremlin monster in the dungeon. This class includes properties for animations,
 * sprite sheets, and behavior such as cloning and sprite updates. The Gremlin uses animations
 * for idle, attack, death, and hit states.
 * @author Aileen
 */
public class Gremlin extends Monster implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // Sprite sheet file paths for various animations
    private static final String GREMLIN_SS_IDLE = "src/resources/assets/Monsters/gremlin_sprite_idle.png";
    private static final String GREMLIN_SS_ATTACK = "src/resources/assets/Monsters/gremlin_sprite_attack.png";
    private static final String GREMLIN_SS_DEATH = "src/resources/assets/Monsters/gremlin_sprite_death.png";
    private static final String GREMLIN_SS_HIT = "src/resources/assets/Monsters/gremlin_sprite_hit.png";

    // Number of frames for each animation type
    private static final int IDLE_ATTACK_SPRITES = 8;
    private static final int DEATH_SPRITES = 16;
    private static final int HIT_SPRITES = 3;

    // Sprite loaders for loading specific animations
    private Sprite idleSpriteLoader;
    private Sprite attackSpriteLoader;
    private Sprite deathSpriteLoader;
    private Sprite hitSpriteLoader;

    // Animations for different states of the Gremlin
    private Animation idleAnimation;
    private Animation attackAnimation;
    private Animation deathAnimation;
    private Animation hitAnimation;

    // Variables for controlling animation updates
    private long lastUpdate;
    private final long FT = 1000 / 60;

    /**
     * Constructs a Gremlin monster with specified properties.
     *
     * @param theHitPoints     The initial hit points of the Gremlin.
     * @param theMinDamage     The minimum damage dealt by the Gremlin.
     * @param theMaxDamage     The maximum damage dealt by the Gremlin.
     * @param theAttackSpeed   The attack speed of the Gremlin.
     * @param theChanceToHit   The chance for the Gremlin to hit its target.
     * @param theHealChance    The chance for the Gremlin to heal.
     * @param theMinHeal       The minimum heal amount.
     * @param theMaxHeal       The maximum heal amount.
     */
    public Gremlin(int theHitPoints, int theMinDamage,
                   int theMaxDamage, int theAttackSpeed, double theChanceToHit, double theHealChance,
                   int theMinHeal, int theMaxHeal) {
        super("Gremlin", theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit,
                theHealChance, theMinHeal, theMaxHeal);
        loadSprites();
        initializeAnimations();
        lastUpdate = System.currentTimeMillis();
    }

    /**
     * Loads the sprite sheets for the Gremlin's animations.
     */
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

    /**
     * Initializes the animations for the Gremlin, including idle, attack, death, and hit states.
     */
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

    /**
     * Updates and returns the current frame of the idle animation.
     *
     * @return The current sprite of the idle animation.
     */
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

    /**
     * Creates a clone of this Gremlin instance.
     *
     * @return A new Gremlin instance with the same properties.
     */
    @Override
    public Monster cloneMonster() {
        return new Gremlin(
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
