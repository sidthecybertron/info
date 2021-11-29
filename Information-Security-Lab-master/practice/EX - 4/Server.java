import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.Math;
import java.math.BigInteger;
class Server {
 
 public static BigInteger discreteKey(BigInteger y,BigInteger a,BigInteger p){
 BigInteger i;
 System.out.println("i\tx\ty");
 for(i=BigInteger.ONE;i.compareTo(p)<0;i=i.add(BigInteger.ONE)){
 BigInteger x=a.modPow(i,p); 
 System.out.println(i+"\t"+x+"\t"+y);
 if(x.equals(y)){
 break;
 }
 }
 return i;
 }
 

 public static void main(String args[]) throws IOException{
 Scanner sc=new Scanner(System.in);
 ServerSocket ss = new ServerSocket(3000);
 Socket s = ss.accept();
 DataInputStream in = new DataInputStream(s.getInputStream());
 DataOutputStream out= new DataOutputStream(s.getOutputStream());
 boolean b=true;
 while(b){
 int choise=in.read();
 
 switch(choise)
 {
 case 1:
 System.out.println("KEY EXCHANGE");
 BigInteger p=new BigInteger(in.readUTF());
 BigInteger a=new BigInteger(in.readUTF());
 System.out.println("The value of p is :"+p);
 System.out.println("The value of a is :"+a);
 System.out.print("Enter Xb : ");
 BigInteger Xb=sc.nextBigInteger();
 BigInteger Yb=a.modPow(Xb,p);
 out.writeUTF(Yb.toString());
 BigInteger Ya=new BigInteger(in.readUTF());
 System.out.println("\nThe value of Ya : "+Ya);
 BigInteger key=Ya.modPow(Xb, p);
 System.out.println("\nKey of User B : "+key);
 break;
 case 2:
 p=new BigInteger(in.readUTF());
 a=new BigInteger(in.readUTF());
 System.out.print("Enter Xb : ");
 Xb=sc.nextBigInteger();
 Yb=a.modPow(Xb,p);
 System.out.println("\nDISCRETE KEY PROBLEM");
 BigInteger x=discreteKey(Yb,a,p);
 System.out.println("\nPrivate key of User B (Xb): "+x); 
 break;
 case 3: 
 ServerSocket ss1 = new ServerSocket(5000);
 Socket s1 = ss1.accept();
 DataInputStream in1 = new DataInputStream(s1.getInputStream());
 DataOutputStream out1= new DataOutputStream(s1.getOutputStream());
 p=new BigInteger(in1.readUTF());
 a=new BigInteger(in1.readUTF());
 System.out.print("Enter Xb : ");
 Xb=sc.nextBigInteger();
 Yb=a.modPow(Xb,p);
 out1.writeUTF(Yb.toString());
 BigInteger Ya1=new BigInteger(in1.readUTF());
 System.out.println("\nThe value of Ya after middle man attack is :"+Ya1);
 BigInteger k=Ya1.modPow(Xb,p);
 System.out.println("\nThe key generated is: "+k);
 break;
 case 4:
 b=false; 
 }} 
 in.close();
 s.close();
 ss.close();
 }
}