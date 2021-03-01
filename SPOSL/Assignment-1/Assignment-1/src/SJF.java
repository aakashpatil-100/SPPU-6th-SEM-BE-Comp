import java.util.Scanner;

  
public class SJF  
{ 
    static void execute(int n,int at[],int bt[]) {
    	int wt[] = new int[n], tat[] = new int[n]; 
        int  total_wt = 0, total_tat = 0; 
        
        int rt[] = new int[n]; 
        
        for (int i = 0; i < n; i++) 
            rt[i] = bt[i]; 
       
        int complete = 0, t = 0, minm = Integer.MAX_VALUE; 
        int shortest = 0, finish_time; 
        boolean check = false; 
       
        while (complete != n) { 
        	for (int j = 0; j < n; j++)  
            { 
                if ((at[j] <= t) && 
                  (rt[j] < minm) && rt[j] > 0) { 
                    minm = rt[j]; 
                    shortest = j; 
                    check = true; 
                } 
            } 
       
            if (check == false) { 
                t++; 
                continue; 
            } 
       
            
            rt[shortest]--; 
       
            minm = rt[shortest]; 
            if (minm == 0) 
                minm = Integer.MAX_VALUE; 
       
            if (rt[shortest] == 0) { 
       
               	complete++; 
                check = false; 
       
                finish_time = t + 1; 
       
                wt[shortest] = finish_time - bt[shortest] - at[shortest]; 
       
                if (wt[shortest] < 0) 
                    wt[shortest] = 0; 
            } 
            t++; 
        } 
        
        for (int i = 0; i < n; i++) 
            tat[i] = bt[i] + wt[i];
        
        System.out.println("Processes " +" Arrival Time " +" Burst time " +" Waiting time " + " Turn around time" +" Completion Time"); 
        for (int i = 0; i < n; i++) { 
            total_wt = total_wt + wt[i]; 
            total_tat = total_tat + tat[i]; 
            int ct = tat[i] + at[i];
            System.out.println(" " + (i+1) +"\t\t"+ at[i] +"\t\t"+ bt[i] + "\t\t " + wt[i] + "\t\t" + tat[i]+"\t\t" + ct); 
        } 
       
        System.out.println("Average waiting time = " + 
                          (float)total_wt / (float)n); 
        System.out.println("Average turn around time = " + 
                           (float)total_tat / (float)n); 
    } 
}