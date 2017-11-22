package LoadBalancer;
import GenCol.entity;
import model.modeling.message;
import view.modeling.ViewableAtomic;
import java.util.Random;
import java.util.Vector;

import GenCol.*;


public class LoadBalancer extends ViewableAtomic 
	{
	protected entity job;
	protected Queue nodes;
	Random random;
	int count = 0;
	int[] nodeConnections;
	
//=============================================================================================
	public LoadBalancer(Queue nodeList, int numNodes) 
		{
		super("LoadBalancer");
		nodes = nodeList;
		nodeConnections = new int[numNodes];
		random = new Random();
		for(int i = 0; i < numNodes; i++)
			nodeConnections[i] = 0;
		addInport("jobIn");
		addInport("Connections");
		addOutport("jobOut");
		addTestInput("jobIn", new Job("job0", 5, 5, 10, true));
		}
//=============================================================================================
		public void initialize() 
			{
			passivateIn("Waiting");
			super.initialize();
			}
//=============================================================================================
		public void deltext(double e, message x) 
			{
			Continue(e);
			if (phaseIs("Waiting"))
				{
				for (int i = 0; i < x.getLength(); i++)
					{
					if (messageOnPort(x, "jobIn", i)) 
						{	
						job = x.getValOnPort("jobIn", i);
						((Job)job).setNode(chooseNode());
						holdIn("Delegating",0);
						}
					else if (messageOnPort(x, "Connections", i)) 
						{
						String processor;
						Pair data = (Pair) x.getValOnPort("Connections", i);
						processor = (String)data.key;
						for(int j = 0; j < nodes.size(); j++)
							{
							Node temp = ((Node)nodes.get(j));
							if(temp.getName().equals(processor))
								nodeConnections[j] = (int)data.value;
							}

						}
					}
				}
			}
//=============================================================================================
		public String chooseNode() 
			{
			String node = "";
			//node = roundRobin();
			//node = random();
			//node = weightedRoundRobin();
			node = leastConnection();
			return node;
			}
//=============================================================================================
		public String roundRobin() 
				{
				Node node;
				node = (Node) nodes.remove();	
				nodes.add(node);
				return node.getName();
				}
//=============================================================================================
		public String weightedRoundRobin() 
				{
				Node node;
				if(count == 3 || count == 6 || count == 8 || count == 10 || count == 11)
					{
					node = (Node)nodes.remove();
					nodes.add(node);
					}
				count++;
				count = count % 12;
				node = 	(Node)nodes.first();
				return node.getName();
				}
//=============================================================================================
		public String random() 
				{
				return ((Node)nodes.get(random.nextInt(nodes.size()))).getName();
				}
//=============================================================================================
		public String leastConnection() 
				{
				int minPos = 0;
				int minNum = nodeConnections[0];
				for(int i = 1; i < nodes.size(); i++)
					if(nodeConnections[i] < minNum)
						{
						minPos = i;
						minNum = nodeConnections[i];
						}
				return ((Node)nodes.get(minPos)).getName();
				}
//=============================================================================================
		public void deltint() 
			{
			passivateIn("Waiting");
			}
//=============================================================================================
		public void deltcon(double e, message msg)
			{
			deltint ( );
			deltext (0, msg);
			}
//=============================================================================================
		public message out()
			{
			message m = new message();
			m.add(makeContent("jobOut", job));
			return m;
			}
//=============================================================================================
		public void showState()
			{
			super.showState();
			}
//=============================================================================================
	}
