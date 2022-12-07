package com.Display;

import com.Board.Difficulty;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Creates this JPanel to represent the Difficulty screen
 */
public class myDifficulty extends JPanel {
    private ButtonGroup difGroup;
    private JToggleButton easyButton;
    private JToggleButton mediumButton;
    private JToggleButton hardButton;

    private JToggleButton infiniteButton;

    private JButton difbackButton;
    private GridBagConstraints gbc;
    private Font headerText;
    private JLabel difLabel;
    private BufferedImage testimagePng;

    private DisplayLayout dl;

    /**
     * Constructor creates the difficulty screen.
     * This constructor makes JToggleButtons for easy, medium,
     * hard, and a JButton for Back. The default selected is easy,
     * and users press the Back button to return to the Title screen.
     * @param dl the JFrame object used to access the different JPanels
     * @param cl the CardLayout object to use its methods
     */
    public myDifficulty(DisplayLayout dl, CardLayout cl){

        this.dl = dl;

        try {
            testimagePng = ImageIO.read(getClass().getResource("/testimage.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        gbc = new GridBagConstraints();
        this.setLayout(new GridBagLayout());

        difLabel = new JLabel("Difficulty");
        headerText = new Font("Times New Roman", Font.BOLD, 30);
        difLabel.setFont(headerText);
        this.add(difLabel);

        // Initialize Toggle/Button objects and add to buttongroup and Difficulty Panel
        difGroup = new ButtonGroup();
        ImageIcon easyImage = new ImageIcon(getClass().getResource("/easy.png"));
        easyButton = new JToggleButton("",easyImage,true);
        gbc.insets = new Insets(dl.displayheight/30,0,0,0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipady = dl.displayheight/30;
        easyButton.setFocusable(false);
        easyButton.setBorderPainted(false);
        easyButton.setOpaque(false);
        easyButton.setContentAreaFilled(false);
        this.add(easyButton, gbc);

        ImageIcon medImage = new ImageIcon(getClass().getResource("/medium.png"));
        mediumButton = new JToggleButton("",medImage,true);
        //gbc.insets = new Insets(50,0,0,0);
        gbc.gridx = 0;
        gbc.gridy = 2;
        mediumButton.setFocusable(false);
        mediumButton.setBorderPainted(false);
        mediumButton.setOpaque(false);
        mediumButton.setContentAreaFilled(false);
        this.add(mediumButton, gbc);

        ImageIcon hardImg = new ImageIcon(getClass().getResource("/hard.png"));
        hardButton = new JToggleButton("",hardImg,true);
        gbc.gridx = 0;
        gbc.gridy = 3;
        hardButton.setFocusable(false);
        hardButton.setBorderPainted(false);
        hardButton.setOpaque(false);
        hardButton.setContentAreaFilled(false);
        this.add(hardButton, gbc);

        ImageIcon infiniteImg = new ImageIcon(getClass().getResource("/infinit.png"));
        infiniteButton = new JToggleButton("",infiniteImg,true);
        gbc.gridx = 0;
        gbc.gridy = 4;
        infiniteButton.setFocusable(false);
        infiniteButton.setBorderPainted(false);
        infiniteButton.setOpaque(false);
        infiniteButton.setContentAreaFilled(false);
        this.add(infiniteButton, gbc);

        ImageIcon backImage = new ImageIcon(getClass().getResource("/back.png"));
        difbackButton = new JButton("",backImage);
        gbc.gridx = 0;
        gbc.gridy = 5;
        difbackButton.setFocusable(false);
        difbackButton.setBorderPainted(false);
        difbackButton.setOpaque(false);
        difbackButton.setContentAreaFilled(false);
        this.add(difbackButton, gbc);

        // Toggles only 1 button
        difGroup.add(easyButton);
        difGroup.add(mediumButton);
        difGroup.add(hardButton);

        easyButton.addActionListener(new ActionListener()
        {
            /**
             * When user presses Easy button, sets difficulty to easy.
             * Sets the dif variable in DisplayLayout object to be enum Easy.
             * @param arg0 the event to be processed
             */
            public void actionPerformed(ActionEvent arg0)
            {
                dl.sound.playClick();
                //Change difficulty to easy
                dl.dif = Difficulty.EASY;
            }
        });

        mediumButton.addActionListener(new ActionListener()
        {
            /**
             * When user presses Medium button, sets difficulty to medium.
             * Sets the dif variable in DisplayLayout object to be enum Medium.
             * @param arg0 the event to be processed
             */
            public void actionPerformed(ActionEvent arg0)
            {
                dl.sound.playClick();
                //Change difficulty to medium
                dl.dif = Difficulty.MEDIUM;
            }
        });

        hardButton.addActionListener(new ActionListener() {
            /**
             * When user presses Hard button, sets difficulty to hard.
             * Sets the dif variable in DisplayLayout object to be enum Hard.
             * @param arg0 the event to be processed
             */
            public void actionPerformed(ActionEvent arg0) {
                dl.sound.playClick();
                //Change difficulty to hard
                dl.dif = Difficulty.HARD;
            }
        });

        infiniteButton.addActionListener(new ActionListener() {
            /**
             * When user presses Hard button, sets difficulty to hard.
             * Sets the dif variable in DisplayLayout object to be enum Hard.
             * @param arg0 the event to be processed
             */
            public void actionPerformed(ActionEvent arg0) {
                dl.sound.playClick();
                //Change difficulty to hard
                dl.dif = Difficulty.INFINITE;
            }
        });

        // add back Button ActionListener
        difbackButton.addActionListener(new ActionListener() {
            /**
             * When user presses Back button, displays Title screen.
             * This method uses the CardLayout show method to change
             * current Difficulty JPanel to Title JPanel, and sets
             * the currentCard variable in DisplayLayout object
             * to match the Title JPanel's reference number.
             * @param arg0 the event to be processed
             */
            public void actionPerformed(ActionEvent arg0) {
                dl.sound.playClick();
                // show associated difficulty panel
                cl.show(dl.displayPanel, "1");
                // current panel is difficulty Panel
                dl.currentCard = 1;
            }
        });

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
    }

    public JToggleButton getEasyButton() {
        return easyButton;
    }

    public JToggleButton getMediumButton(){
        return mediumButton;
    }
    public JToggleButton getHardButton(){
        return hardButton;
    }
}
