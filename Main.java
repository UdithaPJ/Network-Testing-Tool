import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel outputPane;
    private JTextField commandField;
    private JButton runButton;
    private JButton stopButton;
    private static JTextPane textPane;

    private static boolean running = false;
    private Process currentProcess;
    private Thread outputThread;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Main frame = new Main();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void appendText(String text, Color color) {
        StyledDocument doc = textPane.getStyledDocument();
        Style style = textPane.addStyle("Style", null);
        StyleConstants.setForeground(style, color);
        try {
            doc.insertString(doc.getLength(), text + "\n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void stopProcess() {
        if (running && currentProcess != null) {
            currentProcess.destroy();
            appendText("\nProcess stopped by user.", Color.RED);
            running = false;
        }
    }

    public Main() {
        setTitle("Network Testing Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 300);
        outputPane = new JPanel();
        outputPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        outputPane.setLayout(new BorderLayout());

        setContentPane(outputPane);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        outputPane.add(topPanel, BorderLayout.NORTH);

        JLabel commandLabel = new JLabel("Command - ");
        topPanel.add(commandLabel);

        commandField = new JTextField(30);
        topPanel.add(commandField);

        runButton = new JButton("Run");
        runButton.setBackground(Color.cyan);
        runButton.setFocusable(false);
        runButton.addActionListener(this);
        topPanel.add(runButton);

        stopButton = new JButton("Stop");
        stopButton.setBackground(Color.red);
        stopButton.setFocusable(false);
        stopButton.addActionListener(e -> stopProcess());
        topPanel.add(stopButton);

        textPane = new JTextPane();
        textPane.setBackground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(textPane);
        outputPane.add(scrollPane, BorderLayout.CENTER);
    }

    private void runCommand(String command) {
        if (running) {
            appendText("A process is already running, please wait or stop it.", Color.RED);
            return;
        }

        running = true;
        String[] commandParts = command.split("\\s+");
        ProcessBuilder processBuilder = new ProcessBuilder(commandParts);

        try {
            currentProcess = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(currentProcess.getInputStream()));

            appendText("Command - " + command, Color.BLUE);

            outputThread = new Thread(() -> {
                try {
                    String line;
                    while ((line = reader.readLine()) != null && running) {
                        appendText(line, Color.WHITE);
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int exitCode = 0;
                try {
                    exitCode = currentProcess.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                appendText("\nExited with error code: " + exitCode, Color.RED);
                running = false;
            });
            outputThread.start();

        } catch (IOException e) {
            appendText("\nError executing the command: " + e.getMessage(), Color.RED);
            running = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == runButton) {
            String command = commandField.getText();
            commandField.setText("");
            runCommand(command);
        }
    }
}
