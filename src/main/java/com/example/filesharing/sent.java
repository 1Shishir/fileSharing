package com.example.filesharing;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;


public class sent {
   static boolean connected=false;
   static String receiver;
   static boolean finish=false;

    void Client(int div, ArrayList<byte[]> ar,String fileNAME) throws IOException {


        //sent file name

        String reciverIp=SentPageCont.receiverIP;


//             //4ack
//             for (int i=0;i<SentPageCont.nTh+1;i++) {
//                 ack.allDone.add(false);
//             }
        //sent dividing information
            int port=1234;
            Socket s=new Socket(reciverIp,port);
            final DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());

            connected=s.isConnected();
            receiver=s.getInetAddress().getHostAddress();

            dataOutputStream.writeInt(div);
            dataOutputStream.close();
            s.close();

            Thread thread[]=new Thread[div];

            long startTime=System.currentTimeMillis();

            //sent chunkrd files
            for (int i=0;i<div;i++){
                port=port+1;
                int finalPort = port;

                int finalI = i;
               thread[finalI]= new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            Socket s1 = new Socket(reciverIp, finalPort);

                            long st=System.currentTimeMillis();

                            DataOutputStream dataOutputStream1 = new DataOutputStream(s1.getOutputStream());
                            dataOutputStream1.write(ar.get(finalI));
                            dataOutputStream1.close();
                            s1.close();
                            long end=System.currentTimeMillis()-st;

                            System.out.println("Thread : "+finalI+" Successful in "+end+" ms");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
//3ack



                });
               thread[finalI].start();

            }
        //2ack



        for(int i=0;i<div;i++){
            try {
                thread[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All Successfully sent in : "+(System.currentTimeMillis()-startTime)+" ms");



        }
    }

