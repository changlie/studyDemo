package com.changlie.pattern;

import org.junit.Test;

public class s06Adapter {

    interface MediaPlayer {
        public void play(String audioType, String fileName);
    }

    interface AdvancedMediaPlayer {
        public void playVlc(String fileName);
        public void playMp4(String fileName);
    }

    class VlcPlayer implements AdvancedMediaPlayer{
        @Override
        public void playVlc(String fileName) {
            System.out.println("Playing vlc file. Name: "+ fileName);
        }

        @Override
        public void playMp4(String fileName) {
            //什么也不做
        }
    }

    class Mp4Player implements AdvancedMediaPlayer{

        @Override
        public void playVlc(String fileName) {
            //什么也不做
        }

        @Override
        public void playMp4(String fileName) {
            System.out.println("Playing mp4 file. Name: "+ fileName);
        }
    }

    class MediaAdapter implements MediaPlayer{
        AdvancedMediaPlayer advancedMediaPlayer;

        @Override
        public void play(String audioType, String fileName) {
            if(audioType.equalsIgnoreCase("vlc")){
                if(advancedMediaPlayer==null) advancedMediaPlayer = new VlcPlayer();

                advancedMediaPlayer.playVlc(fileName);
            }else if(audioType.equalsIgnoreCase("mp4")){
                if(advancedMediaPlayer==null) advancedMediaPlayer = new Mp4Player();

                advancedMediaPlayer.playMp4(fileName);
            }
        }
    }

    class AudioPlayer implements MediaPlayer {
        MediaAdapter mediaAdapter;

        @Override
        public void play(String audioType, String fileName) {
            //播放 mp3 音乐文件的内置支持
            if(audioType.equalsIgnoreCase("mp3")){
                System.out.println("Playing mp3 file. Name: "+ fileName);
            }
            //mediaAdapter 提供了播放其他文件格式的支持
            else if(audioType.equalsIgnoreCase("vlc")
                    || audioType.equalsIgnoreCase("mp4")){
                mediaAdapter = new MediaAdapter();
                mediaAdapter.play(audioType, fileName);
            }
            else{
                System.out.println("Invalid media. "+
                        audioType + " format not supported");
            }
        }
    }

    @Test
    public void test() throws Exception{
        AudioPlayer audioPlayer = new AudioPlayer();

        audioPlayer.play("mp3", "beyond the horizon.mp3");
        audioPlayer.play("mp4", "alone.mp4");
        audioPlayer.play("vlc", "far far away.vlc");
        audioPlayer.play("avi", "mind me.avi");
    }
}
