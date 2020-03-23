package Assets;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontAssets {
    public static Font mainFont;
    public static Font mainFont_bold;
    public static Font mainFont_italic;

    public static void Init(){
        try{
            InputStream is = FontAssets.class.getResourceAsStream("/others/my_font.ttf");
            mainFont= Font.createFont(Font.TRUETYPE_FONT, is);
            mainFont_bold=mainFont.deriveFont(Font.BOLD);
            mainFont_italic=mainFont.deriveFont(Font.ITALIC);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
