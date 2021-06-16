Generator=[]
g,n=0,0

def sender():
    global Generator,g,n
    print("\n Hello from Sender side !! \n")
    n=int(input("Enter the number of bits for the Message:  "))
    Data=list(map(int,input("Enter the Message:  ")))
    g=int(input("Enter the number of bits in Generator:  "))
    Generator=list(map(int,input("Enter the Generator:  ")))

    Data.extend([0]*(g-1))
    print("Message => ",''.join(map(str,(Data[0:n]+CRC(n,Data,g,Generator)))))

def Receiver():
    global Generator,g,n
    print("\n Hello from Receiver side !! \n")
    Data=list(map(int,input("Enter the Message:  ")))
    
    if 1 in CRC(n,Data,g,Generator):
        print("\n Error in Message \n")
    else:
        print("\n No Error in Message \n")


def CRC(n,Data,g,Generator):
    for i in range(n):
        if Data[i]==0:
            for j in range(i,g+1):
                Data[j]=Data[j]^0
        else:
            count=i
            for j in range(g):
                Data[count]=Data[count]^Generator[j]
                count+=1
    return Data[(len(Data)-(g-1)):len(Data)]

if __name__=="__main__": 
    while(True):
        case=int(input("1.Sender Side  2. Receiver Side  3.Exit\n Select => "))
        if(case==1):
            sender()
        elif(case==2):
            Receiver()
        else:
            break