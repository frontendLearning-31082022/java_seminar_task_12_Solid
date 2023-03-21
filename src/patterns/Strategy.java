package patterns;

import java.awt.*;
import java.io.File;

public class Strategy {
    public static void main(String[] args) {
        //stategy open wide for client, state incapsulate own states
        PictureViewer pictureViewer=new PictureViewer();
        pictureViewer.show();
    }
}

class PictureViewer {
    DecodePicture decoder;
    public void show(){show(decode( open()));}
    private void show(Image image) {}
    private File open() {return null;}
    private void switchDecoder(DecodePicture decoder) {this.decoder = decoder;}
    private Image decode(File file) {
        if (file.getPath().matches(".png$")) switchDecoder(new DecodePicturePng());
        if (file.getPath().matches(".svg$")) switchDecoder(new DecodePictureSVG());
        if (file.getPath().matches(".psd$")) switchDecoder(new DecodePicturePSD());
        return decoder.decode(file);
    }
}


interface DecodePicture { Image decode(File file);}
class DecodePicturePng implements DecodePicture {
    @Override
    public Image decode(File file) {
        return null;
    }
}
class DecodePictureSVG implements DecodePicture {
    @Override
    public Image decode(File file) {
        return null;
    }
}
class DecodePicturePSD implements DecodePicture {
    @Override
    public Image decode(File file) {
        return null;
    }
}
