import java.io.*;
import java.util.*;

class Targetdata
{
    String sign;
    String opcode;
    String op1;
    String op2;
    public Targetdata(String sign,String opcode,String op1,String op2)
    {
        this.sign=sign;
        this.opcode=opcode;
        this.op1=op1;
        this.op2=op2;
    }
}
class Pass2
{
    LinkedHashMap<String,String> Symtable;  
    LinkedHashMap<String,String> Littable; 
    LinkedHashMap<String,Targetdata> Target; 
    Pass2() 
    {
        Symtable=new LinkedHashMap<String,String>();
        Littable=new LinkedHashMap<String,String>();
       
        Target=new LinkedHashMap<String,Targetdata>();
        initialmap();
    }
    public void initialmap() 
    {
       try
       {
        File lt=new File("Literal_table.txt");
        File st=new File("Symbol_table.txt");
        Scanner flt=new Scanner(lt);
        Scanner fst=new Scanner(st);
        while(flt.hasNext())
        {
             String str=flt.nextLine();
             String[] arr=str.split("\\s+");
             Littable.put(arr[0],arr[2]);
        }
        while(fst.hasNext())
        {
             String str=fst.nextLine();
             String[] arr=str.split("\\s+");
             Symtable.put(arr[0],arr[2]);
        }
       }
       catch(Exception e)
       {
           System.out.println("Error");
       }
        
    }
    public void readfilecontents() throws Exception
    {
         File f=new File("IC.txt");
         Scanner input=new Scanner(f);
         String a,b="";
        while(input.hasNext())
        {
           String str=input.nextLine();
           String[] arr=str.split("\\s+");
           //logic
           if(arr[2].equals("IS"))
           {
               //for first operand
               if(arr[6].equals("-"))
               {
                   a="0";
               }
               else
               {
                    a=arr[6].replace("(","").replace(")","");
                    a=a.trim();
               }
               //for second operand
               if(arr[7].equals("-"))
               {
                   b="000";
               }
               else if(arr[8].equals("S"))
               {
                   b=Symtable.get(arr[10]);
               }
               else if(arr[8].equals("L"))
               {
                    b=Littable.get(arr[10]);
               }
               //if already contained
                if(Target.containsKey(arr[0]))
                {
                    Target.get(arr[0]).sign="+";
                    Target.get(arr[0]).opcode=arr[4];
                    Target.get(arr[0]).op1=a;
                    Target.get(arr[0]).op2=b;
                }
                else
                {
                    //create new 
                    Target.put(arr[0],new Targetdata("+",arr[4],a,b));  
                }           
           }
           else if(arr[2].equals("DL"))
           {
              // 01-DC  02-DS
               if(arr[4].equals("01"))
               {
                   //DC
                   arr[10]=addzeros(arr[10]);
                    if(Target.containsKey(arr[0]))
                    {
                        Target.get(arr[0]).sign="+";
                        Target.get(arr[0]).opcode="00";
                        Target.get(arr[0]).op1="0";
                        Target.get(arr[0]).op2=arr[10];
                    }
                    else
                    {
                        Target.put(arr[0],new Targetdata("+","00","0",arr[10]));
                    }
               }
               else
               {
                   int x=Integer.parseInt(arr[10]);
                   int y=Integer.parseInt(arr[0]);
                   for(int k=1;k<=x;k++)
                   {
                       Target.put(String.valueOf(y), new Targetdata("","","",""));
                       y++;
                   }
               }
           }
           else
           {
               //for ADs
           }
        }
    }
    public String addzeros(String str)
    {
        int len=str.length();
        if(len==1)
        {
            str="00"+str;
        }
        else if(len==2)
        {
             str="0"+str;
        }
        return str;
    }
}
public class A2 
{
    public static void main(String[] args) throws Exception
    {
         Pass2 p2=new Pass2();
         p2.readfilecontents();
         //put Target code into file
        FileWriter ftc = new FileWriter("Objectcode.txt");
        for( Map.Entry<String, Targetdata> entry : p2.Target.entrySet() )
        {
            ftc.write(entry.getKey()+"  "+entry.getValue().sign+" "+entry.getValue().opcode+" "+entry.getValue().op1+" "+entry.getValue().op2+"\n");
            System.out.println(entry.getKey()+"  "+entry.getValue().sign+" "+entry.getValue().opcode+" "+entry.getValue().op1+" "+entry.getValue().op2);
        }
        ftc.close();
    }
}