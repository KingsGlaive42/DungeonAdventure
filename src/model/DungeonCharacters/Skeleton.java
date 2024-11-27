package model.DungeonCharacters;

import model.AnimationSystem.Sprite;

import java.awt.image.BufferedImage;

public class Skeleton extends Monster{
    private static final String SKELETON_SS = "src/resources/assets/Monsters/skeleton_sprite64.png";
    private static final int IDLE_ROW = 4;
    private static final int IDLE_SPRITES = 4;

    private Sprite spriteLoader;

    public Skeleton(int theHitPoints, int theMinDamage,
                    int theMaxDamage, int theAttackSpeed, double theChanceToHit, double theHealChance,
                    int theMinHeal, int theMaxHeal) {
        super("Skeleton",theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit,
                theHealChance, theMinHeal, theMaxHeal);
        loadSpriteSheet();
    }

    private void loadSpriteSheet() {
        spriteLoader = new Sprite();
        spriteLoader.loadSprite(SKELETON_SS);
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
