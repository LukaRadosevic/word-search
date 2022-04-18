import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class gamePanel extends JFrame {
    static Random rand = new Random();

    public static ArrayList<String> getDictionary() throws FileNotFoundException {
        Scanner s = new Scanner(new File("./dictionary.txt"));
        ArrayList<String> list = new ArrayList<String>();

        while (s.hasNext()){
            list.add(s.next());
        }
        s.close();
        return list;
    }

    JButton[][] button;
    int rows, columns;
    static int i;
    static int j;
    String currentGrid = new String("");

    gamePanel(int rows, int columns) throws FileNotFoundException{
        this.rows = rows;
        this.columns = columns;

        int score = 0;

        setLayout(new GridLayout(1, 2));

        ArrayList<String> userLetter = new ArrayList<>();

        JPanel panel = new JPanel();
        GridLayout gridLayout = new GridLayout(rows, columns);
        panel.setLayout(gridLayout);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(2, 1));

        JLabel label = new JLabel("Current word is: ");
        JLabel label2 = new JLabel("Current score is " + score);

        JButton stopButton = new JButton("DONE");

        button = new JButton[rows][columns];

        for(i = 0; i < rows; i++) {
            for(j = 0; j < columns; j++) {
                char letter = (char) (rand.nextInt(26)+'A');
                button[i][j] = new JButton();
                button[i][j].setText(String.valueOf(letter));
                button[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        for(int i = 0; i < rows; i++){
                            for(int j = 0; j < columns; j++){
                                if(button[i][j] == e.getSource()){
                                    int p = i;
                                    int l = j;
                                    userLetter.add(button[i][j].getText());

                                    button[i][j].setVisible(false);
                                    button[i][j].setText("*");

                                    for(int r = 0; r < rows; r++){
                                        for(int k = 0; k < columns; k++){
                                            if (r == p - 1 && k == l - 1 || r == p - 1 && k == l || r == p - 1 && k == l + 1 || r == p && k == l - 1 || r == p && k == l + 1 || r == p + 1 && k == l - 1 || r == p + 1 && k == l || r == p + 1 && k == l + 1){
                                                button[r][k].setBackground(Color.GREEN);
                                                button[r][k].setEnabled(true);
                                            }
                                            else{
                                                button[r][k].setBackground(stopButton.getBackground());
                                                button[r][k].setEnabled(false);
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                        panel2.add(label);
                        panel2.add(label2);
                        label.setText("Current word is: " + String.join("", userLetter).toLowerCase(Locale.ROOT));

                        System.out.println(userLetter);
                    }
                });
                panel.add(button[i][j]);
            }
        }



        stopButton.setSize(50, 50);
        stopButton.addActionListener(e -> {
            String userWord = String.join("", userLetter);
            try {
                for(i = 0; i < rows; i++){
                    for(j = 0; j < columns; j++){
                        currentGrid += button[i][j].getText() + "\n";
                    }
                }

                FileWriter writer = new FileWriter("./savepoint.txt");
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write(rows + "\n");
                bufferedWriter.write(columns + "\n");
                bufferedWriter.write(score + "\n");
                bufferedWriter.write(currentGrid);
                System.out.println("saved");
                bufferedWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                new endGame(userWord.toLowerCase(Locale.ROOT));
            }
            catch (FileNotFoundException ex) {
            }
            dispose();
        });

        JButton saveButton = new JButton("SAVE");
        saveButton.addActionListener(e -> {
            try {
                for(i = 0; i < rows; i++){
                    for(j = 0; j < columns; j++){
                        currentGrid += button[i][j].getText() + "\n";
                    }
                }

                FileWriter writer = new FileWriter("./savepoint.txt");
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write(rows + "\n");
                bufferedWriter.write(columns + "\n");
                bufferedWriter.write(score + "\n");
                bufferedWriter.write(currentGrid);
                System.out.println("saved");
                bufferedWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        panel2.add(stopButton);
        panel2.add(saveButton);
        setTitle("Word Search");
        add(panel);
        add(panel2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }
}
