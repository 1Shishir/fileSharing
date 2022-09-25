package com.example.filesharing;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class receiver {
    public static int part = -1;
    public static String FileName=null;
    int port = 1234;
//    joining Joining=new joining();
    void receiverServer() throws IOException {

        File dir=new File(System.getProperty("user.home")+"\\File Sharing\\temp\\sp");
        dir.getParentFile().getParentFile().mkdir();
        dir.getParentFile().mkdir();
        dir.mkdir();
       ArrayList<Thread> thread=new ArrayList<>();
       Thread t= new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    ServerSocket sc = new ServerSocket(port);

                    Socket s = sc.accept();
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    part = dis.readInt();
                    dis.close();
                    s.close();

                    System.out.println("Number of Part : "+part);

                } catch (IOException e) {
                    e.printStackTrace();
                }


                for (int i=0;i<part;i++){
                    thread.add(null);
                }


                for (int i = 0; i < part; i++) {
                    port = port + 1;
                    int finalPort = port;
                    int finalI = i;

                 thread.set(finalI,new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ServerSocket sc1 = new ServerSocket(finalPort);
                                Socket s1 = sc1.accept();

                                DataInputStream dis1 = new DataInputStream(s1.getInputStream());

                                String pfile="part"+finalI+".txt";

                                FileOutputStream fs=new FileOutputStream(dir.getAbsolutePath()+"\\"+pfile);

                                fs.write(dis1.readAllBytes());

                                fs.flush();
                                fs.close();
                                dis1.close();
                                System.out.println("Thhread "+finalI+" : successful");
                                s1.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }));

                    thread.get(finalI).start();

                }

            }
        });

       t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i=0;i<part;i++){
            try {
                thread.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Successfully Received all part");
        ReceivePageCont.j();
    }
}
