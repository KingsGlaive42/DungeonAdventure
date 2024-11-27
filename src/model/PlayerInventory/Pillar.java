package model.PlayerInventory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pillar extends Item {
    private final String myName;
    private static final String IMAGE_PATH = "src/resources/assets/Inventory/";
    private BufferedImage pillarImage;

    public Pillar(String theName) {
        super("'" + theName.charAt(0) + "' pillar", "The pillar of " + theName.toLowerCase(), ItemType.PILLAR);
        this.myName = theName;
        loadPillarImage();
    }

    public String getPillarName() {
        return myName;
    }

    private void loadPillarImage() {
        String imageName = myName.toLowerCase();
        try {
            pillarImage = ImageIO.read(new File(IMAGE_PATH + imageName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            pillarImage = null;
        }
    }


    @Override
    public BufferedImage getImage() {
        return pillarImage;
    }
}
