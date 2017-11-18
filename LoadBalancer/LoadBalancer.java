package LoadBalancer;
import GenCol.entity;
import model.modeling.message;
import view.modeling.ViewableAtomic;

import java.util.Vector;

import GenCol.*;


public class LoadBalancer extends ViewableAtomic 
	{
	protected entity job;
	protected Queue nodes;
	int[] nodeConnections;
	
//=============================================================================================
	public LoadBalancer(Queue nodeList) 
		{
		super("LoadBalancer");
		nodes = nodeList;
		nodeConnections = new int[nodes.size()];
		for(int i = 0; i < nodes.size(); i++)
			nodeConnections[i] = (((Node)nodes.getFirst()).waitLength);
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
			node = roundRobin();
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
		public String getTooltipText() 
			{
			return super.getTooltipText() + "\n" + job.getName();
			}
	}
