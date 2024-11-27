package model.DungeonCharacters;

import model.AnimationSystem.Sprite;

import java.awt.image.BufferedImage;

public class Gremlin extends Monster {
    private static final String GREMLIN_SS = "src/resources/assets/Monsters/gremlin_sprite64.png";
    private static final int IDLE_ROW = 0;
    private static final int IDLE_SPRITES = 4;

    private Sprite spriteLoader;

    public Gremlin(int theHitPoints, int theMinDamage,
                   int theMaxDamage, int theAttackSpeed, double theChanceToHit, double theHealChance,
                   int theMinHeal, int theMaxHeal) {
        super("Gremlin", theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit,
                theHealChance, theMinHeal, theMaxHeal);
        loadSpriteSheet();
    }

    private void loadSpriteSheet() {
        spriteLoader = new Sprite();
        spriteLoader.loadSprite(GREMLIN_SS);
    }

    private BufferedImage getIdleSprite(int theFrameIndex) {
        if (theFrameIndex < 0 || theFrameIndex >= IDLE_SPRITES) {
            throw new IllegalArgumentException("Invalid frame for sprite");
        }
        return spriteLoader.getSprite(theFrameIndex,IDLE_ROW, 64);
    }

    @Override
    public BufferedImage getSprite() {
        int frameIndex = 0;
        return getIdleSprite(frameIndex);
    }
}
