package top.raincold.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class FileUtils {
    /**
     * 检查如果路径不存在则创建
     * @param dirPath
     */
    public static void createDirPathIfNotExist(String dirPath) {
        File file =new File(dirPath);
        if (!file.exists() && !file.isDirectory()) {
            log.info("{}目录不存在，这个创建", dirPath);
            file.mkdirs();
        }
    }

    /**
     * 文件夹递归复制
     * @param srcDirPath
     * @param dstDirPath
     */
    public static void moveFiles(String srcDirPath, String dstDirPath) {
        File srcFile = new File(srcDirPath);
        File dstFile = new File(dstDirPath);
        createDirPathIfNotExist(dstDirPath);

        if (!srcFile.isDirectory()) {
            return;
        }

        File[] sonFileList = srcFile.listFiles();
        for (File sonFile : sonFileList) {
            String sonSrcDirPath = srcDirPath + File.separator + sonFile.getName();
            String sonDstDirPath = dstDirPath + File.separator + sonFile.getName();

            if (sonFile.isDirectory()) {
                moveFiles(sonSrcDirPath, sonDstDirPath);
            }
            if (sonFile.isFile()) {
                copy(sonSrcDirPath, sonDstDirPath);
            }
        }
    }

    /**
     * 复制文件
     * @param srcPathStr
     * @param desPathStr
     */
    public static void copy(String srcPathStr, String desPathStr) {
        System.out.println("源文件地址:"+srcPathStr);
        System.out.println("目标文件地址:"+desPathStr);
        try {
            FileInputStream fis = new FileInputStream(srcPathStr);//创建输入流对象
            FileOutputStream fos = new FileOutputStream(desPathStr); //创建输出流对象
            byte datas[] = new byte[1024 * 8];//创建搬运工具
            int len = 0;//创建长度
            while ((len = fis.read(datas)) != -1) { //循环读取数据
                fos.write(datas, 0, len);
            }
            fis.close();//释放资源
            fos.close();//释放资源
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
