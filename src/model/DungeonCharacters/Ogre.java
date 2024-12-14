package model.DungeonCharacters;

import model.AnimationSystem.Animation;
import model.AnimationSystem.Sprite;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
/**
 * The Ogre class represents a specific type of Monster in the game.
 * It includes animations, sprite loading, and a clone method for creating new Ogre instances.
 * @author Aileen
 */

public class Ogre extends Monster implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // File paths for sprite sheets
    private static final String OGRE_SS_IDLE = "src/resources/assets/Monsters/ogre_sprite_idle.png";
    private static final String OGRE_SS_ATTACK = "src/resources/assets/Monsters/ogre_sprite_attack.png";
    private static final String OGRE_SS_DEATH = "src/resources/assets/Monsters/ogre_sprite_death.png";
    private static final String OGRE_SS_HIT = "src/resources/assets/Monsters/ogre_sprite_hit.png";

    // Frame counts for animations
    private static final int IDLE_ATTACK_SPRITES = 8;
    private static final int DEATH_SPRITES = 10;
    private static final int HIT_SPRITES = 6;

    // Frame time / update
    private long lastUpdate;
    private final long FT = 1000 / 60;

    // Sprites and animations
    private Sprite idleSpriteLoader;
    private Sprite attackSpriteLoader;
    private Sprite deathSpriteLoader;
    private Sprite hitSpriteLoader;
    private Animation idleAnimation;
    private Animation attackAnimation;
    private Animation deathAnimation;
    private Animation hitAnimation;

    /**
     * Constructs an Ogre with the specified attributes.
     *
     * @param theHitPoints    The initial hit points of the Ogre.
     * @param theMinDamage    The minimum damage the Ogre can deal.
     * @param theMaxDamage    The maximum damage the Ogre can deal.
     * @param theAttackSpeed  The attack speed of the Ogre.
     * @param theChanceToHit  The chance of successfully hitting an opponent.
     * @param theHealChance   The chance of the Ogre healing itself after taking damage.
     * @param theMinHeal      The minimum amount of health the Ogre can heal.
     * @param theMaxHeal      The maximum amount of health the Ogre can heal.
     */
    public Ogre(int theHitPoints, int theMinDamage,
                int theMaxDamage, int theAttackSpeed, double theChanceToHit, double theHealChance,
                int theMinHeal, int theMaxHeal) {
        super("Ogre", theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit,
                theHealChance, theMinHeal, theMaxHeal);
        loadSprites();
        initializeAnimations();
        lastUpdate = System.currentTimeMillis();
    }

    /**
     * Loads sprite sheets for the Ogre's animations.
     */
    private void loadSprites() {
        idleSpriteLoader = new Sprite();
        idleSpriteLoader.loadSprite(OGRE_SS_IDLE);

        attackSpriteLoader = new Sprite();
        attackSpriteLoader.loadSprite(OGRE_SS_ATTACK);

        deathSpriteLoader = new Sprite();
        deathSpriteLoader.loadSprite(OGRE_SS_DEATH);

        hitSpriteLoader = new Sprite();
        hitSpriteLoader.loadSprite(OGRE_SS_HIT);
    }

    /**
     * Initializes animations for the Ogre using the loaded sprite sheets.
     */
    private void initializeAnimations() {

        BufferedImage[] idleFrames = new BufferedImage[IDLE_ATTACK_SPRITES];
        for (int i = 0; i < IDLE_ATTACK_SPRITES; i++) {
            idleFrames[i] = idleSpriteLoader.getSprite(i, 0, 128, 128);
        }
        idleAnimation = new Animation(idleFrames, IDLE_ATTACK_SPRITES);
        idleAnimation.start();

        BufferedImage[] attackFrames = new BufferedImage[IDLE_ATTACK_SPRITES];
        for (int i = 0; i < IDLE_ATTACK_SPRITES; i++) {
            attackFrames[i] = attackSpriteLoader.getSprite(i, 0);
        }
        attackAnimation = new Animation(attackFrames, IDLE_ATTACK_SPRITES);
        attackAnimation.start();

        BufferedImage[] deathFrames = new BufferedImage[DEATH_SPRITES];
        for (int i = 0; i < DEATH_SPRITES; i++) {
            deathFrames[i] = deathSpriteLoader.getSprite(i, 0);
        }
        deathAnimation = new Animation(deathFrames, DEATH_SPRITES);
        deathAnimation.start();

        BufferedImage[] hitFrames = new BufferedImage[HIT_SPRITES];
        for (int i = 0; i < HIT_SPRITES; i++) {
            hitFrames[i] = hitSpriteLoader.getSprite(i, 0);
        }
        hitAnimation = new Animation(hitFrames, HIT_SPRITES);
        hitAnimation.start();
    }

    /**
     * Retrieves the current sprite based on the Ogre's state and animation.
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
     * Creates a copy of the Ogre with the same attributes.
     *
     * @return A new instance of Ogre with identical attributes.
     */
    @Override
    public Monster cloneMonster() {
        return new Ogre(
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
