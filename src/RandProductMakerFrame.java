import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.Double.parseDouble;

public class RandProductMakerFrame extends JFrame
{
    private JPanel mainPanel, titlePanel, inputPanel, buttonPanel;
    private JLabel titleLabel, nameLabel, descriptionLabel, idLabel, costLabel, countLabel;
    private JTextField nameText, descriptionText, idText, costText, countText;
    private JButton addButton, quitButton;
    private JOptionPane errorOptionPane;
    private ActionListener quit = new quitListener();
    private ActionListener add = new addListener();

    private String name, description, id, outputString;
    private double cost;
    private int count;

    RandProductMakerFrame()
    {
        setTitle("Random Product Maker");
        setSize(1000,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        titlePanel = new JPanel();
        inputPanel = new JPanel();
        buttonPanel = new JPanel();

        titleLabel = new JLabel("Random Product Maker");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
        nameLabel = new JLabel("Product Name:  ");
        nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        descriptionLabel = new JLabel("Description:  ");
        descriptionLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        idLabel = new JLabel("Product ID:  ");
        idLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        costLabel = new JLabel("Product Cost:  ");
        costLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        countLabel = new JLabel("Records Added:  ");
        countLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        nameText = new JTextField();
        nameText.setFont(new Font("Times New Roman", Font.BOLD, 15));
        descriptionText = new JTextField();
        descriptionText.setFont(new Font("Times New Roman", Font.BOLD, 15));
        idText = new JTextField();
        idText.setFont(new Font("Times New Roman", Font.BOLD, 15));
        costText = new JTextField();
        costText.setFont(new Font("Times New Roman", Font.BOLD, 15));
        countText = new JTextField();
        countText.setFont(new Font("Times New Roman", Font.BOLD, 15));

        addButton = new JButton("Add");
        addButton.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        addButton.addActionListener(add);
        quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        quitButton.addActionListener(quit);

        errorOptionPane = new JOptionPane();

        add(mainPanel);
        mainPanel.setLayout(new GridLayout(3,1));

        mainPanel.add(titlePanel);
        titlePanel.add(titleLabel);

        mainPanel.add(inputPanel);
        inputPanel.setLayout(new GridLayout(5,2));
        inputPanel.add(nameLabel);
        nameLabel.setHorizontalAlignment(JLabel.RIGHT);
        inputPanel.add(nameText);
        inputPanel.add(descriptionLabel);
        descriptionLabel.setHorizontalAlignment(JLabel.RIGHT);
        inputPanel.add(descriptionText);
        inputPanel.add(idLabel);
        idLabel.setHorizontalAlignment(JLabel.RIGHT);
        inputPanel.add(idText);
        inputPanel.add(costLabel);
        costLabel.setHorizontalAlignment(JLabel.RIGHT);
        inputPanel.add(costText);
        inputPanel.add(countLabel);
        countLabel.setHorizontalAlignment(JLabel.RIGHT);
        inputPanel.add(countText);
        countText.setEditable(false);
        countText.setFont(new Font("Times New Roman", Font.BOLD, 15));
        countText.setText(String.valueOf(count));

        mainPanel.add(buttonPanel);
        buttonPanel.add(addButton);
        buttonPanel.add(quitButton);
    }

    private class addListener implements ActionListener
    {
        public void actionPerformed(ActionEvent AE)
        {
            if(!(nameText.getText().equals("")) &&
                    !(descriptionText.getText().equals("")) &&
                    !(idText.getText().equals("")) &&
                    !(costText.getText().equals("")))
            {
                if((nameText.getText().length() <= 35) &&
                        (descriptionText.getText().length() <= 75) &&
                        (idText.getText().length() <= 6))
                {
                    name = nameText.getText();
                    description = descriptionText.getText();
                    id = idText.getText();
                    cost = parseDouble(costText.getText());

                    outputString = String.format("\n%-6s %-35s %-75s  %.2f", id, name, description, cost);

                    File workingDirectory = new File(System.getProperty("user.dir"));
                    Path file = Paths.get(workingDirectory.getPath() + "\\ProductsRecreation.txt");
                    try
                    {
                        RandomAccessFile outFile = new RandomAccessFile(file.toString(), "rw");
                        outFile.seek(outFile.length());
                        outFile.write(outputString.getBytes());
                        outFile.close();
                    }
                    catch (FileNotFoundException e) {
                        System.out.println("File not found.");
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    nameText.setText("");
                    descriptionText.setText("");
                    idText.setText("");
                    costText.setText("");
                    count++;
                    countText.setText(String.valueOf(count));
                }
                else
                {
                    errorOptionPane.showMessageDialog(null, "Please check that the information entered fits into the fields based on the following:\nName: 35 Characters\nDescription: 75 Characters\nID: 6 Characters");
                }
            }
            else
            {
                errorOptionPane.showMessageDialog(null, "Please do not leave any fields empty.");
            }
        }
    }

    private class quitListener implements ActionListener
    {
        public void actionPerformed(ActionEvent AE)
        {
            System.exit(0);
        }
    }
}