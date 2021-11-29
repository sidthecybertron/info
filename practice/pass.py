import hashlib
alphabets = "abcdefghijklmnopqrstuvwxyz"
l=[]
for i in alphabets:
    for j in alphabets:
        l.append(i+j)
d={}
for i in l:
    d[i]=hashlib.sha256(i.encode()).hexdigest()
key_list =list(d.keys())
value_list =list(d.values())
while(True):
    print("1.Convert password to keys\n 2.Dictionary attack\n 3.Exit")
    choice = int(input("Enter the choice"))
    if(choice == 1):
        passwd = input("Enter the password")
        print(d[passwd])
    elif(choice == 2):
        hashvalue = input("Enter the hash value")
        pos = value_list.index(hashvalue)
        print(key_list[pos])
    elif(choice == 3):
        break

