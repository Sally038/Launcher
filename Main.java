import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
  public static void main(String[] args) {
    // Создание окна
    JFrame frame = new JFrame("Launcher");
    frame.setSize(300, 500);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setUndecorated(true);
    frame.setResizable(false);

    // Создание панели с заданным цветом фона
    JPanel mainPanel = new JPanel(new CardLayout());
    mainPanel.setBackground(new Color(108, 48, 130));
    frame.add(mainPanel);

    // Создание панелей для каждой секции
    JPanel downloadPanel = createDownloadPanel();
    JPanel changelogPanel = createChangelogPanel();
    mainPanel.add(downloadPanel, "Download");
    mainPanel.add(changelogPanel, "Changelog");

    // Создание контейнера для кнопок
    JPanel buttonPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(10, 10, 10, 10);
    buttonPanel.setOpaque(false);

    // Создание кнопок
    JButton button1 = new JButton("Download");
    JButton button2 = new JButton("Changelog");
    JButton button3 = new JButton("Exit");

    // Добавление кнопок в контейнер
    buttonPanel.add(button1, gbc);
    gbc.gridy++;
    buttonPanel.add(button2, gbc);
    gbc.gridy++;
    buttonPanel.add(button3, gbc);
    frame.add(buttonPanel);

    // Создание контейнера для круглой кнопки
    JPanel statusPanel = new JPanel();
    statusPanel.setPreferredSize(new Dimension(50, 1));
    statusPanel.setBackground(new Color(108, 48, 130));
    frame.add(statusPanel, "East");

    // Создание круглой кнопки для отображения статуса интернет-соединения
    StatusButton statusButton = new StatusButton();
    statusButton.setPreferredSize(new Dimension(30, 30));
    statusPanel.add(statusButton);

    if (checkInternetConnection()) {
      statusButton.setColor(Color.GREEN);
    } else {
      statusButton.setColor(Color.RED);
    }

    // Установка действий для кнопок
    button1.addActionListener(e -> showPanel(mainPanel, "Download"));
    button2.addActionListener(e -> showPanel(mainPanel, "Changelog"));
    button3.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Confirmation",
            JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
          System.exit(0);
        }
      }
    });

    // Установка размеров и отображение окна
    frame.setVisible(true);
  }

  // Функция для проверки статуса интернет-соединения
  public static boolean checkInternetConnection() {
    try (Socket socket = new Socket()) {
      socket.connect(new InetSocketAddress("google.com", 80), 3000);
      return true;
    } catch (IOException ex) {
      return false;
    }
  }

  static class StatusButton extends JPanel {
    private Color color;

    public StatusButton() {
      super();
    }

    public void setColor(Color color) {
      this.color = color;
      repaint();
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(color);
      g.fillOval(0, 0, getWidth(), getHeight());
    }
  }

  // Создание панели для раздела Download
  private static JPanel createDownloadPanel() {
    JPanel panel = new JPanel();
    JLabel label = new JLabel("Download Panel");
    panel.add(label);
    return panel;
  }

  // Создание панели для раздела Changelog
  private static JPanel createChangelogPanel() {
    JPanel panel = new JPanel();
    JLabel label = new JLabel("Changelog Panel");
    panel.add(label);
    return panel;
  }

  // Показать указанную панель
  private static void showPanel(JPanel panel, String name) {
    CardLayout layout = (CardLayout) panel.getLayout();
    layout.show(panel, name);
  }
}
