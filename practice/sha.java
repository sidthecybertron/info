import java.util.Scanner;
public class sha{
    public static void main(String[] args){
        String a = "67452301";
        String b = "EFCDAB89";
        String c = "98BADCFE";
        String d = "10325476";
        String e = "C3D2E1F0";
        String ft = Long.toHexString(Long.parseLong(b,16)^Long.parseLong(c,16)^Long.parseLong(d,16));
        e = leaveCarry(Long.toHexString(Long.parseLong(e,16)+Long.parseLong(ft,16)));
        String s5 = Long.toHexString(Long.rotateLeft(Long.parseLong(a,16),5));
        e = leaveCarry(e);
        e = leaveCarry(Long.toHexString(Long.parseLong(e,16)+Long.parseLong(s5,16)));
        System.out.println("Message:");
        Scanner input = new Scanner(System.in);
        String word = input.nextLine().substring(0,4).toUpperCase();
        String wt = "";
        for(char i : word.toCharArray()){
            wt+= Integer.toHexString(i);
        }
        String kf = "5A7EF9DB";
        e = leaveCarry(Long.toHexString(Long.parseLong(e,16)+Long.parseLong(wt,16)));
        e = leaveCarry(Long.toHexString(Long.parseLong(e,16)+Long.parseLong(kf,16)));
        String temp3 = a;
        a = e;
        String temp = b;
        b = temp3;
        String temp1 = c;
        c = Long.toHexString(Long.rotateLeft(Long.parseLong(temp,16),30));
        temp = d;
        d = temp1;
        e = temp;
        System.out.println(a+b+c.substring(0,8)+d+e);
    }
    public static String leaveCarry(String bin){
        if(bin.length()>8){
            return(bin.substring(1,bin.length()));
        }
        return bin;
    }
}