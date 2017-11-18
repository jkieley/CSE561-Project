package LoadBalancer;

import GenCol.Queue;
import view.modeling.ViewableAtomic;
import view.modeling.ViewableComponent;
import view.modeling.ViewableDigraph;

import java.awt.*;

public class Network extends ViewableDigraph {
	public Network() {
		super("Network");
		int numNodes = 5;
		Queue nodes = new Queue();
		double expTime = 80.0;
		for (int i = 0; i < numNodes; i++) {
			nodes.add(0);
		}
		ViewableAtomic Balancer = new LoadBalancer(nodes);

		add(Balancer);
		addInport("jobIn");
		addOutport("jobOut");
		addCoupling(this, "jobIn", Balancer, "jobIn");
		addCoupling(Balancer, "jobOut", this, "jobOut");
	}

	//=============================================================================================
	public void layoutForSimView() {
		preferredSize = new Dimension(625, 419);
//		((ViewableComponent) withName("Generator")).setPreferredLocation(new Point(4, 38));
//		((ViewableComponent) withName("Transducer")).setPreferredLocation(new Point(58, 223));
	}
}
