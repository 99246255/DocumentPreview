package document;

import org.apache.commons.io.IOUtils;
import org.jodconverter.office.OfficeException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

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
            FileInputStream fileInputStream = new FileInputStream(file);
            // 处理中文问题
            response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode(fileName,"UTF-8"));
            response.setContentType("multipart/form-data");
            OutputStream outputStream = response.getOutputStream();
            IOUtils.write(IOUtils.toByteArray(fileInputStream), outputStream);
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
