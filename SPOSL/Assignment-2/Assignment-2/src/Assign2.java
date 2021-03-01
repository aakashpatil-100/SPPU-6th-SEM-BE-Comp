
import java.io.*;
import java.util.*;

public class Assign2
{
    class needed
    {
        public String literals;
        public int address;

        needed(String lit, int addr)
        {
            literals = lit;
            address = addr;
        }
    }

    public HashMap<String, String> optab;
    public LinkedHashMap<String, Integer> symtab;
    public HashMap<String, String> registers;
    public HashMap<String, String> conditionalCodes;
    public FileReader f1;
    public int loc = 0;
    public int countOfLiterals = 0;
    //public boolean callLtorg = false;
    public ArrayList<String> inpcode;
    public ArrayList<String> outcode;
    public ArrayList<needed> littab;
    public ArrayList<String> pooltab;
    public LinkedHashMap<String,Integer> tempLit;

    public Assign2() throws FileNotFoundException,IOException
    {
        inpcode = new ArrayList<String>();
        outcode = new ArrayList<String>();
        optab = new HashMap<String, String>();
        symtab = new LinkedHashMap<String, Integer>();
        registers = new HashMap<String, String>();
        conditionalCodes = new HashMap<String, String>();
        littab = new ArrayList<needed>();
        pooltab = new ArrayList<String>();
        tempLit = new LinkedHashMap<String,Integer>();

        f1 = new FileReader("input.txt");
        BufferedReader b1 = new BufferedReader(f1);
        String take = "";
        while(take != null)
        {
            take = b1.readLine();
            inpcode.add(take);
        }
        createTables();
    }
    public static void main(String[] args) throws FileNotFoundException,IOException{
    	Assign2 obj = new Assign2();
        obj.codePass1();
	FileWriter f2 = new FileWriter("IC.txt");
        BufferedWriter b2 = new BufferedWriter(f2);
        System.out.println("Intermediate Code");
        for(int i = 0 ; i < obj.outcode.size() ; i++){
            System.out.println(obj.outcode.get(i));
	    b2.write(""+obj.outcode.get(i)+"\n");}
        System.out.println();
	b2.write("\n");
	System.out.println("Symbol Table");
        for(Map.Entry m:obj.symtab.entrySet()){
            System.out.println(m.getKey()+" "+m.getValue());
	    b2.write(m.getKey()+" "+m.getValue()+"\n");}
        System.out.println();
	b2.write("\n");  
	System.out.println("Literal Table");

        for(int i = 0 ; i < obj.littab.size() ; i++){
            System.out.println(obj.littab.get(i).literals + " " + obj.littab.get(i).address);
	    b2.write(obj.littab.get(i).literals + " " + obj.littab.get(i).address+"\n");}
        System.out.println();
	b2.write("\n");
	System.out.println("Pool Table");

        for(int i = 0 ; i < obj.pooltab.size() - 1 ; i++){
            System.out.println(obj.pooltab.get(i));
	    b2.write(""+obj.pooltab.get(i)+"\n");}
        System.out.println();
	b2.write("\n");
	b2.close();
	f2.close();
    }

    public void createTables() 
    {
        optab.put("STOP", "(IS,00)");
        optab.put("ADD", "(IS,01)");
        optab.put("SUB", "(IS,02)");
        optab.put("MULT", "(IS,03)");
        optab.put("MOVER", "(IS,04)");
        optab.put("MOVEM", "(IS,05)");
        optab.put("COMP", "(IS,06)");
        optab.put("BC", "(IS,07)");
        optab.put("DIV", "(IS,08)");
        optab.put("READ", "(IS,09)");
        optab.put("PRINT", "(IS,10)");
        optab.put("START", "(AD,01)");
        optab.put("END", "(AD,02)");
        optab.put("ORIGIN", "(AD,03)");
        optab.put("EQU", "(AD,04)");
        optab.put("LTORG", "(AD,05)");
        optab.put("DC", "(DL,01)");
        optab.put("DS", "(DL,02)");

        registers.put("AREG", "1");
        registers.put("BREG", "2");
        registers.put("CREG", "3");
        registers.put("DREG", "4");

        conditionalCodes.put("LT", "1");
        conditionalCodes.put("LE", "2");
        conditionalCodes.put("EQ", "3");
        conditionalCodes.put("GT", "4");
        conditionalCodes.put("GE", "5");
        conditionalCodes.put("ANY", "6");
    }

    public void codePass1()
    {
        for(int i = 0 ; i < inpcode.size() ; i++)
        {
            String command = inpcode.get(i);
            String[] split_command = command.split(" ");

            int idx1 = 0 , idx2 = 1 , idx3 = 2;
            if(ifLabel(split_command[0]))
            {
                symtab.put(split_command[0], loc);
                idx1++; idx2++; idx3++;
            }

            String opcode = split_command[idx1];
            String operand1 = "" , operand2 = "";

            if(split_command.length > 1 && idx2 < split_command.length)
                operand1 = split_command[idx2];

            if(split_command.length > 2 && idx3 < split_command.length)
                operand2 = split_command[idx3];

            if(opcode.equals("START"))
            {
                loc = getConvertedintegerLoc(operand1);
                String ans = "    "+optab.get(split_command[0]) + " " + forOperands(operand1);
                outcode.add(ans);
            }
            else if(opcode.equals("ORIGIN"))
            {
                loc = getConvertedintegerLoc(operand1);
                String ans = "    "+optab.get(split_command[0]) + " " + forOperands(operand1);
                outcode.add(ans);
            }
            else if(opcode.equals("LTORG"))
            {
                //callLtorg = true;
		String ans = "    "+optab.get(split_command[0]);
		outcode.add(ans);
                doLiterals();
            }
            else if(opcode.equals("END"))
            {
                String ans = "    "+optab.get(opcode) + " " + forOperands(operand1);
                outcode.add(ans);
                doLiterals();
            }
            else if(opcode.equals("EQU"))
            {
		String ans = "    "+optab.get(opcode) + " " + forOperands(operand1);
		outcode.add(ans);
                symtab.put(split_command[0], getConvertedintegerLoc(operand1));
            }
            else if(if_IS(opcode))
            {
                String ans = loc+" " + optab.get(opcode) + " " + forOperands(operand1) + " " + forOperands(operand2);
                outcode.add(ans);
                loc++;
            }
            else if(if_DL(opcode))
            {
                String temp = operand1;
                temp = temp.replace("‘","");
                temp = temp.replace("’","");
		temp = temp.replace("'","");
		int temp2 = Integer.parseInt(temp);
		
                String ans = loc+" " + optab.get(opcode) + " (C," + Integer.toString(temp2) + ")";
                outcode.add(ans);
		if(if_DC(opcode))
		{
			loc=loc+1;
		}
                else{
                loc += temp2;
		}
            }

            if(split_command[0].equals("END"))
                break;
        }
    }

    public String forOperands(String command)
    {
        if(command.equals(""))
            return command;
        String ans = "" , use="";

        if(command.contains("+"))
        {
            String[] check = command.split("\\+");
            command = check[0];
            use = "+" + check[1];
        }
        else if(command.contains("-"))
        {
            String[] check = command.split("\\-");
            command = check[0];
            use = "-" + check[1];
        }

        if(ifLiterals(command))
        {
            //needed n = new needed(command, 0);
            //littab.add(n);
            if(!tempLit.containsKey(command))
            {
                tempLit.put(command, 0);
                countOfLiterals++;
            }
            //String temp = Integer.toString(countOfLiterals);
            if(pooltab.isEmpty())
                pooltab.add("#1");
            /*else if(callLtorg == true)
            {
                pooltab.add(temp);
                callLtorg = false;
            }*/
            ans = "(L," + Integer.toString(countOfLiterals) + ")";
        }
        else if(registers.containsKey(command))
            ans = registers.get(command);
        else if(conditionalCodes.containsKey(command))
            ans = conditionalCodes.get(command);
        else if(ifConstant(command))
        {
            command = command.replace("‘","");
            command = command.replace("’","");
	    command = command.replace("'","");
            ans = "(C," + command + ")";
        } 
        else if(symtab.containsKey(command))
            ans = "(S," + Integer.toString(getValueforSymtab(command)) + ")";
        else
        {
            symtab.put(command,-1);
            ans = forOperands(command);
        }
        return ans + use;
    }
    public void doLiterals()
    {
        /*int ind = 0;
        for(int i = 0 ; i < pooltab.size() ; i++)
        {
            if(pooltab.get(i).contains("#"))
                continue;
            else 
            {
                ind = Integer.parseInt(pooltab.get(i));
                ind--;
                String t = "#" + pooltab.get(i);
                pooltab.set(i, t);
                break;
            }
        }*/
        ArrayList<String> tempkeys = new ArrayList<String>(tempLit.keySet());
        for(int i = 0 ; i < tempkeys.size() ; i++)
        {
            needed n = new needed(tempkeys.get(i), loc);
            littab.add(n);
            
            String temp = tempkeys.get(i);
            temp = temp.replace("=","");
            String ans = loc+" " + optab.get("DC") + " " + forOperands(temp);
            outcode.add(ans);
		loc++;
        }
        tempLit = new LinkedHashMap<String,Integer>();
        String temp2 = "#" + Integer.toString(littab.size() + 1);
        pooltab.add(temp2);
        /*for(int i = ind ; i < littab.size() ; i++)
        {
            littab.get(i).address = loc;
            loc++;
            String temp = littab.get(i).literals;
            temp = temp.replace("=","");
            String ans = optab.get("DC") + " " + forOperands(temp);
            outcode.add(ans);
        }*/
    }
    public boolean ifConstant(String temp)
    {
        if(temp == null)
            return false;
        temp = temp.replace("‘","");
        temp = temp.replace("’","");
	temp = temp.replace("'","");
        try
        {
            int temp2 = Integer.parseInt(temp);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
        
    }
    public Integer getConvertedintegerLoc(String command)
    {
        int val = 0, use = 0;
        if(ifConstant(command))
        {
            command = command.replace("‘","");
            command = command.replace("’","");
            command = command.replace("'","");
            return Integer.parseInt(command);   
        }
        if(command.contains("+"))
        {
            String[] check = command.split("\\+");
            command = check[0];
            use += Integer.parseInt(check[1]);
        }
        else if(command.contains("-"))
        {
            String[] check = command.split("\\-");
            command = check[0];
            use -= Integer.parseInt(check[1]);
        }
        val = symtab.get(command);
        return val + use;
    }

    public Integer getValueforSymtab(String command)
    {
        ArrayList<String> temp = new ArrayList<String>(symtab.keySet());
        int i = 0;
        for(i = 0 ; i < temp.size() ; i++)
        {
            if(temp.get(i).equals(command))
                break;
        }
        return i+1;
    }

    public boolean ifLabel(String command)
    {
        if(optab.containsKey(command))
            return false;
        return true;
    }

    public boolean if_IS(String command)
    {
        if(optab.containsKey(command) && optab.get(command).contains("IS"))
            return true;
        return false; 
    }
    public boolean if_DL(String command)
    {
        if(optab.containsKey(command) && optab.get(command).contains("DL"))
            return true;
        return false; 
    }
    public boolean if_DC(String command)
    {
	if(optab.get(command).contains("(DL,01)"))
            return true;
        return false; 
    }
    public boolean ifLiterals(String command)
    {
        if(command.contains("="))
            return true;
        return false;
    }
}