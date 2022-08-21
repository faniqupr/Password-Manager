package ui;

import model.Event;
import model.EventLog;
import model.PasswordInfo;
import model.PasswordInfoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Citation: https://www.youtube.com/watch?v=Kmgo00avvEw
// Password App (GUI Version)
public class PasswordAppGUI implements ActionListener {
    private static final String JSON_STORE = "./data/passwordInfoList.json";

    private PasswordInfoList passwordInfoList;
    private PasswordInfo passwordInfo;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JFrame frame;

    // JPanels
    private JPanel allPasswords;
    private List<JPanel> panels;

    // JButtons
    private JButton saveToFile;
    private JButton loadFile;
    private JButton addPassword;
    private JButton save;
    private JButton generate;
    private JButton saveEdit;

    // JTextFields
    private JTextField websiteName;
    private JTextField username;
    private JTextField loginID;
    private JTextField password;
    private JTextField generateLength;

    // EFFECTS: Creates start state of the app
    public PasswordAppGUI() {
        passwordInfoList = new PasswordInfoList();
        passwordInfo = new PasswordInfo();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        allPasswords = new JPanel();
        panels = new ArrayList<>();

        createFrame();
        frame.add(createHeader());

        createOptions();
        WindowListener listener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Event logs");
                for (Event event: EventLog.getInstance()) {
                    System.out.println(event.getDescription());
                }
            }
        };
        frame.addWindowListener(listener);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates a new JFrame for the Password App
    public void createFrame() {
        frame = new JFrame();
        frame.setTitle("Password App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.white);

        ImageIcon icon = new ImageIcon("data/passwordAppLogo.png");
        frame.setIconImage(icon.getImage());
    }

    // MODIFIES: this
    // EFFECTS: Creates a JPanel header and returns it
    public JPanel createHeader() {
        JPanel header = new JPanel();
        header.setBackground(Color.white);
        header.setBounds(50, 50, 500, 100);
        header.setLayout(new BorderLayout());

        JLabel label = new JLabel();
        ImageIcon icon = new ImageIcon("data/passwordAppLogo.png");
        icon = new ImageIcon(icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        label.setText("Password App");
        label.setIcon(icon);
        label.setFont(new Font("Century Gothic", Font.BOLD, 50));
        label.setIconTextGap(50);
        label.setVerticalAlignment(SwingConstants.TOP);

        header.add(label);

        return header;
    }

    // MODIFIES: this
    // EFFECTS: Creates a section for options
    public void createOptions() {
        JPanel options = new JPanel();
        options.setBackground(Color.white);
        options.setBounds(200, 200, 500, 50);
        options.setLayout(new GridLayout(1, 3, 50, 0));

        addPassword = new JButton("Add");
        styleOptions(addPassword, options);

        saveToFile = new JButton("Save");
        styleOptions(saveToFile, options);

        loadFile = new JButton("Load");
        styleOptions(loadFile, options);

        frame.add(options);
    }

    // MODIFIES: this, jbutton
    // EFFECTS: Styles the jbutton ands adds ActionListener
    public void styleOptions(JButton jbutton, JPanel options) {
        jbutton.setFont(new Font("Century Gothic", Font.BOLD, 25));
        jbutton.setFocusable(false);
        jbutton.setBackground(Color.white);
        jbutton.setBorder(BorderFactory.createEtchedBorder());
        jbutton.setForeground(Color.black);
        jbutton.addActionListener(this);
        options.add(jbutton);
    }

    // MODIFIES: this, e
    // EFFECTS: calls appropriate methods based on a particular action event
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadFile) {
            loadFile();
        }
        if (e.getSource() == addPassword) {
            addPassword();
        }
        if (e.getSource() == save) {
            savePassword();
        }
        if (e.getSource() == saveToFile) {
            saveToFile();
        }
        if (e.getSource() == saveEdit) {
            editPasswordInfo();
        }
        if (e.getSource() == generate) {
            generatePasswordInfo();
        }
        handleUncertainButtons(e);

        refresh();
    }

    // MODIFIES: e
    // EFFECTS: Handles events when a button with either delete or edit text field is pressed
    public void handleUncertainButtons(ActionEvent e) {
        if (((JButton)e.getSource()).getText().equals("delete")) {
            deletePassword(((JButton)e.getSource()));
        }
        if (((JButton)e.getSource()).getText().equals("edit")) {
            editPassword(((JButton)e.getSource()));
        }
    }

    // MODIFIES: this
    // EFFECTS: loads file data into passwordInfoList and displays the GUI
    public void loadFile() {
        allPasswords.removeAll();
        try {
            passwordInfoList = jsonReader.read();
        } catch (IOException e) {
            JLabel error = new JLabel("Error while loading file");
            error.setBackground(Color.white);
            error.setFont(new Font("Consolas", Font.BOLD, 25));
            error.setForeground(Color.black);
            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
        }
        panels = new ArrayList<>();
        showPasswords();
    }

    // MODIFIES: this
    // EFFECTS: updates the GUI
    public void refresh() {
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    // MODIFIES: this
    // EFFECTS: displays all the passwords
    public void showPasswords() {
        allPasswords.setBounds(200, 350, 1500, 600);
        allPasswords.setLayout(new GridLayout(9, 1, 0, 0));
        allPasswords.setBorder(BorderFactory.createEmptyBorder(90, 0, 0, 0));
        allPasswords.setBackground(Color.black);

        addTitle();

        for (PasswordInfo passwordInfo : passwordInfoList.getPasswordInfoList()) {
            showPassword(passwordInfo);
        }

        allPasswords.setVisible(true);
        frame.add(allPasswords);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Adds title for displaying the list of password info
    public void addTitle() {
        JPanel titles = new JPanel();
        titles.setLayout(new GridLayout(1, 5, 0, 0));
        titles.setBorder(BorderFactory.createEmptyBorder(0, 80, 25, 0));
        titles.setBackground(Color.black);

        JLabel titleWebsite = new JLabel("Website");
        displayTitle(titleWebsite);
        titles.add(titleWebsite);

        JLabel titleUsername = new JLabel("Username");
        displayTitle(titleUsername);
        titles.add(titleUsername);

        JLabel titleLoginID = new JLabel("Login ID");
        displayTitle(titleLoginID);
        titles.add(titleLoginID);

        JLabel titlePassword = new JLabel("Password");
        displayTitle(titlePassword);
        titles.add(titlePassword);

        JLabel titleOptions = new JLabel("Options");
        displayTitle(titleOptions);
        titles.add(titleOptions);

        titles.setVisible(true);
        allPasswords.add(titles);
    }

    // MODIFIES: jlabel
    // EFFECTS: styles jlabel
    public void displayTitle(JLabel jlabel) {
        jlabel.setFont(new Font("Consolas", Font.PLAIN, 30));
        jlabel.setForeground(Color.white);
        jlabel.setBackground(Color.black);
    }

    // MODIFIES: this, passwordInfo
    // EFFECTS: displays the password info for passwordInfo
    public void showPassword(PasswordInfo passwordInfo) {
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new GridLayout(1, 5, 0, 0));
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
        passwordPanel.setBackground(Color.black);

        JTextField websiteName = new JTextField();
        websiteName.setText(passwordInfo.getWebsiteName());
        stylePasswordInfo(websiteName, passwordPanel);

        JTextField username = new JTextField();
        username.setText(passwordInfo.getUsername());
        stylePasswordInfo(username, passwordPanel);

        JTextField loginID = new JTextField();
        loginID.setText(passwordInfo.getLoginID());
        stylePasswordInfo(loginID, passwordPanel);

        JTextField password = new JTextField();
        password.setText(passwordInfo.getPassword());
        stylePasswordInfo(password, passwordPanel);

        optionsPasswordPanel(passwordPanel);
        passwordPanel.setVisible(true);
        allPasswords.add(passwordPanel);
        allPasswords.setVisible(true);

        panels.add(passwordPanel);
    }

    // MODIFIES: jtextField, passwordPanel
    // EFFECTS: styles jtextField and adds it to passwordPanel
    public void stylePasswordInfo(JTextField jtextField, JPanel passwordPanel) {
        jtextField.setBorder(BorderFactory.createEtchedBorder());
        jtextField.setCaretColor(Color.white);
        jtextField.setEditable(false);
        display(jtextField);
        passwordPanel.add(jtextField);
    }

    // MODIFIES: jtextField
    // EFFECTS: styles jtextField
    public void display(JTextField jtextField) {
        jtextField.setFont(new Font("Consolas", Font.PLAIN, 18));
        jtextField.setForeground(Color.white);
        jtextField.setBackground(Color.black);
    }

    // MODIFIES: passwordPanel
    // EFFECTS: creates option menu for editing or deleting a particular password
    public void optionsPasswordPanel(JPanel passwordPanel) {
        JPanel options = new JPanel();
        options.setBackground(Color.black);
        options.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 100));
        options.setLayout(new GridLayout(1, 2, 0, 0));

        JButton edit = new JButton("edit");
        styleOptionButtonsPasswordPanel(edit);

        JButton delete = new JButton("delete");
        styleOptionButtonsPasswordPanel(delete);

        options.add(edit);
        options.add(delete);
        options.setVisible(true);
        passwordPanel.add(options);
    }

    // MODIFIES: jbutton
    // EFFECTS: styles jbutton
    public void styleOptionButtonsPasswordPanel(JButton jbutton) {
        jbutton.setBackground(Color.black);
        jbutton.setForeground(Color.white);
        jbutton.setFont(new Font("Consolas", Font.BOLD, 18));
        jbutton.setFocusable(false);
        jbutton.setBorder(BorderFactory.createEmptyBorder());
        jbutton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: adds password graphics to a list of password graphics
    public void addPassword() {
        JPanel addPasswordPanel = new JPanel();
        addPasswordPanel.setLayout(new GridLayout(1, 5, 0, 0));
        addPasswordPanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
        addPasswordPanel.setBackground(Color.black);

        websiteName = new JTextField("website");
        styleAddedPasswordInfo(websiteName, addPasswordPanel);

        username = new JTextField("username");
        styleAddedPasswordInfo(username, addPasswordPanel);

        loginID = new JTextField("login ID");
        styleAddedPasswordInfo(loginID, addPasswordPanel);

        password = new JTextField("password");
        styleAddedPasswordInfo(password, addPasswordPanel);

        addPasswordPanel.add(optionsForAddedPasswordInfo());

        if (!allPasswords.isDisplayable()) {
            panels = new ArrayList<>();
            showPasswords();
        }

        addPasswordPanel.setVisible(true);
        allPasswords.add(addPasswordPanel);
        allPasswords.setVisible(true);
    }

    // MODIFIES: jtextField, addPasswordPanel
    // EFFECTS: styles jtextField and adds it to addPasswordPanel
    public void styleAddedPasswordInfo(JTextField jtextField, JPanel addPasswordPanel) {
        jtextField.setBorder(BorderFactory.createEtchedBorder());
        jtextField.setCaretColor(Color.white);
        display(jtextField);
        addPasswordPanel.add(jtextField);
    }

    // MODIFIES: this
    // EFFECTS: provides options after user edits a passwordInfo
    public JPanel optionsForAddedPasswordInfo() {
        JPanel options = new JPanel();
        options.setBackground(Color.black);
        options.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
        options.setLayout(new GridLayout(1, 3, 0, 0));

        save = new JButton("save");
        styleOptionsForAddedPasswordInfo(save);

        generate = new JButton("gen");
        styleOptionsForAddedPasswordInfo(generate);

        generateLength();

        options.add(save);
        options.add(generate);
        options.add(generateLength);
        options.setVisible(true);

        return options;
    }

    // MODIFIES: jbutton
    // EFFECTS: styles jbutton and adds ActionListener
    public void styleOptionsForAddedPasswordInfo(JButton jbutton) {
        jbutton.setBackground(Color.black);
        jbutton.setForeground(Color.white);
        jbutton.setFont(new Font("Consolas", Font.BOLD, 18));
        jbutton.setFocusable(false);
        jbutton.setBorder(BorderFactory.createEmptyBorder());
        jbutton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: assigns 15 as JTextField to generateLength
    public void generateLength() {
        generateLength = new JTextField("15");
        generateLength.setBorder(BorderFactory.createEtchedBorder());
        generateLength.setCaretColor(Color.white);
        display(generateLength);
    }

    // MODIFIES: this
    // EFFECTS: saves current password info
    public void savePassword() {
        PasswordInfo passwordInfo = new  PasswordInfo();
        passwordInfo.setWebsiteName(websiteName.getText());
        passwordInfo.setUsername(username.getText());
        passwordInfo.setLoginID(loginID.getText());
        passwordInfo.setPassword(password.getText());
        passwordInfoList.addPasswordInfo(passwordInfo);

        allPasswords.removeAll();
        allPasswords.revalidate();
        allPasswords.repaint();
        panels = new ArrayList<>();
        showPasswords();
    }

    // MODIFIES: this
    // EFFECTS: saves passwordInfoList to file
    public void saveToFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(passwordInfoList);
            jsonWriter.close();
            JLabel fileSaved = new JLabel("Saved to file");
            fileSaved.setBackground(Color.white);
            fileSaved.setFont(new Font("Consolas", Font.BOLD, 25));
            fileSaved.setForeground(Color.black);
            JOptionPane.showMessageDialog(null, fileSaved, "Message", JOptionPane.PLAIN_MESSAGE);
        } catch (FileNotFoundException e) {
            JLabel error = new JLabel("Error while saving to file (file not found)");
            error.setBackground(Color.white);
            error.setFont(new Font("Consolas", Font.BOLD, 25));
            error.setForeground(Color.black);
            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this, jbutton
    // EFFECTS: deletes current password
    public void deletePassword(JButton jbutton) {
        password(jbutton);
        passwordInfoList.remove(passwordInfoList.getIndexOfPasswordInfo(passwordInfo.getWebsiteName()));
        panels = new ArrayList<>();
        allPasswords.removeAll();
        allPasswords.revalidate();
        allPasswords.repaint();
        showPasswords();
    }

    // MODIFIES: this, jbutton
    // EFFECTS: finds which passwordInfo was selected when a particular button for pressed
    public void password(JButton jbutton) {
        for (int i = 0; i < panels.size(); i++) {
            if (panels.get(i).equals(jbutton.getParent().getParent())) {
                passwordInfo = passwordInfoList.get(i);
                break;
            }
        }
    }

    // MODIFIES: this, jbutton
    // EFFECTS: allows editing current password
    public void editPassword(JButton jbutton) {
        Component[] components = jbutton.getParent().getParent().getComponents();
        int i = 1;
        for (Component c: components) {
            if (i < 5 && i > 1) {
                ((JTextField)c).setEditable(true);
            }
            i++;
        }

        changeButtonEdit(jbutton);

        i = 1;
        for (Component c: components) {
            if (i == 1) {
                websiteName = (JTextField)c;
            } else if (i == 2) {
                username = (JTextField)c;
            } else if (i == 3) {
                loginID = (JTextField)c;
            } else if (i == 4) {
                password = (JTextField)c;
            }
            i++;
        }
        refresh();
    }

    // MODIFIES: jbutton
    // EFFECTS: replaces the options menu for current passwordInfo
    public void changeButtonEdit(JButton jbutton) {
        jbutton.getParent().getParent().add(optionsForEditPasswordInfo());
        jbutton.getParent().getParent().remove(jbutton.getParent());
    }

    // MODIFIES: this
    // EFFECTS: creates new options when the user chooses to edit a passwordInfo
    public JPanel optionsForEditPasswordInfo() {
        JPanel options = new JPanel();
        options.setBackground(Color.black);
        options.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
        options.setLayout(new GridLayout(1, 3, 0, 0));

        generate = new JButton("gen");
        styleEditPasswordInfo(generate);

        generateLength();

        saveEdit = new JButton("save");
        styleEditPasswordInfo(saveEdit);

        options.add(saveEdit);
        options.add(generate);
        options.add(generateLength);
        options.setVisible(true);

        return options;
    }

    // MODIFIES: jbutton
    // EFFECTS: styles jbutton
    public void styleEditPasswordInfo(JButton jbutton) {
        jbutton.setBackground(Color.black);
        jbutton.setForeground(Color.white);
        jbutton.setFont(new Font("Consolas", Font.BOLD, 18));
        jbutton.setFocusable(false);
        jbutton.setBorder(BorderFactory.createEmptyBorder());
        jbutton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: generates a password ane updates the graphic field to display the generated password
    public void generatePasswordInfo() {
        passwordInfo = new PasswordInfo();
        passwordInfo.generatePassword(Integer.parseInt(generateLength.getText()));
        password.setText(passwordInfo.getPassword());
    }

    // MODIFIES: this
    // EFFECTS: edits current PasswordInfo in passwordInfoList
    private void editPasswordInfo() {
        PasswordInfo localPasswordInfo = new PasswordInfo();
        localPasswordInfo.setWebsiteName(websiteName.getText());
        localPasswordInfo.setUsername(username.getText());
        localPasswordInfo.setLoginID(loginID.getText());
        localPasswordInfo.setPassword(password.getText());
        passwordInfoList.edit(passwordInfoList.getIndexOfPasswordInfo(websiteName.getText()), localPasswordInfo);
        allPasswords.removeAll();
        allPasswords.revalidate();
        allPasswords.repaint();
        panels = new ArrayList<>();
        showPasswords();
    }
}
