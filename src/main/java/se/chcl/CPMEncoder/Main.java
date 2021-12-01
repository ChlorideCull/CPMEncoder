package se.chcl.CPMEncoder;

import com.tom.cpl.util.Image;
import com.tom.cpm.shared.io.ChecksumOutputStream;
import com.tom.cpm.shared.io.IOHelper;
import com.tom.cpm.shared.io.SkinDataOutputStream;
import com.tom.cpm.shared.parts.ModelPartDefinitionLink;
import com.tom.cpm.shared.parts.ModelPartEnd;
import com.tom.cpm.shared.parts.ModelPartSkinType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("CPM GitHub Link Encoder");
        System.out.println();
        if (args.length != 2) {
            System.out.println("Pass the URL to the 'Raw' file on GitHub, it should look something like this:");
            System.out.println("    https://raw.githubusercontent.com/user/repo/branch/filename");
            System.out.println();
            System.out.println("Then pass where the PNG should be saved.");
            return;
        }
        String url = args[0];
        String dest = args[1];
        if (!url.startsWith("https://raw.githubusercontent.com/")) {
            System.out.println("Incorrect URL");
            return;
        }
        int maxUrlLength = 8 + 8*3;
        url = url.substring("https://raw.githubusercontent.com/".length());
        if (url.length() > maxUrlLength) {
            System.out.println("Repo path too long - it can be at most " + maxUrlLength + " characters - this includes user/repo/branch/filename.");
            System.out.println("Beyond this length it starts using alpha to encode data, which can't be hidden.");
        }
        Image skin = Image.loadFrom(Main.class.getResourceAsStream("/cpm/default.png"));
        Image template = Image.loadFrom(Main.class.getResourceAsStream("/cpm/free_space_template.png"));

        SkinDataOutputStream str = new SkinDataOutputStream(skin, template, 1);
        str.write(0x53); // Header
        ChecksumOutputStream cos = new ChecksumOutputStream(str);
        try(IOHelper dout = new IOHelper(cos)) {
            dout.writeObjectBlock(new ModelPartSkinType(1));
            String link = "gh:" + url;
            dout.writeObjectBlock(new ModelPartDefinitionLink(link));
            dout.writeObjectBlock(ModelPartEnd.END);
        }
        cos.close();
        str.close();

        if (skin.getRGB(32, 0) != 0) {
            System.out.println("Generated magic pixels too long! This should've been blocked earlier - file an issue.");
            return;
        }
        BufferedImage outImage = new BufferedImage(32, 1, BufferedImage.TYPE_4BYTE_ABGR);
        for (int i = 0; i < 32; i++) {
            if (i >= 8 && i <= 23) {
                // Don't include Steve's big fat head
                outImage.setRGB(i, 0, 0);
                continue;
            }
            int pixel = skin.getRGB(i, 0);
            if ((pixel & 0x00FFFFFF) == 0) {
                outImage.setRGB(i, 0, 0);
            }
            // Force minimum alpha that is not zero to avoid it getting optimized out
            pixel = (pixel & 0x00FFFFFF) ^ 0x01000000;
            outImage.setRGB(i, 0, pixel);
        }
        ImageIO.write(outImage, "PNG", new File(dest));

        System.out.println("All done - an overlay image has been created for your skin. Place it in the top left of the image - making sure the 'unused' square areas it covers are completely transparent beforehand.");
    }
}
