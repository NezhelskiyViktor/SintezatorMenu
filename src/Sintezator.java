import javax.sound.midi.*;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Sintezator {
    public void method1(String requiredFile) throws MidiUnavailableException, InvalidMidiDataException, IOException, InterruptedException {
        Sequencer sequencer = MidiSystem.getSequencer();
        sequencer.open();

        Sequence sequence = MidiSystem.getSequence(new File(requiredFile));
        sequencer.setSequence(sequence);
        sequencer.start();
        var quit = true;
        while (quit) {
            Thread.sleep(1000);
            if (!sequencer.isRunning()) {
                sequencer.close();
                quit = false;
            }
        }
    }

    public void method2(String requiredFile) throws MidiUnavailableException, InvalidMidiDataException, IOException, InterruptedException {
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        Receiver receiver = synth.getReceiver();

        ShortMessage programChange = new ShortMessage();
        programChange.setMessage(ShortMessage.PROGRAM_CHANGE, 0, 0, 0);
        receiver.send(programChange, -1);

        var content = new String(Files.readAllBytes(Paths.get(requiredFile)));
        readString(receiver, content); // Записываем в мелодию

    }

    public void method3() throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        Receiver receiver = synth.getReceiver();
        System.out.println("Please, enter notes:");
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        while (!input.equals("q")) {
            readString(receiver, input); // Записываем в мелодию
            System.out.println("Please, enter notes:");
            input = scanner.nextLine();
        }

        scanner.close();
        synth.close();
        System.out.println("See you!");
    }

    private static void readString(Receiver receiver, String content) throws InvalidMidiDataException, InterruptedException {
        var notes = content.split(",");
        var i = 0;
        while (i < notes.length) {
            int noteCode = convertNote(notes[i].charAt(0), 12 * ( Integer.parseInt(String.valueOf(notes[i].charAt(1))))); // Основание ноты
            int duration = Integer.parseInt(notes[i].substring(2));
            receiver.send(new ShortMessage(ShortMessage.NOTE_ON,
                            0, noteCode, 100),-1); // Начать звучание ноты
            Thread.sleep(duration); // Ждать указанное время
            receiver.send(new ShortMessage(ShortMessage.NOTE_OFF,
                            0, noteCode, 100),-1); // Остановить звучание ноты
            i++;
        }
    }
    private static int convertNote(char note, int octava) {
        if (octava < 105) {
            switch (note) {
                case 'C':
                    return 0 + octava;
                case 'c':
                    return 1 + octava;
                case 'D':
                    return 2 + octava;
                case 'd':
                    return 3 + octava;
                case 'E','e':
                    return 4 + octava;
                case 'F':
                    return 5 + octava;
                case 'f':
                    return 6 + octava;
                case 'G':
                    return 7 + octava;
                case 'g':
                    return 8 + octava;
                case 'A':
                    return 9 + octava;
                case 'a':
                    return 10 + octava;
                case 'B', 'b':
                    return 11 + octava;
                default:
                    return 127; // Если нота не распознана, возвращается код 127
            }
        }
        return 127; // Если нота не распознана, возвращается код 127
    }

}
