pubkey,pvtkey=[],[]
def mul_inv(a,b):
    t1=0
    t2=1
    x=a
    while b!=0:
        q=a//b
        r=a%b
        a=b
        b=r
        # inv
        t=t1-q*t2
        t1=t2
        t2=t  
    if a==1 :
        return t1 % x
    else:
        print("GCD Not Equal to 1")
def NumEnc(p):
    alpha="abcdefghijklmnopqrstuvwxyz"
    temp=[]
    for i in p:
        temp.append(alpha.index(i))
    return temp
def Encrypt(pt,pubkey) :
    msg=NumEnc(pt) 
    ct=[]  
    for i in msg:
        j=pow(i,pubkey[0],pubkey[1]) 
        ct.append(j) 
    return ct   
def Decrypt(ct,pvtkey):
    pt=[]
    plaintext=""
    for i in ct:
        j=pow(i,pvtkey[0],pvtkey[1]) 
        pt.append(j)
    alpha="abcdefghijklmnopqrstuvwxyz"
    for i in pt:
        plaintext+=alpha[i]
    return plaintext                                                                                                                     
def Gen_key(e,phy):
    d=mul_inv(phy,e)
    pubkey.append(e)
    pvtkey.append(d)
    pubkey.append(n)
    pvtkey.append(n)
    print("Public Key:",pubkey)
    print("Private Key:",pvtkey)
while(True):
    print("******RSA******")
    print("1.Key Generation\n2.Encryption\n3.Decryption\n4.Exit")
    choice= int(input("Enter choice:"))
    if choice==1:
        p=int(input("Enter a large prime number:"))
        q=int(input("Enter a large prime number:"))
        e=int(input("Enter public Key:"))
        n=p*q
        phy=(p-1)*(q-1)
        Gen_key(e,phy)
    elif choice==2:
        pt=input("Enter Message:")
        pbkey=list(map(int,input("Enter Public Key pair:").split(" ")))
        ct=Encrypt(pt,pbkey)
        print("Ciphe text numeric Values is",ct)
    elif choice==3:
        ct=list(map(int,input("Enter Cipher text numeric Values").split(" ")))
        ptkey=list(map(int,input("Enter Private  Key pair:").split(" ")))
        print("Plaintext is:",Decrypt(ct,ptkey))
    elif choice==4:
        break
    else:
        print("Enter a valid Choice")
