import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * This class controls the UI by changing UIManager for different purposes like theme setting and add LookAndFeel
 * @author shiva
 * @since 13/05/2020
 */
public class UIControl {

    /**
     * THis is the main method of UIControl class which sets preferences
     */
    public static void UIManagerControl() {

        updateDarkTheme();
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Look and Feel not set");
        }
    }

    /**
     * This method checks the dark theme is active or not.If is active it changes the look and feel colors
     */
    public static void updateDarkTheme() {
        File configFile = new File("src/resources/config.properties");
        FileReader reader = null;
        try {
            reader = new FileReader(configFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties props = new Properties();
        try {
            props.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (props.getProperty("darkTheme").equals("active")) {
            UIManager.put( "control", new Color(128,128,128));
            UIManager.put( "info", new Color(128,128,128) );
            UIManager.put( "nimbusBase", new Color( 18, 30, 49) );
            UIManager.put( "nimbusAlertYellow", new Color( 248, 187, 0) );
            UIManager.put( "nimbusDisabledText", new Color( 128, 128, 128) );
            UIManager.put( "nimbusFocus", new Color(115,164,209) );
            UIManager.put( "nimbusGreen", new Color(176,179,50) );
            UIManager.put( "nimbusInfoBlue", new Color( 66, 139, 221) );
            UIManager.put( "nimbusLightBackground", new Color(5, 1, 5, 84) );
            UIManager.put( "nimbusOrange", new Color(191,98,4) );
            UIManager.put( "nimbusRed", new Color(169,46,34) );
            UIManager.put( "nimbusSelectedText", new Color( 255, 255, 255) );
            UIManager.put( "nimbusSelectionBackground", new Color( 104, 93, 156) );
            UIManager.put( "text", new Color( 230, 230, 230) );
        }
    }
}
