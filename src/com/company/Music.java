package com.company;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.FloatControl.Type;

public class Music extends Thread {
    private String fileName;
    private final int EXTERNAL_BUFFER_SIZE = 524288;

    public Music(String wavFile) {
        this.fileName = wavFile;
    }

    public void run() {
        File soundFile = new File(this.fileName);
        if (!soundFile.exists()) {
            System.err.println("Wave file not found:" + this.fileName);
        } else {
            label102:
            while(true) {
                AudioInputStream audioInputStream = null;

                try {
                    audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                } catch (UnsupportedAudioFileException var18) {
                    var18.printStackTrace();
                    return;
                } catch (IOException var19) {
                    var19.printStackTrace();
                    return;
                }

                AudioFormat format = audioInputStream.getFormat();
                SourceDataLine auline = null;
                Info info = new Info(SourceDataLine.class, format);

                try {
                    auline = (SourceDataLine)AudioSystem.getLine(info);
                    auline.open(format);
                } catch (LineUnavailableException var16) {
                    var16.printStackTrace();
                    return;
                } catch (Exception var17) {
                    var17.printStackTrace();
                    return;
                }

                if (auline.isControlSupported(Type.PAN)) {
                    FloatControl var6 = (FloatControl)auline.getControl(Type.PAN);
                }

                auline.start();
                int nBytesRead = 0;
                byte[] abData = new byte[524288];

                try {
                    while(true) {
                        if (nBytesRead == -1) {
                            continue label102;
                        }

                        nBytesRead = audioInputStream.read(abData, 0, abData.length);
                        if (nBytesRead >= 0) {
                            auline.write(abData, 0, nBytesRead);
                        }
                    }
                } catch (IOException var20) {
                    var20.printStackTrace();
                } finally {
                    auline.drain();
                }

                return;
            }
        }
    }
}
