package com.Display;

import com.Helpers.HeroColor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

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
    private final JButton leftScroll;
    private final JButton rightScroll;
    private final BufferedImage testimagePng;
    private final JToggleButton muteButton;
    private final JToggleButton unmuteButton;
    private final DisplayLayout dl;

    /**
     * Sets up the available squirrel colours to select.
     * This method creates a hashmap containing all the
     * images of available squirrel colours.
     */
    private void getHeroColors(){
        try {
            heroColorPngs = new HashMap<>();
            URL pathUrl = getClass().getClassLoader().getResource("squirrels/");
            if ((pathUrl != null) && pathUrl.getProtocol().equals("file")) {
                File[] files = new File(pathUrl.toURI()).listFiles();
                for(final File fileEntry : files){
                    if(fileEntry.isFile()){
                        String fileName = fileEntry.getName();
                        HeroColor color = HeroColor.BROWN;
                        if(fileName.contains("Brown")){
                        } else if (fileName.contains("Grey")) {
                            color = HeroColor.GREY;
                        } else if (fileName.contains("Red")) {
                            color = HeroColor.RED;
                        } else if (fileName.contains("White")) {
                            color = HeroColor.WHITE;
                        }
                        if(fileName.contains("East2")){
                            heroColorPngs.put(color, ImageIO.read(Objects.requireNonNull(getClass().getResource("/squirrels/" + fileName))));
                        }
                    }
                }
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets variables to have triangular wooden images.
     */
    private void getButtonPngs() {
        try {
            leftButtonPng = ImageIO.read(Objects.requireNonNull(getClass().getResource("/scrollButtonLeft.png")));
            rightButtonPng = ImageIO.read(Objects.requireNonNull(getClass().getResource("/scrollButtonRight.png")));

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

        this.dl = dl;
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        // Setting up the settings panel
        setLayout(new GridBagLayout());
        JLabel settLabel = new JLabel("Settings");
        Font headerText = new Font("Times New Roman", Font.BOLD, (dl.displayheight / 15));
        settLabel.setFont(headerText);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0,0,0,0);
        this.add(settLabel, gbc);

        ButtonGroup soundGroup = new ButtonGroup();
        ImageIcon muteImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/muteBtn.png")));
        muteButton = new JToggleButton("",muteImage);
        gbc.insets = new Insets((dl.displayheight/15),0,0,0);
        gbc.gridx = 1;
        gbc.gridy = 2;
        muteButton.setFocusable(false);
        muteButton.setBorderPainted(false);
        muteButton.setContentAreaFilled(false);
        muteButton.setOpaque(false);
        this.add(muteButton, gbc);

        ImageIcon unmuteImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/unmute.png")));
        unmuteButton = new JToggleButton("",unmuteImage,true);
        gbc.gridx = 1;
        gbc.gridy = 3;
        unmuteButton.setFocusable(false);
        unmuteButton.setBorderPainted(false);
        unmuteButton.setContentAreaFilled(false);
        unmuteButton.setOpaque(false);
        this.add(unmuteButton, gbc);


        ImageIcon backImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/back.png")));
        JButton settbackButton = new JButton("", backImage);
        gbc.gridx = 1;
        gbc.gridy = 4;
        settbackButton.setBorderPainted(false);
        settbackButton.setFocusable(false);
        settbackButton.setOpaque(false);
        settbackButton.setContentAreaFilled(false);
        this.add(settbackButton, gbc);

        settbackButton.addActionListener(new ActionListener() {
            /**
             * When user presses com.Main Menu button, goes to Title screen.
             * This method uses the CardLayout show method to change
             * current com.Game Over JPanel to Title JPanel, and sets the
             * currentCard variable in DisplayLayout object to match the
             * Title JPanel's reference number.
             * @param arg0 the event to be processed
             */
            public void actionPerformed(ActionEvent arg0) {
                // go back to title panel
                cl.show(dl.displayPanel, "Title");
                dl.sound.playClick();
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
                dl.sound.playClick();
                //disable the sound
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
                //dl.sound.startupMusic();
                //enable the sound
                //dl.sound.playMusic(0);
                dl.sound.playClick();
                dl.sound.play();
            }
        });

        try {
            getButtonPngs();
            getHeroColors();
            testimagePng = ImageIO.read(Objects.requireNonNull(getClass().getResource("/settingB.jpg")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        color = dl.heroColor;

        leftScroll = iconButton(leftButtonPng, (dl.displayheight/20), (dl.displayheight/20));
        leftScroll.setFocusable(false);
        leftScroll.addActionListener(new ActionListener()
        {
            /**
             * Shows the next squirrel colour when right arrow button pressed.
             * @param arg0 the event to be processed
             */
            public void actionPerformed(ActionEvent arg0)
            {
                dl.sound.playClick();
                dl.heroColor = dl.heroColor.next();
                color = dl.heroColor;
                repaint();
            }
        });
        // 1/6 of height, /3.75 when 400 pixels from 1500 pixel-wide screen
        gbc.insets = new Insets((dl.displayheight/6),0,0,(dl.displaywidth/4));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        this.add(leftScroll, gbc);

        // Scroll Button /20 of screen height
        rightScroll = iconButton(rightButtonPng, (dl.displayheight/20), (dl.displayheight/20));
        rightScroll.setFocusable(false);
        rightScroll.addActionListener(new ActionListener()
        {
            /**
             * Shows the previous squirrel colour when left arrow button pressed.
             * @param arg0 the event to be processed
             */
            public void actionPerformed(ActionEvent arg0)
            {
                dl.sound.playClick();
                dl.heroColor = dl.heroColor.prev();
                color = dl.heroColor;

                repaint();

            }
        });
        gbc.insets = new Insets((dl.displayheight/6),(dl.displaywidth/4),0,0);
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
        if (testimagePng != null) {
            g.drawImage(testimagePng, 0, 0, dl.displaywidth, dl.displayheight, null);
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
        g2.drawImage(hero, (int)(dl.displaywidth*0.47), (int)(dl.displayheight*0.28), (dl.displayheight/10), (dl.displayheight/10), null);

    }
    public JToggleButton getmuteButton(){return muteButton;}

    public JToggleButton getUnmuteButton(){return unmuteButton;}

    public JButton getLeftScroll(){return leftScroll;}

    public JButton getRightScroll(){return rightScroll;}

    public HeroColor getHeroColor(){return color;}
}