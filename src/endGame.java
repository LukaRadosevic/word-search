import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.Locale;

public class endGame extends JFrame{
    String userWord;
    static int score;

    endGame(String userWord) throws FileNotFoundException {
        this.userWord = userWord;

        int currentScore = 0;

        Scanner scanner = new Scanner(new File("./savepoint.txt"));

        scanner.next();
        scanner.next();
        score = Integer.parseInt(scanner.next());

        int index = userWord.length();

        JPanel panel = new JPanel();
        setSize(400,400);
        panel.setLayout(new BorderLayout());

        ArrayList<String> list = gamePanel.getDictionary();

        if(list.contains(userWord)){
            for(int i = 0; i < index; i++){
                currentScore += (Math.pow(2, i));
            }
        }

        score += currentScore;

        JLabel label = new JLabel("Your score is " + score);
        label.setSize(300, 100);

        panel.add(label, BorderLayout.CENTER);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1, 2));

        JButton replayButton = new JButton("NEW GAME");
        replayButton.addActionListener(e -> {
            dispose();
            try {
                new setSize();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        JButton continueButton = new JButton("KEEP PLAYING");
        continueButton.addActionListener(e -> {
            try {
                ArrayList<String> loadArray = new ArrayList<String>();
                Scanner scan = new Scanner(new File("./savepoint.txt"));

                int height = Integer.parseInt(scan.next());
                int width = Integer.parseInt(scan.next());
                score = Integer.parseInt(scan.next());

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

        JButton quitButton = new JButton("QUIT");
        quitButton.addActionListener(e -> {
            dispose();
            System.exit(0);
        });

        panel2.add(replayButton);
        panel2.add(continueButton);
        panel2.add(quitButton);


        setLayout(new GridLayout(2, 1));
        add(panel);
        add(panel2);
        setTitle("Word Search");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        System.out.println(userWord);
    }
}
