import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class setSize extends JFrame{
    setSize() throws IOException{
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel widthLabel = new JLabel("Enter playing field width: ");
        widthLabel.setBounds(100, 50, 250, 50);
        JTextField widthTF = new JTextField("");
        widthTF.setBounds(300, 50, 80, 50);

        JLabel heightLabel = new JLabel("Enter playing field height: ");
        heightLabel.setBounds(100, 150, 250, 50);
        JTextField heightTF = new JTextField("");
        heightTF.setBounds(300, 150, 80, 50);

        JButton startButton = new JButton("START");
        startButton.setBounds(250, 300, 200, 50);

        JButton loadButton = new JButton("LOAD PREVIOUS GAME");
        loadButton.setBounds(50, 300, 200, 50);

        startButton.addActionListener(e -> {
            int width = Integer.parseInt(widthTF.getText());
            int height = Integer.parseInt(heightTF.getText());
            try {
                new gamePanel(height, width);
            }
            catch (FileNotFoundException ex)
            {
                // insert code to run when exception occurs
            }
            dispose();
        });

        loadButton.addActionListener(e -> {
            try {
                ArrayList<String> loadArray = new ArrayList<String>();
                Scanner scan = new Scanner(new File("./savepoint.txt"));

                int height = Integer.parseInt(scan.next());
                int width = Integer.parseInt(scan.next());
                int score = Integer.parseInt(scan.next());

                while (scan.hasNext()){
                    loadArray.add(scan.next());
                }
                scan.close();
                System.out.println(loadArray);
                new gamePanel2(height, width, score, loadArray);
            }
            catch (IOException ex)
            {
                // insert code to run when exception occurs
            }
            dispose();
        });

        panel.add(widthLabel);
        panel.add(widthTF);
        panel.add(heightLabel);
        panel.add(heightTF);
        panel.add(startButton);
        panel.add(loadButton);

        setSize(500,500);
        setTitle("Word Search");
        add(panel);
        setVisible(true);
    }
}