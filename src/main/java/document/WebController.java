package document;

import org.jodconverter.office.OfficeException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

@Controller
public class WebController {


    /**
     * 获取上传页面
     * @return
     */
    @RequestMapping(value = "upload", method = RequestMethod.GET)
    public String show() {
        return "upload";
    }

    /**
     * 查看pdf页面， 路径加上 file=xx 查看pdf
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "viewer";
    }

    /**
     * 下载文件
     * @param fileName
     * @param request
     * @param response
     */
    @RequestMapping(value = "download/{fileName}", method = RequestMethod.GET)
    public void index(@PathVariable("fileName")String fileName,  HttpServletRequest request,
                        HttpServletResponse response) {
        try {
            File file = new File("/pmfile/" +fileName + ".pdf");
            FileChannel channel = new FileInputStream(file).getChannel();
            ServletOutputStream out = response.getOutputStream();
            WritableByteChannel channel_out = Channels.newChannel(out);
            channel.transferTo(0,file.length(),channel_out);
            out.flush();
            out.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 上传文件
     * @return
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam(value = "file",required = false) MultipartFile file) throws OfficeException {
        File uploadFile = saveFile(file);
        File output = new File("/pmfile/" + System.currentTimeMillis() + ".pdf");
        OpenOfficeUtl.convert(uploadFile, output);
        return output.getName();
    }

    /**
     * 保存文件到本地
     * @param file
     * @return
     */
    public File saveFile(MultipartFile file){
        BufferedOutputStream out = null;
        try {
            File file1 = new File("/pmfile/" + file.getOriginalFilename());
            out = new BufferedOutputStream(new FileOutputStream(file1));
            out.write(file.getBytes());
            out.flush();
            return file1;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
