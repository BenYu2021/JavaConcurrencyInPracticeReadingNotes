package ch06;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: ymm
 * @date: 2021/8/1
 * @version: 1.0.0
 * @description: 6-10 串行地渲染页面元素
 */
public abstract class SingleThreadRenderer {
    void renderPage(CharSequence source) {
        renderText(source);
        ArrayList<ImageData> imageData = new ArrayList<>();
        List<ImageInfo> imageInfos = scanForImageInfo(source);

        for (ImageInfo imageInfo : scanForImageInfo(source)) {
            imageData.add(imageInfo.downloadImage());
        }
        for (ImageData data : imageData) {
            renderImage(data);
        }
    }

    abstract void renderImage(ImageData data);

    abstract List<ImageInfo> scanForImageInfo(CharSequence source);

    abstract void renderText(CharSequence source);

    interface ImageData {

    }

    interface ImageInfo {
        ImageData downloadImage();
    }
}
