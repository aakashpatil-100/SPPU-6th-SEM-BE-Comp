import java.util.Scanner;

public class RoundRobin {
    private Scanner sc;

    public void execute(int n,int at[],int bt[],int quantum) {
        
        
        
        

        int wt[] = new int[n], tat[] = new int[n]; 
        int total_wt = 0, total_tat = 0;
        
        int rem_bt[] = new int[n]; 
        for (int i = 0 ; i < n ; i++) 
            rem_bt[i] =  bt[i]; 
       
        int t = 0;
        
        int ct[] =new int[n];
        
        
        while(true) 
        { 
            boolean done = true; 
       
            for (int i = 0 ; i < n; i++) 
            { 
                if (rem_bt[i] > 0) 
                { 
                    done = false; 
       
                    if (rem_bt[i] > quantum) 
                    { 
                       t += quantum; 
       
                       rem_bt[i] -= quantum; 
                    } 
                    else
                    { 
                        t = t + rem_bt[i]; 
                        wt[i] = t - bt[i]; 
       
                        rem_bt[i] = 0; 
                    } 
                } 
            }
            if (done == true) 
              break; 
        }
        
        for (int i = 0; i < n ; i++) 
            tat[i] = bt[i] + wt[i]; 
        
        System.out.println("Processes " +" Arrival Time " +" Burst time " + " Waiting time " + " Turn around time" +" Completion Time"); 
        for (int i=0; i<n; i++) 
        { 
        	
            total_wt = total_wt + wt[i]; 
            total_tat = total_tat + tat[i];
            int compl_time = tat[i] + at[i];
            System.out.println(" " + (i+1) +"\t\t" + at[i] +"\t\t" + bt[i] +"\t " + wt[i] +"\t\t " + tat[i] +"\t\t " +compl_time); 
        } 
       
        System.out.println("Average waiting time = " + 
                          (float)total_wt / (float)n); 
        System.out.println("Average turn around time = " + 
                           (float)total_tat / (float)n); 

    }

}