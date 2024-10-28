/**
 * Copyright (c) 2008-2013 by DHCC
 * All rights reserved.
 */

package com.student.pojo.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * ����ʵ���˽�һ�����ת��ΪExcel��񣬲��ҿ��Դ�Excel����ж�ȡ��һ��List������
 * ����������BeanUtils����еķ������
 * ʹ�ø����ǰ�ᣬ����Ӧ��ʵ�������ͨ��ExcelReources�������Ӧ��ע��
 * 
 * @author <a href="mailto:zhangyanyu@dhcc.com.cn">zhangyanyu</a>
 * @date 2013-7-18
 */
public class ExcelUtil {
    private static ExcelUtil eu = new ExcelUtil();
    private ExcelUtil(){}
    
    public static ExcelUtil getInstance(){
        return eu;
    }
    
    /**
     * ������ת��ΪExcel���ҵ������÷����ǻ���ģ��ĵ�����������һ�������·����
     * @param datas ģ���е��滻�ĳ�������
     * @param template ģ��·��
     * @param os ���·��
     * @param objs �����б�
     * @param clz ���������
     * @param isClasspath ģ���Ƿ���classPath·����
     */
    public void exportObj2ExcelByTemplate(Map<String, String> datas,String template, String outPath, List<?> objs, Class<?> clz, boolean isClasspath) throws SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException{
        ExcelTemplate et = ExcelTemplate.getInstance();
        if(isClasspath){
            et.readTemplateByClasspath(template);
        }else{
            et.readTemplateByPath(template);
        }
        List<ExcelHeader> headers = getHeaderList(clz);
        Collections.sort(headers);
        //�������
        et.createNewRow();
        for (ExcelHeader eh : headers) {
            et.createCell(eh.getTitle());
        }
        //���ֵ
        for(Object obj : objs){
            et.createNewRow();
            for (ExcelHeader eh : headers) {
                et.createCell(BeanUtils.getProperty(obj,getMethodName(eh) ));
            }
        }
        et.replaceFinalData(datas);
        et.writeToFile(outPath);
    }
    
    /**
     * ��������Excel�����ǻ���ģ��ģ�ֱ���½�һ��Excel��ɵ���������·���ĵ���
     * @param outPath ����·��
     * @param objs �����б�
     * @param clz ָ������
     * @param isXssf �Ƿ���2007�汾
     */
    public void exportObj2Excel(String outPath, List<?> objs, Class<?> clz, boolean isXssf) throws SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, IOException{
        Workbook wb = null;
        if(isXssf){
            wb = new XSSFWorkbook();
        }else{
            wb = new HSSFWorkbook();
        }
        Sheet sheet = wb.createSheet();
        Row r = sheet.createRow(0);
        List<ExcelHeader> headers = getHeaderList(clz);
        Collections.sort(headers);
        //д����
        for (int i = 0; i<headers.size(); i++) {
            r.createCell(i).setCellValue(headers.get(i).getTitle());
        }
        //д����
        Object obj = null;
        for (int i = 0; i<objs.size(); i++) {
            r = sheet.createRow(i+1);
            obj = objs.get(i);
            for (int j = 0; j<headers.size(); j++) {
                r.createCell(j).setCellValue(BeanUtils.getProperty(obj, getMethodName(headers.get(j))));
            }
        }
        FileOutputStream fos = new FileOutputStream(outPath);
        wb.write(fos);
        fos.close();
    }
    
    /**
     * ���ݱ����ȡ��Ӧ�ķ�������
     * @param eh
     * @return
     */    
    private String getMethodName(ExcelHeader eh){
        String mn = eh.getMethodName().substring(3);
        mn = mn.substring(0,1).toLowerCase()+mn.substring(1);
        return mn;
    }
    
    /**
     * �õ�ָ�����ExcelHeader����
     * @param clz ָ������
     */
    private List<ExcelHeader> getHeaderList(Class<?> clz){
        List<ExcelHeader> headers = new ArrayList<ExcelHeader>();
        for(Method m: clz.getDeclaredMethods()){
            if(m.getName().startsWith("get")){
                if(m.isAnnotationPresent(ExcelResources.class)){
                    ExcelResources er = m.getAnnotation(ExcelResources.class);
                    headers.add(new ExcelHeader(er.title(), er.order(), m.getName()));
                }
            }
        }
        return headers;
    }
    
    /**
     * ����·����ȡ��Ӧ��Excel�ļ��������б�
     * @param path ��·���µ�path
     * @param clz ָ����
     * @param readLine ��ʼ�У������Ǳ���������
     * @param tailLine �ײ��ж����У��ڶ������ʱ�����ȥ��Щ��
     * @return
     */
    public List<Object> readExcel2ObjsByClasspath(String path, Class<?> clz, int readLine, int tailLine) throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException, SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException{
        Workbook wb = null;
        wb = WorkbookFactory.create(ExcelUtil.class.getResourceAsStream(path));
        return handlerExcel2Objs(wb, clz, readLine, tailLine);
    }
    
    /**
     * ���ļ�·����ȡ��Ӧ��Excel�ļ��������б�
     * @param path �ļ�·���µ�path
     * @param clz ��������
     * @param readLine ��ʼ�У�ע���Ǳ���������
     * @param tailLine �ײ��ж����У��ڶ������ʱ�����ȥ��Щ��
     * @return
     */
    public List<Object> readExcel2ObjsByPath(String path, Class<?> clz, int readLine, int tailLine) throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException, SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException{
        Workbook wb = null;
        wb = WorkbookFactory.create(new File(path));
        return handlerExcel2Objs(wb, clz, readLine, tailLine);
    }    
    
    /**
     * �õ�Excel��Ķ��󼯺�
     * @param wb Excel����������
     * @param clz ָ������
     * @param readLine ��ʼ�У�ע���Ǳ���������
     * @param tailLine �ײ��ж����У��ڶ������ʱ�����ȥ��Щ��
     * @return
     */
    public List<Object> handlerExcel2Objs(Workbook wb, Class<?> clz, int readLine, int tailLine) throws InstantiationException, IllegalAccessException, SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException{
        Sheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(readLine);
        List<Object> objs = new ArrayList<Object>();
        Map<Integer,String> maps = getHeaderMap(row, clz);
        if(maps == null||maps.size()<=0){
            throw new RuntimeException("Ҫ��ȡ��Excel�ĸ�ʽ����ȷ������Ƿ��趨�˺��ʵ���");
        }
        Object obj = null;
        for(int i = readLine+1; i<=sheet.getLastRowNum()-tailLine; i++){
            row = sheet.getRow(i);
            obj = clz.newInstance();
            for(Cell c : row){
                int ci = c.getColumnIndex();
                String mn = maps.get(ci).substring(3);
                mn = mn.substring(0,1).toLowerCase()+mn.substring(1);
                BeanUtils.copyProperty(obj, mn, this.getCellValue(c));
            }
            objs.add(obj);
        }
        return objs;
    }
    
    /**
     * �õ��������������Ͷ�Ӧ��������Map����
     * @param titleRow ������
     * @param clz ָ����
     */
    private Map<Integer,String> getHeaderMap(Row titleRow, Class<?> clz){
        List<ExcelHeader> headers = getHeaderList(clz);
        Map<Integer, String> maps = new HashMap<Integer, String>();
        for(Cell c : titleRow){
            String title = c.getStringCellValue();
            for (ExcelHeader eh : headers) {
                if(eh.getTitle().equals(title)){
                    maps.put(c.getColumnIndex(), eh.getMethodName().replace("get", "set"));
                    break;
                }
            }
        }
        return maps;
    }
    
    /**
     * �ѵ�Ԫ���е�����ת��ΪString����
     * @param c ��Ԫ��
     */
    private String getCellValue(Cell c) {
        String o = null;
        switch (c.getCellType()) {
        case Cell.CELL_TYPE_BLANK:
            o = ""; break;
        case Cell.CELL_TYPE_BOOLEAN:
            o = String.valueOf(c.getBooleanCellValue()); break;
        case Cell.CELL_TYPE_FORMULA:
            o = String.valueOf(c.getCellFormula()); break;
        case Cell.CELL_TYPE_NUMERIC:
            o = String.valueOf(c.getNumericCellValue()); break;
        case Cell.CELL_TYPE_STRING:
            o = c.getStringCellValue(); break;
        default:
            o = null;
            break;
        }
        return o;
    }
    
}
