import java.net.*;
import java.io.*;
import java.math.BigInteger;
import java.util.*;
class middle {
public static void main(String args[]) throws IOException{

 Scanner sc=new Scanner(System.in);
 ServerSocket ss = new ServerSocket(4000);
 Socket s = ss.accept();
 DataInputStream in = new DataInputStream(s.getInputStream());
 DataOutputStream out= new DataOutputStream(s.getOutputStream());

 BigInteger p=new BigInteger(in.readUTF());
 BigInteger a=new BigInteger(in.readUTF());
 System.out.println("The value of p is :"+p);
 System.out.println("The value of a is :"+a);
 System.out.print("Enter Xc : ");
 BigInteger Xc=sc.nextBigInteger();
 BigInteger Yc=a.modPow(Xc,p);
 out.writeUTF(Yc.toString());
 BigInteger Ya=new BigInteger(in.readUTF());
 System.out.println("\nThe value of Ya : "+Ya);
 BigInteger key=Ya.modPow(Xc, p);
 System.out.println("\nKey of Client: "+key);
 s.close();
 ss.close();
 Socket s1 = new Socket("localhost", 5000);
 DataInputStream in1 = new DataInputStream(s1.getInputStream());
 DataOutputStream out1 = new DataOutputStream(s1.getOutputStream()); 
 out1.writeUTF(p.toString());
 out1.writeUTF(a.toString());
 System.out.print("Enter Xd : ");
 BigInteger Xd=sc.nextBigInteger();
 BigInteger Yd=a.modPow(Xd,p);
 out1.writeUTF(Yd.toString());
 BigInteger Yb=new BigInteger(in1.readUTF());
 System.out.println("\nThe value of Yb is :"+Yb);
 BigInteger k=Yb.modPow(Xd,p);
 System.out.println("\nThe key of server: "+k);
}
}