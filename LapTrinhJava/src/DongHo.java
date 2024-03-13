import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DongHo extends JFrame {

    private JPanel contentPane;
    private JPanel clockPanel;
    private JLabel timeLabel;
    private JTextField textField;
    private JButton btnNewButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DongHo frame = new DongHo();
                    frame.setVisible(true);
                    frame.startClock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public DongHo() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        clockPanel = new JPanel();
        clockPanel.setBounds(113, 48, 200, 50);
        contentPane.add(clockPanel);

        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        clockPanel.add(timeLabel);

        textField = new JTextField();
        textField.setBounds(81, 181, 96, 19);
        contentPane.add(textField);
        textField.setColumns(10);

        btnNewButton = new JButton("open");
        btnNewButton.setBounds(252, 180, 76, 20);
        contentPane.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openNewClock();
            }
        });
    }

    public void startClock() {
        ClockThread clockThread = new ClockThread();
        clockThread.start();
    }

    private class ClockThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String time = sdf.format(cal.getTime());
                    SwingUtilities.invokeLater(() -> timeLabel.setText(time));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void openNewClock() {
        String newTimeZone = textField.getText().trim();
        if (!newTimeZone.isEmpty()) {
            DongHo newClock = new DongHo();
            newClock.setTitle("World Clock - " + newTimeZone);
            newClock.setVisible(true);
            newClock.startClock();
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a time zone!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
