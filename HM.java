import java.util.Scanner;
public class HM
{
public static void main(String[] args)
{
Scanner sc=new Scanner(System.in);
char [][]rooms=new char[3][4];
for(int i=0;i<3;i++)
{
for(int j=0;j<4;j++)
{
rooms[i][j]='A';
}
}
System.out.println("Available rooms are:");
for(int i=0;i<3;i++)
{
for(int j=0;j<4;j++)
{
System.out.print(rooms[i][j]+" ");
}
System.out.print("\n");
}
System.out.println("enter the row and coloumn which you want to book");
int c=sc.nextInt();
int r=sc.nextInt();
if (rooms[c-1][r-1]=='A')
{
System.out.println("your room is booked");
rooms[c-1][r-1]='B';
}
System.out.println("Available rooms are:");
for(int i=0;i<3;i++)
{
for(int j=0;j<4;j++)
{
System.out.print(rooms[i][j]+" ");
}
System.out.print("\n");
}
}
}

