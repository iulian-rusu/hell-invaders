package Assets;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * @brief Holds references to some fonts.
 */
public class FontAssets {
    public static Font mainFont;
    public static Font mainFontBold;
    public static Font mainFontItalic;

    /**
     * Loads fonts from the disk.
     */
    public static void Init() {
        try {
            InputStream is = FontAssets.class.getResourceAsStream("/others/my_font.ttf");
            mainFont = Font.createFont(Font.TRUETYPE_FONT, is);
            mainFontBold = mainFont.deriveFont(Font.BOLD);
            mainFontItalic = mainFont.deriveFont(Font.ITALIC);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
