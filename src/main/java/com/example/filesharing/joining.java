package com.example.filesharing;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class joining {

    static boolean compressed;
    static String fileName;

    File joinFiles() throws IOException {
        //get name from file
        File spDir=new File(System.getProperty("user.home")+"\\File Sharing\\temp\\sp");

        FileInputStream fisN=new FileInputStream(spDir+"\\part0.txt");

        byte[] b=fisN.readAllBytes();

        fisN.close();
// check compression

        if(b[0]==1){
           compressed=true;
        }
        else{
            compressed=false;
        }
//check file name lenght

        StringBuilder nameL=new StringBuilder();
        for(int i=1;i<4;i++){
            nameL.append((char)b[i]);
        }
        int L=Integer.parseInt(nameL.toString());

        //file name
        StringBuilder fileN=new StringBuilder();
        for(int i=4;i<4+L;i++) {
            fileN.append((char) b[i]);
        }

        int from=4+L;
        int to=b.length;

        byte fi[]= Arrays.copyOfRange(b,from,to);
        FileOutputStream fosN=new FileOutputStream(spDir+File.separator+"part0.txt");
        fosN.write(fi);
        fosN.flush();
        fosN.close();

        //get filname

        String name=fileN.toString();
        fileName=name;

        File finalFiledir=new File(System.getProperty("user.home")+"\\File Sharing\\temp\\joined");
        finalFiledir.mkdir();
        File finalFile=new File(finalFiledir+"\\"+name);

        FileOutputStream fos = new FileOutputStream(finalFile);
        int part = receiver.part;
        ArrayList<byte[]> f=new ArrayList<>();

        for (int i = 0; i < part; i++) {
            int finalI = i;
                    try {

                        String fileName = "part" + finalI + ".txt";
                        FileInputStream fis = new FileInputStream(spDir+"\\"+fileName);

                        f.add(finalI, fis.readAllBytes());
                        fos.write(f.get(finalI));
                        fis.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

        }
        fos.close();

        System.out.println("Joined Successful");
        return finalFile;

    }

}
