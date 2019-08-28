package test;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageTest {

    @Test
    public void test1() throws IOException {
        BufferedImage thumbImage = new BufferedImage(1202, 1700, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = thumbImage.createGraphics();

        File file = new File("C:\\Users\\Chen\\Desktop\\666.jpg");
        Image src = javax.imageio.ImageIO.read(file);
        g.drawImage(src.getScaledInstance(1202,1700,Image.SCALE_SMOOTH), 0, 0, null);

        File file2 = new File("C:\\Users\\Chen\\Desktop\\touxiang.png");
        Image img = javax.imageio.ImageIO.read(file2);
        g.drawImage(img.getScaledInstance(484,467,Image.SCALE_SMOOTH), 0, 0, null);

        String s="医疗兵作战服[4★]";
        g.setColor(Color.RED);
        g.setFont(new Font("黑体",Font.PLAIN,50));
        g.drawString(s,100,100);

        s="医疗兵作战服[4★]";
        g.drawString(s,100,150);
        g.dispose();

        String path = "C:\\Users\\Chen\\Desktop\\123.jpg";
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
        String formatName = path.substring(path.lastIndexOf(".") + 1);
        ImageIO.write(thumbImage, /*"GIF"*/ formatName /* format desired */ , new File(path) /* target */ );

        out.close();
    }
}
