import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.File;


public class SintezatorMenu extends JFrame {
    private Sintezator sintezator;
    private static String requiredFile;

    public SintezatorMenu() {
        super("Синтезатор");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null); // окно - в центре экрана
        setLayout(new GridLayout(4, 1));

        sintezator = new Sintezator();

        JButton button1 = new JButton("Воспроизвести MIDI");
        JButton button2 = new JButton("Воспроизвести TXT");
        JButton button3 = new JButton("Набрать мелодию в консоли");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("MIDI (*.mid)", "mid"));

                    int returnValue = fileChooser.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        requiredFile = selectedFile.getAbsolutePath();
                        System.out.println("Выбран файл: " + requiredFile);
                        sintezator.method1(requiredFile);
                    }
                } catch (MidiUnavailableException ex) {
                    throw new RuntimeException(ex);
                } catch (InvalidMidiDataException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Текстовой файл (*.txt)", "txt"));

                    int returnValue = fileChooser.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        requiredFile = selectedFile.getAbsolutePath();
                        System.out.println("Выбран файл: " + requiredFile);
                        sintezator.method2(requiredFile);
                    }
                } catch (MidiUnavailableException ex) {
                    throw new RuntimeException(ex);
                } catch (InvalidMidiDataException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sintezator.method3();
                } catch (MidiUnavailableException ex) {
                    throw new RuntimeException(ex);
                } catch (InvalidMidiDataException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        add(new JLabel("Выберите желаемое дийствие:"));
        add(button1);
        add(button2);
        add(button3);

        setVisible(true);
    }

    public static void main(String[] args) {
        new SintezatorMenu();
    }
}
