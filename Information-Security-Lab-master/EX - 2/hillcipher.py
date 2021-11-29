import numpy as np

def matrix_multiplication_mod(x,y,mod):
    matrix=[]
    for i in range(3):
        l=[]
        for j in range(3):
            l.append(0)
        matrix.append(l)
    for i in range(len(x)):
        for j in range(len(y[0])):
            for k in range(len(y)):
                matrix[i][j] += x[i][k] * y[k][j]
                matrix[i][j]=matrix[i][j]%mod
    return matrix

def determinant(a,order,mod):
    d=0
    if order==2:
        d=a[0][0]*a[1][1] - a[0][1]*a[1][0]
        return d %mod
    if order==3:
        for i in range(len(a)):
            for j in range(len(a[0])):
                minus=(-1)**(i+j)
                co=cofactor(a,i,j)
                adj=co[0][0]*co[1][1] - co[0][1]*co[1][0]
                d=d+ (minus * adj * a[i][j])   
            break
        return d %mod

def mul_inv(a,b):
    t1=0
    t2=1
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
        return t1 % 26
    else:
        print("key matrix cannot be inversed")

def cofactor(a,i,j):
    mat=[]
    for row in a[:i]+a[i+1:]:
        cof=row[:j]+ row[j+1:]
        mat.append(cof)
    return mat

def adj(a,order):
    arr=np.zeros((order,order),dtype=int)
    if order==3:
        for i in range(len(a)):
            for j in range(len(a[0])):
                co=cofactor(a,i,j)
                minus=(-1)**(i+j)
                adj=co[0][0]*co[1][1] - co[0][1]*co[1][0]
                arr[i][j]=minus * adj
        arr=arr.T
        return arr
    if order==2:
        a[0][0],a[1][1]=a[1][1],a[0][0]
        a[0][1]=-1* a[0][1]
        a[1][0]=-1*a[1][0]
        return a

def scal(mat,s,mod):
    for i in range(len(mat)):
        for j in range(len(mat[0])):
            mat[i][j]=(mat[i][j] * s)%mod
    return mat

def NumEnc(p,order):
    alpha="abcdefghijklmnopqrstuvwxyz"
    if (len(p)%order==0):
        p_row=order
        p_col=len(p)//order
    else:
        p_row=order
        p_col=(len(p)//order)+1
    temp=[]
    for i in p:
        temp.append(alpha.index(i))
    mat=np.resize(temp,(p_col,p_row)) 
    return np.transpose(mat)

def CharDec(c,length):
    alpha="abcdefghijklmnopqrstuvwxyz"
    c=np.transpose(c)
    text=""
    for i in range(len(c)):
        for j in range(len(c[0])):
            text+=alpha[c[i][j]]
    
    if(len(text)>length):
       text=text[:length]
    return text

def Convert(p,order):
    alpha="abcdefghijklmnopqrstuvwxyz"
    temp=[]
    mat=[]
    for i in p:
        temp.append(alpha.index(i))
    for j in range(0,len(temp),order):
        mat.append(temp[j:j+order])
    return mat

def Encrypt(p,order,key):
    pt=NumEnc(p,order)
    res=matrix_multiplication_mod(key,pt,26)
    ct=CharDec(res,len(p))
    print("Cipher Text is:",ct)

def Decrypt(c,order,key):
    ct=NumEnc(c,order)
    det=determinant(key,order,26)
    det_inv=mul_inv(26,det)
    adjoint=adj(key,order)
    key_inv=scal(adjoint,det_inv,26)
    res=matrix_multiplication_mod(key_inv,ct,26)
    pt=CharDec(res,len(c))
    print("Plain Text is:",pt)


def Known_PT_CT_Attack(p,c,order):
    temp_p=p[:order**2]
    temp_c=c[:order**2]
    pt=Convert(temp_p,order)
    ct=Convert(temp_c,order)
    det=determinant(pt,order,26)
    det_inv=mul_inv(26,det)
    adjoint=adj(pt,order)
    pt_inv=scal(adjoint,det_inv,26)
    pt_inv=np.transpose(pt_inv)
    ct=np.transpose(ct)
    res=matrix_multiplication_mod(ct,pt_inv,26)
    return res

while(True):
    print("\n1.ENCRYPTION\n2.DECRYPTION\n3.KNOWN_PT_CT_ATTACK\n4.EXIT")
    choice=int(input("Enter Your Choice:"))
    if (choice==1):
        order=int(input("Enter Order of Key Matrix:"))
        key=[]
        for i in range(order):
            print("Enter Elements of row",i+1,":",end="")
            k=list(map(int,input().split()))
            key.append(k)
        p=input("Enter Plain Text:")
        Encrypt(p,order,key)
    elif (choice==2):
        order=int(input("Enter Order of Key Matrix:"))
        key=[]
        for i in range(order):
            print("Enter Elements of row",i+1,":",end="")
            k=list(map(int,input().split()))
            key.append(k)
        c=input("Enter Cipher Text:")
        Decrypt(c,order,key)
        
    elif (choice==3):
        p=input("Enter Plain Text:")
        c=input("Enter Cipher Text:")
        order=int(input("Enter Order of Key Matrix:"))
        key=Known_PT_CT_Attack(p,c,order)
        print("Key Matrix is:\n")
        for i in key:
            for j in i:
                print(j,end=" ")
            print()
    elif (choice==4):
        break
    else:
        print("Enter a Valid Choice")
     

    

