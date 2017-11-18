/**
 * DEVS-Suite Simulator
 * Arizona Center for Integrative Modeling & Simulation
 * Arizona State University, Tempe, AZ, USA
 *
 * Author(s): H.S. Sarjoughian & C. Zhang
 */

package LoadBalancer;

import java.awt.Dimension;
import java.awt.Point;
import view.modeling.ViewableComponent;
import view.modeling.ViewableDigraph;

public class EF_Network extends ViewableDigraph 
	{

	public EF_Network() 
		{
		super("EF_Network");
		ViewableDigraph ExpFrame = new ExpFrame();
		ViewableDigraph Network = new Network();
		add(ExpFrame);
		add(Network);
		addOutport("NumCompletedJobs");
		addOutport("NumJobsDropped");
		addOutport("AverageTimeNeeded");
		addCoupling(ExpFrame, "jobOut", Network, "jobIn");
		addCoupling(Network, "jobOut", ExpFrame, "jobIn");
		addCoupling(ExpFrame, "NumCompletedJobs", this, "NumCompletedJobs");
		addCoupling(ExpFrame, "NumJobsDropped", this, "NumJobsDropped");
		addCoupling(ExpFrame, "AverageTimeNeeded", this, "AverageTimeNeeded");
		}
 //=============================================================================================
    public void layoutForSimView()
    	{
        preferredSize = new Dimension(1223, 507);
        ((ViewableComponent)withName("ExpFrame")).setPreferredLocation(new Point(39, 54));
        ((ViewableComponent)withName("Network")).setPreferredLocation(new Point(736, 223));
    	}
	}
