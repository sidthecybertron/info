import java.net.*;
import java.io.*;
import java.util.*;

class ccs
{
    private final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    public static String encrypt(String pt,int key)
    {
        String ct="";
        for (int i = 0; i < pt.length(); i++) {
            int pos= ALPHABET.indexOf(pt.charAt(i));
            int keyVal = (key + pos) % 26;
            char replaceVal = ALPHABET.charAt(keyVal);
            ct += replaceVal;
            }
        return ct;

    }
    public static String decrypt(String ct,int key)
    {
        String pt="";
        for (int i = 0; i < ct.length(); i++) {
            int pos= ALPHABET.indexOf(ct.charAt(i));
            int keyVal = (pos-key) % 26;
            if (keyVal < 0) {
                keyVal =26+ keyVal;
                }
            char replaceVal = ALPHABET.charAt(keyVal);
            pt += replaceVal;
            }
        return pt;

    }
    public static void main(String[] args) throws IOException
    {
        ServerSocket ss;
        Socket s;
        Scanner sc=new Scanner(System.in);
        DataInputStream dis=null;
    DataOutputStream dos=null;
        try {
            ss=new ServerSocket(3333);
            s=ss.accept();
         dis= new DataInputStream(s.getInputStream());
        dos=new DataOutputStream(s.getOutputStream());
          } catch (Exception e) {
            System.out.println(e);
        }
        boolean a=true;
        while(a){
            int choice=Integer.parseInt(dis.readUTF());
            System.out.println(choice);
            switch(choice)
            {
                case 1:
                    String pt=dis.readUTF();
                    int key=Integer.parseInt(dis.readUTF());
                    String ct=encrypt(pt,key);
                    dos.writeUTF(ct);
                    break;
                case 2:
                    ct=dis.readUTF();
                     key=Integer.parseInt(dis.readUTF());
                    pt=decrypt(ct,key);
                    dos.writeUTF(pt);
                    break;
                case 3:
                ct=dis.readUTF();
                String b="";
                for (int i=1;i<26;i++){
                    pt=decrypt(ct,i);
                    b+=i+" text: "+pt+"\n";
                    
                }
                    dos.writeUTF(b);
                break;

            }
        }
    }

}