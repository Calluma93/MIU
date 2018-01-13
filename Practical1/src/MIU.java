import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class MIU {
	
	String substring;
	String s;
	
	public String rule1(String s){
		
		if(s.endsWith("I")){
			s = s.concat("U");
		}
		return s;
	}
	
	public String rule2(String s){

		substring = s.substring(1);
		s = s.concat(substring);
		return s;
	}
	
	public List<String> rule3(String s){
		List<String> states = new ArrayList<String>();
		String temp = "";
		
		for(int i = 0; i < s.length()-2; i++){
			if(s.charAt(i)=='I' && s.charAt(i+1)=='I' && s.charAt(i+2)=='I'){
				temp = s.substring(0, i).concat("U").concat(s.substring(i+3));
				if(!states.contains(temp)){
				states.add(temp);
				}
			}
		}
		
		return states;
	}
	
	public List<String> rule4(String s){
		List<String> states = new ArrayList<String>();
		String temp = "";
		
		for(int i = 0; i < s.length()-1; i++){
			if(s.charAt(i)=='U' && s.charAt(i+1)=='U'){
				temp = s.substring(0, i).concat(s.substring(i+2));
				if(!states.contains(temp)){
				states.add(temp);
				}
			}
		}
		
		return states;
	}
	
	public ArrayList<String> nextstates (String inputString){
		ArrayList<String> state =  new ArrayList<String>();
		
		String rule1state = rule1(inputString);
		String rule2state = rule2(inputString);
		List<String> rule3state = rule3(inputString);
		List<String> rule4state = rule4(inputString);
		
		if(inputString != rule1state){
		state.add(rule1state);
		}
		
		if(inputString != rule2state){
			state.add(rule2state);
			}
		
			state.addAll(rule3state);
			
			state.addAll(rule4state);
			
		
//		for(int i = 0;i < state.size();i++){
//			System.out.println("next-states ("+inputString+") --> "+state.get(i));
//		}
		return state;
	}
	
	public List<List<String>> extendPath(List<String> s){
		List<List<String>> extendPath = new ArrayList<>();
		List<String> output = new ArrayList<String>(s);
		List<String> nextStep = new ArrayList<String>();
		nextStep = nextstates(s.get(s.size()-1));
		
		for(int i = 0; i < nextStep.size(); i++){
			output.add(nextStep.get(i));
			extendPath.add(output);
			output = new ArrayList<String>(s);
			
		}
//		for(int i = 0; i < extendPath.size(); i++){
//			System.out.println("extendPath "+ s + "="+ extendPath.get(i));
//		}
		return extendPath;
	}
	
	public List<String> breadthFirstSearch (String goalString){
		Queue<List<String>> agenda = new LinkedList<>();
		List<String> currentPath = new ArrayList<>();
		List<String> initial = new ArrayList<String>();
		int timesExtendCalled = 0;
		initial.add("MI");
		agenda.add(initial);
		
			while(timesExtendCalled < 1000){		
			currentPath = agenda.remove();
			if(currentPath.get(currentPath.size()-1).equals(goalString)){
				System.out.println("Breadth First");
				System.out.println("Length of the Path: " + currentPath.size());
				System.out.println("Size of the agenda: " + agenda.size());
				System.out.println("Amount of times Extend called: " + timesExtendCalled);
				return currentPath;
			}

				agenda.addAll(extendPath(currentPath));
				timesExtendCalled ++;
				}
			
		currentPath.clear();
		return currentPath;
	}
	
	public List<String> depthLimitedDFS (String goalString, int limit){
		Stack<List<String>> agenda = new Stack<List<String>>();
		List<String> currentPath = new ArrayList<String>();
		List<String> initial = new ArrayList<String>();		
		int timesExtendCalled = 0;
		initial.add("MI");
		agenda.add(initial);
		
			while(!agenda.isEmpty()){		
			currentPath = agenda.pop();

			if(currentPath.get(currentPath.size()-1).equals(goalString)){
				System.out.println("Depth First");
				System.out.println("Length of the Path: " + currentPath.size());
				System.out.println("Size of the agenda: " + agenda.size());
				System.out.println("Amount of times Extend called: " + timesExtendCalled);
				return currentPath;
			}
				if(currentPath.size() < limit){
				List<List<String>> newPath = extendPath(currentPath);
				for(int i = 0; i < newPath.size(); i++){
				agenda.push(newPath.get(i));
				}
				timesExtendCalled++;
				}
			
		}
		System.out.println("Couldn't get to goal within given limit");	
		currentPath.clear();
		return currentPath;
	}

	public List<String> iterativeDeepening (String goalString){
		Stack<List<String>> agenda = new Stack<>();
		List<String> currentPath = new ArrayList<String>();
		List<String> initial = new ArrayList<String>();		
		int limit = 2;
		int timesExtendCalled = 0;
		initial.add("MI");
		agenda.add(initial);
		
			while(!agenda.isEmpty()){		
			currentPath = agenda.pop();
			if(currentPath.get(currentPath.size()-1).equals(goalString)){
				System.out.println("Iterative Deepening");
				System.out.println("Length of the Path: " + currentPath.size());
				System.out.println("Size of the agenda: " + agenda.size());
				System.out.println("Amount of times Extend called: " + timesExtendCalled);
				return currentPath;
			}
				if(currentPath.size() < limit){
				List<List<String>> newPath = extendPath(currentPath);
				for(int i = 0; i < newPath.size(); i++){
				agenda.push(newPath.get(i));
				limit++;
				}
				timesExtendCalled++;
				}
			
		}
		return currentPath;
	}
	
	
	public static void main(String[] args){
		MIU miu = new MIU();
		
		System.out.println(miu.nextstates("MIIIIII")); // Part A
		System.out.println("");
		
		List<String> input = new ArrayList<String>(); //Part B
		input.add("MI"); //Part B
		input.add("MII"); //Part B
		System.out.println(miu.extendPath(input)); //Part B
		System.out.println("");
		
		miu.breadthFirstSearch("MIIII"); //Part C
		System.out.println("");
		
	    miu.depthLimitedDFS("MIUIU", 8);//PartD Part1
		System.out.println("");
		
	    miu.iterativeDeepening("MIUIU");//PartD Part2	
		System.out.println("");

	}
}
