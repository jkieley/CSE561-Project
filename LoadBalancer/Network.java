package LoadBalancer;

import GenCol.Queue;
import view.modeling.ViewableAtomic;
import view.modeling.ViewableComponent;
import view.modeling.ViewableDigraph;

import java.awt.*;

public class Network extends ViewableDigraph {
	public Network(int nodeType, int algorithm) {
		super("Network");
		int numNodes = 5;
		Queue nodes = new Queue();
		double expTime = 80.0;

		ViewableAtomic Balancer = new LoadBalancer(nodes, numNodes, algorithm);

		add(Balancer);
		addInport("jobIn");
		addOutport("jobOut");
		addCoupling(this, "jobIn", Balancer, "jobIn");
		double nodeScale = 2;
		for (int i = 0; i < numNodes; i++) 
			{
			Node node;
			if(nodeType == 0)
				node = new Node(String.valueOf(i), 100,100, 5, 1.0);
			else
				{
				node = new Node(String.valueOf(i), 100,100, 5, nodeScale);
				nodeScale = nodeScale - 0.2;
				}
			nodes.add(node);
			add(node);
			addCoupling(Balancer, "jobOut", node, "jobIn");
			addCoupling(node, "Connections", Balancer, "Connections");
			addCoupling(node, "jobOut", this, "jobOut");
			}

	}

	//=============================================================================================
	public void layoutForSimView() {
		preferredSize = new Dimension(625, 419);
//		((ViewableComponent) withName("Generator")).setPreferredLocation(new Point(4, 38));
//		((ViewableComponent) withName("Transducer")).setPreferredLocation(new Point(58, 223));
	}
}
