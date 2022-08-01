package ro.sda.javaro35.finalProject.utils;

import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class ImageUtil {
    public static String getImageData(byte[] byteData) {
        if (byteData == null) {
            return "";
        }
        return Base64.getMimeEncoder().encodeToString(byteData);
    }

    public static String resizeAndCrop(MultipartFile file, int width, int height){
        try {
            return resizeAndCropInternal(file, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String resizeAndCropInternal(MultipartFile file, int width, int height) throws IOException {
        byte[] byteData = null;
        File tempFile = new File("src/main/resources/targetFile.tmp");

        try (OutputStream os = new FileOutputStream(tempFile)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage bufferedImage = ImageIO.read(tempFile);
        float ratio = (float) bufferedImage.getWidth() / (float) bufferedImage.getHeight();
        float targetRatio = (float) width/height;
        BufferedImage outputImage = null;
        if (ratio <= targetRatio){
            //Fit to W
            outputImage = Scalr.resize(bufferedImage, Scalr.Mode.FIT_TO_WIDTH, width, height);
            int cropY = outputImage.getHeight()/2 - height/2;
            outputImage = Scalr.crop(outputImage, 0, cropY, width, height);
        }else{
            //Fit to H
            outputImage = Scalr.resize(bufferedImage, Scalr.Mode.FIT_TO_HEIGHT, width, height);
            int cropX = outputImage.getWidth()/2 - width/2;
            outputImage = Scalr.crop(outputImage, cropX, 0, width, height);
        }
        Path path = Paths.get(tempFile.getParent(),"resized.jpg" );
        File newImageFile = path.toFile();
        ImageIO.write(outputImage, "jpg", newImageFile);
        outputImage.flush();

        return getImageData(Files.readAllBytes(newImageFile.toPath()));

    }
}
