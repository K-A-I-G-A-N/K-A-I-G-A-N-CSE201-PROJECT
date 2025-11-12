import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FilterGUI extends JFrame {
    private static final long serialVersionUID = 1L;  // fixes serialization warning

    private JComboBox<String> platformBox;
    private JCheckBox freeOnlyBox;
    private JButton filterButton;
    private JTextArea resultArea;

    private List<App> allApps;   // global variable so ActionListener can access it
    private Filter filter;

    public FilterGUI() {
        super("App Filter GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout());

        // ---------- Top Controls ----------
        JPanel topPanel = new JPanel(new FlowLayout());

        platformBox = new JComboBox<>(new String[]{"", "Windows", "Mac", "Android", "iOS", "Web"});
        freeOnlyBox = new JCheckBox("Show Free Apps Only");
        filterButton = new JButton("Apply Filter");

        topPanel.add(new JLabel("Platform:"));
        topPanel.add(platformBox);
        topPanel.add(freeOnlyBox);
        topPanel.add(filterButton);
        add(topPanel, BorderLayout.NORTH);

        // ---------- Result Area ----------
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // ---------- Load App Data ----------
        allApps = AppLoader.loadAppsFromCSV("app_database.csv");
        filter = new Filter("GUI Filter");

        // ---------- Filter Button Action ----------
        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String platform = (String) platformBox.getSelectedItem();
                boolean freeOnly = freeOnlyBox.isSelected();

                List<App> results = filter.applyFilter(allApps, platform, freeOnly);
                resultArea.setText("");

                if (results.isEmpty()) {
                    resultArea.append("No apps match your filters.\n");
                } else {
                    for (App app : results) {
                        resultArea.append(app.toString() + "\n");
                    }
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new FilterGUI();
    }
}
