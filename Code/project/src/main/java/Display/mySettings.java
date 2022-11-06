package Display;

import Board.Difficulty;
import Entities.Enemy;
import Entities.Hero;
import Helpers.HeroColor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Creates this JPanel to represent the Settings screen.
 * Users are able to select squirrel skin colours and
 * audio mute/un-mute option.
 */
public class mySettings extends JPanel {
    private HashMap<HeroColor, BufferedImage> heroColorPngs;
    private BufferedImage leftButtonPng;
    private BufferedImage rightButtonPng;
    private HeroColor color;
    private JButton leftScroll;
    private JButton rightScroll;
    private BufferedImage testimage_png;
    private JLabel settLabel;
    private Font headerText;
    private ButtonGroup soundGroup;
    private JToggleButton muteButton;
    private JToggleButton unmuteButton;
    private GridBagConstraints gbc;
    private JButton settbackButton;
    private CardLayout cl;
    private DisplayLayout dl;

    /**
     * Sets up the available squirrel colours to select.
     * This method creates a hashmap containing all the
     * images of available squirrel colours.
     */
    private void getHeroColors(){
        try {
            heroColorPngs = new HashMap<>();
            for(HeroColor color: HeroColor.values()){
                BufferedImage img = ImageIO.read(getClass().getResource("/squirrels/Squirrel" + color.toString() + "East2.png"));
                heroColorPngs.put(color, img);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets variables to have triangular wooden images.
     */
    private void getButtonPngs() {
        try {
            leftButtonPng = ImageIO.read(getClass().getResource("/scrollButtonLeft.png"));
            rightButtonPng = ImageIO.read(getClass().getResource("/scrollButtonRight.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructor creates this Settings screen.
     * This constructor makes buttons to mute and un-mute sound, and
     * go back to the Title screen. The shown squirrel image is the
     * colour the user plays with, and the wooden left and right arrow
     * buttons switches these colours.
     * @param dl the JFrame object used to access the different JPanels
     * @param cl the CardLayout object to use its methods
     */
    public mySettings(DisplayLayout dl, CardLayout cl) {

        this.cl = cl;
        gbc = new GridBagConstraints();
        gbc.anchor = gbc.CENTER;

        // Setting up the settings panel
        setLayout(new GridBagLayout());
        settLabel = new JLabel("Settings");
        headerText = new Font("Times New Roman", Font.BOLD, 30);
        settLabel.setFont(headerText);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0,0,0,450);
        this.add(settLabel,gbc);

        soundGroup = new ButtonGroup();
        muteButton = new JToggleButton(" Mute ");
        gbc.insets = new Insets(100,0,0,450);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.ipadx = 200;
        gbc.ipady = 50;
        muteButton.setFocusable(false);
        this.add(muteButton,gbc);

        unmuteButton = new JToggleButton("Unmute", true);
        gbc.gridx = 1;
        gbc.gridy = 3;
        unmuteButton.setFocusable(false);
        this.add(unmuteButton,gbc);


        settbackButton = new JButton(" Back ");
        gbc.gridx = 1;
        gbc.gridy = 4;
        settbackButton.setFocusable(false);
        this.add(settbackButton,gbc);

        settbackButton.addActionListener(new ActionListener() {
            /**
             * When user presses Main Menu button, goes to Title screen.
             * This method uses the CardLayout show method to change
             * current Game Over JPanel to Title JPanel, and sets the
             * currentCard variable in DisplayLayout object to match the
             * Title JPanel's reference number.
             * @param arg0 the event to be processed
             */
            public void actionPerformed(ActionEvent arg0) {
                // go back to title panel
                cl.show(dl.displayPanel, "1");

                // current panel is difficulty Panel
                dl.currentCard = 1;
            }
        });

        //toggle only mute or unmute
        soundGroup.add(muteButton);
        soundGroup.add(unmuteButton);

        muteButton.addActionListener(new ActionListener()
        {
            /**
             * When user presses Mute button, stops the music.
             * @param arg0 the event to be processed
             */
            public void actionPerformed(ActionEvent arg0)
            {
                dl.sound.stopMusic();
            }
        });

        unmuteButton.addActionListener(new ActionListener()
        {
            /**
             * When user presses un-mute button, starts the music.
             * Sound is initially played when DisplayLayout object is made.
             * @param arg0 the event to be processed
             */
            public void actionPerformed(ActionEvent arg0)
            {
                dl.sound.startupMusic();
            }
        });

        try {
            getButtonPngs();
            getHeroColors();
            testimage_png = ImageIO.read(getClass().getResource("/testimage.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        color = dl.heroColor;

        leftScroll = iconButton(leftButtonPng, 40, 40);
        leftScroll.setFocusable(false);
        leftScroll.addActionListener(new ActionListener()
        {
            /**
             * Shows the next squirrel colour when right arrow button pressed.
             * @param arg0 the event to be processed
             */
            public void actionPerformed(ActionEvent arg0)
            {
                dl.heroColor = dl.heroColor.next();
                color = dl.heroColor;


                repaint();
            }
        });
        gbc.insets = new Insets(150,300,0,0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        this.add(leftScroll, gbc);

        rightScroll = iconButton(rightButtonPng, 40, 40);
        rightScroll.setFocusable(false);
        rightScroll.addActionListener(new ActionListener()
        {
            /**
             * Shows the previous squirrel colour when left arrow button pressed.
             * @param arg0 the event to be processed
             */
            public void actionPerformed(ActionEvent arg0)
            {
                dl.heroColor = dl.heroColor.prev();
                color = dl.heroColor;

                repaint();

            }
        });
        gbc.insets = new Insets(150,0,0,100);
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(rightScroll, gbc);

    }

    /**
     * Draws a background image of forest onto this JPanel.
     * This Override method draws from the top-left corner
     * of the JPanel the PNG image starting from its top-left corner.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (testimage_png != null) {
            g.drawImage(testimage_png, 0, 0, 1500, 960, null);
        }
        skinSelection(g);
    }

    /**
     * Changes default JButton image to the parameter's image.
     * @param image the Image with an accessible buffer of image data
     * @param width the amount of horizontal pixels in one row
     * @param height the amount of vertical pixels in one column
     * @return the JButton with default rectangle button changed to the parameter image
     */
    private JButton iconButton(BufferedImage image, int width, int height) {
        Image scaledImage = image.getScaledInstance( width, height,  java.awt.Image.SCALE_SMOOTH ) ;
        ImageIcon iconImage = new ImageIcon(scaledImage);
        JButton b = new JButton(iconImage);
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        return b;
    }


    /**
     * Draws the squirrel image onto this JPanel.
     * @param g the Graphics class object to put onto JPanel
     */
    private void skinSelection(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;   //   Draws shapes
        BufferedImage hero = heroColorPngs.get(color);
        g2.drawImage(hero, 670, 250, 80, 80, null);

    }
}