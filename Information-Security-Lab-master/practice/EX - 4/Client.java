import java.net.*;
import java.io.*;
import java.math.BigInteger;
import java.util.*;
class Client {
 
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
 
public static void main(String args[]) throws Exception {
 Scanner sc=new Scanner(System.in);
 
 Socket s = new Socket("localhost", 3000);
 DataInputStream in = new DataInputStream(s.getInputStream());
 DataOutputStream out = new DataOutputStream(s.getOutputStream());
 boolean b=true;
 while(b)
 {
 System.out.println("Enter choise: ");
 System.out.println("\n1.Key exchange\n2.Discrete log\n3.man in the middle\n4.exit\n");
 int choise=sc.nextInt();
 out.write(choise);
 switch(choise)
 {
 case 1:
 System.out.print("KEY EXCHANGE\n"); 
 System.out.print("Enter p (prime) :");
 BigInteger p=sc.nextBigInteger();
 out.writeUTF(p.toString());
 System.out.print("Enter a :");
 BigInteger a=sc.nextBigInteger();
 out.writeUTF(a.toString());
 
 System.out.print("Enter Xa(less than p) :");
 BigInteger Xa=sc.nextBigInteger();
 BigInteger Ya=a.modPow(Xa,p);
 out.writeUTF(Ya.toString());
 BigInteger Yb=new BigInteger(in.readUTF());
 System.out.println("\nThe value of Yb :"+Yb);
 BigInteger key=Yb.modPow(Xa,p);
 System.out.println("\nKey of User A :"+key);
 break;
 case 2: 
 System.out.print("Discrete log\n"); 
 System.out.print("Enter p (prime) :");
 p=sc.nextBigInteger();
 out.writeUTF(p.toString());
 System.out.print("Enter a :");
 a=sc.nextBigInteger();
 out.writeUTF(a.toString());
 System.out.print("Enter Xa :");
 Xa=sc.nextBigInteger();
 Ya=a.modPow(Xa,p);
 
 System.out.print("\nDISCRETE KEY PROBLEM\n");
 
 BigInteger x=discreteKey(Ya,a,p);
 System.out.println("\nPrivate key of User A (Xa): "+x);
 break;
 case 3:
 Socket s1 = new Socket("localhost", 4000);
 DataInputStream in1 = new DataInputStream(s1.getInputStream());
 DataOutputStream out1 = new DataOutputStream(s1.getOutputStream()); 
 System.out.print("\nMAN IN THE MIDDLE\n");
 System.out.print("Enter p :");
 p=sc.nextBigInteger();
 out1.writeUTF(p.toString());
 System.out.print("Enter a :");
 a=sc.nextBigInteger();
 out1.writeUTF(a.toString());
 System.out.print("Enter Xa :");
 Xa=sc.nextBigInteger();
 Ya=a.modPow(Xa,p);
 out1.writeUTF(Ya.toString());

 Yb=new BigInteger(in1.readUTF());
 System.out.println("\nThe value of Yb after middle man attack is :"+Yb);
 BigInteger k=Yb.modPow(Xa,p);
 System.out.println("\nThe key generated: "+k);
 System.out.println("");
 break;
 case 4:
 b=false;
 }
 }
 out.close();
 s.close();
}
}
