package software.ulpgc.imageviewer.swing;

import software.ulpgc.imageviewer.Image;
import software.ulpgc.imageviewer.ImageDisplay;
import software.ulpgc.imageviewer.ImagePresenter;

import javax.swing.*;
import java.io.File;

public class Main {
    public static final String root = "C://Users//itzha//Downloads//fotos";
    public static void main(String[] args) {


        MainFrame mainFrame = new MainFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int selection = fileChooser.showOpenDialog(mainFrame);
        if (selection == JFileChooser.APPROVE_OPTION)
        {
            File folder = fileChooser.getSelectedFile();
            Image image = new FileImageLoader(folder).load();
            ImageDisplay imageDisplay = mainFrame.imageDisplay();
            new ImagePresenter(image, imageDisplay);
        }else if (selection==JFileChooser.CANCEL_OPTION){
            System.exit(0);
        }

        mainFrame.setVisible(true);

    }
}
