import java.net.*;
import java.io.*;
import java.util.*;

class ccc
{
    
    public static void main(String[] args) throws IOException
    {
        Socket cs;
    DataInputStream dis=null;
    DataOutputStream dos=null;
        Scanner sc=new Scanner(System.in);
    try {
        cs=new Socket("localhost",3333);
         dis= new DataInputStream(cs.getInputStream());
        dos=new DataOutputStream(cs.getOutputStream());
      } catch (Exception e) {
        System.out.println(e);
    }
        boolean a=true;
        
        while(a)
        {
            System.out.println("Enter choise: ");
        int choice=sc.nextInt();
        dos.writeUTF(Integer.toString(choice));
            switch (choice) {
                case 1:
                    System.out.println("Enter plain text: ");
                    String pt=sc.nextLine();
                     pt=sc.nextLine();
                    
                    System.out.println("Enter key: ");
                    int key=sc.nextInt();
                    
                    dos.writeUTF(pt);
                    dos.writeUTF(Integer.toString(key));
                    String ct=dis.readUTF();
                    System.out.println(ct);
                    break;
                case 2:
                    System.out.println("Enter cipher text: ");
                    ct=sc.nextLine();
                     ct=sc.nextLine();
                    
                    System.out.println("Enter key: ");
                    key=sc.nextInt();
                    
                    dos.writeUTF(ct);
                    dos.writeUTF(Integer.toString(key));
                    pt=dis.readUTF();
                    System.out.println(pt);
                    break;
                case 3:
                System.out.println("Enter cipher text: ");
                 ct=sc.nextLine();
                 ct=sc.nextLine();
                 dos.writeUTF(ct);
                 System.out.println(dis.readUTF());
                 break;
                 
                 


            
            
                default:
                    break;
            }
        }
    }
}