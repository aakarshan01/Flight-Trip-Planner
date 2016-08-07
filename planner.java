import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * this is my main class and contains the main function.
 * @author Aakarshan
 *
 */
public class planner {

	/**
	 * stores the number of cities.
	 */
	public int ncities=0;
	/**
	 * stores how many flights are coming to same station.
	 */
	public int qwert[];
	
	//----------------------------node class starts-------------------------------
	
	/**
	 * Node class
	 * @author Aakarshan
	 *
	 */
	private class Node
	{
		private int tnumber;              //stores flight number
		private int acity;               //stores arrival city.
		private int dtime;                //stores departure time.
		private int atime;                //stores arrival time.
		
		public Node(int t,int ac,int dt,int at) //constructor
		{
			tnumber=t;
			acity=ac;
			dtime=dt;
			atime=at;
		}
	}
	//--------------------------------node class starts-------------------------------
	/**
	 * stores information about each and every flight. 
	 */
	public Node data[][];
	
	/**
	 * stores various possible times of the given querry.
	 */
	public int clock[]=new int[100];
	
	/**
	 * global counter.
	 */
	public int ctr=0;
	
	/**
	 * stores the previous arrival time.
	 */
	public int prevtime=0;
	
	/**
	 * stores required arrival time,departure time,arrival city and departure time of the various querry.
	 */
	public int reqatime,reqdtime,reqacity,reqdcity;
	
	/**
	 * this function searches the given querry and stores various times in clock array.
	 * @param dcity
	 * stores departure city.
	 * @param asdf
	 * this array stores departure time and arrival time.
	 * @param prevtime
	 * stores previous arrival time. 
	 */
	public void search(int dcity,int asdf[],int prevtime)
	{
		int i;
		
		//System.out.println("dcity="+(dcity-1));
		
		for(i=0;i<data[dcity-1].length;i++)              //till length of data[dcity-1]
		{
			if(data[dcity-1][i]!=null)                  //if the node is not null.
			{   
				if(reqdcity==dcity)                   //if reqdcity is equal to dcity.
					asdf[0]=0;
				if(asdf[0]==0)                         
				   {
					asdf[0]=1;
				    asdf[1]=data[dcity-1][i].dtime;           //stores the departure time in asdf array.
				   }
				if(data[dcity-1][i].acity==reqacity&&data[dcity-1][i].dtime>=reqdtime&&data[dcity-1][i].atime<=reqatime&&data[dcity-1][i].dtime>=prevtime+30)
				{
					asdf[2]=data[dcity-1][i].atime;        //stores the arrival time in asdf array.
					clock[ctr]=asdf[2]-asdf[1];            //duration time.
					//System.out.println(asdf[1]); 
					ctr++;                                 //increments counter.
					
				}
				else if(data[dcity-1][i].dtime>=reqdtime&&data[dcity-1][i].atime<=reqatime&&data[dcity-1][i].dtime>=prevtime+30)
				{
					search(data[dcity-1][i].acity,asdf,data[dcity-1][i].atime);	 //recursive call
				}
			}
		}
	}
	
	//-------------------------------------search function ends----------------------------------------
	
	/**
	 * this function stores the information of various flights in data array.
	 * @param filename
	 * stores the filename.
	 */
	public void input(String filename)
	{
		BufferedReader input1=null;
		String line=null;
		int ncities=0,ncases=0,test=0;		
		int index=0;
		try 
	    {
			input1=new BufferedReader(new FileReader(filename));  //i am reading the text file line by line.
	        int i=0;
			char c;
			//int qwert[]=new int[ncities];   //array tells how many flights on same dcity.
			
			int tnumber=0,atime=0,dtime=0,acity=0,dcity=0;
			//int reqatime=0,reqdtime=0,reqacity=0,reqdcity=0;
			
			while ((line=input1.readLine())!=null)              //while there is a next line to read
		    { 
				if(i==0)                                              //reads the first line.
				{
				 while(index<line.length())                            
				 {
					ncities=10*ncities+(line.charAt(index)-'0');      //stores the no of cities.
					index++;                                            //increment index.
				 }
				// System.out.println(ncities);
				 i++;
				}
				else if(i==1)       //reads the second line.
				{
					index=0;
					ncases=0;
					 while(index<line.length())
					 {
						ncases=10*ncases+(line.charAt(index)-'0');               //stores the no. of cases.
						index++;                                               //increments index.
					 }
					 
					 //System.out.println(ncases);
					 i++;
					 data=new Node[ncities][ncases];
					 
					 qwert=new int[ncities];  
					 for(int h=0;h<qwert.length;h++)
						{
							qwert[h]=0;                    //all values in the qwert array are initialized to zero.
						}
				
				}	
				else if(i>=2&&i<(2+ncases))    //lines containind various flights.
				{
					
					tnumber=0;atime=0;dtime=0;acity=0;dcity=0;
					//System.out.println(line);
					
					i++;
					int p=0,l=0;
					int nspaces[]=new int[4];            //stores the index of spaces.
					for(p=0;p<line.length();p++)
					{
						if(line.charAt(p)==32)
						{
							nspaces[l]=p;                
							l++;                    
						}
					}
					
					 index=0;
					 while(index<nspaces[0])                   //extract the flight number
					 {
						tnumber=10*tnumber+(line.charAt(index)-'0');
						index++; 
					 }
					 index++;
					 while(index<nspaces[1])                  //extracts the departure city.
					 {
						dcity=10*dcity+(line.charAt(index)-'0');
						index++; 
					 }
					 index++;
					 while(index<nspaces[2])                //extracts the arrival city.
					 {
						acity=10*acity+(line.charAt(index)-'0');
						index++; 
					 }
					 index++;
					 int temp1,temp2;
					 while(index<nspaces[3])                 //stores the departure time.
					 { 
					    dtime=10*dtime+(line.charAt(index)-'0');
						index++; 
					 }
					 temp2=dtime%100;
					 dtime=(dtime/100)*60+temp2;
					 
					 index++;
					 while(index<line.length())           //stores the arrival time.
					 {
					    atime=10*atime+(line.charAt(index)-'0');
						index++; 
					 }
					 temp2=atime%100;
					 atime=(atime/100)*60+temp2;
					 
					// System.out.println(tnumber);
					// System.out.println(dcity);
					 //System.out.println(acity);        //just for testing.
					 //System.out.println(dtime);
					// System.out.println(atime);
				
					 //for(int m=0;m<qwert.length;m++)
							//System.out.println(qwert[m]);
				     //System.out.println(dcity-1);
					 data[dcity-1][qwert[dcity-1]]=new Node(tnumber,acity,dtime,atime);
				     qwert[dcity-1]++;
				}
				else if(i==2+ncases)             //reads next line
				{
					index=0;
					while(index<line.length())
					 {
						test=10*test+(line.charAt(index)-'0');         //stores the no. of test cases.
						index++; 
					 }
					 //System.out.println(test);
					 i++;
				}
				else if(i>=3+ncases)                          //this part of if-else works for the test cases.
				{
					i++;
					reqdcity=0;reqacity=0;reqdtime=0;reqatime=0;
					
					ctr=0;
					for(int h=0;h<clock.length;h++)
				     {  
						 clock[h]=0;                       //initiaize all elements of clock array to zero.
				     }
					
				    int p=0,l=0;
					int nspaces[]=new int[3];
					for(p=0;p<line.length();p++)
					{
						if(line.charAt(p)==32)             //store the indices of spaces in nspaces array.
						{
							nspaces[l]=p;
							l++;
						}
					}
					index=0;
					 while(index<nspaces[0])
					 {
						reqdcity=10*reqdcity+(line.charAt(index)-'0');  //stores required departure city.
						index++; 
					 }
					 index++;
					 while(index<nspaces[1])
					 {
						reqacity=10*reqacity+(line.charAt(index)-'0'); //stores required arrival city.
						index++; 
					 }
					 index++;
					 int temp1,temp2;
					 while(index<nspaces[2]) 
					 {
					    reqdtime=10*reqdtime+(line.charAt(index)-'0');     //stores required departure time.
						index++; 
					 }
					 temp2=reqdtime%100;
					 reqdtime=(reqdtime/100)*60+temp2;     //convertss time into minutes.
					 
					 index++;
					 while(index<line.length())
					 {
					    reqatime=10*reqatime+(line.charAt(index)-'0');    //stores required arrival time. 
						index++; 
					 }
					 temp2=reqatime%100;
					 reqatime=(reqatime/100)*60+temp2;                     //converts time into minutes.
				
					//System.out.println(reqdcity+" "+reqacity+" "+reqdtime+" "+reqatime);
				     int asdf[]=new int[3];
				     
					 search(reqdcity,asdf,0);                 //calls search function.
					int min=0;
					//System.out.println(clock[0]);
					
					 if(clock[0]==0)
						 System.out.println("-1");        //that is there is no possible flight pattern available.
					 else{
						 min=clock[0];
			           		 for(int h=0;h<clock.length;h++)
				              {  
					           	 if(clock[h]!=0)
					           	 {
					           		 if(clock[h]<min)
					           			 min=clock[h];           //find the minimum time out of various possible times.
					           	 }
				              }
			           		 float ans=min;
			           		ans=ans/60;		 
			           		System.out.println(ans);
					 }
				 
				}
			}
	    }
		catch(IOException e){
			e.printStackTrace();
		}
	 
	}
	/**
	 * this is my main function
	 * @param args
	 * args contains the input given by command line that is filename.
	 * @throws IOException
	 * throws exception if there comes an error while reading the file.
	 */
	public static void main(String[] args) throws IOException{	
	planner obj=new planner();
	String filename=args[0];
	obj.input(filename);           //calls input function.
	}
}
