import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class find_route {
	
	public void uniformCostSearch(ArrayList<InputTypes> input, String startNode, String goalNode) {
		
		SortedMap<Double, String> fringe = new TreeMap<Double, String>();
		List<String> closedSet = new ArrayList<>();
		List<String> nodesGenerated = new ArrayList<>();
		List<String> nodesExpanded = new ArrayList<>();
		List<String> nodesPoped = new ArrayList<>();
		String currentNode = startNode;
		Double currentNodeCost = 0.0;
		Integer srcDepth = 0;
		fringe.put(currentNodeCost, currentNode);
		nodesGenerated.add(currentNode);
		//Determining path 
		List<PathCalc> path = new ArrayList<>();
		while(!currentNode.equals(goalNode)) {
			nodesPoped.add(currentNode);
			fringe.remove(currentNodeCost, currentNode);
			if(!closedSet.contains(currentNode)) {
				closedSet.add(currentNode);
				nodesExpanded.add(currentNode);
				for(InputTypes searchTree: input) {
					if(searchTree.getSource().equals(currentNode)) {
						fringe.put(searchTree.getCost()+currentNodeCost, searchTree.getDestination());
						PathCalc pathCalc = new PathCalc();
						pathCalc.setSrc(currentNode);
						pathCalc.setDest(searchTree.getDestination());
						pathCalc.setCost(searchTree.getCost()+currentNodeCost);
						pathCalc.setSrcDepth(srcDepth);
						pathCalc.setDestDepth(srcDepth+1);
						path.add(pathCalc);
						nodesGenerated.add(searchTree.getDestination());
					}
					if(searchTree.getDestination().equals(currentNode)) {
						fringe.put(searchTree.getCost()+currentNodeCost, searchTree.getSource());
						PathCalc pathCalc = new PathCalc();
						pathCalc.setSrc(currentNode);
						pathCalc.setDest(searchTree.getSource());
						pathCalc.setCost(searchTree.getCost()+currentNodeCost);
						pathCalc.setSrcDepth(srcDepth);
						pathCalc.setDestDepth(srcDepth+1);
						path.add(pathCalc);
						nodesGenerated.add(searchTree.getSource());
					}
				}
			}
			
			if(!fringe.isEmpty()) {
				Map.Entry<Double, String> entry = fringe.entrySet().iterator().next();
				 currentNode = entry.getValue();
				 currentNodeCost = entry.getKey();
				 for(PathCalc calc: path){
					 if(calc.getDest().equals(currentNode)) {
						 srcDepth = calc.getDestDepth();
						 break;
					 }
				 }
				 
			}else {
				System.out.println("Nodes Popped: "+nodesPoped.size()+"\n"
						+ "Nodes Expanded: "+nodesExpanded.size()+"\n"
						+ "Nodes Generated: "+nodesGenerated.size()+"\n"
						+ "Distance: Infinity \n"
						+ "Route:\nNone");
				break;
			}

			
		};
		if(currentNode.equals(goalNode)) {
			nodesPoped.add(currentNode);
		}
		if(!fringe.isEmpty()) {
		
		String currentPathNode = currentNode;
		Integer currentDepth = 0;
		List<PathCalc> pathFinal = new ArrayList<>();
		while(!currentPathNode.equals(startNode)) {
			for(PathCalc calc: path) {
				if(calc.getDest().equals(currentPathNode)) {
					if(calc.getCost().equals(currentNodeCost)) {
						currentPathNode = calc.getSrc();
						currentDepth = calc.getSrcDepth();
						pathFinal.add(calc);
						break;
					}else if(calc.getDestDepth().equals(currentDepth)) {
						currentPathNode = calc.getSrc();
						currentDepth = calc.getSrcDepth();
						pathFinal.add(calc);
						break;
					}
				} 
			}
		};
		System.out.println("Nodes Popped: "+nodesPoped.size()+"\n"
				+ "Nodes Expanded: "+nodesExpanded.size()+"\n"
				+ "Nodes Generated: "+nodesGenerated.size()+"\n"
				+ "Distance: "+currentNodeCost+ " km\nRoute:");
		Double pathCost = 0.0;
		for(int i=pathFinal.size()-1; i>=0;i--) {
			Double res = pathFinal.get(i).getCost()-pathCost;
			System.out.println(pathFinal.get(i).getSrc()+ " to "+pathFinal.get(i).getDest()+ ", " +res+"");
			pathCost = pathFinal.get(i).getCost();
		}
		}
		
	}

	public void aStarSearch(ArrayList<InputTypes> input, String startNode, String goalNode, ArrayList<HeuristicTypes> heuristicFile) {
		
		Map<Double, String> fringe = new TreeMap<Double, String>();
		SortedMap<Double , Map<Double, String>> heuristicFringe = new TreeMap<Double , Map<Double, String>>();
		List<String> closedSet = new ArrayList<>();
		List<String> nodesGenerated = new ArrayList<>();
		List<String> nodesExpanded = new ArrayList<>();
		List<String> nodesPoped = new ArrayList<>();
		//Determining path 
		List<PathCalc> path = new ArrayList<>();
		String currentNode = startNode;
		Double currentNodeCost = 0.0;
		Double currentNodeHeuristicCost = 0.0;
		Integer srcDepth = 0;
		fringe.put(currentNodeCost, currentNode);
		heuristicFringe.put(currentNodeHeuristicCost, fringe);
		nodesGenerated.add(currentNode);
		while(!currentNode.equals(goalNode)) {
			Double nodeHeuristicCost = 0.0;
			nodesPoped.add(currentNode);
			Map<Double, String> fringeLoop;
			heuristicFringe.remove(heuristicFringe.firstKey());
			if(!closedSet.contains(currentNode)) {
				closedSet.add(currentNode);
				nodesExpanded.add(currentNode);
				for(InputTypes searchTree: input) {
					if(searchTree.getSource().equals(currentNode)) {
						for(HeuristicTypes heuristicTypes: heuristicFile) {
							if(heuristicTypes.getNode().equals(searchTree.getDestination())) {
								nodeHeuristicCost = heuristicTypes.getHeuristicCost();
							}
						}
						fringeLoop = new TreeMap<Double, String>();
						fringeLoop.put(searchTree.getCost()+currentNodeCost, searchTree.getDestination());
						heuristicFringe.put(searchTree.getCost()+currentNodeCost+nodeHeuristicCost, fringeLoop);
						PathCalc pathCalc = new PathCalc();
						pathCalc.setSrc(currentNode);
						pathCalc.setDest(searchTree.getDestination());
						pathCalc.setCost(searchTree.getCost()+currentNodeCost);
						pathCalc.setSrcDepth(srcDepth);
						pathCalc.setDestDepth(srcDepth+1);
						path.add(pathCalc);
						nodesGenerated.add(searchTree.getDestination());
					}
					if(searchTree.getDestination().equals(currentNode)) {
						for(HeuristicTypes heuristicTypes: heuristicFile) {
							if(heuristicTypes.getNode().equals(searchTree.getSource())) {
								nodeHeuristicCost = heuristicTypes.getHeuristicCost();
							}
						}
						fringeLoop = new TreeMap<Double, String>();
						fringeLoop.put(searchTree.getCost()+currentNodeCost, searchTree.getSource());
						heuristicFringe.put(searchTree.getCost()+currentNodeCost+nodeHeuristicCost, fringeLoop);
						PathCalc pathCalc = new PathCalc();
						pathCalc.setSrc(currentNode);
						pathCalc.setDest(searchTree.getSource());
						pathCalc.setCost(searchTree.getCost()+currentNodeCost);
						pathCalc.setSrcDepth(srcDepth);
						pathCalc.setDestDepth(srcDepth+1);
						path.add(pathCalc);
						nodesGenerated.add(searchTree.getSource());
					}
				}
			}
			
			if(!heuristicFringe.isEmpty()) {
				Map.Entry<Double , Map<Double, String>> entry = heuristicFringe.entrySet().iterator().next();
				Map.Entry<Double, String> currentNodeValues = entry.getValue().entrySet().iterator().next();
				currentNode = currentNodeValues.getValue();
				currentNodeCost = currentNodeValues.getKey();
				currentNodeHeuristicCost = entry.getKey();
				for(PathCalc calc: path){
					 if(calc.getDest().equals(currentNode)) {
						 srcDepth = calc.getDestDepth();
						 break;
					 }
				 }
			}else {
				System.out.println("Nodes Popped: "+nodesPoped.size()+"\n"
						+ "Nodes Expanded: "+nodesExpanded.size()+"\n"
						+ "Nodes Generated: "+nodesGenerated.size()+"\n"
						+ "Distance: Infinity \n"
						+ "Route:\nNone");
				break;
			}

			
		};
		if(currentNode.equals(goalNode)) {
			nodesPoped.add(currentNode);
		}
		if(!heuristicFringe.isEmpty()) {
			String currentPathNode = currentNode;
			Integer currentDepth = 0;
			List<PathCalc> pathFinal = new ArrayList<>();
			while(!currentPathNode.equals(startNode)) {
				for(PathCalc calc: path) {
					if(calc.getDest().equals(currentPathNode)) {
						if(calc.getCost().equals(currentNodeCost)) {
							currentPathNode = calc.getSrc();
							currentDepth = calc.getSrcDepth();
							pathFinal.add(calc);
							break;
						}else if(calc.getDestDepth().equals(currentDepth)) {
							currentPathNode = calc.getSrc();
							currentDepth = calc.getSrcDepth();
							pathFinal.add(calc);
							break;
						}
					} 
				}
			};
		System.out.println("Nodes Popped: "+nodesPoped.size()+"\n"
				+ "Nodes Expanded: "+nodesExpanded.size()+"\n"
				+ "Nodes Generated: "+nodesGenerated.size()+"\n"
				+ "Distance: "+currentNodeCost+ " km\nRoute:");
		Double pathCost = 0.0;
		for(int i=pathFinal.size()-1; i>=0;i--) {
			Double res = pathFinal.get(i).getCost()-pathCost;
			System.out.println(pathFinal.get(i).getSrc()+ " to "+pathFinal.get(i).getDest()+ ", " +res+"");
			pathCost = pathFinal.get(i).getCost();
		}
		}
	}
	
	public ArrayList<InputTypes> readInputFile(String inputFile) throws FileNotFoundException, IOException, URISyntaxException { 
		File resource = new File(Thread.currentThread().getContextClassLoader().getResource(inputFile).toURI());
		ArrayList<InputTypes> input = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(resource))) {
        String line;
        while((line = reader.readLine()) != null){
        	if(line.contains("END OF INPUT")) {
        		break;
        	}else {
            String[] values = line.split(" ");
            InputTypes inputTypes = new InputTypes();
            inputTypes.setSource(values[0]);
            inputTypes.setDestination(values[1]);
            inputTypes.setCost(Double.parseDouble(values[2]));
            input.add(inputTypes);
        }
    }
		return input;
		}
	}
	
	public ArrayList<HeuristicTypes> readHeuristicFile(String heuristicFile) throws FileNotFoundException, IOException, URISyntaxException { 
		File resource = new File(Thread.currentThread().getContextClassLoader().getResource(heuristicFile).toURI());
		ArrayList<HeuristicTypes> input = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(resource))) {
        String line;
        while((line = reader.readLine()) != null){
        	if(line.contains("END OF INPUT")) {
        		break;
        	}else {
            String[] values = line.split(" ");
            HeuristicTypes heuristicTypes = new HeuristicTypes();
            heuristicTypes.setNode(values[0]);
            heuristicTypes.setHeuristicCost(Double.parseDouble(values[1]));
            input.add(heuristicTypes);
        }
    }
		return input;
		}
	}
	
	public class InputTypes {

		String source;
		String destination;
		Double cost;
		
		public InputTypes() {
			super();
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getDestination() {
			return destination;
		}

		public void setDestination(String destination) {
			this.destination = destination;
		}

		public Double getCost() {
			return cost;
		}

		public void setCost(Double cost) {
			this.cost = cost;
		}
	}
	
	public class PathCalc{
		String src;
		String dest;
		Double cost;
		Integer	srcDepth;
		Integer destDepth;
		public PathCalc() {
			super();
		}
		public String getSrc() {
			return src;
		}
		public void setSrc(String src) {
			this.src = src;
		}
		public String getDest() {
			return dest;
		}
		public void setDest(String dest) {
			this.dest = dest;
		}
		public Double getCost() {
			return cost;
		}
		public void setCost(Double cost) {
			this.cost = cost;
		}
		public Integer getSrcDepth() {
			return srcDepth;
		}
		public void setSrcDepth(Integer srcDepth) {
			this.srcDepth = srcDepth;
		}
		public Integer getDestDepth() {
			return destDepth;
		}
		public void setDestDepth(Integer destDepth) {
			this.destDepth = destDepth;
		}
		
	}
	
	public class HeuristicTypes {

		String node;
		Double heuristicCost;
		public String getNode() {
			return node;
		}
		public void setNode(String node) {
			this.node = node;
		}
		public Double getHeuristicCost() {
			return heuristicCost;
		}
		public void setHeuristicCost(Double heuristicCost) {
			this.heuristicCost = heuristicCost;
		}
		public HeuristicTypes() {
			super();
		}
		
	}
	
	 public static void main(String args[]) throws FileNotFoundException, IOException, URISyntaxException {
		 String inputFile;
		 String source;
		 String destination;
		 String heuristicFile;
		 find_route findRoute = new find_route();
		 if(args.length == 3) {
			 inputFile = args[0];
			 source = args[1];
			 destination = args[2];
			 ArrayList<InputTypes> input = findRoute.readInputFile(inputFile);
			 findRoute.uniformCostSearch(input, source, destination);
		 }else if(args.length == 4) {
			 inputFile = args[0];
			 source = args[1];
			 destination = args[2];
			 heuristicFile = args[3];
			 ArrayList<InputTypes> input = findRoute.readInputFile(inputFile);
			 ArrayList<HeuristicTypes> readHeuristicFile = findRoute.readHeuristicFile(heuristicFile);
			 findRoute.aStarSearch(input, source, destination, readHeuristicFile);
		 }else {
			 System.out.println("Error in arguments");
		 }
	 }
}
