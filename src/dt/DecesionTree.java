/*	Aaron Regan
 * 	13446668
 * 	4BP1
 * 	Assignment 3- Machine Learning
 * 	Implementation of the ID3 Algorithm for data classification
 */

package dt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;					//importing relevant libraries//

public class DecesionTree {


    public static void main(String[] args) throws FileNotFoundException 
    {
    	for(int i=0;i<=10;i++)
    	{
        DecesionTree mytree = new DecesionTree();
    	}
    }
    
    public DecesionTree() throws FileNotFoundException				
    {
    	ArrayList<ArrayList<String>> new_data_set = csvReader();//read in CSV file//
        
        List<ArrayList<String>>train = train_set(new_data_set);//create training set//
        
        trainer_counter(train);//count uniqe values in training set for system entropy//
        
        sys_entropy();//calculate system entropy//
        
        List<ArrayList<String>>test_split = dividesets(train);//run trainig set through divide set//(RULE 1)
        
        System.out.println("");
        
        List<ArrayList<String>>test_split1 = dividesets(test_split);//rung first split through divide set (RULE2)
        
        List<ArrayList<String>>test = test_set(new_data_set);//create test set//
        
        test(test);//run test set through testing method//
        
        performance();//get performance measures//
        
          
    }

    public static ArrayList<ArrayList<String>> csvReader() throws FileNotFoundException
    {
    	ArrayList<ArrayList<String>> owls = new ArrayList<ArrayList<String>>();		//createing arraylist of arraylist called owls//
        //Get scanner instance														//allows us to access row(owl) and its attributes seperatly//
        Scanner scanner = new Scanner(new File("/Users/Aaron/Documents/College/Fourth_Year/Machine_Learning/Assignment_3/owls15.csv"));
         
        // while there is a new line, create a case for that line
        // for each comma seperated value add to case
        // and add each case to owls
        
        scanner.nextLine(); /// skip the first line attribute headings
        
        while (scanner.hasNextLine()) 
        {
        	String addme = scanner.nextLine();
        	//delimit of commma's//
            String[] wholeline = addme.split(",");
            //new arraylist cases for owl attributes//
            ArrayList<String> cases = new ArrayList<String>();
            //loop through columns//
            for (int i=0;i<5;i++)								
            {	//add individual cases to wholeone//
            	cases.add(wholeline[i]);
            	//System.out.print(cases.get(i));
            }
            //System.out.print("\n");
            owls.add(cases);//add cases arraylist to owls arraylist//
        }
         
         
        scanner.close();
        //shuffle data for splitting//
        Collections.shuffle(owls);
        //return owl arraylist//
        return owls;
    }
    //counter for system entropy//
    private double sys_ent_count =0;
    public List<ArrayList<String>> train_set(ArrayList<ArrayList<String>> owls)
    {	//get owls size for system entropy calculation//
    	int m =owls.size();
    	List<ArrayList<String>> train =  owls.subList(1, 2*m/3);//create a sublist from owls 2/3 the size of set//
        Collections.shuffle(train);//shuffle
        
        for(int i = 0; i < train.size(); i++){
        	//loop through each row and add to an arraylist , createing a list of arraylists//
        	ArrayList<String>  n = new ArrayList<String> ();
        	n= train.get(i);
        	System.out.print("[");
        	for(int j=0;j<5;j++)	//loop through each column , to print out each attribute with class//
        	{
        		System.out.print(n.get(j)+",");
        	 
        	}
        	System.out.print("]");
        	System.out.print("\n");
        }
        double s = train.size();
        sys_ent_count = s;		//print count value//
        System.out.println("Total count:"+sys_ent_count);
        return train;//return training set//
    }
    
    public List<ArrayList<String>> test_set(ArrayList<ArrayList<String>> owls)
    {	
    	int m =owls.size();
    	List<ArrayList<String>> test =  owls.subList(2*m/3, m);//sublist of owls set 1/3 the size of original set//
        Collections.shuffle(test);//shuffle//
        
        for(int i = 0; i < test.size(); i++){
        	//lop through each row and add to array list//
        	ArrayList<String>  n = new ArrayList<String> ();
        	n= test.get(i);		
        	//System.out.print("[");
        	for(int j=0;j<5;j++)	//for debugging printing each value at rowXcolumn//
        	{
        		//System.out.print(n.get(j));
        	 
        	}
        	//System.out.print("]");
        	//System.out.print("\n");
        }
        //System.out.println("Total Test Set Number:"+m);
        return test;//return test set//
    }
    
    private int count=0;
    public void uniquecounts(String type, ArrayList<String> cases)//get uniquecounts of each instance//
    {		//read in the arraylist of cases(attributes)
    		if(cases.get(cases.size()-1).contains(type))
    		{	//if itemat class column equals X type increment count//
    			count++;
    		}
    }
    //initilizing variables//
    private double train_numLongEar;
    private double train_numBarn;
    private double train_numSnowy;
    public void trainer_counter(List<ArrayList<String>> list)//for training set//
    {	//loop through entire train set//
    	for(int i=0; i< list.size() ; i++)
    	{	//pass barn owl and row(i) into uniquecounts to determine count//
    		uniquecounts("\"BarnOwl",list.get(i));
    	}
    	train_numBarn = count;//assign to train_numbarn , to be used into entropy()//
    	System.out.println("BarnOwls:" + train_numBarn);//print for debugging//
    	count=0;//reset count for next variable//
    	for(int i=0; i< list.size() ; i++)//repeat for Snowy owl//
    	{
    		uniquecounts("\"SnowyOwl",list.get(i));
    	}
    	train_numSnowy = count;
    	System.out.println("SnowyOwl:" + train_numSnowy);
    	count=0;
    	for(int i=0; i< list.size() ; i++)//repeat for Long Earred Owl
    	{
    		uniquecounts("\"LongEaredOwl",list.get(i));
    	}
    	train_numLongEar = count;
    	System.out.println("LongEared:" + train_numLongEar);
    	count=0;
    }
    //initilizing variables//
    private double numLongEar;
    private double numBarn;
    private double numSnowy;
    public void counter(List<ArrayList<String>> list)//this is a repeat of traing_counter
    {	//used in the entropy class to determine entropy os split sets//											 	 
    	for(int i=0; i< list.size() ; i++)
    	{
    		uniquecounts("\"BarnOwl",list.get(i));
    	}
    	numBarn = count;
    	//System.out.println("BarnOwls:" + numBarn);
    	count=0;
    	for(int i=0; i< list.size() ; i++)
    	{
    		uniquecounts("\"SnowyOwl",list.get(i));
    	}
    	numSnowy = count;
    	//System.out.println("SnowyOwl:" + numSnowy);
    	count=0;
    	for(int i=0; i< list.size() ; i++)
    	{
    		uniquecounts("\"LongEaredOwl",list.get(i));
    	}
    	numLongEar = count;
    	//System.out.println("LongEared:" + numLongEar);
    	count=0;
    }
    //initilizing variables//
    private double print_numLongEar;
    private double print_numBarn;
    private double print_numSnowy;
    public void print_counter(List<ArrayList<String>> list)//same as previous counter's
    {	//used to print final counter of divied set(node)//
    	for(int i=0; i< list.size() ; i++)
    	{
    		uniquecounts("\"BarnOwl",list.get(i));
    	}
    	print_numBarn = count;
    	System.out.println("BarnOwls:" + print_numBarn);
    	count=0;
    	for(int i=0; i< list.size() ; i++)
    	{
    		uniquecounts("\"SnowyOwl",list.get(i));
    	}
    	print_numSnowy = count;
    	System.out.println("SnowyOwl:" + print_numSnowy);
    	count=0;
    	for(int i=0; i< list.size() ; i++)
    	{
    		uniquecounts("\"LongEaredOwl",list.get(i));
    	}
    	print_numLongEar = count;
    	System.out.println("LongEared:" + print_numLongEar);
    	count=0;
    }
    //initlaizinh split_ent to 0 for each use of entropy() method//
    double split_ent = 0;
    public double entropy()
    {	//split_ent is the total of owl in split set/total owl in split set//
    	double split_ent1 = -(double)(numLongEar/divide_counter)*Math.log((double)(numLongEar/divide_counter))/Math.log(2)
    			- (double)(numBarn/divide_counter)*Math.log((double)(numBarn/divide_counter))/Math.log(2)
    			-(double)(numSnowy/divide_counter)*Math.log((double)(numSnowy/divide_counter))/Math.log(2);
    	//max entropy of system to normalize the triple classification//
    	double max_entropy = -((double)((sys_ent_count/3)/sys_ent_count))*Math.log((double)((sys_ent_count/3)/sys_ent_count))/Math.log(2)
    			-((double)((sys_ent_count/3)/sys_ent_count))*Math.log((double)((sys_ent_count/3)/sys_ent_count))/Math.log(2)
    			-((double)((sys_ent_count/3)/sys_ent_count))*Math.log((double)((sys_ent_count/3)/sys_ent_count))/Math.log(2);
    	split_ent = split_ent1/max_entropy;//normalising//
    	return split_ent;//return split_ent used to determine best split on node//
    }
    
    private double system_entropy = 0;
    public void sys_entropy()//method for calculating system entropy of training set//
    {		//system entropy//
    	double max_entropy = -((double)((sys_ent_count/3)/sys_ent_count))*Math.log((double)((sys_ent_count/3)/sys_ent_count))/Math.log(2)
    			-((double)((sys_ent_count/3)/sys_ent_count))*Math.log((double)((sys_ent_count/3)/sys_ent_count))/Math.log(2)
    			-((double)((sys_ent_count/3)/sys_ent_count))*Math.log((double)((sys_ent_count/3)/sys_ent_count))/Math.log(2);
				
    	//calculating system_entropy for normalistation//
    	double calc_ent = -((double)(train_numLongEar/sys_ent_count))*Math.log((double)(train_numLongEar/sys_ent_count))/Math.log(2)
    			-((double)(train_numBarn/sys_ent_count))*Math.log((double)(train_numBarn/sys_ent_count))/Math.log(2)
    			-((double)(train_numSnowy/sys_ent_count))*Math.log((double)(train_numSnowy/sys_ent_count))/Math.log(2);
    	
    	system_entropy = (double)calc_ent/(double)max_entropy;//calcualtion//
    	System.out.println("sys_ent_count " + sys_ent_count);//print total system number//
    	System.out.println("System Entropy: " + system_entropy);//print system entropy//
    }
    
    private int divide_counter=0;
	double old_split_ent = 1;
	double best_split_thres = 0;
	int best_split_attribute = 0;
	private ArrayList<Rules> set_rules = new ArrayList<Rules>();
    public List<ArrayList<String>> dividesets(List<ArrayList<String>>list)//dividesets from input of list's//
    	{  	ArrayList<ArrayList<String>> split1 = new ArrayList<ArrayList<String>>();//split list for less than threshold//
			ArrayList<ArrayList<String>> split2 = new ArrayList<ArrayList<String>>();//split list for above threshold//
			List<ArrayList<String>> best_split = new ArrayList<ArrayList<String>>();//save set from best set from entropy//
			List<ArrayList<String>> best_splitAT = new ArrayList<ArrayList<String>>();//save set from best entropy > threshold//
			old_split_ent = 1;//reset entropy comparator for next test//
			for(int k = 0;k<4;k++)//loop through col.//
			{
				for(double t=7;t>=0;t -=0.1 )//iterate through each possible threshold value , decrementing by 0.1//
				{
					for(int i = 0;i<list.size();i++)//for all rows in set//
					{	//get value from rowXcol
						String case_value = list.get(i).get(k);
						if(Double.parseDouble(case_value) <= t)//is it < than threshold//
						{
							split1.add(list.get(i));//add to split set 1 for saving//
							divide_counter++;//increment divide counter used with entropy //
							
						}
						else
						{	//if above threshold add to split 2//
							split2.add(list.get(i));
						}
					}
					counter(split1);//run lower than threshold set through counter to get unique counts to be used in entropy//
					double split_ent = entropy();//assing variable to entropy() output for comparision//
					if(split_ent < old_split_ent)//if calc entropy of set is lower than previos saved value do this//
					{
						best_split_thres = t;		//save threshold of better split//
						best_split_attribute = k;	//save attribute of better split//
						old_split_ent = split_ent;	//re-assign best entropy//
						best_split = new ArrayList<ArrayList<String>>(split1);	//save split set//
						best_splitAT = new ArrayList<ArrayList<String>>(split2);//save split set above threshold//
					}
					split1.clear();//clear arraylists for re-start of loop//
					split2.clear();
					divide_counter=0;//reset divide counter for calculating new entropy of next split//
				}
			}
			for(int j =0;j<best_split.size();j++)
			{
				System.out.println(best_split.get(j));//print best split calcualted//
			}
			print_counter(best_split);//print prevalance of each class in new split//
			String left = left();		//assign left to class which is most prevalant in split for prediction//
			for(int j =0;j<best_splitAT.size();j++)//repeat for split set above threshold//
			{
				System.out.println(best_splitAT.get(j));
			}
			print_counter(best_splitAT);
			String right = right();//assign right variable to most prevalant class in right node//
			System.out.println("Set Split at threshold value:"+best_split_thres);//print best split threshold//
			Rules rule = new Rules(best_split_thres, best_split_attribute, left, right);//create new rule object//
			set_rules.add(rule);//add new rule object to rule arraylist to be stored for classification//
			System.out.println("Split on column:"+best_split_attribute);//print best split attribute//
			System.out.println("Entropy of split set:"+old_split_ent);//print entropy of best_split//
			System.out.println(set_rules.toString());//print rule arraylist//
	return best_splitAT;
   }

    public String right()
    {
    	String right = null;//set string to null//
		double maxClass = 0.0;
		//for loop getting greatest instance
			if(print_numLongEar > maxClass)//if number of unqiuecounts of long ear owls is most assign to maxclass//
			{
				maxClass = print_numLongEar;
			}
			if(print_numBarn > maxClass)//repeat for barn owl//
			{
				maxClass = print_numBarn;
			}
			if(print_numSnowy > maxClass)//repeat for snowy owl//
			{
				maxClass = print_numSnowy;
			}	//if maxclass(most prevlant class) is long ear owl , right node is classified as X//
			if(maxClass == print_numLongEar)
			{
				right = "\"LongEaredowl";
			}
			if(maxClass == print_numSnowy)//repeat for snow owl//
			{
				right = "\"SnowyOwl";
			}
			if(maxClass == print_numBarn)//repeat for barnowl//
			{
				right = "\"BarnOwl";
			}
    	return right;//return node string of class//
    }
    
    public String left()//same as right class, but calculated for left node//
    {
    	String left = null;
		double maxClass = 0.0;
		//for loop getting greatest instance
			if(print_numLongEar > maxClass)
			{
				maxClass = print_numLongEar;
			}
			if(print_numBarn > maxClass)
			{
				maxClass = print_numBarn;
			}
			if(print_numSnowy > maxClass)
			{
				maxClass = print_numSnowy;
			}	
			if(maxClass == print_numLongEar)
			{
				left = "\"LongEaredowl";
			}
			if(maxClass == print_numSnowy)
			{
				left = "\"SnowyOwl";
			}
			if(maxClass == print_numBarn)
			{
				left = "\"BarnOwl";
			}
    	return left;
    }
    
    private String[][] classification = new String[2][46];//create string matrix 2X46 for actual/predicted evluation//
    public void test(List<ArrayList<String>> test)//uses test case set created at start//
    {
    	for(int i=0;i<test.size();i++)//loop thorugh test set rows//
    	{	
    		String predicted = null;
    		String actual = test.get(i).get(4);//actual is the actual owl contained in test set at row number x//
    		outerloop://for loop break//
    		for(int j=0;j<set_rules.size();j++)//for number of rules in set_rules arraylist do this//
    		{	//compare valye is value contained at row(i) of attribute set from rule object arraylist//
    		double compare_value = Double.parseDouble(test.get(i).get(set_rules.get(j).getAttribute()));
    			if (compare_value <= set_rules.get(j).getThreshold())//compares against saved threshold from rule object//
    			{//if lower than threshold from rule//
    				predicted = set_rules.get(j).getLeft();//predicted gets from rule object - owl that is saved//
    				System.out.println(predicted + " "+ actual);//print for analysis + debugging//
    				break outerloop;//break if there if this is leaf//
    			}
    			else//else do value is greater than assign to right side//
    			{
    				predicted = set_rules.get(j).getRight();
    				System.out.println(predicted+" " +actual);
    			}
    	}
    		classification[0][i] = actual;//actual represents col 0 in classification matrix
			classification[1][i] = predicted;//predicted represents  col 1 in classificaiton matrix//
    }
    }
    
    public void performance()//for calculating preformance
    {
    	int count_correct = 0;//intialize counter//
    	for(int i =0;i<45;i++)
    	{
    		if(classification[0][i].contains(classification[1][i]))//comapre actual and predicted//
    		{
    			count_correct++;//increment if actual is equal to predicted//
    		}
    	}
    	double class_acc = ((double)count_correct/46)*100;//divide count correct by total set size to get percentage//
    	System.out.println("Classification Accuracy:"+class_acc);//print for analysis//
    }
}
