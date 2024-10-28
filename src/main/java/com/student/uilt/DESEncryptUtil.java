package com.student.uilt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

/**
 * <ul>
 * <li>Title:[DESEncryptUtil]</li>
 * <li>Description: [�����������]</li>
 * <li>Copyright 2009 RoadWay Co., Ltd.</li>
 * <li>All right reserved.</li>
 * <li>Created by [Huyvanpull] [Jul 19, 2010]</li>
 * <li>Midified by [�޸���] [�޸�ʱ��]</li>
 * </ul>
 * 
 * @version 1.0
 */
public class DESEncryptUtil
{
    public static void main(String[] args) throws Exception
    {
        /** ����KEY */
        String operatorType = "key";
        String keyFilePath = "D:/key.k";
        DESEncryptUtil.test(keyFilePath, null, operatorType);
        
        /** ���� */
        operatorType = "encrypt";
        String sourceFilePath = "D:/database.properties";
        DESEncryptUtil.test(keyFilePath, sourceFilePath, operatorType);
        
        /** ���� */
        operatorType = "decrypt";
        sourceFilePath = "D:/database.properties";
        DESEncryptUtil.test(keyFilePath, sourceFilePath, operatorType);
    }
    /**
     * <ul>
     * <li>Description:[����һ����Կ]</li>
     * <li>Created by [Huyvanpull] [Jul 19, 2010]</li>
     * <li>Midified by [�޸���] [�޸�ʱ��]</li>
     * </ul>
     * 
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Key createKey() throws NoSuchAlgorithmException
    {
        Security.insertProviderAt(new com.sun.crypto.provider.SunJCE(), 1);
        KeyGenerator generator = KeyGenerator.getInstance("DES");
        generator.init(new SecureRandom());
        Key key = generator.generateKey();
        return key;
    }
    
    /**
     * <ul>
     * <li>Description:[�������õ���Կ]</li>
     * <li>Created by [Huyvanpull] [Jul 19, 2010]</li>
     * <li>Midified by [�޸���] [�޸�ʱ��]</li>
     * </ul>
     * 
     * @param is
     * @return
     */
    public static Key getKey(InputStream is)
    {
        try
        {
            ObjectInputStream ois = new ObjectInputStream(is);
            return (Key) ois.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * <ul>
     * <li>Description:[�����ݽ��м���]</li>
     * <li>Created by [Huyvanpull] [Jul 19, 2010]</li>
     * <li>Midified by [�޸���] [�޸�ʱ��]</li>
     * </ul>
     * 
     * @param key
     * @param data
     * @return
     */
    private static byte[] doEncrypt(Key key, byte[] data)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] raw = cipher.doFinal(data);
            return raw;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * <ul>
     * <li>Description:[�����ݽ��н���]</li>
     * <li>Created by [Huyvanpull] [Jul 19, 2010]</li>
     * <li>Midified by [�޸���] [�޸�ʱ��]</li>
     * </ul>
     * 
     * @param key
     * @param in
     * @return
     */
    public static InputStream doDecrypt(Key key, InputStream in)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] tmpbuf = new byte[1024];
            int count = 0;
            while ((count = in.read(tmpbuf)) != -1)
            {
                bout.write(tmpbuf, 0, count);
                tmpbuf = new byte[1024];
            }
            in.close();
            byte[] orgData = bout.toByteArray();
            byte[] raw = cipher.doFinal(orgData);
            ByteArrayInputStream bin = new ByteArrayInputStream(raw);
            return bin;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    private static void test(String keyFilePath, String sourceFilePath,
            String operatorType) throws Exception
    {
        // �ṩ��Java����ʹ�øù��ߵĹ���
        if (operatorType.equalsIgnoreCase("key"))
        {
            // ������Կ�ļ�
            Key key = DESEncryptUtil.createKey();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(keyFilePath));
            oos.writeObject(key);
            oos.close();
            System.out.println("�ɹ�������Կ�ļ�" + keyFilePath);
        }
        else if (operatorType.equalsIgnoreCase("encrypt"))
        {
            // ���ļ����м���
            File file = new File(sourceFilePath);
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] tmpbuf = new byte[1024];
            int count = 0;
            while ((count = in.read(tmpbuf)) != -1)
            {
                bout.write(tmpbuf, 0, count);
                tmpbuf = new byte[1024];
            }
            in.close();
            byte[] orgData = bout.toByteArray();
            Key key = getKey(new FileInputStream(keyFilePath));
            byte[] raw = DESEncryptUtil.doEncrypt(key, orgData);
            file = new File(file.getParent() + "\\en_" + file.getName());
            FileOutputStream out = new FileOutputStream(file);
            out.write(raw);
            out.close();
            System.out.println("�ɹ����ܣ������ļ�λ��:" + file.getAbsolutePath());
        }
        else if (operatorType.equalsIgnoreCase("decrypt"))
        {
            // ���ļ����н���
            File file = new File(sourceFilePath);
            FileInputStream fis = new FileInputStream(file);
            
            Key key = getKey(new FileInputStream(keyFilePath));
            InputStream raw = DESEncryptUtil.doDecrypt(key, fis);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] tmpbuf = new byte[1024];
            int count = 0;
            while ((count = raw.read(tmpbuf)) != -1)
            {
                bout.write(tmpbuf, 0, count);
                tmpbuf = new byte[1024];
            }
            raw.close();
            byte[] orgData = bout.toByteArray();
            file = new File(file.getParent() + "\\rs_" + file.getName());
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(orgData);
            System.out.println("�ɹ����ܣ������ļ�λ��:" + file.getAbsolutePath());
        }
    }
}