import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE;

public class RandProductSearchFrame extends JFrame {
    JPanel mainPnl, titlePnl, sdPnl, searchPnl, displayPnl, btnPnl;
    JLabel titleLbl, searchLbl;
    JTextField searchTF;
    JTextArea displayTA;
    JScrollPane scroller;
    JButton quitBtn, clearBtn, searchBtn;
    RandomAccessFile randFile;
    ArrayList<String> results = new ArrayList<>();


    RandProductSearchFrame() { //done
        setTitle("Product Searcher ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        setSize((screenWidth / 4) * 3, screenHeight);
        setLocationRelativeTo(null); //centers

        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());
        add(mainPnl);

        sdPnl = new JPanel();
        sdPnl.setLayout(new BorderLayout());
        mainPnl.add(sdPnl, BorderLayout.CENTER);

        createTitlePanel();
        createSearchPanel();
        createDisplayPanel();
        createButtonPanel();

        setVisible(true);
    }

    private void createTitlePanel()//done
    {
        titlePnl = new JPanel();

        titleLbl = new JLabel("Product Searcher:", JLabel.CENTER);
        titleLbl.setFont(new Font("Times New Roman", Font.PLAIN, 48));

        //aligns text and image to be stacked not side by side
        titleLbl.setVerticalTextPosition(JLabel.BOTTOM);
        titleLbl.setHorizontalTextPosition(JLabel.CENTER);

        titlePnl.add(titleLbl);
        mainPnl.add(titlePnl, BorderLayout.NORTH);
    }

    private void createSearchPanel() { //done
        searchPnl = new JPanel();

        searchLbl = new JLabel("Enter Product you would like to search:", JLabel.CENTER);
        searchLbl.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        searchTF = new JTextField("", 20);

        searchPnl.add(searchLbl);
        searchPnl.add(searchTF);
        sdPnl.add(searchPnl, new BorderLayout().NORTH);

    }

    private void createDisplayPanel() { //done
        displayPnl = new JPanel();

        displayTA = new JTextArea(35, 150);
        scroller = new JScrollPane(displayTA);
        displayTA.setFont(new Font("Times New Roman", Font.PLAIN, 12));

        displayPnl.add(scroller);
        displayTA.setEditable(false);

        sdPnl.add(displayPnl, new BorderLayout().CENTER);
    }

    private void createButtonPanel() {
        btnPnl = new JPanel();
        btnPnl.setLayout(new GridLayout(1, 3));

        searchBtn = new JButton("Search");
        searchBtn.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        quitBtn = new JButton("Quit");
        quitBtn.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        clearBtn = new JButton("Clear Display");
        clearBtn.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        btnPnl.add(searchBtn);
        btnPnl.add(clearBtn);
        btnPnl.add(quitBtn);

        mainPnl.add(btnPnl, BorderLayout.SOUTH);

        //quit - DONE
        quitBtn.addActionListener(new ActionListener() {
            JOptionPane pane = new JOptionPane();

            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(pane, "Do you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
            }
        });

        //clear - DONE
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchTF.setText("");
                displayTA.setText("");
            }
        });

        //search
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {
                    randFile = new RandomAccessFile("ProductsRecreation.txt", "rw");


                    while (randFile.getFilePointer() < randFile.length()) {

                        // randFile.seek(0);
                        results.add(randFile.readLine() + "\n");
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException i) {
                    i.printStackTrace();
                }
                filterFile();
            }
        });
    }



    private void filterFile() {
        String searchWord = searchTF.getText().toLowerCase();

        List<String> list = results.stream()
                .filter(str -> str.toLowerCase()
                        .contains(searchWord)).toList();

        list.forEach(str -> displayTA.setText(str + "\n"));


    }
}


