package document;

import org.jodconverter.OfficeDocumentConverter;
import org.jodconverter.office.DefaultOfficeManagerBuilder;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeManager;

import java.io.File;

public class OpenOfficeUtl {

    /**
     * openOffice目录，window 可能在C://Program Files (x86)//OpenOffice 4
     */
    private static final String openOfficeHome = "/opt/openoffice4/";
    /**
     * openOffice端口
     */
    private static final int openOfficePort = 8100;

    /**
     * 转化文档
     * @param input
     * @param output
     */
    public static void convert(File input , File output) throws OfficeException {
        try {
            DefaultOfficeManagerBuilder defaultOfficeManagerBuilder = new DefaultOfficeManagerBuilder();
            defaultOfficeManagerBuilder.setOfficeHome(openOfficeHome);
            defaultOfficeManagerBuilder.setPortNumber(openOfficePort);
            OfficeManager officeManager = defaultOfficeManagerBuilder.build();
            officeManager.start();

            //转换，自动识别转化类型
            OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
            converter.convert(input, output);

            officeManager.stop();
        } catch (OfficeException e) {
            throw e;
        }
    }

}
